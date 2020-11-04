package edu.pse.beast.highlevel.javafx;

import java.awt.MouseInfo;
import java.awt.Point;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.wellbehaved.event.InputMap;
import org.fxmisc.wellbehaved.event.Nodes;

import com.google.gson.JsonSyntaxException;

import edu.pse.beast.codeareajavafx.AutoCompleter;
import edu.pse.beast.codeareajavafx.BoundedVarCodeArea;
import edu.pse.beast.codeareajavafx.NewCodeArea;
import edu.pse.beast.codeareajavafx.NewPropertyCodeArea;
import edu.pse.beast.codeareajavafx.SaverLoader;
import edu.pse.beast.datatypes.electioncheckparameter.ElectionCheckParameter;
import edu.pse.beast.datatypes.electioncheckparameter.TimeOut;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.electionsimulator.ElectionSimulation;
import edu.pse.beast.highlevel.BEASTCommunicator;
import edu.pse.beast.highlevel.javafx.resultpresenter.ResultPresenterNEW;
import edu.pse.beast.highlevel.javafx.resultpresenter.resultTypes.ResultPresentationType;
import edu.pse.beast.options.OptionsNew;
import edu.pse.beast.propertychecker.Result;
import edu.pse.beast.saverloader.ChildTreeItemSaverLoader;
import edu.pse.beast.saverloader.MinimalSaverInterface;
import edu.pse.beast.toolbox.SuperFolderFinder;
import edu.pse.beast.toolbox.Tuple3;
import edu.pse.beast.types.InputType;
import edu.pse.beast.types.InternalTypeContainer;
import edu.pse.beast.types.InternalTypeRep;
import edu.pse.beast.types.OutputType;

/**
 * The Class GUIController.
 *
 * @author Lukas Stapelbroek
 */
public class GUIController {
    /** The Constant NEW_PROPERTY_STRING. */
    private static final String NEW_PROPERTY_STRING = "New Property";
    /** The Constant CONF_DIALOG_TITLE. */
    private static final String CONF_DIALOG_TITLE = "Confirmation Dialog";
    /** The Constant DESCR_ELEC. */
    private static final String DESCR_ELEC = "description.elec";
    /** The Constant PROP_LIST_STRING. */
    private static final String PROP_LIST_STRING = "propertyList";
    /** The Constant INPUT_ELEC_IN. */
    private static final String INPUT_ELEC_IN = "input.elecIn";
    /** The Constant OPT_STRING. */
    private static final String OPT_STRING = "options";
    /** The Constant BALLOT_PROFILE_Y_INDEX_STRING. */
    private static final String BALLOT_PROFILE_Y_INDEX_STRING = "Voters";
    /** The Constant BALLOT_PROFILE_Y_INDEX_STRING. */
    private static final String BALLOT_PROFILE_Y_STACK_INDEX_STRING = "Cand";
    /** The Constant BALLOT_PROFILE_X_INDEX_STRING. */
    private static final String BALLOT_PROFILE_X_INDEX_STRING = "Cand →";
    /** The Constant BALLOT_PROFILE_X_INDEX_STRING. */
    private static final String BALLOT_PROFILE_X_STACK_INDEX_STRING = "Num →";
    /** The Constant MINUS_SIGN. */
    private static final String MINUS_SIGN = "-";
    /** The Constant BLANK. */
    private static final String BLANK = " ";
    /** The Constant MATCH_DEC. */
    private static final String MATCH_DEC = "\\d*";
    /** The Constant REPLACE_FROM_DEC. */
    private static final String REPLACE_FROM_DEC = "[^\\d]";
    /** The Constant ZERO. */
    private static final String ZERO = "0";

    /** The Constant ITEM_COUNT. */
    private static final int ITEM_COUNT = 3;
    /** The Constant GAP_SIZE. */
    private static final int GAP_SIZE = 10;
    /** The Constant SCROLLBAR_PADDING. */
    private static final double SCROLLBAR_PADDING = 15;
    /** The Constant SIXTEEN. */
    private static final int SIXTEEN = 16;
    /** The Constant TOP. */
    private static final int TOP = 20;
    /** The Constant WIDTH. */
    private static final int WIDTH = 150;

    /**
     * The Constant THRESHOLD: 10 seconds to click "remove item" after
     *  one was selected.
     */
    private static final int THRESHOLD = 10000;

    /** The controller. */
    private static GUIController controller;

    /** The properties. */
    private static List<ParentTreeItem> properties =
            new ArrayList<ParentTreeItem>();

    /** The tree items. */
    private static List<TreeItem<CustomTreeItem>> treeItems =
            new ArrayList<TreeItem<CustomTreeItem>>();

    /** The root. */
    private static TreeItem<CustomTreeItem> root;

    /** The Constant SLASH. */
    private static final char SLASH = '/';

    /** The Constant FILE. */
    private static final String FILE = "file:///";

    /** The Constant RELATIVE_PATH. */
    private static final String RELATIVE_PATH = "/core/images/";

    /** The Constant PATH_TO_IMAGES. */
    private static final String PATH_TO_IMAGES = FILE
            + SuperFolderFinder.getSuperFolder() + RELATIVE_PATH;

    /** The Constant BEAST_LOGO. */
    private static final String BEAST_LOGO = "other/BEAST.png";

    /** The Constant START_BUTTON. */
    private static final String START_BUTTON = "toolbar/start.png";

    /** The Constant STOP_BUTTON. */
    private static final String STOP_BUTTON = "toolbar/stop.png";

    /** The Constant LOAD_BUTTON. */
    private static final String LOAD_BUTTON = "toolbar/load.png";

    /** The Constant SAVE_BUTTON. */
    private static final String SAVE_BUTTON = "toolbar/save.png";

    /** The Constant SAVE_AS_BUTTON. */
    private static final String SAVE_AS_BUTTON = "toolbar/save_as.png";

    /** The Constant UNDO_BUTTON. */
    private static final String UNDO_BUTTON = "toolbar/undo.png";

    /** The Constant REDO_BUTTON. */
    private static final String REDO_BUTTON = "toolbar/redo.png";

    /** The Constant CUT_BUTTON. */
    private static final String CUT_BUTTON = "toolbar/cut.png";

    /** The Constant COPY_BUTTON. */
    private static final String COPY_BUTTON = "toolbar/copy.png";

    /** The Constant PASTE_BUTTON. */
    private static final String PASTE_BUTTON = "toolbar/paste.png";

    /** The Constant X_MARK_BUTTON. */
    private static final String X_MARK_BUTTON = "toolbar/x-mark.png";

    /** The focused main tab. */
    private MenuBarInterface focusedMainTab;

    /** The election simulation. */
    private ElectionSimulation electionSimulation;

    /** The max voter. */
    @FXML // fx:id="maxVoter"
    private TextField maxVoter;

    /** The min voter. */
    @FXML // fx:id="minVoter"
    private TextField minVoter;

    /** The max candidates. */
    @FXML // fx:id="maxCandidates"
    private TextField maxCandidates;

    /** The min candidates. */
    @FXML // fx:id="minCandidates"
    private TextField minCandidates;

    /** The max seats. */
    @FXML // fx:id="maxSeats"
    private TextField maxSeats;

    /** The min seats. */
    @FXML // fx:id="minSeats"
    private TextField minSeats;

    /** The time out. */
    @FXML // fx:id="timeOut"
    private TextField timeOut;

    /** The time unit choice. */
    @FXML // fx:id="timeUnitChoice"
    private ChoiceBox<TimeUnit> timeUnitChoice;

    /** The processes. */
    @FXML // fx:id="processes"
    private TextField processes;

    /** The solver choice. */
    @FXML // fx:id="solverChoice"
    private ChoiceBox<?> solverChoice;

    /** The advanced parameters. */
    @FXML // fx:id="advancedParameters1"
    private TextField advancedParameters;

    /** The max unrolls. */
    @FXML // fx:id="maxUnrolls"
    private TextField maxUnrolls;

    /** The help button. */
    @FXML // fx:id="helpButton"
    private MenuItem helpButton;

    /** The start stop button. */
    @FXML // fx:id="startStopButton"
    private Button startStopButton;

    /** The open button. */
    @FXML // fx:id="openButton"
    private Button openButton;

    /** The save button. */
    @FXML // fx:id="saveButton"
    private Button saveButton;

    /** The save as button. */
    @FXML // fx:id="saveAsButton"
    private Button saveAsButton;

    /** The undo button. */
    @FXML // fx:id="undoButton"
    private Button undoButton;

    /** The redo button. */
    @FXML // fx:id="redoButton"
    private Button redoButton;

    /** The cut button. */
    @FXML // fx:id="cutButton"
    private Button cutButton;

    /** The copy button. */
    @FXML // fx:id="copyButton"
    private Button copyButton;

    /** The paste button. */
    @FXML // fx:id="pasteButton"
    private Button pasteButton;

    /** The delete button. */
    @FXML // fx:id="deleteButton"
    private Button deleteButton;

