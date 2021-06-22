package edu.pse.beast.gui;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.NotImplementedException;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.json.JSONException;

import edu.pse.beast.api.c_parser.ExtractedCLoop;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.electiondescription.VotingInputTypes;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;
import edu.pse.beast.api.electiondescription.function.CElectionDescriptionFunction;
import edu.pse.beast.api.savingloading.SavingLoadingInterface;
import edu.pse.beast.api.testrunner.propertycheck.processes.process_handler.CBMCProcessHandler;
import edu.pse.beast.api.testrunner.propertycheck.processes.process_handler.CBMCProcessHandlerWindows;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.gui.ceditor.CEditorCodeElement;
import edu.pse.beast.gui.ceditor.CElectionEditor;
import edu.pse.beast.gui.log.LogGuiController;
import edu.pse.beast.gui.paths.PathHandler;
import edu.pse.beast.gui.propertyeditor.PreAndPostPropertyEditor;
import edu.pse.beast.gui.propertyeditor.PropertyEditorCodeElement;
import edu.pse.beast.gui.testconfigeditor.TestConfigurationTopLevelGUIHandler;
import edu.pse.beast.gui.testconfigeditor.testconfig.TestConfiguration;
import edu.pse.beast.gui.testconfigeditor.testconfig.cbmc.CBMCPropertyTestConfiguration;
import edu.pse.beast.gui.testconfigeditor.treeview.TestConfigTreeItemSuper;
import edu.pse.beast.gui.workspace.BeastWorkspace;
import edu.pse.beast.gui.workspace.WorkspaceUpdateListener;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class BeastGUIController implements WorkspaceUpdateListener {

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
	private ListView<ExtractedCLoop> loopBoundList;

	@FXML
	private ListView<CElectionDescriptionFunction> functionList;

	@FXML
	private TitledPane prePropertyPane;

	@FXML
	private TitledPane postPropertyPane;

	@FXML
	private TreeView<String> variableTreeView;

	@FXML
	private MenuButton addSymbVarMenu;

	@FXML
	private TabPane propertyTestRunPane;

	@FXML
	private ChoiceBox<CElectionDescription> openedElectionDescriptionChoiceBox;
	@FXML
	private Button addElectionDescriptionButton;

	@FXML
	private ChoiceBox<PreAndPostConditionsDescription> openedPropertyDescriptionChoiceBox;
	@FXML
	private Button addPropDescrButton;

	// TestConfigHandler
	@FXML
	private ChoiceBox<String> sortCriteriumChoiceBox;
	@FXML
	private TreeView<TestConfigTreeItemSuper> testConfigTreeView;
	@FXML
	private Button addTestConfigButton;
	@FXML
	private Button removeTestConfigButton;
	@FXML
	private Button startTestConfigButton;
	@FXML
	private Button stopTestConfigButton;
	@FXML
	private AnchorPane testConfigDetailsAnchorPane;
	// TestConfigHandler end

	@FXML
	private AnchorPane logAnchorPane;

	@FXML
	private Button saveButton;
	@FXML
	private Button saveAllButton;
	@FXML
	private Button saveWorkspaceButton;

	private CElectionEditor cElectionEditor;
	private PreAndPostPropertyEditor preAndPostPropertyEditor;
	private TestConfigurationTopLevelGUIHandler testConfigurationHandler;
	private LogGuiController logGuiController;

	private BeastWorkspace beastWorkspace;

	private CElectionDescription getTestDescr() {
		String name = "test";
		CElectionDescription descr = new CElectionDescription(
				VotingInputTypes.PREFERENCE, VotingOutputTypes.CANDIDATE_LIST,
				"test");
		descr.getVotingFunction()
				.setCode("for(int i = 0; i < V; ++i) {}\n" + "return 0;\n");

		descr.createNewVotingSigFunctionAndAdd("votehelper");

		File f = new File("testfiles/borda.belec");
		try {
			descr = SavingLoadingInterface.loadCElection(f);
		} catch (NotImplementedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return descr;
	}

	private PreAndPostConditionsDescription getTestProperty() {
		File f = new File("testfiles/reinforcement.bprp");
		PreAndPostConditionsDescription test;
		try {
			test = SavingLoadingInterface.loadPreAndPostConditionDescription(f);
			return test;
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	
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

		cElectionEditor = new CElectionEditor(
				primaryStage,
				cEditorGUIElementVsp,
				addElectionDescriptionButton,
				loadElectionDescriptionButton,
				addFunctionMenuButton, 
				removeFunctionButton,
				testLoopBoundButton,
				cEditorGUIElement, 
				funcDeclArea,
				closingBracketArea, 
				functionList, loopBoundList,
				openedElectionDescriptionChoiceBox,
				beastWorkspace);
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
				variableTreeView, addSymbVarMenu,
				openedPropertyDescriptionChoiceBox, beastWorkspace);
	}

	private void initTestConfigHandler() throws IOException {

		this.testConfigurationHandler = new TestConfigurationTopLevelGUIHandler(
				startTestConfigButton, stopTestConfigButton,
				sortCriteriumChoiceBox, testConfigTreeView,
				testConfigDetailsAnchorPane, beastWorkspace);
	}

	private void initLogHandler(ErrorHandler errorHandler) {
		logGuiController = new LogGuiController(logAnchorPane, errorHandler);
	}

	@FXML
	public void initialize() throws IOException {
		PathHandler pathHandler = new PathHandler();
		
		//TODO check if we have a a workspace which was open in the last session
		beastWorkspace = BeastWorkspace.getStandardWorkspace();
		
		ErrorHandler errorHandler = new ErrorHandler(this);
		beastWorkspace.setErrorHandler(errorHandler);

		initElectionEditor();
		initPropertyEditor();
		initTestConfigHandler();
		initLogHandler(errorHandler);

		saveButton.setOnAction(e -> {
			Tab selectedTab = topLeveLTabPane.getSelectionModel()
					.getSelectedItem();
			String id = selectedTab.getId().toString();
			if (id.equals("election")) {
				cElectionEditor.save();
			} else if (id.equals("property")) {
				preAndPostPropertyEditor.save();
			}
		});

		saveAllButton.setOnAction(e -> {
			beastWorkspace.saveAll();
		});

		saveWorkspaceButton.setOnAction(e -> {
			beastWorkspace.saveWorkspace();
		});		
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;

	}

	@Override
	public void handleWorkspaceUpdateGeneric() {
	}

	public void shutdown() {
		beastWorkspace.shutdown();
	}

}
