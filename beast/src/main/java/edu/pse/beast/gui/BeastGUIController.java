package edu.pse.beast.gui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;

import edu.pse.beast.api.codegen.cbmc.SymbolicVariable;
import edu.pse.beast.api.cparser.ExtractedCLoop;
import edu.pse.beast.api.io.PathHandler;
import edu.pse.beast.api.io.options.OptionsInputOutput;
import edu.pse.beast.api.method.CElectionDescription;
import edu.pse.beast.api.method.function.CElectionDescriptionFunction;
import edu.pse.beast.api.os.OS;
import edu.pse.beast.api.os.OSHelper;
import edu.pse.beast.api.property.PropertyDescription;
import edu.pse.beast.gui.ceditor.CEditorCodeElement;
import edu.pse.beast.gui.ceditor.CElectionEditor;
import edu.pse.beast.gui.configurationeditor.ConfigurationTopLevelGUIHandler;
import edu.pse.beast.gui.configurationeditor.treeview.ConfigurationItem;
import edu.pse.beast.gui.error.ErrorGUIController;
import edu.pse.beast.gui.error.ErrorHandler;
import edu.pse.beast.gui.error.ErrorMessageLoader;
import edu.pse.beast.gui.options.OptionsCategoryGUI;
import edu.pse.beast.gui.options.OptionsGUIController;
import edu.pse.beast.gui.options.ceditor.CEditorOptions;
import edu.pse.beast.gui.options.ceditor.CEditorOptionsGUI;
import edu.pse.beast.gui.options.processhandler.ProcessHandlerWindowsOptionsGUI;
import edu.pse.beast.gui.processhandler.CBMCProcessHandlerCreator;
import edu.pse.beast.gui.propertyeditor.PropertyEditor;
import edu.pse.beast.gui.propertyeditor.PropertyEditorCodeElement;
import edu.pse.beast.gui.workspace.BeastWorkspace;
import edu.pse.beast.gui.workspace.WorkspaceUpdateListener;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class BeastGUIController implements WorkspaceUpdateListener {
    // =================Options Start
    private static final String NEW_WS_TEXT = "New";
    private static final String LOAD_WS_TEXT = "Load";
    private static final String SAVE_WS_TEXT = "Save";
    private static final String QUIT_WS_TEXT = "Quit";
    private static final String FILE_TEXT = "Workspace";
    private static final String PREFERENCES_TEXT = "Preferences";
    private static final String OPTIONS_TEXT = "Options";

    private static final int TOP_OFFSET = 20;
    private static final int BOTTOM_OFFSET = 100;
    private static final int LEFT_OFFSET = 0;
    private static final int RIGHT_OFFSET = 0;

    private static final String OPTIONS_FXML = "/edu/pse/beast/gui/options.fxml";
    private OptionsGUIController optionsGUIController;
    private FXMLLoader optionsFXMLLoader =
            new FXMLLoader(getClass().getResource(OPTIONS_FXML));
    // =================Options End

    // =================Menu Start
    @FXML
    private MenuBar menuBar;
    // =================Menu End

    @FXML
    private Button computeLoopBoundButton;
    @FXML
    private TabPane topLeveLTabPane;

    private Stage primaryStage;

    @FXML
    private Button loadElectionDescriptionButton;

    @FXML
    private AnchorPane codePane;

    @FXML
    private Label descriptionInputStatus;

    @FXML
    private Label descriptionOutputStatus;

    @FXML
    private MenuButton addFunctionMenuButton;

    @FXML
    private Button removeFunctionButton;

    @FXML
    private Button editDescrButton;

    @FXML
    private ListView<ExtractedCLoop> loopBoundList;

    @FXML
    private ListView<CElectionDescriptionFunction> functionList;

    @FXML
    private TitledPane prePropertyPane;

    @FXML
    private TitledPane postPropertyPane;

    @FXML
    private ListView<SymbolicVariable> symbVarsListView;

    @FXML
    private MenuButton addSymbVarMenu;

    @FXML
    private Button removeSymbVarButton;

    @FXML
    private Button editLoopBoundButton;

    @FXML
    private Button deleteDescrButton;
    @FXML
    private Button deletePropDescrButton;
    @FXML
    private ChoiceBox<CElectionDescription> openedElectionDescriptionChoiceBox;
    @FXML
    private Button addElectionDescriptionButton;
    @FXML
    private Button saveElectionDescriptionButton;

    @FXML
    private ChoiceBox<PropertyDescription> openedPropertyDescriptionChoiceBox;
    @FXML
    private Button addPropDescrButton;
    @FXML
    private Button loadPropDescrButton;
    @FXML
    private Button savePropDescrButton;

    // ConfigurationHandler Start
    @FXML
    private ChoiceBox<String> sortCriteriumChoiceBox;
    @FXML
    private TreeView<ConfigurationItem> configurationTreeView;
    @FXML
    private AnchorPane configDetailsAnchorPane;
    // ConfigurationHandler End

    @FXML
    private AnchorPane logAnchorPane;

    private CElectionEditor cElectionEditor;

    private PropertyEditor propertyEditor;

    private BeastWorkspace beastWorkspace;

    private File optionsSaveFile;

    private CBMCProcessHandlerCreator cbmcProcessHandlerCreator = new CBMCProcessHandlerCreator();

    private static void addChildToAnchorPane(final AnchorPane pane,
                                             final Node child,
                                             final double top,
                                             final double bottom,
                                             final double left,
                                             final double right) {
        pane.getChildren().add(child);
        AnchorPane.setTopAnchor(child, top);
        AnchorPane.setLeftAnchor(child, left);
        AnchorPane.setRightAnchor(child, right);
        AnchorPane.setBottomAnchor(child, bottom);
    }

    private void initElectionEditor() {
        final CodeArea funcDeclArea = new CodeArea();
        codePane.getChildren().add(funcDeclArea);
        AnchorPane.setTopAnchor(funcDeclArea, 0d);
        AnchorPane.setLeftAnchor(funcDeclArea, 0d);
        AnchorPane.setRightAnchor(funcDeclArea, 0d);

        final CodeArea closingBracketArea = new CodeArea();
        codePane.getChildren().add(closingBracketArea);
        AnchorPane.setBottomAnchor(closingBracketArea, 0d);
        AnchorPane.setLeftAnchor(closingBracketArea, 0d);
        AnchorPane.setRightAnchor(closingBracketArea, 0d);

        final String empty = "–";
        descriptionInputStatus.setText(empty);
        descriptionOutputStatus.setText(empty);

        final CEditorCodeElement cEditorGUIElement = new CEditorCodeElement();
        final VirtualizedScrollPane<CEditorCodeElement> cEditorGUIElementVsp =
                new VirtualizedScrollPane<CEditorCodeElement>(cEditorGUIElement);
        addChildToAnchorPane(codePane, cEditorGUIElementVsp,
                             TOP_OFFSET, BOTTOM_OFFSET,
                             LEFT_OFFSET, RIGHT_OFFSET);
        final CElectionEditor.ElectionDescriptionButtons electDescButtons =
                new CElectionEditor.ElectionDescriptionButtons(addElectionDescriptionButton,
                                                               deleteDescrButton,
                                                               loadElectionDescriptionButton,
                                                               saveElectionDescriptionButton,
                                                               editDescrButton,
                                                               openedElectionDescriptionChoiceBox);
        final CElectionEditor.FunctionEditor functionEditor =
                new CElectionEditor.FunctionEditor(addFunctionMenuButton, removeFunctionButton,
                                                   funcDeclArea, functionList);
        final CElectionEditor.LoopBoundEditor loopBoundEditor =
                new CElectionEditor.LoopBoundEditor(computeLoopBoundButton, editLoopBoundButton,
                                                    loopBoundList);
        final CElectionEditor.CodeAreas codeAreas =
                new CElectionEditor.CodeAreas(cEditorGUIElement, closingBracketArea,
                                              descriptionInputStatus, descriptionOutputStatus);
        cElectionEditor =
                new CElectionEditor(primaryStage, cEditorGUIElementVsp, electDescButtons,
                                    functionEditor, loopBoundEditor, codeAreas, beastWorkspace);
    }

    private void initPropertyEditor() {
        final PropertyEditorCodeElement prePropertyEditor = new PropertyEditorCodeElement();
        final PropertyEditorCodeElement postPropertyEditor = new PropertyEditorCodeElement();

        final VirtualizedScrollPane<PropertyEditorCodeElement> preVsp =
                new VirtualizedScrollPane<PropertyEditorCodeElement>(prePropertyEditor);
        final VirtualizedScrollPane<PropertyEditorCodeElement> postVsp =
                new VirtualizedScrollPane<PropertyEditorCodeElement>(postPropertyEditor);
        prePropertyPane.setContent(preVsp);
        postPropertyPane.setContent(postVsp);
        final PropertyEditor.Buttons buttons =
                new PropertyEditor.Buttons(addPropDescrButton, loadPropDescrButton,
                                           savePropDescrButton, deletePropDescrButton,
                                           removeSymbVarButton);
        propertyEditor =
                new PropertyEditor(prePropertyEditor, postPropertyEditor, buttons,
                                   symbVarsListView, addSymbVarMenu,
                                   openedPropertyDescriptionChoiceBox, beastWorkspace);
    }

    private void initConfigurationHandler() throws IOException {
        new ConfigurationTopLevelGUIHandler(sortCriteriumChoiceBox, configurationTreeView,
                                         configDetailsAnchorPane, beastWorkspace);
    }

    private void initLogHandler(final ErrorHandler errorHandler) throws IOException {
        new ErrorGUIController(logAnchorPane, errorHandler);
    }

    private void initWorkspace(final ErrorHandler errorHandler) {
        beastWorkspace = BeastWorkspace
                .getStandardWorkspace(cbmcProcessHandlerCreator);
        beastWorkspace.setErrorHandler(errorHandler);
    }

    private void initMenu() {
        final Menu fileMenu = new Menu();
        fileMenu.setText(FILE_TEXT);
        final MenuItem newWorkspaceMenuItem = new MenuItem(NEW_WS_TEXT);
        final MenuItem loadWorkspaceMenuItem = new MenuItem(LOAD_WS_TEXT);
        final MenuItem saveWorkspaceMenuItem = new MenuItem(SAVE_WS_TEXT);
        final MenuItem exitWorkspaceMenuItem = new MenuItem(QUIT_WS_TEXT);

        loadWorkspaceMenuItem.setOnAction(e -> {
            beastWorkspace.letUserLoadWorkSpace();
        });
        saveWorkspaceMenuItem.setOnAction(e -> {
            beastWorkspace.saveWorkspace();
        });
        exitWorkspaceMenuItem.setOnAction(e -> {
            beastWorkspace.shutdown();
            Platform.exit();
        });
        newWorkspaceMenuItem.setOnAction(e -> {
            beastWorkspace.newWorkspace();
        });
        fileMenu.getItems().add(newWorkspaceMenuItem);
        fileMenu.getItems().add(loadWorkspaceMenuItem);
        fileMenu.getItems().add(saveWorkspaceMenuItem);
        fileMenu.getItems().add(exitWorkspaceMenuItem);

        final Menu prefMenu = new Menu();
        prefMenu.setText(PREFERENCES_TEXT);
        final MenuItem optionsMenuItem = new MenuItem();
        optionsMenuItem.setText(OPTIONS_TEXT);
        optionsMenuItem.setOnAction(e -> {
            optionsGUIController.display();
        });
        prefMenu.getItems().add(optionsMenuItem);
        menuBar.getMenus().add(fileMenu);
        menuBar.getMenus().add(prefMenu);
    }

    private List<OptionsCategoryGUI> createStandardOptionsAndSave(final File optionsFile)
            throws IOException {
        final OS os = OSHelper.getOS();
        final List<OptionsCategoryGUI> options = new ArrayList<OptionsCategoryGUI>();
        if (os == OS.WINDOWS) {
            cbmcProcessHandlerCreator.askUserForCBMCProcessHandler();
            final ProcessHandlerWindowsOptionsGUI processHandlerWindowsOptions =
                    new ProcessHandlerWindowsOptionsGUI(cbmcProcessHandlerCreator);
            options.add(processHandlerWindowsOptions);
        }
        options.add(new CEditorOptionsGUI(new CEditorOptions()));
        OptionsInputOutput.saveOptions(optionsFile, options);
        return options;
    }

    private void initOptionsGUIController(final PathHandler pathHandler) throws IOException {
        final List<OptionsCategoryGUI> options;

        optionsSaveFile = pathHandler.getOptionsFile();
        if (!optionsSaveFile.exists()) {
            options = createStandardOptionsAndSave(optionsSaveFile);
        } else {
            options = OptionsInputOutput.loadOptions(optionsSaveFile);
            for (final OptionsCategoryGUI cat : options) {
                switch (cat.getCategory()) {
                case PROCESS_HANDLER_WINDOWS:
                    cbmcProcessHandlerCreator = ((ProcessHandlerWindowsOptionsGUI) cat)
                            .getCbmcProcessHandlerCreator();
                    break;
                default:
                    break;
                }
            }
        }
        optionsGUIController =
                new OptionsGUIController(options, optionsSaveFile);
        optionsFXMLLoader.setController(optionsGUIController);
        optionsFXMLLoader.load();
    }

    private void linkOptions() {
        for (final OptionsCategoryGUI cat : optionsGUIController.getCategories()) {
            switch (cat.getCategory()) {
            case C_DESCR_EDITOR:
                ((CEditorOptionsGUI) cat).setcElectionEditor(cElectionEditor);
                break;
            default:
                break;
            }
        }
    }

    @FXML
    public final void initialize() throws IOException {
        // option Controller
        final PathHandler pathHandler = new PathHandler();
        initOptionsGUIController(pathHandler);

        // init gui
        final ErrorMessageLoader errorMessageLoader = new ErrorMessageLoader();
        final ErrorHandler errorHandler = new ErrorHandler(errorMessageLoader);

        initWorkspace(errorHandler);
        initElectionEditor();
        initPropertyEditor();
        initConfigurationHandler();
        initLogHandler(errorHandler);

        linkOptions();

        initMenu();
    }

    public final void setPrimaryStage(final Stage primStage) {
        this.primaryStage = primStage;
    }

    @Override
    public final void handleWorkspaceUpdateGeneric() {
    }

    public final void shutdown() {
        optionsGUIController.saveOptions();
        beastWorkspace.shutdown();
    }
}