    /** The button. */
    @FXML
    private Button button;

    /** The load prop. */
    @FXML
    private Button loadProp;

    /** The load prop list. */
    @FXML
    private Button loadPropList;

    /** The delete items checkbox. */
    @FXML
    private CheckBox deleteItemsCheckbox;

    /** The code pane. */
    @FXML // fx:id="codePane"
    private Tab codePane;

    /** The property pane. */
    @FXML // fx:id="propertyPane"
    private Tab propertyPane;

    /** The result tab. */
    @FXML // fx:id="resultPane"
    private Tab resultTab;

    /** The input pane. */
    @FXML // fx:id="inputPane"
    private Tab inputPane;

    /** The error pane. */
    @FXML // fx:id="errorPane"
    private Tab errorPane;

    /** The console pane. */
    @FXML // fx:id="consolePane"
    private Tab consolePane;

    /** The display format. */
    @FXML
    private MenuButton displayFormat;

    /** The bound variables tab. */
    @FXML
    private Tab boundVariablesTab;

    /** The boolean expression tab. */
    @FXML
    private Tab booleanExpressionTab;

    /** The information pane. */
    @FXML
    private Tab informationPane;

    /** The property scroll pane. */
    @FXML
    private ScrollPane propertyScrollPane;

    /** The result scroll pane. */
    @FXML
    private ScrollPane resultScrollPane;

    /** The input scroll pane. */
    @FXML
    private ScrollPane inputScrollPane;

    /** The pre property pane. */
    @FXML
    private TitledPane prePropertyPane;

    /** The post property pane. */
    @FXML
    private TitledPane postPropertyPane;

    /** The info text area. */
    @FXML
    private TextArea infoTextArea;

    /** The console text area. */
    @FXML
    private TextArea consoleTextArea;

    /** The error text area. */
    @FXML
    private TextArea errorTextArea;

    /** The tree view. */
    @FXML
    private TreeView<CustomTreeItem> treeView;

    /** The main tab pane. */
    @FXML
    private TabPane mainTabPane;

    /** The sub tab pane. */
    @FXML
    private TabPane subTabPane;

    /** The variable tree view. */
    @FXML
    private TreeView<String> variableTreeView;

    /** The symb var field. */
    @FXML
    private TextField symbVarField;

    /** The input voter field. */
    @FXML
    private TextField inputVoterField;

    /** The input candidate field. */
    @FXML
    private TextField inputCandidateField;

    /** The input seat field. */
    @FXML
    private TextField inputSeatField;

    /** The input grid pane. */
    @FXML
    private GridPane inputGridPane;

    /** The voter scroll pane. */
    @FXML
    private ScrollPane voterScrollPane;

    /** The voter grid pane. */
    @FXML
    private GridPane voterGridPane;

    /** The candidate scroll pane. */
    @FXML
    private ScrollPane candidateScrollPane;

    /** The candidate grid pane. */
    @FXML
    private GridPane candidateGridPane;

    /** The ballot profile Y index. */
    @FXML
    private Text ballotProfileYIndex;

    /** The ballot profile X index. */
    @FXML
    private Text ballotProfileXIndex;

    /** The remove symb var button. */
    @FXML
    private Button removeSymbVarButton;

    /** The add symb var button. */
    @FXML
    private MenuButton addSymbVarButton;

    /** The prop name button. */
    @FXML
    private Button propNameButton;

    /** The prop name field. */
    @FXML
    private TextField propNameField;

    /** The result name field. */
    @FXML
    private TextField resultNameField;

    /** The result border pane. */
    @FXML
    private BorderPane resultBorderPane;

    /** The zoom slider. */
    @FXML
    private Slider zoomSlider;

    /** The running. */
    @FXML
    private boolean running;

    // @FXML
    // private Text
    //
    // @FXML
    // private Button removeVarButton;

    /** The auto complete. */
    private AutoCompleter autoComplete = new AutoCompleter();

    /** The code area. */
    private NewCodeArea codeArea;

    /** The bounded var area. */
    private BoundedVarCodeArea boundedVarArea;

    /** The pre area. */
    private NewPropertyCodeArea preArea;

    /** The post area. */
    private NewPropertyCodeArea postArea;

    /** The result area. */
    private ResultArea resultArea = new ResultArea();

    /** The boolean exp editor. */
    private BooleanExpEditorNEW booleanExpEditor;

    /** The voter items. */
    private TreeItem<String> voterItems;

    /** The candidate items. */
    private TreeItem<String> candidateItems;

    /** The seat items. */
    private TreeItem<String> seatItems;

    /** The last clicked. */
    private long lastClicked;

    /** The symb var to remove. */
    private TreeItem<String> symbVarToRemove;

    // /** The property to remove. */
    // private TreeItem<CustomTreeItem> propertyToRemove; FIXME

    // /** The main stage. */
    // private Stage mainStage; FIXME

    /** The name field is changeable. */
    private boolean nameFieldIsChangeable;

    /** The property list saver loader. */
    private SaverLoader propertyListSaverLoader = new SaverLoader(
            SaverLoader.PROP_LIST_FILE_ENDING, "BEAST list of properties",
            new MinimalSaverInterface() {
                @Override
                public void saveAs() {
                    GUIController.getController().saveAsPropertyList(null);
                }

                @Override
                public void save() {
                    GUIController.getController().savePropertyList(null);
                }
            });

    /** The child item saver loader. */
    private SaverLoader childItemSaverLoader = new SaverLoader(
            SaverLoader.CHILD_PROP_FILE_ENDING, "BEAST child property item",
            null);

    /** The property list GSON. */
    private ChildTreeItemSaverLoader propertyListGSON = new ChildTreeItemSaverLoader();

    /** The project saver loader. */
    private SaverLoader projectSaverLoader = new SaverLoader(
            SaverLoader.PROJECT_FILE_ENDING, "BEAST project file",
            new MinimalSaverInterface() {
                @Override
                public void saveAs() {
                    GUIController.getController().saveAsProject(null);
                }

                @Override
                public void save() {
                    GUIController.getController().saveProject(null);
                }
            });

    /** The option saver loader. */
    private OptionsSaverLoader optionSaverLoader = new OptionsSaverLoader(
            SaverLoader.OPT_FILE_ENDING, "BEAST option file");
    // private ProjectSaverLoader projectGSON = new ProjectSaverLoader();

    /**
     * The constructor.
     *
     * @param mainJfxStage
     *            the main jfx stage
     */
    public GUIController(final Stage mainJfxStage) {
        // this.mainStage = mainJfxStage; FIXME
    }

    // Initial setup

