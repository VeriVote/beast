package edu.pse.beast.gui;

import java.io.File;
import java.io.IOException;

import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.json.JSONException;

import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.electiondescription.VotingInputTypes;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;
import edu.pse.beast.api.savingloading.SavingLoadingInterface;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.gui.elements.CEditorElement;
import edu.pse.beast.gui.elements.PropertyEditorElement;
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

public class BeastGUIController {
	@FXML
	private AnchorPane codePane;

	@FXML
	private ListView<String> loopBoundList;

	@FXML
	private ListView<String> functionList;

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
	private ChoiceBox<String> openedElectionDescriptionChoiceBox;
	@FXML
	private Button addElectionDescriptionButton;

	@FXML
	private ChoiceBox<String> openedPropertyDescriptionChoiceBox;
	@FXML
	private Button addPropDescrButton;

	// TestConfigHandler
	@FXML
	private TreeView testConfigTreeView;
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

	private CodeGenOptions codeGenOptions;
	private CElectionEditor cElectionEditor;
	private PreAndPostPropertyEditor preAndPostPropertyEditor;
	private TestConfigurationHandler testConfigurationHandler;

	private BeastWorkspace beastWorkspace = new BeastWorkspace();

	private CElectionDescription getTestDescr() {
		String name = "test";
		CElectionDescription descr = new CElectionDescription(
				VotingInputTypes.WEIGHTED_APPROVAL,
				VotingOutputTypes.PARLIAMENT_STACK, "test");
		descr.getVotingFunction()
				.setCode("for(int i = 0; i < V; ++i) {}\n" + "return 0;\n");
		descr.addLoopBoundForFunction("voting", 0, "V");

		descr.createNewVotingSigFunctionAndAdd("votehelper");
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

	private TestConfiguration getTestConfig(CElectionDescription descr,
			PreAndPostConditionsDescription propDescr) {

		TestConfiguration created = new TestConfiguration(descr);
		CBMCPropertyTestConfiguration cc = new CBMCPropertyTestConfiguration();
		cc.setMinVoters(5);
		cc.setMinCands(5);
		cc.setMinSeats(5);

		cc.setMaxCands(6);
		cc.setMaxVoters(6);
		cc.setMaxSeats(6);

		cc.setDescr(descr);
		cc.setPropDescr(propDescr);

		cc.setName("5-6");

		created.getCbmcPropertyTestConfigurations().add(cc);

		return created;
	}

	@FXML
	public void addFunction() {
		cElectionEditor.addFunction();
	}

	@FXML
	public void removeFunction() {
		cElectionEditor.removeFunction();
	}

	@FXML
	public void addLoopBound() {
		cElectionEditor.addLoopBound();
	}

	@FXML
	public void removeLoopBound() {
		cElectionEditor.removeLoopBound();
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

		CEditorElement cEditor = new CEditorElement();
		VirtualizedScrollPane<CEditorElement> vsp = new VirtualizedScrollPane(
				cEditor);
		addChildToAnchorPane(codePane, vsp, 20, 100, 0, 0);

		cElectionEditor = new CElectionEditor(codeGenOptions, cEditor,
				funcDeclArea, closingBracketArea, functionList, loopBoundList,
				openedElectionDescriptionChoiceBox, beastWorkspace);
	}

	private void initPropertyEditor() {
		PropertyEditorElement prePropertyEditor = new PropertyEditorElement();
		PropertyEditorElement postPropertyEditor = new PropertyEditorElement();

		VirtualizedScrollPane<PropertyEditorElement> preVsp = new VirtualizedScrollPane<>(
				prePropertyEditor);
		VirtualizedScrollPane<PropertyEditorElement> postVsp = new VirtualizedScrollPane<>(
				postPropertyEditor);

		prePropertyPane.setContent(preVsp);
		postPropertyPane.setContent(postVsp);

		preAndPostPropertyEditor = new PreAndPostPropertyEditor(
				prePropertyEditor, postPropertyEditor, variableTreeView,
				addSymbVarMenu, openedPropertyDescriptionChoiceBox,
				beastWorkspace);
	}

	private void initTestConfigHandler() throws IOException {
		this.testConfigurationHandler = new TestConfigurationHandler(
				testConfigTreeView, testConfigDetailsAnchorPane,
				beastWorkspace);
	}

	@FXML
	public void initialize() throws IOException {
		this.codeGenOptions = new CodeGenOptions();
		codeGenOptions.setCbmcAmountCandidatesVarName("C");
		codeGenOptions.setCbmcAmountVotesVarName("V");
		
		CElectionDescription descr = getTestDescr();
		PreAndPostConditionsDescription propDescr = getTestProperty();

		beastWorkspace.getLoadedDescrs().add(getTestDescr());
		beastWorkspace.getLoadedPropDescrs().add(getTestProperty());
		beastWorkspace.getTestConfigs().add(getTestConfig(descr, propDescr));

		initElectionEditor();
		initPropertyEditor();
		initTestConfigHandler();
	}
}
