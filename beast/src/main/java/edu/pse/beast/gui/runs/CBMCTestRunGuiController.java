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

public class CBMCTestRunGuiController implements CBMCTestCallback, WorkspaceUpdateListener {
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

    private double currentDisplayFontSize = 12;

    private CounterExampleGuiController counterExampleGuiController =
            new CounterExampleGuiController();
    private final String counterExampleFXML = "/edu/pse/beast/cbmcCounterExampleGui.fxml";
    final FXMLLoader counterExampleLoader =
            new FXMLLoader(getClass().getResource(counterExampleFXML));

    public CBMCTestRunGuiController(final BeastWorkspace workspace,
                                    final TreeView<TestConfigTreeItemSuper> configTreeView)
            throws IOException {
        this.beastWorkspace = workspace;
        workspace.registerUpdateListener(this);
        this.testConfigTreeView = configTreeView;
        counterExampleLoader.setController(counterExampleGuiController);
        counterExampleLoader.load();
    }

    public void display(final CBMCTestRunWithSymbolicVars cbmcRun) {
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
            messages.appendText(m.toString() + "\n");
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
            outputAnchorPane.getChildren().clear();
            testConfigTreeView.refresh();

            // created file controls
            final CBMCCodeFileData cbmcFile = run.getCbmcCodeFile();
            createdFileTextField.setText(cbmcFile.getFile().getAbsolutePath());
            openCreatedFileButton.setOnAction(e -> {
                displayCodeArea(fileContents);
            });

            final WorkUnitState state = run.getState();
            stateHBox.getChildren().clear();
            stateLabel.setText("state: " + run.getStatusString());
            stateHBox.getChildren().add(stateLabel);

            if (run.isDescrChanged() || run.isPropDescrChanged()) {
                final Label descrChangedLabel =
                        new Label("Run out of date, has changes");
                stateHBox.getChildren().add(descrChangedLabel);
            }

            final Button showLogsButton = new Button("show logs");
            showLogsButton.setOnAction(e -> {
                displayCodeArea(logs);
            });

            final Button showMessagesButton = new Button("CBMC Messages");
            showMessagesButton.setOnAction(e -> {
                displayCodeArea(messages);
            });

            final Button deleteButton = new Button("delete");
            deleteButton.setOnAction(e -> {
                beastWorkspace.deleteCBMCRun(run);
            });

            switch (state) {
            case INITIALIZED:
                final Button putRunOnQueueButton = new Button("put Run on Queue");
                putRunOnQueueButton.setOnAction(e -> {
                    beastWorkspace.addRunToQueue(run);
                });
                stateHBox.getChildren().addAll(List.of(putRunOnQueueButton, deleteButton));
                break;
            case ON_QUEUE:
                break;
            case WORKED_ON:
                final Button stopCBMCButton = new Button("stop");
                stopCBMCButton.setOnAction(e -> {
                    beastWorkspace.stopRun(run);
                });
                stateHBox.getChildren().add(showLogsButton);
                stateHBox.getChildren().add(showMessagesButton);
                stateHBox.getChildren().add(stopCBMCButton);
                break;
            case FINISHED:
                if (run.getJsonOutputHandler().didCBMCFindExample()) {
                    final Button showExampleButton = new Button("show generated Example");
                    showExampleButton.setOnAction(e -> {
                        final AnchorPane pane =
                                counterExampleGuiController
                                .display(run.getJsonOutputHandler().getGeneratedExample());
                        displayNode(pane);
                    });
                    stateHBox.getChildren().add(showExampleButton);
                }
                stateHBox.getChildren().add(showLogsButton);
                stateHBox.getChildren().add(showMessagesButton);
                stateHBox.getChildren().add(deleteButton);
                break;
            case STOPPED:
                final Button putRunOnQueueButton = new Button("put Run on Queue");
                putRunOnQueueButton.setOnAction(e -> {
                    beastWorkspace.addRunToQueue(run);
                });
                stateHBox.getChildren().add(putRunOnQueueButton);
                stateHBox.getChildren().add(deleteButton);
                break;
            default:
                break;
            }
        });
    }

    @Override
    public void onPropertyTestStart(final CElectionDescription description,
                                    final PreAndPostConditionsDescription propertyDescr,
                                    final int s, final int c, final int v,
                                    final String uuid) {
        display();
    }

    @Override
    public void onPropertyTestFinished(final CElectionDescription description,
                                       final PreAndPostConditionsDescription propertyDescr,
                                       final int s, final int c, final int v,
                                       final String uuid) {
        display();
    }

    @Override
    public void onPropertyTestStopped(final CElectionDescription descr,
                                      final PreAndPostConditionsDescription propertyDescr,
                                      final int s, final int c, final int v,
                                      final String uuid) {
        display();
    }

    @Override
    public void onPropertyTestRawOutput(final String sessionUUID,
                                        final CElectionDescription description,
                                        final PreAndPostConditionsDescription propertyDescr,
                                        final int s, final int c, final int v,
                                        final String uuid, final String output) {
        Platform.runLater(() -> {
            logs.appendText(output + "\n");
        });
    }

    @Override
    public void onPropertyTestRawOutputComplete(final CElectionDescription description,
                                                final PreAndPostConditionsDescription propertyDescr,
                                                final int s, final int c, final int v,
                                                final String uuid,
                                                final List<String> cbmcOutput) {
    }

    @Override
    public void onNewCBMCMessage(final CBMCJsonMessage msg) {
        Platform.runLater(() -> {
            messages.appendText(msg.toString() + "\n");
        });
    }

    public AnchorPane getTopLevelAnchorPane() {
        return topLevelAnchorPane;
    }

    private void displayCodeArea(final CodeArea codeArea) {
        final VirtualizedScrollPane<CodeArea> vsp =
                new VirtualizedScrollPane<>(codeArea);
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
    public void initialize() {
        createdFileTextField.setEditable(false);
        runTextfieldFontSizeSlider.setShowTickMarks(true);
        runTextfieldFontSizeSlider.setMin(4.0);
        runTextfieldFontSizeSlider.setMax(30.0);
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
    public void handleCBMConfigUpdatedFiles(final CBMCTestConfiguration currentConfig) {
        display();
    }
}
