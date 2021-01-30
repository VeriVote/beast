package edu.pse.beast.gui;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;

import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.electiondescription.VotingInputTypes;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;
import edu.pse.beast.api.electiondescription.VotingSigFunction;
import edu.pse.beast.api.electiondescription.to_c.FunctionToC;
import edu.pse.beast.codeareajavafx.AutoCompletionCodeArea;
import edu.pse.beast.codeareajavafx.NewCodeArea;
import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.types.InputType;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class TestGUIController {

	private CElectionDescription descr;

	@FXML
	private AnchorPane codePane;

	@FXML
	private Accordion functionacc;

	@FXML
	public void changeVote() {
		Dialog<String> dialog = new Dialog<>();
		ButtonType okType = new ButtonType("OK", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(okType, ButtonType.CANCEL);

		GridPane grid = new GridPane();

		ChoiceBox<VotingInputTypes> inputType = new ChoiceBox<VotingInputTypes>(
				FXCollections.observableList(List.of(VotingInputTypes.values())));
		inputType.getSelectionModel().selectFirst();
		grid.add(inputType, 1, 1);

		ChoiceBox<VotingOutputTypes> outputType = new ChoiceBox<VotingOutputTypes>(
				FXCollections.observableList(List.of(VotingOutputTypes.values())));
		outputType.getSelectionModel().selectFirst();
		grid.add(outputType, 1, 2);

		dialog.getDialogPane().setContent(grid);
		Optional<String> result = dialog.showAndWait();

		if (result.isPresent()) {
			VotingInputTypes inType = inputType.getSelectionModel().getSelectedItem();
			VotingOutputTypes outType = outputType.getSelectionModel().getSelectedItem();
			descr = new CElectionDescription(inType, outType);
			display(descr);			
		}
	}

	private void display(VotingSigFunction func) {
		AnchorPane anchor = new AnchorPane();
		CodeArea codeArea = new CodeArea();		
		
		codeArea.insertText(0, FunctionToC.getConstEnding(func));
		codeArea.insertText(0, FunctionToC.getConstPreamble(func) + "\n");

		final VirtualizedScrollPane<CodeArea> vsp = new VirtualizedScrollPane<>(codeArea);
		anchor.getChildren().add(vsp);
		AnchorPane.setTopAnchor(vsp, (double) 0);
		AnchorPane.setBottomAnchor(vsp, (double) 0);
		AnchorPane.setLeftAnchor(vsp, (double) 0);
		AnchorPane.setRightAnchor(vsp, (double) 0);

		//functionacc.getPanes().add(new TitledPane(FunctionToC.getFunctionSignature(func), anchor));
	}

	private void display(CElectionDescription descr) {
		functionacc.getPanes().clear();
		display(descr.getVotingFunction());
	}
	
	@FXML
	public void gencode() {
	}

	@FXML
	public void addFunction() {
		display(descr.createNewAndAdd("func"));
	}

	@FXML
	public void removeFunction() {
	}

	@FXML
	public void initialize() {
	}

}
