package edu.pse.beast.gui;

import java.awt.MouseInfo;
import java.awt.Point;
import java.util.List;
import java.util.Optional;

import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;

import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.electiondescription.VotingInputTypes;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;
import edu.pse.beast.api.electiondescription.VotingSigFunction;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class BeastGUIController {
	@FXML
	private AnchorPane codePane;

	@FXML
	private ListView<String> loopBoundList;

	@FXML
	private ListView<String> functionList;

	private CodeGenOptions codeGenOptions;
	CElectionEditor cElectionEditor;

	private CElectionDescription descr;

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

	@FXML
	public void addFunction() {
		TextField nameField = new TextField();
		Optional<String> res = generateDialog(List.of("name"),
				List.of(nameField)).showAndWait();
		if (res.isPresent()) {
			String name = nameField.getText();
			descr.createNewVotingSigFunctionAndAdd(name);
			populateFunctionList(descr);
			functionList.getSelectionModel().select(name);
		}
	}

	@FXML
	public void removeFunction() {
		String functionName = functionList.getSelectionModel()
				.getSelectedItem();

		descr.removeFunction(functionName);
		populateFunctionList(descr);
	}

	private Dialog<String> generateDialog(List<String> inputNames,
			List<Node> inputs) {
		Point position = MouseInfo.getPointerInfo().getLocation();
		Dialog<String> dialog = new Dialog<String>();
		dialog.setX(position.getX());
		dialog.setY(position.getY());
		ButtonType buttonType = new ButtonType("OK", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(buttonType,
				ButtonType.CANCEL);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);

		for (int i = 0; i < inputNames.size(); ++i) {
			grid.add(new Label(inputNames.get(i)), 0, i);
			grid.add(inputs.get(i), 1, i);
		}

		dialog.getDialogPane().setContent(grid);
		return dialog;
	}

	@FXML
	public void addLoopBound() {
		String functionName = functionList.getSelectionModel()
				.getSelectedItem();

		TextField indexField = new TextField();
		TextField boundField = new TextField();

		Optional<String> res = generateDialog(List.of("index", "bound"),
				List.of(indexField, boundField)).showAndWait();

		if (res.isPresent()) {
			String index = indexField.getText();
			String bound = boundField.getText();

			descr.addLoopBoundForFunction(functionName, Integer.valueOf(index),
					bound);
			populateLoopBoundList(descr.getLoopBoundsForFunction(functionName));
		}
	}

	@FXML
	public void removeLoopBound() {
		String loopBoundString = loopBoundList.getSelectionModel()
				.getSelectedItem();

		String functionName = functionList.getSelectionModel()
				.getSelectedItem();
		descr.removeLoopBoundForFunction(functionName, loopBoundString);
		populateLoopBoundList(descr.getLoopBoundsForFunction(functionName));
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

	private void loadDescr(CElectionDescription descr) {
		populateFunctionList(descr);
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
		cElectionEditor.loadFunction(f);
		List<LoopBound> loopbounds = descr
				.getLoopBoundsForFunction(f.getName());
		populateLoopBoundList(loopbounds);
	}

	private void selectedFunctionChanged(String selectedFunctionName) {
		if (descr.getVotingFunction().getName().equals(selectedFunctionName)) {
			loadVotingSigFunction(descr.getVotingFunction());
			return;
		}
		for (VotingSigFunction f : descr.getVotingSigFunctions()) {
			if (f.getName().equals(selectedFunctionName)) {
				loadVotingSigFunction(f);
				return;
			}
		}
	}
	private void addChildToAnchorPane(AnchorPane pane, Node child, double top,
			double bottom, double left, double right) {
		codePane.getChildren().add(child);
		AnchorPane.setTopAnchor(child, top);
		AnchorPane.setLeftAnchor(child, left);
		AnchorPane.setRightAnchor(child, right);
		AnchorPane.setBottomAnchor(child, bottom);
	}

	@FXML
	public void initialize() {
		this.codeGenOptions = new CodeGenOptions();
		codeGenOptions.setCbmcAmountCandidatesVarName("C");
		codeGenOptions.setCbmcAmountVotesVarName("V");

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

		cElectionEditor = new CElectionEditor(codeGenOptions, funcDeclArea,
				closingBracketArea);
		VirtualizedScrollPane<CElectionEditor> vsp = new VirtualizedScrollPane(
				cElectionEditor);
		addChildToAnchorPane(codePane, vsp, 20, 100, 0, 0);

		functionList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		functionList.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends String> observable,
						String oldValue, String newValue) -> {
					selectedFunctionChanged(newValue);
				});

		loopBoundList.getSelectionModel()
				.setSelectionMode(SelectionMode.SINGLE);

		descr = getTestDescr();
		loadDescr(descr);
	}
}
