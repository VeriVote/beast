package edu.kit.kastel.formal.beast.gui.run;

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

import edu.kit.kastel.formal.beast.api.PropertyCheckCallback;
import edu.kit.kastel.formal.beast.api.io.InputOutputInterface;
import edu.kit.kastel.formal.beast.api.method.CElectionDescription;
import edu.kit.kastel.formal.beast.api.property.PropertyDescription;
import edu.kit.kastel.formal.beast.api.runner.codefile.CodeFileData;
import edu.kit.kastel.formal.beast.api.runner.propertycheck.output.JSONMessage;
import edu.kit.kastel.formal.beast.api.runner.propertycheck.run.PropertyCheckRun;
import edu.kit.kastel.formal.beast.api.runner.threadpool.WorkUnitState;
import edu.kit.kastel.formal.beast.gui.configurationeditor.configuration.cbmc.Configuration;
import edu.kit.kastel.formal.beast.gui.configurationeditor.treeview.ConfigurationItem;
import edu.kit.kastel.formal.beast.gui.workspace.BeastWorkspace;
import edu.kit.kastel.formal.beast.gui.workspace.WorkspaceUpdateListener;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class RunGUIController implements PropertyCheckCallback, WorkspaceUpdateListener {
    private static final String STATE_TEXT = "State: ";
    private static final String LINE_BREAK = "\n";
    private static final String SPACE = " ";
    private static final double MIN_FONT_SIZE = 4.0;
    private static final double MAX_FONT_SIZE = 30.0;
    private static final int DEFAULT_FONT_SIZE = 12;

    private static final String CODE_FILE_MSG = "Display Source Code";
    private static final String CODE_FILE_BUTTON = "far-file-code";
    private static final String HIDE = "Hide";
    private static final String HIDE_BUTTON = "far-eye-slash";
    private static final String SHOW_MESSAGES_BUTTON = "far-envelope-open";
    private static final String RUN_OUT_OF_DATE = "Has Changes";
    private static final String SHOW_LOGS = "Show Logs";
    private static final String SHOW_LOGS_BUTTON = "far-comments";
    private static final String OUTPUT_MESSAGES = "Output Messages";
    private static final String DELETE = "Delete";
    private static final String PUT_RUN_ON_QUEUE = "Start Check";
    private static final String PLAY_BUTTON = "far-play-circle";
    private static final String STOP = "Stop";
    private static final String SHOW_GENERATED_EXAMPLE = "Show Generated Example";
    private static final String SHOW_EXAMPLE_BUTTON = "far-file-alt";

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

    private String firstPart(final String s) {
        return s == null ? "" : s.trim().split(SPACE)[0];
    }

    private void setButton(final Button button, final String showTooltip, final String showIcon) {
        button.setGraphic(new FontIcon(showIcon));
        button.setText(firstPart(showTooltip));
        button.setTooltip(new Tooltip(showTooltip));
    }

    private Button createButton(final String showTooltip, final String showIcon) {
        final Button button = new Button();
        setButton(button, showTooltip, showIcon);
        return button;
    }

    private void switchIcon(final Button button, final String showIcon, final String hideIcon) {
        final Node n = button.graphicProperty().getValue();
        if (n instanceof FontIcon) {
            final FontIcon icon = (FontIcon) n;
            if (icon.getIconLiteral().equalsIgnoreCase(showIcon)) {
                icon.setIconLiteral(hideIcon);
            } else if (icon.getIconLiteral().equalsIgnoreCase(hideIcon)) {
                icon.setIconLiteral(showIcon);
            }
        }
    }

    private void switchTooltip(final Button button,
                               final String showTooltip, final String hideTooltip) {
        if (button.getTooltip().getText().equalsIgnoreCase(showTooltip)) {
            button.getTooltip().setText(hideTooltip);
            button.setText(firstPart(hideTooltip));
        } else if (button.getTooltip().getText().equalsIgnoreCase(hideTooltip)) {
            button.getTooltip().setText(showTooltip);
            button.setText(firstPart(showTooltip));
        }
    }

    private void displayNode(final Node n, final boolean show) {
        outputAnchorPane.getChildren().clear();
        if (n != null && show) {
            outputAnchorPane.getChildren().add(n);
            AnchorPane.setTopAnchor(n, 0d);
            AnchorPane.setBottomAnchor(n, 0d);
            AnchorPane.setLeftAnchor(n, 0d);
            AnchorPane.setRightAnchor(n, 0d);
        }
    }

    private void hideOrDisplayCodeArea(final CodeArea codeArea, final boolean show) {
        final VirtualizedScrollPane<CodeArea> vsp =
                codeArea != null ? new VirtualizedScrollPane<CodeArea>(codeArea) : null;
        displayNode(vsp, show);
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
        setButton(openFileButton, CODE_FILE_MSG, CODE_FILE_BUTTON);
        openFileButton.setOnAction(e -> {
            final boolean show = !openFileButton.getTooltip().getText().startsWith(HIDE);
            hideOrDisplayCodeArea(contents, show);
            switchIcon(openFileButton, CODE_FILE_BUTTON, HIDE_BUTTON);
            switchTooltip(openFileButton, CODE_FILE_MSG, "Hide Source Code");
        });

        final WorkUnitState state = checkRun.getState();
        hBox.getChildren().clear();
        workUnitStateLabel.setText(STATE_TEXT + checkRun.getStatusString() + SPACE);
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
            final Button showExampleButton =
                    createButton(SHOW_GENERATED_EXAMPLE, SHOW_EXAMPLE_BUTTON);
            showExampleButton.setOnAction(e -> {
                final boolean show = !showExampleButton.getTooltip().getText().startsWith(HIDE);
                final AnchorPane pane =
                        controller.display(checkRun.getJsonOutputHandler().getGeneratedExample());
                displayNode(pane, show);
                switchIcon(showExampleButton, SHOW_EXAMPLE_BUTTON, HIDE_BUTTON);
                switchTooltip(showExampleButton, SHOW_GENERATED_EXAMPLE, "Hide Generated Example");
            });
            hBox.getChildren().add(showExampleButton);
        }
    }

    private HorizontalButtons initHorizontalButtons() {
        final Button showLogsButton = createButton(SHOW_LOGS, SHOW_LOGS_BUTTON);
        showLogsButton.setOnAction(e -> {
            final boolean show = !showLogsButton.getTooltip().getText().startsWith(HIDE);
            hideOrDisplayCodeArea(logs, show);
            switchIcon(showLogsButton, SHOW_LOGS_BUTTON, HIDE_BUTTON);
            switchTooltip(showLogsButton, SHOW_LOGS, "Hide Logs");
        });

        final Button showMessagesButton = createButton(OUTPUT_MESSAGES, SHOW_MESSAGES_BUTTON);
        showMessagesButton.setOnAction(e -> {
            final boolean show = !showMessagesButton.getTooltip().getText().startsWith(HIDE);
            hideOrDisplayCodeArea(messages, show);
            switchIcon(showMessagesButton, SHOW_MESSAGES_BUTTON, HIDE_BUTTON);
            switchTooltip(showMessagesButton, OUTPUT_MESSAGES, "Hide Messages");
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
                final Button putRunOnQueueButton = createButton(PUT_RUN_ON_QUEUE, PLAY_BUTTON);
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
                final Button putRunOnQueueButton2 = createButton(PUT_RUN_ON_QUEUE, PLAY_BUTTON);
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
