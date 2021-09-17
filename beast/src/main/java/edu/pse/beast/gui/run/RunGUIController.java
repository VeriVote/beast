package edu.pse.beast.gui.run;

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
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.kordamp.ikonli.javafx.FontIcon;

import edu.pse.beast.api.PropertyCheckCallback;
import edu.pse.beast.api.io.InputOutputInterface;
import edu.pse.beast.api.method.CElectionDescription;
import edu.pse.beast.api.property.PropertyDescription;
import edu.pse.beast.api.runner.codefile.CodeFileData;
import edu.pse.beast.api.runner.propertycheck.output.JSONMessage;
import edu.pse.beast.api.runner.propertycheck.run.PropertyCheckRun;
import edu.pse.beast.api.runner.threadpool.WorkUnitState;
import edu.pse.beast.gui.configurationeditor.configuration.cbmc.Configuration;
import edu.pse.beast.gui.configurationeditor.treeview.ConfigurationItem;
import edu.pse.beast.gui.workspace.BeastWorkspace;
import edu.pse.beast.gui.workspace.WorkspaceUpdateListener;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class RunGUIController implements PropertyCheckCallback, WorkspaceUpdateListener {
    private static final String STATE_TEXT = "State: ";
    private static final String LINE_BREAK = "\n";
    private static final double MIN_FONT_SIZE = 4.0;
    private static final double MAX_FONT_SIZE = 30.0;
    private static final int DEFAULT_FONT_SIZE = 12;

    private static final String HIDE = "far-eye-slash";
    private static final String RUN_OUT_OF_DATE = "Has Changes";
    private static final String SHOW_LOGS = "Show Logs";
    private static final String OUTPUT_MESSAGES = "Output Messages";
    private static final String DELETE = "Delete";
    private static final String PUT_RUN_ON_QUEUE = "Start Check";
    private static final String PLAY = "far-play-circle";
    private static final String STOP = "Stop";
    private static final String SHOW_GENERATED_EXAMPLE = "Show Generated Example";

    private static final String COUNTER_EXAMPLE_FXML = "counterexample.fxml";

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

    private TreeView<ConfigurationItem> configurationTreeView;

    private CodeArea fileContents = new OutputTextElement();
    private CodeArea logs = new OutputTextElement();
    private CodeArea messages = new OutputTextElement();

    private PropertyCheckRun run;

    private BeastWorkspace beastWorkspace;

    private double currentDisplayFontSize = DEFAULT_FONT_SIZE;

    private CounterExampleGuiController counterExampleGuiController =
            new CounterExampleGuiController();
    final FXMLLoader counterExampleLoader =
            new FXMLLoader(getClass().getResource(COUNTER_EXAMPLE_FXML));

    public RunGUIController(final BeastWorkspace workspace,
                            final TreeView<ConfigurationItem> configTreeView) throws IOException {
        this.beastWorkspace = workspace;
        workspace.registerUpdateListener(this);
        this.configurationTreeView = configTreeView;
        counterExampleLoader.setController(counterExampleGuiController);
        counterExampleLoader.load();
    }

    private void setButton(final Button button, final String tooltip, final String icon) {
        button.setTooltip(new Tooltip(tooltip));
        button.setGraphic(new FontIcon(icon));
    }

    private Button createButton(final String tooltip, final String icon) {
        final Button button = new Button();
        setButton(button, tooltip, icon);
        return button;
    }

    private WorkUnitState prepareWorkUnitState(final PropertyCheckRun checkRun,
                                               final Label workUnitStateLabel,
                                               final Button openFileButton,
                                               final HBox hBox,
                                               final TextField textField,
                                               final CodeArea contents) {
        outputAnchorPane.getChildren().clear();
        configurationTreeView.refresh();

        // created file controls
        final CodeFileData codeFile = checkRun.getCodeFile();
        textField.setText(codeFile.getFile().getAbsolutePath());
        openFileButton.setOnAction(e -> {
            hideOrDisplayCodeArea(contents);
            final boolean empty = outputAnchorPane.getChildren().isEmpty();
            setButton(openFileButton,
                      empty ? "Display Source Code" : "Hide Source Code",
                      empty ? "far-file-code" : HIDE);
        });

        final WorkUnitState state = checkRun.getState();
        hBox.getChildren().clear();
        workUnitStateLabel.setText(STATE_TEXT + checkRun.getStatusString() + " ");
        hBox.getChildren().add(workUnitStateLabel);

        if (checkRun.isDescrChanged() || checkRun.isPropDescrChanged()) {
            final Label descrChangedLabel = new Label(RUN_OUT_OF_DATE);
            hBox.getChildren().add(descrChangedLabel);
        }
        return state;
    }

    private void addHButtons(final Button... buttons) {
        stateHBox.getChildren().addAll(List.of(buttons));
    }

    private void displayCounterExample(final PropertyCheckRun checkRun, final HBox hBox,
                                       final CounterExampleGuiController controller) {
        if (checkRun.getJsonOutputHandler().didFindCounterExample()) {
            final Button showExampleButton = new Button();
            showExampleButton.setOnAction(e -> {
                final AnchorPane pane =
                        controller.display(checkRun.getJsonOutputHandler().getGeneratedExample());
                displayNode(pane);
                final boolean empty = outputAnchorPane.getChildren().isEmpty();
                setButton(showExampleButton,
                          empty ? SHOW_GENERATED_EXAMPLE : "Hide Generated Example",
                          empty ? "far-file-alt" : HIDE);
            });
            hBox.getChildren().add(showExampleButton);
        }
    }

    private HorizontalButtons initHorizontalButtons() {
        final Button showLogsButton = new Button();
        showLogsButton.setOnAction(e -> {
            hideOrDisplayCodeArea(logs);
            final boolean empty = outputAnchorPane.getChildren().isEmpty();
            setButton(showLogsButton,
                      empty ? SHOW_LOGS : "Hide Logs",
                      empty ? "far-comments" : HIDE);
        });

        final Button showMessagesButton = new Button();
        showMessagesButton.setOnAction(e -> {
            hideOrDisplayCodeArea(messages);
            final boolean empty = outputAnchorPane.getChildren().isEmpty();
            setButton(showMessagesButton,
                      empty ? OUTPUT_MESSAGES : "Hide Messages",
                      empty ? "far-envelope-open" : HIDE);
        });

        final Button deleteButton = createButton(DELETE, "fas-times");
        deleteButton.setOnAction(e -> {
            beastWorkspace.deleteCBMCRun(run);
        });

        return new HorizontalButtons(showLogsButton, showMessagesButton, deleteButton);
    }

    private void display() {
        Platform.runLater(() -> {
            if (run == null) {
                return;
            }
            final WorkUnitState state =
                    prepareWorkUnitState(run, stateLabel, openCreatedFileButton, stateHBox,
                                         createdFileTextField, fileContents);
            final HorizontalButtons buttons = initHorizontalButtons();

            switch (state) {
            case INITIALIZED:
                final Button putRunOnQueueButton = createButton(PUT_RUN_ON_QUEUE, PLAY);
                putRunOnQueueButton.setOnAction(e -> {
                    beastWorkspace.addRunToQueue(run);
                });
                addHButtons(putRunOnQueueButton, buttons.delete);
                break;
            case ON_QUEUE:
                break;
            case RUNNING:
                final Button stopCBMCButton = createButton(STOP, "far-stop-circle");
                stopCBMCButton.setOnAction(e -> {
                    beastWorkspace.stopRun(run);
                });
                addHButtons(buttons.showLogs, buttons.showMessages, stopCBMCButton);
                break;
            case FINISHED:
                displayCounterExample(run, stateHBox, counterExampleGuiController);
                addHButtons(buttons.showLogs, buttons.showMessages, buttons.delete);
                break;
            case STOPPED:
                final Button putRunOnQueueButton2 = createButton(PUT_RUN_ON_QUEUE, PLAY);
                putRunOnQueueButton2.setOnAction(e -> {
                    beastWorkspace.addRunToQueue(run);
                });
                addHButtons(putRunOnQueueButton2, buttons.delete);
                break;
            default:
                break;
            }
        });
    }

    public final void display(final PropertyCheckRun checkRun) {
        if (this.run != null) {
            this.run.setCb(null);
        }
        this.run = checkRun;
        this.run.setCb(this);

        String code = "";
        try {
            code = InputOutputInterface.readStringFromFile(checkRun.getCodeFile().getFile());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        fileContents.clear();
        fileContents.insertText(0, code);

        messages.clear();
        for (final JSONMessage m : checkRun.getMessagesAsList()) {
            messages.appendText(m.toString() + LINE_BREAK);
        }

        logs.clear();
        logs.appendText(checkRun.getOutput());
        display();
    }

    @Override
    public final void onPropertyCheckStart(final CElectionDescription description,
                                          final PropertyDescription propertyDescr,
                                          final BoundValues bounds,
                                          final String uuid) {
        display();
    }

    @Override
    public final void onPropertyCheckFinished(final CElectionDescription description,
                                             final PropertyDescription propertyDescr,
                                             final BoundValues bounds,
                                             final String uuid) {
        display();
    }

    @Override
    public final void onPropertyCheckStopped(final CElectionDescription descr,
                                            final PropertyDescription propertyDescr,
                                            final BoundValues bounds,
                                            final String uuid) {
        display();
    }

    @Override
    public final void onPropertyCheckRawOutput(final String sessionUUID,
                                              final CElectionDescription description,
                                              final PropertyDescription propertyDescr,
                                              final BoundValues bounds,
                                              final String uuid, final String output) {
        Platform.runLater(() -> {
            logs.appendText(output + LINE_BREAK);
        });
    }

    @Override
    public final void onPropertyCheckRawOutputComplete(final CElectionDescription description,
                                                      final PropertyDescription propertyDescr,
                                                      final BoundValues bounds,
                                                      final String uuid,
                                                      final List<String> output) {
    }

    @Override
    public final void onNewCBMCMessage(final JSONMessage msg) {
        Platform.runLater(() -> {
            messages.appendText(msg.toString() + LINE_BREAK);
        });
    }

    public final AnchorPane getTopLevelAnchorPane() {
        return topLevelAnchorPane;
    }

    private void hideOrDisplayCodeArea(final CodeArea codeArea) {
        final VirtualizedScrollPane<CodeArea> vsp =
                codeArea != null ? new VirtualizedScrollPane<CodeArea>(codeArea) : null;
        displayNode(vsp);
    }

    private void displayNode(final Node n) {
        outputAnchorPane.getChildren().clear();
        if (n != null) {
            outputAnchorPane.getChildren().add(n);
            AnchorPane.setTopAnchor(n, 0d);
            AnchorPane.setBottomAnchor(n, 0d);
            AnchorPane.setLeftAnchor(n, 0d);
            AnchorPane.setRightAnchor(n, 0d);
        }
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
    public final void handleConfigurationUpdatedFiles(final Configuration currentConfig) {
        display();
    }

    /**
     * TODO: Write documentation.
     *
     * @author Michael Kirsten
     *
     */
    public static final class HorizontalButtons {
        final Button showLogs;
        final Button showMessages;
        final Button delete;

        public HorizontalButtons(final Button showLogsButton,
                                 final Button showMessagesButton,
                                 final Button deleteButton) {
            this.showLogs = showLogsButton;
            this.showMessages = showMessagesButton;
            this.delete = deleteButton;
        }
    }
}
