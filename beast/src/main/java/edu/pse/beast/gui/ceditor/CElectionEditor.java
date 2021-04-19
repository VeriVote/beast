package edu.pse.beast.gui.ceditor;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.NotImplementedException;
import org.fxmisc.richtext.CodeArea;

import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.electiondescription.VotingInputTypes;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;
import edu.pse.beast.api.electiondescription.VotingSigFunction;
import edu.pse.beast.gui.DialogHelper;
import edu.pse.beast.gui.OpenFileDialogHelper;
import edu.pse.beast.gui.workspace.BeastWorkspace;
import edu.pse.beast.gui.workspace.WorkspaceErrorEvent;
import edu.pse.beast.gui.workspace.WorkspaceUpdateEvent;
import edu.pse.beast.gui.workspace.WorkspaceUpdateEventType;
import edu.pse.beast.gui.workspace.WorkspaceUpdateListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CElectionEditor implements WorkspaceUpdateListener {
	private final String cssResource = "/edu/pse/beast/ceditor.css";
	private final String cssLockedClassName = "locked";

	private ListView<String> functionList;
	private ListView<String> loopBoundList;

	private CodeArea funcDeclArea;
	private CodeArea closingBracketArea;
	private CEditorCodeElement electionCodeArea;
	private ChoiceBox<String> openedElectionDescriptionChoiceBox;

	private CElectionDescription currentDescr;

	private BeastWorkspace beastWorkspace;

	private Stage primaryStage;

	public CElectionEditor(Stage primaryStage,
			CEditorCodeElement electionCodeArea, CodeArea funcDeclArea,
			CodeArea closingBracketArea, ListView<String> functionList,
			ListView<String> loopBoundList,
			ChoiceBox<String> openedElectionDescriptionChoiceBox,
			BeastWorkspace beastWorkspace) {
		final String stylesheet = this.getClass().getResource(cssResource)
				.toExternalForm();

		this.primaryStage = primaryStage;

		this.functionList = functionList;
		this.loopBoundList = loopBoundList;

		this.electionCodeArea = electionCodeArea;
		this.funcDeclArea = funcDeclArea;
		this.closingBracketArea = closingBracketArea;

		this.funcDeclArea.setEditable(false);
		this.closingBracketArea.setEditable(false);

		electionCodeArea.getStylesheets().add(stylesheet);
		funcDeclArea.getStylesheets().add(stylesheet);
		closingBracketArea.getStylesheets().add(stylesheet);

		this.beastWorkspace = beastWorkspace;
		this.openedElectionDescriptionChoiceBox = openedElectionDescriptionChoiceBox;

		initListViews();
		initOpenedDescrChoiceBox();
		handleWorkspaceUpdate(
				WorkspaceUpdateEvent.fromType(WorkspaceUpdateEventType.ALL));
		beastWorkspace.registerUpdateListener(this);
	}

	private void selectedDescrChanged(String newSelectedName) {
		if (newSelectedName == null)
			return;
		CElectionDescription descr = beastWorkspace
				.getDescrByName(newSelectedName);
		loadElectionDescr(descr);
	}

	private void initOpenedDescrChoiceBox() {
		openedElectionDescriptionChoiceBox.getSelectionModel()
				.selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> {
					selectedDescrChanged(newValue);
				});
	}

	public void handleWorkspaceUpdate(WorkspaceUpdateEvent evt) {
		openedElectionDescriptionChoiceBox.getItems().clear();
		for (CElectionDescription descr : beastWorkspace.getLoadedDescrs()) {
			openedElectionDescriptionChoiceBox.getItems().add(descr.getName());
		}
		openedElectionDescriptionChoiceBox.getSelectionModel().selectLast();
	}

	private void initListViews() {
		functionList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		functionList.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends String> observable,
						String oldValue, String newValue) -> {
					selectedFunctionChanged(newValue);
				});

		loopBoundList.getSelectionModel()
				.setSelectionMode(SelectionMode.SINGLE);

	}

	private void selectedFunctionChanged(String selectedFunctionName) {
		if (currentDescr.getVotingFunction().getName()
				.equals(selectedFunctionName)) {
			loadVotingSigFunction(currentDescr.getVotingFunction());
			return;
		}
		for (VotingSigFunction f : currentDescr.getVotingSigFunctions()) {
			if (f.getName().equals(selectedFunctionName)) {
				loadVotingSigFunction(f);
				return;
			}
		}
	}

	private void populateFunctionList(CElectionDescription descr) {
		ObservableList<String> observableList = FXCollections
				.observableArrayList();

		observableList.add(descr.getVotingFunction().getName());

		for (VotingSigFunction f : descr.getVotingSigFunctions()) {
			observableList.add(f.getName());
		}
		functionList.setItems(observableList);
		functionList.getSelectionModel().clearAndSelect(0);
	}

	private void populateLoopBoundList(List<LoopBound> loopbounds) {
		ObservableList<String> observableList = FXCollections
				.observableArrayList();
		for (LoopBound b : loopbounds) {
			observableList.add(b.toString());
		}
		loopBoundList.setItems(observableList);
	}

	private void loadVotingSigFunction(VotingSigFunction f) {
		loadFunction(f);
		List<LoopBound> loopbounds = currentDescr
				.getLoopBoundsForFunction(f.getName());
		populateLoopBoundList(loopbounds);
	}

	private String votingInputTypeToCType(VotingInputTypes inputType,
			String varname) {
		switch (inputType) {
			case APPROVAL :
			case WEIGHTED_APPROVAL :
			case PREFERENCE :
				return "unsigned int VAR[AMT_VOTERS][AMT_CANDIDATES]"
						.replaceAll("VAR", varname)
						.replaceAll("AMT_VOTERS",
								beastWorkspace.getCodeGenOptions()
										.getCbmcAmountVotersVarName())
						.replaceAll("AMT_CANDIDATES",
								beastWorkspace.getCodeGenOptions()
										.getCbmcAmountCandidatesVarName());
		}
		return null;
	}

	private String votingOutputTypeToCType(VotingOutputTypes outputType) {
		switch (outputType) {
			case CANDIDATE_LIST :
				return "unsigned int *";
			case PARLIAMENT_STACK :
				return "unsigned int *";
		}
		return null;
	}

	private String votingSigFuncToCString(VotingSigFunction func) {
		String template = "OUTPUT_TYPE NAME(INPUT_VAR)";
		return template
				.replaceAll("OUTPUT_TYPE",
						votingOutputTypeToCType(func.getOutputType()))
				.replaceAll("NAME", func.getName()).replaceAll("INPUT_VAR",
						votingInputTypeToCType(func.getInputType(), "votes"));
	}

	private void setLockedColor() {
		funcDeclArea.setStyleClass(0, funcDeclArea.getLength(),
				cssLockedClassName);
		closingBracketArea.setStyleClass(0, closingBracketArea.getLength(),
				cssLockedClassName);
	}

	private void loadFunction(VotingSigFunction func) {
		electionCodeArea.clear();
		funcDeclArea.clear();
		closingBracketArea.clear();

		funcDeclArea.insertText(0, votingSigFuncToCString(func) + "{");
		electionCodeArea.insertText(0, func.getCodeAsString());
		closingBracketArea.insertText(0, "}");
		setLockedColor();
	}

	public void loadElectionDescr(CElectionDescription descr) {
		this.currentDescr = descr;
		populateFunctionList(descr);
		functionList.getSelectionModel().clearAndSelect(0);
	}

	public void addLoopBound() {
		String functionName = functionList.getSelectionModel()
				.getSelectedItem();

		TextField indexField = new TextField();
		TextField boundField = new TextField();

		Optional<ButtonType> res = DialogHelper
				.generateDialog(List.of("index", "bound"),
						List.of(indexField, boundField))
				.showAndWait();

		if (res.isPresent()) {
			if (res.get().getButtonData().isCancelButton())
				return;
			String index = indexField.getText();
			String bound = boundField.getText();

			currentDescr.addLoopBoundForFunction(functionName,
					Integer.valueOf(index), bound);
			populateLoopBoundList(
					currentDescr.getLoopBoundsForFunction(functionName));
		}
	}

	public void removeLoopBound() {
		String loopBoundString = loopBoundList.getSelectionModel()
				.getSelectedItem();

		String functionName = functionList.getSelectionModel()
				.getSelectedItem();
		currentDescr.removeLoopBoundForFunction(functionName, loopBoundString);
		populateLoopBoundList(
				currentDescr.getLoopBoundsForFunction(functionName));
	}

	public void addFunction() {
		TextField nameField = new TextField();
		Optional<ButtonType> res = DialogHelper
				.generateDialog(List.of("name"), List.of(nameField))
				.showAndWait();
		if (res.isPresent()) {
			if (res.get().getButtonData().isCancelButton())
				return;
			String name = nameField.getText();
			currentDescr.createNewVotingSigFunctionAndAdd(name);
			populateFunctionList(currentDescr);
			functionList.getSelectionModel().select(name);
		}
	}

	public void removeFunction() {
		String functionName = functionList.getSelectionModel()
				.getSelectedItem();
		int selectionindex = functionList.getSelectionModel()
				.getSelectedIndex();
		currentDescr.removeFunction(functionName);
		populateFunctionList(currentDescr);
		functionList.getSelectionModel().select(selectionindex - 1);
	}

	public void createNewDescr() {
		List<String> inputNames = List.of("name", "inputType", "outputType");
		TextField nameField = new TextField();

		ChoiceBox<String> inputTypeChoiceBox = new ChoiceBox<>();
		for (VotingInputTypes it : VotingInputTypes.values()) {
			inputTypeChoiceBox.getItems().add(it.toString());
		}
		inputTypeChoiceBox.getSelectionModel().selectFirst();

		ChoiceBox<String> outputTypeChoiceBox = new ChoiceBox<>();
		for (VotingOutputTypes ot : VotingOutputTypes.values()) {
			outputTypeChoiceBox.getItems().add(ot.toString());
		}
		outputTypeChoiceBox.getSelectionModel().selectFirst();

		List<Node> nodes = List.of(nameField, inputTypeChoiceBox,
				outputTypeChoiceBox);

		Optional<ButtonType> res = DialogHelper
				.generateDialog(inputNames, nodes).showAndWait();
		if (res.isPresent()) {
			if (res.get().getButtonData().isCancelButton())
				return;
			String name = nameField.getText();
			VotingInputTypes inputType = VotingInputTypes.valueOf(
					inputTypeChoiceBox.getSelectionModel().getSelectedItem());
			VotingOutputTypes outputType = VotingOutputTypes.valueOf(
					outputTypeChoiceBox.getSelectionModel().getSelectedItem());
			CElectionDescription descr = new CElectionDescription(inputType,
					outputType, name);
			beastWorkspace.addElectionDescription(descr);
		}
	}

	public void letUserLoad() throws NotImplementedException, IOException {
		CElectionDescription descr = OpenFileDialogHelper
				.letUserLoadElectionDescription(beastWorkspace.getBaseDir(),
						primaryStage);
		beastWorkspace.addElectionDescription(descr);
	}

	@Override
	public void handleWorkspaceError(WorkspaceErrorEvent evt) {
		// TODO Auto-generated method stub
		
	}

}
