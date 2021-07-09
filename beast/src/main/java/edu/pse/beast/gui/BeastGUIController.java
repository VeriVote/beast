package edu.pse.beast.gui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;

import edu.pse.beast.api.c_parser.ExtractedCLoop;
import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.electiondescription.function.CElectionDescriptionFunction;
import edu.pse.beast.api.os.OS;
import edu.pse.beast.api.os.OSHelper;
import edu.pse.beast.api.paths.PathHandler;
import edu.pse.beast.api.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.api.savingloading.options.OptionsSaverLoader;
import edu.pse.beast.gui.ceditor.CEditorCodeElement;
import edu.pse.beast.gui.ceditor.CElectionEditor;
import edu.pse.beast.gui.errors.ErrorHandler;
import edu.pse.beast.gui.errors.ErrorMessageLoader;
import edu.pse.beast.gui.errors.ErrorGuiController;
import edu.pse.beast.gui.options.OptionsCategoryGUI;
import edu.pse.beast.gui.options.OptionsGUIController;
import edu.pse.beast.gui.options.ceditor.CEditorOptions;
import edu.pse.beast.gui.options.ceditor.CEditorOptionsGUI;
import edu.pse.beast.gui.options.process_handler.ProcessHandlerWindowsOptionsGUI;
import edu.pse.beast.gui.processHandler.CBMCProcessHandlerCreator;
import edu.pse.beast.gui.propertyeditor.PreAndPostPropertyEditor;
import edu.pse.beast.gui.propertyeditor.PropertyEditorCodeElement;
import edu.pse.beast.gui.testconfigeditor.TestConfigTopLevelGUIHandler;
import edu.pse.beast.gui.testconfigeditor.treeview.TestConfigTreeItemSuper;
import edu.pse.beast.gui.workspace.BeastWorkspace;
import edu.pse.beast.gui.workspace.WorkspaceUpdateListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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

public class BeastGUIController implements WorkspaceUpdateListener {

    // =================options start
    private String optionsFXML = "/edu/pse/beast/optionsGUI.fxml";
    private OptionsGUIController optionsGUIController;
    private FXMLLoader optionsFXMLLoader = new FXMLLoader(
            getClass().getResource(optionsFXML));
    // =================options end

    // =================Menu start
    @FXML
    private MenuBar menuBar;
    // =================Menu end

    @FXML
    private Button testLoopBoundButton;
    @FXML
    private TabPane topLeveLTabPane;

    private Stage primaryStage;

    @FXML
    private Button loadElectionDescriptionButton;

    @FXML
    private AnchorPane codePane;

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
    private ListView<SymbolicCBMCVar> symbVarsListView;

    @FXML
    private MenuButton addSymbVarMenu;

    @FXML
    private Button removeSymbVarButton;

    @FXML
    private TabPane propertyTestRunPane;

    @FXML
    private Button editLoopboundButton;

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
    private ChoiceBox<PreAndPostConditionsDescription> openedPropertyDescriptionChoiceBox;
    @FXML
    private Button addPropDescrButton;
    @FXML
    private Button loadPropDescrButton;
    @FXML
    private Button savePropDescrButton;

    // TestConfigHandler
    @FXML
    private ChoiceBox<String> sortCriteriumChoiceBox;
    @FXML
    private TreeView<TestConfigTreeItemSuper> testConfigTreeView;
    @FXML
    private AnchorPane testConfigDetailsAnchorPane;
    // TestConfigHandler end

    @FXML
    private AnchorPane logAnchorPane;

    @FXML
    private Button loadWorkspaceButton;
    @FXML
    private Button saveWorkspaceButton;

    private CElectionEditor cElectionEditor;
    private PreAndPostPropertyEditor preAndPostPropertyEditor;
    private TestConfigTopLevelGUIHandler testConfigurationHandler;
    private ErrorGuiController logGuiController;

    private BeastWorkspace beastWorkspace;

