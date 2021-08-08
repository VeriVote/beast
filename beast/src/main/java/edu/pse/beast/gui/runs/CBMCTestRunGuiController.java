package edu.pse.beast.gui.runs;

import java.io.IOException;
import java.util.List;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;

import edu.pse.beast.api.CBMCTestCallback;
import edu.pse.beast.api.descr.c_electiondescription.CElectionDescription;
import edu.pse.beast.api.descr.property_description.PreAndPostConditionsDescription;
import edu.pse.beast.api.savingloading.SavingLoadingInterface;
import edu.pse.beast.api.testrunner.code_files.CBMCCodeFileData;
import edu.pse.beast.api.testrunner.propertycheck.jsonoutput.CBMCJsonMessage;
import edu.pse.beast.api.testrunner.propertycheck.symbolic_vars.CBMCTestRunWithSymbolicVars;
import edu.pse.beast.api.testrunner.threadpool.WorkUnitState;
import edu.pse.beast.gui.testconfigeditor.testconfig.cbmc.CBMCTestConfiguration;
import edu.pse.beast.gui.testconfigeditor.treeview.TestConfigTreeItemSuper;
import edu.pse.beast.gui.workspace.BeastWorkspace;
import edu.pse.beast.gui.workspace.WorkspaceUpdateListener;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CBMCTestRunGuiController implements CBMCTestCallback, WorkspaceUpdateListener {
    private static final String STATE_TEXT = "State: ";
    private static final String LINE_BREAK = "\n";
    private static final double MIN_FONT_SIZE = 4.0;
    private static final double MAX_FONT_SIZE = 30.0;
    private static final int DEFAULT_FONT_SIZE = 12;

    private static final String RUN_OUT_OF_DATE = "Has Changes";
    private static final String SHOW_LOGS = "Show Logs";
    private static final String CBMC_MESSAGES = "CBMC Messages";
    private static final String DELETE = "Delete";
    private static final String PUT_RUN_ON_QUEUE = "Put Run on Queue";
    private static final String STOP = "Stop";
    private static final String SHOW_GENERATED_EXAMPLE = "Show Generated Example";

    private static final String COUNTER_EXAMPLE_FXML =
            "/edu/pse/beast/cbmcCounterExampleGui.fxml";

    @FXML
    private AnchorPane topLevelAnchorPane;
    @FXML
    private Label stateLabel;
    @FXML
    private HBox stateHBox;
    @FXML
    private TextField createdFileTextField;
    @FXML
    private Button openCreatedFileButton;
    @FXML
    private AnchorPane outputAnchorPane;
    @FXML
    private Slider runTextfieldFontSizeSlider;

    private TreeView<TestConfigTreeItemSuper> testConfigTreeView;

    private CodeArea fileContents = new OutputTextElement();
    private CodeArea logs = new OutputTextElement();
    private CodeArea messages = new OutputTextElement();

    private CBMCTestRunWithSymbolicVars run;

    private BeastWorkspace beastWorkspace;

    private double currentDisplayFontSize = DEFAULT_FONT_SIZE;

    private CounterExampleGuiController counterExampleGuiController =
            new CounterExampleGuiController();
    final FXMLLoader counterExampleLoader =
            new FXMLLoader(getClass().getResource(COUNTER_EXAMPLE_FXML));

    public CBMCTestRunGuiController(final BeastWorkspace workspace,
                                    final TreeView<TestConfigTreeItemSuper> configTreeView)
            throws IOException {
        this.beastWorkspace = workspace;
        workspace.registerUpdateListener(this);
        this.testConfigTreeView = configTreeView;
        counterExampleLoader.setController(counterExampleGuiController);
        counterExampleLoader.load();
    }

    private WorkUnitState prepareWorkUnitState(final CBMCTestRunWithSymbolicVars testRun,
                                               final Label workUnitStateLabel,
                                               final Button openFileButton,
                                               final HBox hBox,
                                               final TextField textField,
                                               final CodeArea contents) {
        outputAnchorPane.getChildren().clear();
        testConfigTreeView.refresh();

        // created file controls
        final CBMCCodeFileData cbmcFile = testRun.getCbmcCodeFile();
        textField.setText(cbmcFile.getFile().getAbsolutePath());
        openFileButton.setOnAction(e -> {
            displayCodeArea(contents);
        });

        final WorkUnitState state = testRun.getState();
        hBox.getChildren().clear();
        workUnitStateLabel.setText(STATE_TEXT + testRun.getStatusString() + " ");
        hBox.getChildren().add(workUnitStateLabel);

        if (testRun.isDescrChanged() || testRun.isPropDescrChanged()) {
            final Label descrChangedLabel = new Label(RUN_OUT_OF_DATE);
            hBox.getChildren().add(descrChangedLabel);
        }
        return state;
    }

    private void displayCounterExample(final CBMCTestRunWithSymbolicVars testRun, final HBox hBox,
                                       final CounterExampleGuiController controller) {
        if (testRun.getJsonOutputHandler().didCBMCFindExample()) {
            final Button showExampleButton = new Button(SHOW_GENERATED_EXAMPLE);
            showExampleButton.setOnAction(e -> {
                final AnchorPane pane =
                        controller.display(testRun.getJsonOutputHandler().getGeneratedExample());
                displayNode(pane);
            });
            hBox.getChildren().add(showExampleButton);
        }
    }

    public final void display(final CBMCTestRunWithSymbolicVars cbmcRun) {
        if (this.run != null) {
            this.run.setCb(null);
        }
        this.run = cbmcRun;
        this.run.setCb(this);

        String code = "";
        try {
            code = SavingLoadingInterface.readStringFromFile(cbmcRun.getCbmcCodeFile().getFile());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        fileContents.clear();
        fileContents.insertText(0, code);

        messages.clear();
        for (final CBMCJsonMessage m : cbmcRun.getMessagesAsList()) {
            messages.appendText(m.toString() + LINE_BREAK);
        }

        logs.clear();
        logs.appendText(cbmcRun.getTestOutput());
        display();
    }

    private void display() {
        Platform.runLater(() -> {
            if (run == null) {
                return;
            }
            final WorkUnitState state =
                    prepareWorkUnitState(run, stateLabel, openCreatedFileButton, stateHBox,
                                         createdFileTextField, fileContents);

            final Button showLogsButton = new Button(SHOW_LOGS);
            showLogsButton.setOnAction(e -> {
                displayCodeArea(logs);
            });

            final Button showMessagesButton = new Button(CBMC_MESSAGES);
            showMessagesButton.setOnAction(e -> {
                displayCodeArea(messages);
            });

            final Button deleteButton = new Button(DELETE);
            deleteButton.setOnAction(e -> {
                beastWorkspace.deleteCBMCRun(run);
            });

            switch (state) {
            case INITIALIZED:
                final Button putRunOnQueueButton = new Button(PUT_RUN_ON_QUEUE);
                putRunOnQueueButton.setOnAction(e -> {
                    beastWorkspace.addRunToQueue(run);
                });
                stateHBox.getChildren().addAll(List.of(putRunOnQueueButton, deleteButton));
                break;
            case ON_QUEUE:
                break;
            case RUNNING:
                final Button stopCBMCButton = new Button(STOP);
                stopCBMCButton.setOnAction(e -> {
                    beastWorkspace.stopRun(run);
                });
                stateHBox.getChildren().addAll(
                        List.of(showLogsButton, showMessagesButton, stopCBMCButton));
                break;
            case FINISHED:
                displayCounterExample(run, stateHBox, counterExampleGuiController);
                stateHBox.getChildren().addAll(
                        List.of(showLogsButton, showMessagesButton, deleteButton));
                break;
            case STOPPED:
                final Button putRunOnQueueButton2 = new Button(PUT_RUN_ON_QUEUE);
                putRunOnQueueButton2.setOnAction(e -> {
                    beastWorkspace.addRunToQueue(run);
                });
                stateHBox.getChildren().addAll(List.of(putRunOnQueueButton2, deleteButton));
                break;
            default:
                break;
            }
        });
    }

    @Override
    public final void onPropertyTestStart(final CElectionDescription description,
                                          final PreAndPostConditionsDescription propertyDescr,
                                          final BoundValues bounds,
                                          final String uuid) {
        display();
    }

    @Override
    public final void onPropertyTestFinished(final CElectionDescription description,
                                             final PreAndPostConditionsDescription propertyDescr,
                                             final BoundValues bounds,
                                             final String uuid) {
        display();
    }

    @Override
    public final void onPropertyTestStopped(final CElectionDescription descr,
                                            final PreAndPostConditionsDescription propertyDescr,
                                            final BoundValues bounds,
                                            final String uuid) {
        display();
    }

    @Override
    public final void onPropertyTestRawOutput(final String sessionUUID,
                                              final CElectionDescription description,
                                              final PreAndPostConditionsDescription propertyDescr,
                                              final BoundValues bounds,
                                              final String uuid, final String output) {
        Platform.runLater(() -> {
            logs.appendText(output + LINE_BREAK);
        });
    }

    @Override
    public final void onPropertyTestRawOutputComplete(final CElectionDescription description,
                                                      final PreAndPostConditionsDescription
                                                              propertyDescr,
                                                      final BoundValues bounds,
                                                      final String uuid,
                                                      final List<String> cbmcOutput) {
    }

    @Override
    public final void onNewCBMCMessage(final CBMCJsonMessage msg) {
        Platform.runLater(() -> {
            messages.appendText(msg.toString() + LINE_BREAK);
        });
    }

    public final AnchorPane getTopLevelAnchorPane() {
        return topLevelAnchorPane;
    }

    private void displayCodeArea(final CodeArea codeArea) {
        final VirtualizedScrollPane<CodeArea> vsp =
                new VirtualizedScrollPane<CodeArea>(codeArea);
        displayNode(vsp);
    }

    private void displayNode(final Node n) {
        outputAnchorPane.getChildren().clear();
        outputAnchorPane.getChildren().add(n);
        AnchorPane.setTopAnchor(n, 0d);
        AnchorPane.setBottomAnchor(n, 0d);
        AnchorPane.setLeftAnchor(n, 0d);
        AnchorPane.setRightAnchor(n, 0d);
    }

    @FXML
    public final void initialize() {
        createdFileTextField.setEditable(false);
        runTextfieldFontSizeSlider.setShowTickMarks(true);
        runTextfieldFontSizeSlider.setMin(MIN_FONT_SIZE);
        runTextfieldFontSizeSlider.setMax(MAX_FONT_SIZE);
        runTextfieldFontSizeSlider.setValue(currentDisplayFontSize);
        runTextfieldFontSizeSlider.valueProperty().addListener((ob, o, n) -> {
            final double displayFontSize = Math.round((double) n * 100) / 100;
            final String styleString =
                    "-fx-font-size: " + displayFontSize + "px;";
            logs.setStyle(styleString);
            messages.setStyle(styleString);
        });
    }

    @Override
    public final void handleCBMConfigUpdatedFiles(final CBMCTestConfiguration currentConfig) {
        display();
    }
}
