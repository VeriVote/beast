package edu.pse.beast.gui.ceditor;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.NotImplementedException;
import org.fxmisc.richtext.CodeArea;

import edu.pse.beast.api.codegen.c_code.CFunction;
import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.electiondescription.VotingInputTypes;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;
import edu.pse.beast.api.electiondescription.function.CElectionDescriptionFunction;
import edu.pse.beast.api.electiondescription.function.VotingSigFunction;
import edu.pse.beast.gui.DialogHelper;
import edu.pse.beast.gui.FileDialogHelper;
import edu.pse.beast.gui.workspace.BeastWorkspace;
import edu.pse.beast.gui.workspace.WorkspaceUpdateListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventType;
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

	private ListView<CElectionDescriptionFunction> functionList;
	private ListView<LoopBound> loopBoundList;

	private CodeArea funcDeclArea;
	private CodeArea closingBracketArea;
	private CEditorCodeElement electionCodeArea;
	private ChoiceBox<CElectionDescription> openedElectionDescriptionChoiceBox;

	private CElectionDescription currentDescr;
	private CElectionDescriptionFunction currentDisplayedFunction;

	private BeastWorkspace beastWorkspace;

	private Stage primaryStage;

	public CElectionEditor(Stage primaryStage,
			CEditorCodeElement electionCodeArea, CodeArea funcDeclArea,
			CodeArea closingBracketArea,
			ListView<CElectionDescriptionFunction> functionList,
			ListView<LoopBound> loopBoundList,
			ChoiceBox<CElectionDescription> openedElectionDescriptionChoiceBox,
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
		handleWorkspaceUpdateGeneric();
		beastWorkspace.registerUpdateListener(this);

		electionCodeArea.setChangeListener((text) -> {
			beastWorkspace.updateCodeForDescrFunction(currentDescr,
					currentDisplayedFunction, text);
		});
	}

	private void selectedDescrChanged(CElectionDescription descr) {
		if (descr == null)
			return;
		loadElectionDescr(descr);
	}

	private void initOpenedDescrChoiceBox() {
		openedElectionDescriptionChoiceBox.getSelectionModel()
				.selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> {
					selectedDescrChanged(newValue);
				});
	}

	public void handleWorkspaceUpdateGeneric() {
		openedElectionDescriptionChoiceBox.getItems().clear();
		for (CElectionDescription descr : beastWorkspace.getLoadedDescrs()) {
			openedElectionDescriptionChoiceBox.getItems().add(descr);
		}
		openedElectionDescriptionChoiceBox.getSelectionModel().selectLast();
	}

	private void initListViews() {
		functionList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		functionList.getSelectionModel().selectedItemProperty()
				.addListener((o, oldVal, newVal) -> {
					selectedFunctionChanged(newVal);
				});

		loopBoundList.getSelectionModel()
				.setSelectionMode(SelectionMode.SINGLE);

	}

	private void selectedFunctionChanged(CElectionDescriptionFunction newVal) {

	}

	private void populateFunctionList(CElectionDescription descr) {
		ObservableList<CElectionDescriptionFunction> observableList = FXCollections
				.observableArrayList();

		observableList.add(descr.getVotingFunction());

		for (VotingSigFunction f : descr.getVotingSigFunctions()) {
			observableList.add(f);
		}
		functionList.setItems(observableList);
		functionList.getSelectionModel().clearAndSelect(0);
	}

	private void populateLoopBoundList(List<LoopBound> loopbounds) {
		ObservableList<LoopBound> observableList = FXCollections
				.observableArrayList();
		for (LoopBound b : loopbounds) {
			observableList.add(b);
		}
		loopBoundList.setItems(observableList);
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

	private void setLockedColor() {
		funcDeclArea.setStyleClass(0, funcDeclArea.getLength(),
				cssLockedClassName);
		closingBracketArea.setStyleClass(0, closingBracketArea.getLength(),
				cssLockedClassName);
	}

	private void loadFunction(CElectionDescriptionFunction func) {
		electionCodeArea.clear();
		funcDeclArea.clear();
		closingBracketArea.clear();

		funcDeclArea.insertText(0, func.getDeclCString() + "{");
		
		electionCodeArea.insertText(0, func.getCode());
		
		closingBracketArea.insertText(0, "}");
		setLockedColor();
	}

	public void loadElectionDescr(CElectionDescription descr) {
		this.currentDescr = descr;
		populateFunctionList(descr);
		functionList.getSelectionModel().clearAndSelect(0);
	}

	public void addLoopBound() {
		throw new NotImplementedException();
	}
	
	public void removeLoopBound() {
		throw new NotImplementedException();
	}

	public void addFunction() {		
		throw new NotImplementedException();
	}

	public void removeFunction() {
		throw new NotImplementedException();
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
		CElectionDescription descr = FileDialogHelper
				.letUserLoadElectionDescription(beastWorkspace.getBaseDir(),
						primaryStage);
		beastWorkspace.addElectionDescription(descr);
	}

	public void save() {
		beastWorkspace.saveDescr(currentDescr);
	}

}