    private File optionsSaveFile;

    private CBMCProcessHandlerCreator cbmcProcessHandlerCreator = new CBMCProcessHandlerCreator();;

    private void addChildToAnchorPane(AnchorPane pane, Node child, double top,
            double bottom, double left, double right) {
        codePane.getChildren().add(child);
        AnchorPane.setTopAnchor(child, top);
        AnchorPane.setLeftAnchor(child, left);
        AnchorPane.setRightAnchor(child, right);
        AnchorPane.setBottomAnchor(child, bottom);
    }

    private void initElectionEditor() {
        CodeArea funcDeclArea = new CodeArea();
        codePane.getChildren().add(funcDeclArea);
        AnchorPane.setTopAnchor(funcDeclArea, 0d);
        AnchorPane.setLeftAnchor(funcDeclArea, 0d);
        AnchorPane.setRightAnchor(funcDeclArea, 0d);

        CodeArea closingBracketArea = new CodeArea();
        codePane.getChildren().add(closingBracketArea);
        AnchorPane.setBottomAnchor(closingBracketArea, 0d);
        AnchorPane.setLeftAnchor(closingBracketArea, 0d);
        AnchorPane.setRightAnchor(closingBracketArea, 0d);

        CEditorCodeElement cEditorGUIElement = new CEditorCodeElement();
        VirtualizedScrollPane<CEditorCodeElement> cEditorGUIElementVsp = new VirtualizedScrollPane(
                cEditorGUIElement);
        addChildToAnchorPane(codePane, cEditorGUIElementVsp, 20, 100, 0, 0);

        cElectionEditor = new CElectionEditor(primaryStage,
                cEditorGUIElementVsp, addElectionDescriptionButton,
                deleteDescrButton, loadElectionDescriptionButton,
                saveElectionDescriptionButton, editDescrButton,
                addFunctionMenuButton, removeFunctionButton,
                testLoopBoundButton, editLoopboundButton, cEditorGUIElement,
                funcDeclArea, closingBracketArea, functionList, loopBoundList,
                openedElectionDescriptionChoiceBox, beastWorkspace);
    }

    private void initPropertyEditor() {
        PropertyEditorCodeElement prePropertyEditor = new PropertyEditorCodeElement();
        PropertyEditorCodeElement postPropertyEditor = new PropertyEditorCodeElement();

        VirtualizedScrollPane<PropertyEditorCodeElement> preVsp = new VirtualizedScrollPane<>(
                prePropertyEditor);
        VirtualizedScrollPane<PropertyEditorCodeElement> postVsp = new VirtualizedScrollPane<>(
                postPropertyEditor);

        prePropertyPane.setContent(preVsp);
        postPropertyPane.setContent(postVsp);

        preAndPostPropertyEditor = new PreAndPostPropertyEditor(
                prePropertyEditor, postPropertyEditor, addPropDescrButton,
                loadPropDescrButton, savePropDescrButton, removeSymbVarButton,
                symbVarsListView, addSymbVarMenu,
                openedPropertyDescriptionChoiceBox, beastWorkspace);
    }

    private void initTestConfigHandler() throws IOException {

        this.testConfigurationHandler = new TestConfigTopLevelGUIHandler(
                sortCriteriumChoiceBox, testConfigTreeView,
                testConfigDetailsAnchorPane, beastWorkspace);
    }

    private void initLogHandler(ErrorHandler errorHandler) throws IOException {
        logGuiController = new ErrorGuiController(logAnchorPane, errorHandler);
    }

    private void initWorkspace(ErrorHandler errorHandler) {
        beastWorkspace = BeastWorkspace
                .getStandardWorkspace(cbmcProcessHandlerCreator);

        beastWorkspace.setErrorHandler(errorHandler);

        saveWorkspaceButton.setOnAction(e -> {
            beastWorkspace.saveWorkspace();
        });
        loadWorkspaceButton.setOnAction(e -> {
            beastWorkspace.letUserLoadWorkSpace();
        });
    }

