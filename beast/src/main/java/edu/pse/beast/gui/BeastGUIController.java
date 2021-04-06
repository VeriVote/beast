package edu.pse.beast.gui;

import org.fxmisc.flowless.VirtualizedScrollPane;

import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.electiondescription.VotingInputTypes;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;
import edu.pse.beast.api.electiondescription.VotingSigFunction;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.AnchorPane;

public class BeastGUIController {
	@FXML
	private AnchorPane codePane;

	@FXML
	private ListView<String> functionList;

	private CodeGenOptions codeGenOptions;
	CElectionEditor cElectionEditor;

	private CElectionDescription descr;

	private void addChildToAnchorPane(AnchorPane pane, Node child, double top,
			double bottom, double left, double right) {
		codePane.getChildren().add(child);
		AnchorPane.setTopAnchor(child, top);
		AnchorPane.setLeftAnchor(child, left);
		AnchorPane.setRightAnchor(child, right);
		AnchorPane.setBottomAnchor(child, bottom);
	}

	private CElectionDescription getTestDescr() {
		String name = "test";
		CElectionDescription descr = new CElectionDescription(
				VotingInputTypes.WEIGHTED_APPROVAL,
				VotingOutputTypes.PARLIAMENT_STACK, "test");
		descr.getVotingFunction().setCode("return 0;\n");

		VotingSigFunction f1 = new VotingSigFunction("voteHelper",
				VotingInputTypes.WEIGHTED_APPROVAL,
				VotingOutputTypes.PARLIAMENT_STACK);

		descr.getVotingSigFunctions().add(f1);
		return descr;
	}

	@FXML
	public void setLoopBounds() {
	}

	@FXML
	public void addFunction() {
	}

	@FXML
	public void removeFunction() {
	}


	void populateFunctionList(CElectionDescription descr) {
		ObservableList<String> observableList = FXCollections
				.observableArrayList();

		observableList.add(descr.getVotingFunction().getName());

		for (VotingSigFunction f : descr.getVotingSigFunctions()) {
			observableList.add(f.getName());
		}
		functionList.setItems(observableList);
		functionList.getSelectionModel().clearAndSelect(0);
	}

	private void loadDescr(CElectionDescription descr) {
		populateFunctionList(descr);
	}
	
	private void selectedFunctionChanged(String selectedFunctionName) {
		if(descr.getVotingFunction().getName() == selectedFunctionName) {
			cElectionEditor.loadFunction(descr.getVotingFunction());
			return;
		}
		for (VotingSigFunction f : descr.getVotingSigFunctions()) {
			if(f.getName() == selectedFunctionName) 
				cElectionEditor.loadFunction(f);
			return;
		}
	}

	@FXML
	public void initialize() {
		this.codeGenOptions = new CodeGenOptions();
		codeGenOptions.setCbmcAmountCandidatesVarName("C");
		codeGenOptions.setCbmcAmountVotesVarName("V");

		cElectionEditor = new CElectionEditor(codeGenOptions);
		VirtualizedScrollPane<CElectionEditor> vsp = new VirtualizedScrollPane(
				cElectionEditor);
		addChildToAnchorPane(codePane, vsp, 0, 0, 0, 0);
		
		functionList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);		
		functionList.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends String> observable,
						String oldValue, String newValue) -> {
					selectedFunctionChanged(newValue);
				});

		descr = getTestDescr();
		loadDescr(descr);
	}
}