    /**
     * Initialize.
     */
    @FXML
    public void initialize() {
        controller = this;
        setButtonImages();
        // Populate boxes
        // Add the time units you can choose
        timeUnitChoice.getItems().add(TimeUnit.SECONDS);
        timeUnitChoice.getItems().add(TimeUnit.MINUTES);
        timeUnitChoice.getItems().add(TimeUnit.HOURS);
        timeUnitChoice.setValue(TimeUnit.SECONDS);
        addListeners();

        treeView.setShowRoot(false);

        root = new TreeItem<CustomTreeItem>();
        root.setExpanded(true);

        treeView.setRoot(root);
        codeArea = new NewCodeArea();
        final VirtualizedScrollPane<NewCodeArea> vsp =
                new VirtualizedScrollPane<NewCodeArea>(codeArea);
        codePane.setContent(vsp);
        preArea = new NewPropertyCodeArea();
        final VirtualizedScrollPane<NewPropertyCodeArea> vspPre =
                new VirtualizedScrollPane<NewPropertyCodeArea>(preArea);
        prePropertyPane.setContent(vspPre);
        postArea = new NewPropertyCodeArea();
        final VirtualizedScrollPane<NewPropertyCodeArea> vspPost =
                new VirtualizedScrollPane<NewPropertyCodeArea>(postArea);
        postPropertyPane.setContent(vspPost);
        boundedVarArea = new BoundedVarCodeArea();
        final VirtualizedScrollPane<BoundedVarCodeArea> vspBound =
                new VirtualizedScrollPane<BoundedVarCodeArea>(boundedVarArea);
        boundVariablesTab.setContent(vspBound);
        variableTreeView.setShowRoot(false);
        final TreeItem<String> symbVarRoot = new TreeItem<String>();
        symbVarRoot.setExpanded(true);
        variableTreeView.setRoot(symbVarRoot);
        this.voterItems = new TreeItem<String>("Voters");
        this.candidateItems = new TreeItem<String>("Candidates");
        this.seatItems = new TreeItem<String>("Seats");
        symbVarRoot.getChildren().add(voterItems);
        symbVarRoot.getChildren().add(candidateItems);
        symbVarRoot.getChildren().add(seatItems);
        booleanExpEditor = new BooleanExpEditorNEW(
                preArea, postArea, boundedVarArea,
                new PreAndPostConditionsDescription("default description"),
                null);
        variableTreeView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue,
                        newValue) -> setSymbVarToRemove(newValue));
        codeArea.setStyle("-fx-font-family: consolas; -fx-font-size: 11pt;");

        mainTabPane.getSelectionModel().select(codePane);
        focusedMainTab = codeArea;
        mainTabPane.getSelectionModel().selectedItemProperty()
                .addListener((ov, oldTab, newTab) -> {
                    autoComplete.reset();
                    if (newTab.equals(codePane)) {
                        focusedMainTab = codeArea;
                    } else if (newTab.equals(propertyPane)) {
                        focusedMainTab = booleanExpEditor;
                    } else if (newTab.equals(resultTab)) {
                        focusedMainTab = resultArea;
                    } else if (newTab.equals(inputPane)) {
                        focusedMainTab = electionSimulation;
                    }
                });

        final Thread scrollUpdater = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    final long time = System.currentTimeMillis();
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            voterScrollPane.vvalueProperty()
                                    .set(inputScrollPane.getVvalue());
                        }
                    });
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            candidateScrollPane.hvalueProperty()
                                    .set(inputScrollPane.getHvalue());
                        }
                    });
                    inputScrollPane.fireEvent(new Event(ScrollEvent.ANY));
                    try {
                        Thread.sleep(Math.max(0,
                                SIXTEEN - (System.currentTimeMillis() - time)));
                    } catch (InterruptedException e) {
                        // Do nothing
                    }
                }
            }
        });
        scrollUpdater.start();
        voterScrollPane.setPadding(new Insets(0, 0, SCROLLBAR_PADDING, 0));
        candidateScrollPane.setPadding(new Insets(0, SCROLLBAR_PADDING, 0, 0));
        voterScrollPane.addEventFilter(ScrollEvent.SCROLL,
                new EventHandler<ScrollEvent>() {
                    @Override
                    public void handle(final ScrollEvent event) {
                        event.consume();
                    }
                });
        candidateScrollPane.addEventFilter(ScrollEvent.SCROLL,
                new EventHandler<ScrollEvent>() {
                    @Override
                    public void handle(final ScrollEvent event) {
                        event.consume();
                    }
                });

        resultScrollPane.setFitToHeight(true);
        resultScrollPane.setFitToWidth(true);

        // Turn off the zoom slider in the beginning.
        zoomSlider.setDisable(true);

        electionSimulation = new ElectionSimulation(
                codeArea.getElectionDescription().getContainer(), inputGridPane,
                voterGridPane, candidateGridPane);

        // Update all numbers for the input fields.
        this.addInputNumberEnforcer(inputVoterField, "");
        addTreeItem(new PreAndPostConditionsDescription(NEW_PROPERTY_STRING));
        properties.get(0).wasClicked(false);
    }

    /**
     * Sets the button images.
     */
    private void setButtonImages() {
        startStopButton
                .setGraphic(new ImageView(PATH_TO_IMAGES + START_BUTTON));
        openButton.setGraphic(new ImageView(PATH_TO_IMAGES + LOAD_BUTTON));
        saveButton.setGraphic(new ImageView(PATH_TO_IMAGES + SAVE_BUTTON));
        saveAsButton.setGraphic(new ImageView(PATH_TO_IMAGES + SAVE_AS_BUTTON));
        undoButton.setGraphic(new ImageView(PATH_TO_IMAGES + UNDO_BUTTON));
        redoButton.setGraphic(new ImageView(PATH_TO_IMAGES + REDO_BUTTON));
        cutButton.setGraphic(new ImageView(PATH_TO_IMAGES + CUT_BUTTON));
        copyButton.setGraphic(new ImageView(PATH_TO_IMAGES + COPY_BUTTON));
        pasteButton.setGraphic(new ImageView(PATH_TO_IMAGES + PASTE_BUTTON));
        deleteButton.setGraphic(new ImageView(PATH_TO_IMAGES + X_MARK_BUTTON));
    }

    /**
     * Adds the listeners.
     */
    private void addListeners() {
        addNumberEnforcer(minVoter, maxVoter, 1);
        addNumberEnforcer(maxVoter, minVoter, -1);
        addNumberEnforcer(minCandidates, maxCandidates, 1);
        addNumberEnforcer(maxCandidates, minCandidates, -1);
        addNumberEnforcer(minSeats, maxSeats, 1);
        addNumberEnforcer(maxSeats, minSeats, -1);
        addNumberEnforcer(maxUnrolls);
        addNumberEnforcer(processes);
        addNumberEnforcer(timeOut);

        inputVoterField.textProperty()
                .addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(final ObservableValue<? extends String> observable,
                                        final String oldValue, final String newValue) {
                        addInputNumberEnforcer(inputVoterField, newValue);
                    }
                });
        inputCandidateField.textProperty()
                .addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(final ObservableValue<? extends String> observable,
                                        final String oldValue, final String newValue) {
                        addInputNumberEnforcer(inputCandidateField, newValue);
                    }
                });
        inputSeatField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observable,
                                final String oldValue, final String newValue) {
                addInputNumberEnforcer(inputSeatField, newValue);
            }
        });
    }

    // Top Panels
    /**
     * Result pane clicked.
     *
     * @param event
     *            the event
     */
    @FXML
    public void resultPaneClicked(final Event event) {
    }

    /**
     * Error pane clicked.
     *
     * @param event
     *            the event
     */
    @FXML
    public void errorPaneClicked(final Event event) {
    }

    /**
     * Input pane clicked.
     *
     * @param event
     *            the event
     */
    @FXML
    public void inputPaneClicked(final Event event) {
    }

    /**
     * Property pane clicked.
     *
     * @param event
     *            the event
     */
    @FXML
    public void propertyPaneClicked(final Event event) {
    }

    // ------------
    // symb Var
    /**
     * Adds the symb cand.
     *
     * @param event
     *            the event
     */
    @FXML
    public void addSymbCand(final ActionEvent event) {
        String toAdd =
                GUIController.getController().getVariableNameField().getText();
        toAdd = booleanExpEditor.addSymbVar(
                new InternalTypeContainer(InternalTypeRep.CANDIDATE), toAdd,
                false);
    }

    /**
     * Adds the symb seat.
     *
     * @param event
     *            the event
     */
    @FXML
    public void addSymbSeat(final ActionEvent event) {
        String toAdd =
                GUIController.getController().getVariableNameField().getText();
        toAdd = booleanExpEditor.addSymbVar(
                new InternalTypeContainer(InternalTypeRep.SEAT), toAdd, false);
    }

    /**
     * Adds the symb voter.
     *
     * @param event
     *            the event
     */
    @FXML
    public void addSymbVoter(final ActionEvent event) {
        String toAdd =
                GUIController.getController().getVariableNameField().getText();
        toAdd = booleanExpEditor.addSymbVar(
                new InternalTypeContainer(InternalTypeRep.VOTER), toAdd, false);
    }

    /**
     * Removes the symb var.
     */
    @FXML
    public void removeSymbVar() {
        if (symbVarToRemove != null) {
            final long time = System.currentTimeMillis();
            if ((time - lastClicked) < THRESHOLD) {
                booleanExpEditor.removeVariable(symbVarToRemove.getValue());
                symbVarToRemove = null;
            }
        }
    }

    /**
     * Reset prop list.
     */
    @FXML
    public void resetPropList() {
        final Alert confirmation = new Alert(AlertType.CONFIRMATION);
        final Point position = MouseInfo.getPointerInfo().getLocation();

        confirmation.setX(position.getX() - confirmation.getWidth());
        confirmation.setY(position.getY() - confirmation.getHeight());

        final Stage stage =
                (Stage) confirmation.getDialogPane().getScene().getWindow();
        // Add a custom icon.
        stage.getIcons().add(new Image(PATH_TO_IMAGES + BEAST_LOGO));
        confirmation.setTitle(CONF_DIALOG_TITLE);
        confirmation.setHeaderText(
                "Do you really want to reset the property List?");
        confirmation
                .setContentText("Doing so will delete all current properties!");
        final Optional<ButtonType> result = confirmation.showAndWait();
        if (result.get() == ButtonType.OK) {
            newPropertyList();
        }
    }

    /**
     * Removes the property.
     *
     * @param toRemove
     *            the to remove
     */
    public void removeProperty(final TreeItem<CustomTreeItem> toRemove) {
        properties.remove(toRemove.getValue());
        root.getChildren().remove(toRemove);
        // Reset the property fields.
        GUIController.getController().resultNameField
                .setText("No property selected.");
        // Reset the result field.
        GUIController.getController().getResultScrollPane().setContent(null);
    }

    /**
     * Removes the all properties.
     */
    private void removeAllProperties() {
        final List<TreeItem<CustomTreeItem>> tmpCopy =
                new ArrayList<TreeItem<CustomTreeItem>>();
        tmpCopy.addAll(treeItems);
        for (final TreeItem<CustomTreeItem> treeItem : tmpCopy) {
            removeProperty(treeItem);
        }
    }

    /**
     * Gets the input voters.
     *
     * @return the input voters
     */
    public TextField getInputVoters() {
        return inputVoterField;
    }

    /**
     * Gets the input candidates.
     *
     * @return the input candidates
     */
    public TextField getInputCandidates() {
        return inputCandidateField;
    }

    /**
     * Gets the input seats.
     *
     * @return the input seats
     */
    public TextField getInputSeats() {
        return inputSeatField;
    }

    // ------------
    // Bottom Panels

    /**
     * Code pane clicked.
     *
     * @param event
     *            the event
     */
    @FXML
    public void codePaneClicked(final Event event) {
    }

    /**
     * Console pane clicked.
     *
     * @param event
     *            the event
     */
    @FXML
    public void consolePaneClicked(final Event event) {
    }

    /**
     * Check finished.
     */
    public void checkFinished() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                startStopButton.setGraphic(
                        new ImageView(PATH_TO_IMAGES + START_BUTTON));
            }
        });
        running = false;
    }

    // --------
    // Icon Bar

    /**
     * Start stop pressed.
     *
     * @param event
     *            the event
     */
    @FXML
    public synchronized void startStopPressed(final ActionEvent event) {
        if (!running) {
            // react = false; // Lock the GUI
            if (BEASTCommunicator.startCheckNEW()) {
                // If we start it successfully
                startStopButton.setGraphic(new ImageView(PATH_TO_IMAGES + STOP_BUTTON));
                running = true;
            }
        } else {
            if (BEASTCommunicator.stopCheck()) {
                checkFinished();
            }
        }
    }

    /**
     * Copy button.
     *
     * @param event
     *            the event
     */
    @FXML
    public void copyButton(final ActionEvent event) {
        getFocusedArea().copy();
    }

    /**
     * Undo button.
     *
     * @param event
     *            the event
     */
    @FXML
    public void undoButton(final ActionEvent event) {
        getFocusedArea().undo();
    }

    /**
     * Cut button.
     *
     * @param event
     *            the event
     */
    @FXML
    public void cutButton(final ActionEvent event) {
        getFocusedArea().cut();
    }

    /**
     * Open button.
     *
     * @param event
     *            the event
     */
    @FXML
    public void openButton(final ActionEvent event) {
        getFocusedArea().open();
    }

    /**
     * Paste button.
     *
     * @param event
     *            the event
     */
    @FXML
    public void pasteButton(final ActionEvent event) {
        getFocusedArea().paste();
    }

    /**
     * Redo button.
     *
     * @param event
     *            the event
     */
    @FXML
    public void redoButton(final ActionEvent event) {
        getFocusedArea().redo();
    }

    /**
     * Save as button.
     *
     * @param event
     *            the event
     */
    @FXML
    public void saveAsButton(final ActionEvent event) {
        getFocusedArea().saveAs();
    }

    /**
     * Save button.
     *
     * @param event
     *            the event
     */
    @FXML
    public void saveButton(final ActionEvent event) {
        getFocusedArea().save();
    }

    /**
     * Delete button.
     *
     * @param event
     *            the event
     */
    @FXML
    public void deleteButton(final ActionEvent event) {
        getFocusedArea().delete();
    }

    // Text manipulation menu buttons.

    /**
     * Copy.
     *
     * @param event
     *            the event
     */
    @FXML
    public void copy(final ActionEvent event) {
        copyButton(event);
    }

    /**
     * Delete.
     *
     * @param event
     *            the event
     */
    @FXML
    public void delete(final ActionEvent event) {
        deleteButton(event);
    }

    /**
     * Cut.
     *
     * @param event
     *            the event
     */
    @FXML
    public void cut(final ActionEvent event) {
        cutButton(event);
    }

    /**
     * Paste.
     *
     * @param event
     *            the event
     */
    @FXML
    public void paste(final ActionEvent event) {
        pasteButton(event);
    }

    /**
     * Redo.
     *
     * @param event
     *            the event
     */
    @FXML
    public void redo(final ActionEvent event) {
        redoButton(event);
    }

    /**
     * Undo.
     *
     * @param event
     *            the event
     */
    @FXML
    public void undo(final ActionEvent event) {
        undoButton(event);
    }

    // Other menu buttons.

    /**
     * Prop name button clicked.
     * // The user wants to edit the name of the current property.
     *
     * @param event
     *            the event
     */
    @FXML
    public void propNameButtonClicked(final Event event) {
        if (nameFieldIsChangeable) {
            final String text = propNameField.getText();
            if (!text.isEmpty()) {
                if (isValidFileName(text)) {
                    propNameField.setEditable(false);
                    resultNameField.setText(text);
                    booleanExpEditor.getPropertyDescription().setName(text);
                    if (booleanExpEditor.getCurrentItem() != null) {
                        booleanExpEditor.getCurrentItem().setText(text);
                    }
                    nameFieldIsChangeable = false;
                    propNameButton.setText("change");
                } else {
                    setErrorText("Invalid property name.");
                }
            }
        } else {
            propNameButton.setText("save");
            propNameField.setEditable(true);
            nameFieldIsChangeable = true;
        }
    }

    /**
     * Advanced parameters.
     *
     * @param event
     *            the event
     */
    @FXML
    public void advancedParameters(final Event event) {
    }

    /**
     * Help clicked.
     *
     * @param event
     *            the event
     */
    @FXML
    public void helpClicked(final ActionEvent event) {
    }

    /**
     * Max candidates.
     *
     * @param event
     *            the event
     */
    @FXML
    public void maxCandidates(final ActionEvent event) {
    }

    /**
     * Max seats.
     *
     * @param event
     *            the event
     */
    @FXML
    public void maxSeats(final ActionEvent event) {
    }

    /**
     * Max unrolls.
     *
     * @param event
     *            the event
     */
    @FXML
    public void maxUnrolls(final ActionEvent event) {
    }

    /**
     * Max voter.
     *
     * @param event
     *            the event
     */
    @FXML
    public void maxVoter(final ActionEvent event) {
        System.out.println(maxVoter.getText());
    }

    /**
     * Min candidates.
     *
     * @param event
     *            the event
     */
    @FXML
    public void minCandidates(final ActionEvent event) {
    }

    /**
     * Min seats.
     *
     * @param event
     *            the event
     */
    @FXML
    public void minSeats(final ActionEvent event) {
    }

    /**
     * Min voter.
     *
     * @param event
     *            the event
     */
    @FXML
    public void minVoter(final ActionEvent event) {
    }

    /**
     * New election description.
     *
     * @param event
     *            the event
     */
    @FXML
    public void newElectionDescription(final ActionEvent event) {
        codeArea.resetSaveFile();
        final Tuple3<String, InputType, OutputType> triplet =
                showPopUp(
                        "New Election Description",
                        "Choose name and type of the election description.",
                        "Input Type:",
                        InputType.getInputTypes(),
                        "Output Type:",
                        OutputType.getOutputTypes()
                        );
        if (triplet != null) {
            codeArea.setNewElectionDescription(
                    new ElectionDescription(triplet.first(), triplet.second(),
                                            triplet.third(), 0, 0, 0, true));
            updateElectionInputIndexText(triplet.second());
            this.getElectionSimulation().updateContainer(
                    codeArea.getElectionDescription().getContainer());
        }
    }

    /**
     * New project.
     *
     * @param event
     *            the event
     */
    @FXML
    public void newProject(final ActionEvent event) {
        projectSaverLoader.resetHasSaveFile();
        newElectionDescription(event);
        newVotingInput();
        newPropertyList();
        setOptions(new OptionsNew()); // Reset the voting bounds

        this.getElectionSimulation().updateContainer(
                codeArea.getElectionDescription().getContainer());
    }

    /**
     * Open election description.
     *
     * @param event
     *            the event
     */
    @FXML
    public void openElectionDescription(final ActionEvent event) {
        codeArea.open();
        this.getElectionSimulation().updateContainer(
                codeArea.getElectionDescription().getContainer());
    }

    /**
     * Open election description.
     *
     * @param elecDescFile
     *            the elec desc file
     */
    private void openElectionDescription(final File elecDescFile) {
        codeArea.open(elecDescFile);
        this.getElectionSimulation().updateContainer(
                codeArea.getElectionDescription().getContainer());
    }

    /**
     * Open property.
     *
     * @param event
     *            the event
     */
    @FXML
    public void openProperty(final ActionEvent event) {
        booleanExpEditor.open();
    }

    /**
     * Open project.
     *
     * @param event
     *            the event
     */
    @FXML
    public void openProject(final ActionEvent event) {
        final File projectFile = projectSaverLoader.showFileLoadDialog("");
        if (projectFile != null) {
            removeAllProperties();
            final String folderName = FilenameUtils
                    .removeExtension(projectFile.getName());
            final File parent = new File(projectFile.getParentFile()
                    + Character.toString(SLASH) + folderName);
            if (parent != null) {
                // Load the election Description
                final File elecDescFile = new File(
                        parent.getAbsolutePath() + SLASH + DESCR_ELEC);
                openElectionDescription(elecDescFile);
                // Load the propertyList
                final File propListFile = new File(parent.getAbsolutePath() + SLASH
                        + PROP_LIST_STRING + SaverLoader.PROP_LIST_FILE_ENDING);
                openPropertyList(propListFile);
                // Load the electionInput
                final File elecInputFile = new File(
                        parent.getAbsolutePath() + SLASH + INPUT_ELEC_IN);
                openVotingInput(elecInputFile);
                // Load the options
                final File optionsFile = new File(parent.getAbsolutePath() + SLASH
                        + OPT_STRING + SaverLoader.OPT_FILE_ENDING);
                openOptions(optionsFile);
            }
        }
    }

    /**
     * Open property list.
     *
     * @param event
     *            the event
     */
    @FXML
    public void openPropertyList(final ActionEvent event) {
        final File listFile = propertyListSaverLoader.showFileLoadDialog("");
        openPropertyListFile(listFile);
    }

    /**
     * Open property list.
     *
     * @param propListFile
     *            the prop list file
     */
    private void openPropertyList(final File propListFile) {
        openPropertyListFile(propListFile);
    }

    /**
     * Adds the property list item.
     *
     * @param currentDir
     *            the current dir
     * @param prop
     *            the prop
     * @param children
     *            the children
     * @return true upon error
     */
    private boolean addPropertyListItem(final File currentDir,
                                        final PreAndPostConditionsDescription prop,
                                        final String[] children) {
        final TreeItem<CustomTreeItem> treeItem =
                new TreeItem<CustomTreeItem>();
        final ParentTreeItem parentItem =
                new ParentTreeItem(prop, false,
                                   treeItem, false);
        treeItems.add(treeItem);
        properties.add(parentItem);
        root.getChildren().add(treeItem);

        boolean breakOut = false;
        for (int j = 0; j < children.length && !breakOut; j++) {
            final String json = childItemSaverLoader
                    .load(new File(currentDir.getPath() + SLASH
                            + children[j]));
            final String[] splits = children[j].split("\\.");
            final String stringIndex = splits[splits.length - 2];
            ChildTreeItemValues values = null;
            try {
                values = propertyListGSON
                        .createFromSaveString(json);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                breakOut = true;
            }
            if (!breakOut) {
                parentItem.addChild(values,
                                    Integer.parseInt(stringIndex));
            }
        }
        if (parentItem.getCounter() != ITEM_COUNT) {
            System.out.println("Error, ");
        }
        return breakOut;
    }

    /**
     * Get error text for invalid property list.
     *
     * @param currentDir
     *            the current dir
     * @return the string
     */
    private String errorTextInvalidPropList(final File currentDir) {
        return "Invalid property list save format in folder: "
                + currentDir.getName();
    }

    /**
     * Open property list file.
     *
     * @param listFile
     *            the list file
     */
    private void openPropertyListFile(final File listFile) {
        projectSaverLoader.resetHasSaveFile();
        if (listFile != null) {
            removeAllProperties();
            final String folderName = FilenameUtils
                    .removeExtension(listFile.getName());
            final File parent = new File(listFile.getParentFile()
                    + Character.toString(SLASH) + folderName);
            final File[] directories = parent.listFiles(File::isDirectory);

            if (directories != null && directories.length > 0) {
                boolean breakOut = false;
                for (int i = 0; i < directories.length && !breakOut; i++) {
                    final File currentDir = directories[i];
                    final String[] property = currentDir
                            .list(new FilenameFilter() {
                                @Override
                                public boolean accept(final File dir,
                                                      final String name) {
                                    return name.endsWith(
                                            SaverLoader.PROP_DESCR_FILE_ENDING);
                                }
                            });
                    if (property.length != 1) {
                        errorTextArea.setText(errorTextInvalidPropList(currentDir));
                    } else {
                        final PreAndPostConditionsDescription prop =
                                booleanExpEditor
                                .open(new File(currentDir.getPath() + SLASH
                                        + property[0]));
                        final String[] children = currentDir
                                .list(new FilenameFilter() {
                                    @Override
                                    public boolean accept(final File dir,
                                                          final String name) {
                                        return name.endsWith(
                                                SaverLoader.CHILD_PROP_FILE_ENDING);
                                    }
                                });
                        if (children.length != ITEM_COUNT) {
                            errorTextArea.setText(errorTextInvalidPropList(currentDir));
                        } else {
                            breakOut = addPropertyListItem(currentDir,
                                                           prop, children);
                        }
                    }
                }
            }
        } else if (properties.size() > 0) {
            setCurrentPropertyDescription(properties.get(0), false);
        }
    }

    /**
     * Open voting input.
     *
     * @param event
     *            the event
     */
    @FXML
    public void openVotingInput(final ActionEvent event) {
        electionSimulation.open();
    }

    /**
     * Open voting input.
     *
     * @param file
     *            the file
     */
    public void openVotingInput(final File file) {
        if (file.exists()) {
            electionSimulation.open(file);
        }
    }

    /**
     * Processes.
     *
     * @param event
     *            the event
     */
    @FXML
    public void processes(final ActionEvent event) {
    }

    /**
     * Quit program.
     *
     * @param event
     *            the event
     */
    @FXML
    public void quitProgram(final ActionEvent event) {
    }

    /**
     * Save as election description.
     *
     * @param event
     *            the event
     */
    @FXML
    public void saveAsElectionDescription(final ActionEvent event) {
        codeArea.saveAs();
    }

    /**
     * Save as election description.
     *
     * @param file
     *            the file
     */
    private void saveAsElectionDescription(final File file) {
        codeArea.saveAs(file);
    }

    /**
     * Save as property list.
     *
     * @param event
     *            the event
     */
    @FXML
    public void saveAsPropertyList(final ActionEvent event) {
        savePropertyListFromFile(null, true, true);
    }

    /**
     * Save as voting input.
     *
     * @param event
     *            the event
     */
    @FXML
    public void saveAsVotingInput(final ActionEvent event) {
        electionSimulation.saveAs();
    }

    /**
     * Save election description.
     *
     * @param event
     *            the event
     */
    @FXML
    public void saveElectionDescription(final ActionEvent event) {
        codeArea.save();
    }

    /**
     * Save property.
     *
     * @param event
     *            the event
     */
    @FXML
    public void saveProperty(final ActionEvent event) {
        booleanExpEditor.save();
    }

    /**
     * Save as property.
     *
     * @param event
     *            the event
     */
    @FXML
    public void saveAsProperty(final ActionEvent event) {
        booleanExpEditor.saveAs();
    }

    /**
     * Save project.
     *
     * @param event
     *            the event
     */
    @FXML
    public void saveProject(final ActionEvent event) {
        if (projectSaverLoader.hasSaveFile()) {
            saveProjectFile(projectSaverLoader.getSaveFile());
        } else {
            saveAsProject(event);
        }
    }

    /**
     * Save project file.
     *
     * @param projectFile
     *            the project file
     */
    private void saveProjectFile(final File projectFile) {
        if (projectFile != null) {
            final File folder = createFolderWithName(projectFile, true);
            projectSaverLoader.saveAs(new File(folder.getParentFile()
                    + Character.toString(SLASH) + projectFile.getName()), "");
            // Save the electionDescription
            saveAsElectionDescription(
                    new File(folder.getPath() + SLASH + DESCR_ELEC));
            // Save the property list
            savePropertyListFromFile(new File(folder.getPath() + SLASH
                    + PROP_LIST_STRING + SaverLoader.PROP_LIST_FILE_ENDING),
                    false, true);
            // Save election input
            electionSimulation.saveAs(
                    new File(folder.getPath() + SLASH + INPUT_ELEC_IN));
            // Save the options
            saveOptions(new File(folder.getPath() + SLASH + OPT_STRING
                    + SaverLoader.OPT_FILE_ENDING));
        }
    }

    /**
     * Save as project.
     *
     * @param event
     *            the event
     */
    @FXML
    public void saveAsProject(final ActionEvent event) {
        final File projectFile = projectSaverLoader.showFileSaveDialog("");
        saveProjectFile(projectFile);
        projectSaverLoader.setSaveFile(projectFile);
    }

    /**
     * Save property list.
     *
     * @param event
     *            the event
     */
    @FXML
    public void savePropertyList(final ActionEvent event) {
        savePropertyListFromFile(null, true, false);
    }

    /**
     * New property list.
     */
    public void newPropertyList() {
        propertyListSaverLoader.resetHasSaveFile();
        if (properties.size() > 0) {
            removeAllProperties();
        }
        addProperty(new PreAndPostConditionsDescription(NEW_PROPERTY_STRING));
        properties.get(0).wasClicked(false);
        GUIController.getController().resultNameField
                .setText("no property selected");
        // Reset the result field
        GUIController.getController().getResultScrollPane().setContent(null);
    }

    /**
     * New voting input.
     */
    public void newVotingInput() {
        electionSimulation.reset();
    }

    /**
     * Save property list from file.
     *
     * @param listFile
     *            the list file
     * @param askUser
     *            the ask user
     * @param saveAs
     *            the save as
     */
    private void savePropertyListFromFile(final File listFile,
                                          final boolean askUser,
                                          final boolean saveAs) {
        if (properties.size() > 0) {
            final File file;
            if (!saveAs && askUser) {
                if (propertyListSaverLoader.hasSaveFile()) {
                    savePropertyListFromFile(
                            propertyListSaverLoader.getSaveFile(), false,
                            false);
                } else {
                    savePropertyListFromFile(listFile, true, true);
                }
                file = listFile;
            } else if (saveAs && askUser) {
                file = propertyListSaverLoader.showFileSaveDialog("");
                savePropertyListFromFile(file, false, false);
            } else {
                file = listFile;
            }
            if (file != null) {
                final File folder = createFolderWithName(file, true);
                propertyListSaverLoader.save(
                        new File(folder.getParentFile()
                                + Character.toString(SLASH) + file.getName()),
                        "");
                // String listFileContent = ""; FIXME: What is this for? Remove?

                int counter = 0;
                for (ParentTreeItem parentItem : properties) {
                    // listFileContent += (0 < counter) ? "\n" : 0; FIXME
                    String name = parentItem.getPreAndPostProperties()
                            .getName();
                    File saveFolder = new File(
                            folder + Character.toString(SLASH) + name + "_"
                                    + counter++);
                    // We want to save the parentTreeItem into this folder
                    saveFolder = createFolderWithName(saveFolder, true);
                    name = saveFolder.getName();
                    // listFileContent += name; FIXME
                    booleanExpEditor.saveAs(
                            parentItem.getPreAndPostProperties(),
                            new File(saveFolder + Character.toString(SLASH)
                                    + parentItem.getText()
                                    + SaverLoader.PROP_DESCR_FILE_ENDING));
                    final List<ChildTreeItem> children = parentItem.getSubItems();
                    int subCounter = 0;
                    for (ChildTreeItem childItem : children) {
                        final String saveString = propertyListGSON
                                .createSaveString(childItem.getValues());
                        childItemSaverLoader.saveAs(
                                new File(saveFolder.getAbsolutePath() + SLASH
                                        + subCounter++
                                        + SaverLoader.CHILD_PROP_FILE_ENDING),
                                saveString);
                    }
                }
            }
        } else {
            errorTextArea.setText("No property items to save exist.");
        }
    }

    /**
     * Save options.
     *
     * @param file
     *            the file
     */
    private void saveOptions(final File file) {
        OptionsNew toSave = new OptionsNew();
        toSave = fillOptionObject(toSave);
        final String json = optionSaverLoader.createSaveString(toSave);
        optionSaverLoader.save(file, json);
    }

    /**
     * Open options.
     *
     * @param file
     *            the file
     */
    private void openOptions(final File file) {
        if (file.exists()) {
            final String jsonToLoad = optionSaverLoader.load(file);
            OptionsNew options = null;
            try {
                options = optionSaverLoader.createFromSaveString(jsonToLoad);
            } catch (JsonSyntaxException e) {
                // Do nothing
            }
            if (options != null) {
                setOptions(options);
            }
        }
    }

    /**
     * Sets the options.
     *
     * @param options
     *            the new options
     */
    private void setOptions(final OptionsNew options) {
        this.minVoter.setText("" + options.getMinVoters());
        this.maxVoter.setText("" + options.getMaxVoters());

        this.minCandidates.setText("" + options.getMinCandidates());
        this.maxCandidates.setText("" + options.getMaxCandidates());

        this.minSeats.setText("" + options.getMinSeats());
        this.maxSeats.setText("" + options.getMaxSeats());
    }

    /**
     * Fill option object.
     *
     * @param options
     *            the options
     * @return the options new
     */
    private OptionsNew fillOptionObject(final OptionsNew options) {
        options.setMinVoters(Integer.parseInt(minVoter.getText()));
        options.setMaxVoters(Integer.parseInt(maxVoter.getText()));

        options.setMinCandidates(Integer.parseInt(minCandidates.getText()));
        options.setMaxCandidates(Integer.parseInt(maxCandidates.getText()));

        options.setMinSeats(Integer.parseInt(minSeats.getText()));
        options.setMaxSeats(Integer.parseInt(maxSeats.getText()));
        return options;
    }

    /**
     * Save voting input.
     *
     * @param event
     *            the event
     */
    @FXML
    public void saveVotingInput(final ActionEvent event) {
        electionSimulation.save();
    }

    public void updateElectionInputIndexText(final InputType elecInputType) {
        if (elecInputType.isStackType()) {
            ballotProfileYIndex.setText(BALLOT_PROFILE_Y_STACK_INDEX_STRING);
            ballotProfileXIndex.setText(BALLOT_PROFILE_X_STACK_INDEX_STRING);
        } else {
            ballotProfileYIndex.setText(BALLOT_PROFILE_Y_INDEX_STRING);
            ballotProfileXIndex.setText(BALLOT_PROFILE_X_INDEX_STRING);
        }
    }

    /**
     * Time out.
     *
     * @param event
     *            the event
     */
    @FXML
    public void timeOut(final ActionEvent event) {

    }

    /**
     * New property.
     *
     * @param event
     *            the event
     */
    @FXML
    public void newProperty(final ActionEvent event) {
        addProperty(new PreAndPostConditionsDescription(NEW_PROPERTY_STRING));
    }

    /**
     * Load property.
     *
     * @param event
     *            the event
     */
    @FXML
    public void loadProperty(final ActionEvent event) {
        openProperty(null);
    }

    /**
     * Load property list.
     *
     * @param event
     *            the event
     */
    @FXML
    public void loadPropertyList(final ActionEvent event) {
        openPropertyList(new ActionEvent());
    }

    /**
     * Reset input.
     *
     * @param event
     *            the event
     */
    @FXML
    public void resetInput(final ActionEvent event) {
        final Alert confirmation = new Alert(AlertType.CONFIRMATION);
        final Point position = MouseInfo.getPointerInfo().getLocation();

        confirmation.setX(position.getX() - confirmation.getWidth());
        confirmation.setY(position.getY() - confirmation.getHeight());

        final Stage stage =
                (Stage) confirmation.getDialogPane().getScene().getWindow();
        // Add a custom icon.
        stage.getIcons().add(new Image(PATH_TO_IMAGES + BEAST_LOGO));
        confirmation.setTitle(CONF_DIALOG_TITLE);
        confirmation.setHeaderText("Do you really want to reset the input?");
        confirmation.setContentText(
                "Doing so will reset all previously given values.");
        final Optional<ButtonType> result = confirmation.showAndWait();
        if (result.get() == ButtonType.OK) {
            electionSimulation.reset();
        }
    }

    /**
     * Adds the number enforcer.
     *
     * @param field
     *            the field
     */
    private void addNumberEnforcer(final TextField field) {
        field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    final ObservableValue<? extends String> observable,
                    final String oldValue, final String newValue) {
                if (!newValue.matches(MATCH_DEC)) {
                    field.setText(newValue.replaceAll(REPLACE_FROM_DEC, ""));
                }
            }
        });
    }

    /**
     * Adds the number enforcer.
     *
     * @param field
     *            the field which shall be enforced
     * @param partnerField
     *            the partner field, which is supposed to be not bigger /
     *            smaller than the main field
     * @param sign
     *            a sign to show if the field has to be bigger or smaller than
     *            the other one. e.g a sign of 1 means field <= partnerField, a
     *            sign of (-1) would mean field => partner field
     */
    private void addNumberEnforcer(final TextField field,
                                   final TextField partnerField,
                                   final int sign) {
        if (sign == 0) {
            field.setText("");
            partnerField.setText("");
        }
        field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    final ObservableValue<? extends String> observable,
                    final String oldValue, final String newValue) {
                if (!newValue.matches(MATCH_DEC)) {
                    field.setText(newValue.replaceAll(REPLACE_FROM_DEC, ""));
                }
                if (newValue.isEmpty()) {
                    field.setText("" + 1);
                }
                final int valueField = Integer.parseInt(field.getText());
                final int valuePartner = Integer.parseInt(partnerField.getText());
                if (valueField * sign > valuePartner * sign) {
                    partnerField.setText("" + valueField);
                }
            }
        });
    }

    /**
     * Gets the parameter.
     *
     * @return the parameter
     */
    public ElectionCheckParameter getParameter() {
        Integer numberProcesses = Runtime.getRuntime().availableProcessors();
        if (!processes.getText().isEmpty()) {
            numberProcesses = Integer.parseInt(processes.getText());
        }
        TimeOut time = new TimeOut(TimeUnit.SECONDS, 0);
        if (!timeOut.getText().isEmpty()) {
            time = new TimeOut(timeUnitChoice.getValue(),
                    Integer.parseInt(timeOut.getText()));
        }
        String argument = advancedParameters.getText();
        final int maxUnrollings = getMaxUnrolls();
        if (maxUnrollings > 0) {
            argument += " --unwind " + maxUnrollings;
        }
        final List<Integer> voter = getValues(minVoter, maxVoter);
        final List<Integer> cand = getValues(minCandidates, maxCandidates);
        final List<Integer> seat = getValues(minSeats, maxSeats);
        final int marginVoters = electionSimulation.getNumVoters();
        final int marginCandidates = electionSimulation.getNumCandidates();
        final int marginSeats = electionSimulation.getNumSeats();
        final ElectionCheckParameter param =
                new ElectionCheckParameter(voter, cand,
                                           seat, marginVoters,
                                           marginCandidates,
                                           marginSeats, time,
                                           numberProcesses, argument);
        return param;
    }

    /**
     * Gets the values.
     *
     * @param minfield
     *            the minfield
     * @param maxField
     *            the max field
     * @return the values
     */
    private static List<Integer> getValues(final TextField minfield,
                                           final TextField maxField) {
        final List<Integer> toReturn = new ArrayList<Integer>();
        final int valueMin = Integer.parseInt(minfield.getText());
        final int valueMax = Integer.parseInt(maxField.getText());
        for (int i = valueMin; i <= valueMax; i++) {
            toReturn.add(i);
        }
        return toReturn;
    }

    /**
     * Gets the info text.
     *
     * @return the info text
     */
    public static String getInfoText() {
        return controller.infoTextArea.getText();
    }

    /**
     * Sets the info text.
     *
     * @param text
     *            the new info text
     */
    public static void setInfoText(final String text) {
        controller.infoTextArea.setText(text);
        controller.getSubTabPane().getSelectionModel()
                .select(controller.informationPane);
    }

    /**
     * Gets the console text.
     *
     * @return the console text
     */
    public static String getConsoleText() {
        return controller.consoleTextArea.getText();
    }

    /**
     * Sets the console text.
     *
     * @param text
     *            the new console text
     */
    public static void setConsoleText(final String text) {
        controller.consoleTextArea.setText(text);
        controller.getSubTabPane().getSelectionModel()
                .select(controller.consolePane);
    }

    /**
     * Gets the error text.
     *
     * @return the error text
     */
    public static String getErrorText() {
        return controller.errorTextArea.getText();
    }

    /**
     * Sets the error text.
     *
     * @param text
     *            the new error text
     */
    public static void setErrorText(final String text) {
        controller.errorTextArea.setText(text);
        controller.getSubTabPane().getSelectionModel()
                .select(controller.errorPane);
    }

    /**
     * Adds the tree item.
     *
     * @param description
     *            the description
     * @return the tree item
     */
    public static TreeItem<CustomTreeItem> addTreeItem(
            final PreAndPostConditionsDescription description) {
        final TreeItem<CustomTreeItem> propRoot = new TreeItem<CustomTreeItem>();
        treeItems.add(propRoot);
        properties.add(new ParentTreeItem(description, false, propRoot, true));
        root.getChildren().add(propRoot);
        return propRoot;
    }

    /**
     * Removes the tree item.
     *
     * @param item
     *            the item
     */
    public static void removeTreeItem(final TreeItem<String> item) {
        root.getChildren().remove(item);
    }

    /**
     * Gets the controller.
     *
     * @return the controller
     */
    public static GUIController getController() {
        return controller;
    }

    /**
     * Gets the result border pane.
     *
     * @return the result border pane
     */
    public BorderPane getResultBorderPane() {
        return resultBorderPane;
    }

    /**
     * Gets the code area.
     *
     * @return the code area
     */
    public NewCodeArea getCodeArea() {
        return codeArea;
    }

    /**
     * Gets the pre conditions area.
     *
     * @return the pre conditions area
     */
    public NewPropertyCodeArea getPreConditionsArea() {
        return preArea;
    }

    /**
     * Gets the post conditions area.
     *
     * @return the post conditions area
     */
    public NewPropertyCodeArea getPostConditionsArea() {
        return postArea;
    }

    /**
     * Gets the main tab pane.
     *
     * @return the main tab pane
     */
    public TabPane getMainTabPane() {
        return mainTabPane;
    }

    /**
     * Gets the sub tab pane.
     *
     * @return the sub tab pane
     */
    public TabPane getSubTabPane() {
        return subTabPane;
    }

    /**
     * Gets the property tab.
     *
     * @return the property tab
     */
    public Tab getPropertyTab() {
        return propertyPane;
    }

    /**
     * Gets the result tab.
     *
     * @return the result tab
     */
    public Tab getResultTab() {
        return resultTab;
    }

    /**
     * Gets the code tab.
     *
     * @return the code tab
     */
    public Tab getCodeTab() {
        return codePane;
    }

    /**
     * Gets the input tab.
     *
     * @return the input tab
     */
    public Tab getInputTab() {
        return inputPane;
    }

    /**
     * Gets the zoom slider.
     *
     * @return the zoom slider
     */
    public Slider getZoomSlider() {
        return zoomSlider;
    }

    /**
     * Gets the result scroll pane.
     *
     * @return the result scroll pane
     */
    public ScrollPane getResultScrollPane() {
        return resultScrollPane;
    }

    /**
     * Gets the properties.
     *
     * @return the properties
     */
    public List<ParentTreeItem> getProperties() {
        return properties;
    }

    /**
     * Gets the election description.
     *
     * @return the election description
     */
    public ElectionDescription getElectionDescription() {
        return codeArea.getElectionDescription();
    }

    /**
     * Gets the pre code area.
     *
     * @return the pre code area
     */
    public NewPropertyCodeArea getPreCodeArea() {
        return preArea;
    }

    /**
     * Gets the post code area.
     *
     * @return the post code area
     */
    public NewPropertyCodeArea getPostCodeArea() {
        return postArea;
    }

    /**
     * Gets the variable name field.
     *
     * @return the variable name field
     */
    public TextField getVariableNameField() {
        return symbVarField;
    }

    /**
     * Gets the delete tmp files.
     *
     * @return the delete tmp files
     */
    public boolean getDeleteTmpFiles() {
        return deleteItemsCheckbox.isSelected();
    }

    /**
     * Adds the input number enforcer.
     *
     * @param field
     *            the field
     * @param newValue
     *            the new value
     */
    private void addInputNumberEnforcer(final TextField field,
                                        final String newValue) {
        final String val = newValue.replaceAll(BLANK, "");
        if (val.length() != 0) {
            String sign = "";
            if (val.charAt(0) == '-' && val.length() > 1) {
                sign = MINUS_SIGN;
            }
            if (!val.matches(MATCH_DEC)) {
                String newText = ZERO;
                if (!val.isEmpty()) {
                    newText = val.replaceAll(REPLACE_FROM_DEC, "");
                }
                if (newText.isEmpty()) {
                    newText = ZERO;
                }
                field.setText(sign + newText);
            }
        } else {
            field.setText(ZERO);
        }
        final String vettedVoters = electionSimulation
                .setAndVetVoterNumber(inputVoterField.getText());
        if (vettedVoters != inputVoterField.getText()) {
            inputVoterField.setText(vettedVoters);
        }
        final String vettedCandidates = electionSimulation
                .setAndVetCandidateNumber(inputCandidateField.getText());
        if (vettedCandidates != inputCandidateField.getText()) {
            inputCandidateField.setText(vettedCandidates);
        }
        final String vettedSeats = electionSimulation
                .setAndVetSeatNumber(inputSeatField.getText());
        if (vettedSeats != inputSeatField.getText()) {
            inputSeatField.setText(vettedSeats);
        }
    }

    /**
     * Gets the election simulation.
     *
     * @return the election simulation
     */
    public ElectionSimulation getElectionSimulation() {
        return electionSimulation;
    }

    /**
     * Gets the boolean exp editor.
     *
     * @return the boolean exp editor
     */
    public BooleanExpEditorNEW getBooleanExpEditor() {
        return booleanExpEditor;
    }

    /**
     * Gets the voter tree items.
     *
     * @return the voter tree items
     */
    public TreeItem<String> getVoterTreeItems() {
        return voterItems;
    }

    /**
     * Gets the candidate tree items.
     *
     * @return the candidate tree items
     */
    public TreeItem<String> getCandidateTreeItems() {
        return candidateItems;
    }

    /**
     * Gets the seat tree items.
     *
     * @return the seat tree items
     */
    public TreeItem<String> getSeatTreeItems() {
        return seatItems;
    }

    /**
     * Sets the symb var to remove.
     *
     * @param item
     *            the new symb var to remove
     */
    public void setSymbVarToRemove(final TreeItem<String> item) {
        this.symbVarToRemove = item;
        this.lastClicked = System.currentTimeMillis();
    }

    /**
     * Sets the current property description.
     *
     * @param propertyItem
     *            the property item
     * @param bringToFront
     *            the bring to front
     */
    public void setCurrentPropertyDescription(final ParentTreeItem propertyItem,
                                              final boolean bringToFront) {
        if (nameFieldIsChangeable) {
            propNameButtonClicked(null); // Try to save the text the user wrote
        }
        booleanExpEditor.setCurrentPropertyDescription(propertyItem,
                                                       bringToFront);
        propNameField.setText(propertyItem.getPreAndPostProperties().getName());
        resultNameField
                .setText(propertyItem.getPreAndPostProperties().getName());
    }

    /**
     * Sets the prop name field.
     *
     * @param newText
     *            the new prop name field
     */
    public void setPropNameField(final String newText) {
        propNameField.setText(newText);
    }

    /**
     * Show pop up.
     *
     * @param titleText
     *            the title text
     * @param infoText
     *            the info text
     * @param inTypeDescription
     *            the in type description
     * @param inTypes
     *            the in types
     * @param outTypeDescription
     *            the out type description
     * @param outTypes
     *            the out types
     * @return the tuple 3
     */
    private Tuple3<String, InputType, OutputType> showPopUp(
            final String titleText, final String infoText,
            final String inTypeDescription, final List<InputType> inTypes,
            final String outTypeDescription, final List<OutputType> outTypes) {
        final Point position = MouseInfo.getPointerInfo().getLocation();
        final Dialog<String> dialog = new Dialog<String>();
        dialog.setX(position.getX());
        dialog.setY(position.getY());
        dialog.setTitle(titleText);
        dialog.setHeaderText(infoText);

        final Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        // Add a custom icon.
        stage.getIcons().add(new Image(PATH_TO_IMAGES + BEAST_LOGO));
        final ButtonType buttonType = new ButtonType("OK", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(buttonType,
                                                       ButtonType.CANCEL);
        final GridPane grid = new GridPane();
        grid.setHgap(GAP_SIZE);
        grid.setVgap(GAP_SIZE);
        grid.setPadding(new Insets(TOP, WIDTH, GAP_SIZE, GAP_SIZE));
        // Populate the grid with the choices
        grid.add(new Label("Name:"), 0, 0);
        final TextField nameField = new TextField();
        grid.add(nameField, 1, 0);
        grid.add(new Label(inTypeDescription), 0, 1);
        final ChoiceBox<InputType> inputType =
                new ChoiceBox<InputType>(FXCollections.observableList(inTypes));
        inputType.getSelectionModel().selectFirst();
        grid.add(inputType, 1, 1);
        grid.add(new Label(outTypeDescription), 0, 2);
        final ChoiceBox<OutputType> outputType =
                new ChoiceBox<OutputType>(FXCollections.observableList(outTypes));
        outputType.getSelectionModel().selectFirst();
        grid.add(outputType, 1, 2);
        dialog.getDialogPane().setContent(grid);
        // Wait for the user to select
        final Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            final String newName = nameField.getText();
            if (isValidFileName(newName)) {
                final Tuple3<String, InputType, OutputType> toReturn =
                        new Tuple3<String, InputType, OutputType>(
                                newName, inputType.getValue(), outputType.getValue()
                                );
                return toReturn;
            } else {
                setErrorText("File name not valid, try again.");
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Taken from
     * https://stackoverflow.com/questions/6730009/validate-a-file-name-on-windows
     * The method checks if a name is allowed for the windows file system (more
     * restrictive than Linux), so we can just name the files like that.
     *
     * @param text
     *            the file name to check
     * @return true if the file name is valid, false if not
     */
    public static boolean isValidFileName(final String text) {
        final Pattern pattern = Pattern.compile(
                "# Match a valid Windows filename (unspecified file system).          \n"
                        + "^                                # Anchor to start of string.        \n"
                        + "(?!                              # Assert filename is not: CON, PRN, \n"
                        + "  (?:                            # AUX, NUL, COM1, COM2, COM3, COM4, \n"
                        + "    CON|PRN|AUX|NUL|             # COM5, COM6, COM7, COM8, COM9,     \n"
                        + "    COM[1-9]|LPT[1-9]            # LPT1, LPT2, LPT3, LPT4, LPT5,     \n"
                        + "  )                              # LPT6, LPT7, LPT8, and LPT9...     \n"
                        + "  (?:\\.[^.]*)?                  # followed by optional extension    \n"
                        + "  $                              # and end of string                 \n"
                        + ")                                # End negative lookahead assertion. \n"
                        + "[^<>:\"/\\\\|?*\\x00-\\x1F]*     # Zero or more valid filename chars.\n"
                        + "[^<>:\"/\\\\|?*\\x00-\\x1F\\ .]  # Last char is not a space or dot.  \n"
                        + "$                                # Anchor to end of string.            ",
                Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE
                        | Pattern.COMMENTS);
        final Matcher matcher = pattern.matcher(text);
        final boolean isMatch = matcher.matches();
        return isMatch;
    }

    /**
     * Creates the folder with name.
     *
     * @param desiredFolderName
     *            the desired folder name
     * @param clearFolder
     *            the clear folder
     * @return the file
     */
    private File createFolderWithName(final File desiredFolderName,
                                      final boolean clearFolder) {
        final String origFileNameWithougExt = FilenameUtils
                .removeExtension(desiredFolderName.getAbsolutePath());
        final File finalListFile = new File(origFileNameWithougExt);
        finalListFile.mkdirs();
        if (clearFolder) {
            try {
                FileUtils.cleanDirectory(finalListFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return finalListFile;
    }

    /**
     * Adds the property.
     *
     * @param prop
     *            the prop
     */
    public void addProperty(final PreAndPostConditionsDescription prop) {
        addTreeItem(prop);
    }

    /**
     * Sets the shortcuts to consume.
     *
     * @param shortcutsToConsume
     *            the new shortcuts to consume
     */
    public void setShortcutsToConsume(final InputMap<Event> shortcutsToConsume) {
        Nodes.addInputMap(codeArea, shortcutsToConsume);
        Nodes.addInputMap(preArea, shortcutsToConsume);
        Nodes.addInputMap(postArea, shortcutsToConsume);
        // TODO Maybe add more areas later
    }

    /**
     * Gets the focused area.
     *
     * @return the focused area
     */
    public MenuBarInterface getFocusedArea() {
        return focusedMainTab;
    }

    /**
     * Gets the auto completer.
     *
     * @return the auto completer
     */
    public AutoCompleter getAutoCompleter() {
        return autoComplete;
    }

    /**
     * Gets the result presenter.
     *
     * @return the result presenter
     */
    public ResultPresenterNEW getResultPresenter() {
        return ResultPresenterNEW.getInstance();
    }

    /**
     * Sets the presentation type text.
     *
     * @param name
     *            the new presentation type text
     */
    public void setPresentationTypeText(final String name) {
        this.displayFormat.setText(name);
    }

    /**
     * Disable zoom slider.
     *
     * @param disabled
     *            the disabled
     */
    public void disableZoomSlider(final boolean disabled) {
        zoomSlider.setDisable(disabled);
    }

    /**
     * Sets the eligible types.
     *
     * @param eligibleTypes
     *            the new eligible types
     */
    public void setEligibleTypes(final List<ResultPresentationType> eligibleTypes) {
        displayFormat.getItems().clear();
        for (Iterator<ResultPresentationType> iterator =
                    eligibleTypes.iterator();
                iterator.hasNext();) {
            final ResultPresentationType type =
                    (ResultPresentationType) iterator.next();
            displayFormat.getItems().add(type.getMenuItem());
        }
    }

    /**
     * Show save changes dialog.
     *
     * @param caller
     *            the caller
     */
    public void showSaveChangesDialog(final MinimalSaverInterface caller) {
        final Alert alert = new Alert(AlertType.CONFIRMATION);
        final Point position = MouseInfo.getPointerInfo().getLocation();
        alert.setX(position.getX() - alert.getWidth());
        alert.setY(position.getY() - alert.getHeight());
        alert.setTitle(CONF_DIALOG_TITLE);
        alert.setHeaderText(
                "You have unsaved changes. Do you want to change them?");

        final Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        // Add a custom icon.
        stage.getIcons().add(new Image(PATH_TO_IMAGES + BEAST_LOGO));

        final ButtonType buttonTypeSave = new ButtonType("Save");
        final ButtonType buttonTypeSaveAs = new ButtonType("Save as");
        final ButtonType buttonTypeCancel = new ButtonType("Cancel",
                                                           ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeSave, buttonTypeSaveAs,
                                      buttonTypeCancel);
        final Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeSave) {
            caller.save();
        } else if (result.get() == buttonTypeSaveAs) {
            caller.saveAs();
        }
        // else {
        // Do nothing
        // }
    }

    /**
     * Gets the max unrolls.
     *
     * @return the max unrolls
     */
    public int getMaxUnrolls() {
        final String text = maxUnrolls.getText();
        return "".equals(text) ? -1 : Integer.parseInt(maxUnrolls.getText());
    }

    /**
     * Sets the previous state.
     *
     * @param result
     *            the new previous state
     */
    public void setPreviousState(final Result result) {
        result.getPropertyDesctiption();
    }
}