    private void initMenu() {
        Menu fileMenu = new Menu();
        fileMenu.setText("File");

        MenuItem loadWorkspaceMenuItem = new MenuItem("Load Workspace");
        MenuItem saveWorkspaceMenuItem = new MenuItem("Save Workspace");

        loadWorkspaceMenuItem.setOnAction(e -> {
            beastWorkspace.letUserLoadWorkSpace();
        });

        saveWorkspaceMenuItem.setOnAction(e -> {
            beastWorkspace.saveWorkspace();
        });

        fileMenu.getItems().add(loadWorkspaceMenuItem);
        fileMenu.getItems().add(saveWorkspaceMenuItem);

        Menu prefMenu = new Menu();
        prefMenu.setText("Preferences");

        MenuItem optionsMenuItem = new MenuItem();
        optionsMenuItem.setText("Options");
        optionsMenuItem.setOnAction(e -> {
            optionsGUIController.display();
        });
        prefMenu.getItems().add(optionsMenuItem);

        menuBar.getMenus().add(fileMenu);
        menuBar.getMenus().add(prefMenu);
    }

    private List<OptionsCategoryGUI> createStandardOptionsAndSave(
            File optionsFile) throws IOException {
        OS os = OSHelper.getOS();
        List<OptionsCategoryGUI> options = new ArrayList<>();
        if (os == OS.WINDOWS) {
            cbmcProcessHandlerCreator.askUserForCBMCProcessHandler();
            ProcessHandlerWindowsOptionsGUI processHandlerWindowsOptions = new ProcessHandlerWindowsOptionsGUI(
                    cbmcProcessHandlerCreator);
            options.add(processHandlerWindowsOptions);
        }

        options.add(new CEditorOptionsGUI(new CEditorOptions()));

        OptionsSaverLoader.saveOptions(optionsFile, options);

        return options;
    }

    private void initOptionsGUIController(PathHandler pathHandler)
            throws IOException {
        List<OptionsCategoryGUI> options = new ArrayList<>();

        optionsSaveFile = pathHandler.getOptionsFile();
        if (!optionsSaveFile.exists()) {
            options = createStandardOptionsAndSave(optionsSaveFile);
        } else {
            options = OptionsSaverLoader.loadOptions(optionsSaveFile);
            for (OptionsCategoryGUI cat : options) {
                switch (cat.getCategory()) {
                case PROCESS_HANDLER_WINDOWS:
                    cbmcProcessHandlerCreator = ((ProcessHandlerWindowsOptionsGUI) cat)
                            .getCbmcProcessHandlerCreator();
                    break;
                }
            }
        }

        optionsGUIController = new OptionsGUIController(options,
                optionsSaveFile);
        optionsFXMLLoader.setController(optionsGUIController);
        optionsFXMLLoader.load();
    }

    private void linkOptions() {
        for (OptionsCategoryGUI cat : optionsGUIController.getCategories()) {
            switch (cat.getCategory()) {
            case C_DESCR_EDITOR:
                ((CEditorOptionsGUI) cat).setcElectionEditor(cElectionEditor);
                break;
            }
        }

    }

    @FXML
    public void initialize() throws IOException {
        // option Controller
        PathHandler pathHandler = new PathHandler();
        initOptionsGUIController(pathHandler);

        // init gui
        ErrorMessageLoader errorMessageLoader = new ErrorMessageLoader();
        ErrorHandler errorHandler = new ErrorHandler(errorMessageLoader);

        initWorkspace(errorHandler);
        initElectionEditor();
        initPropertyEditor();
        initTestConfigHandler();
        initLogHandler(errorHandler);

        linkOptions();

        initMenu();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public void handleWorkspaceUpdateGeneric() {
    }

    public void shutdown() {
        optionsGUIController.saveOptions();
        beastWorkspace.shutdown();
    }

}
