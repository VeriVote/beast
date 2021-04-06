package edu.pse.beast.gui;

import static edu.pse.beast.toolbox.CCodeHelper.lineBreak;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.fxmisc.richtext.CodeArea;

import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.electiondescription.VotingInputTypes;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;
import edu.pse.beast.api.electiondescription.VotingSigFunction;
import edu.pse.beast.gui.elements.CEditorElement;
import edu.pse.beast.toolbox.TextStyle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.Style;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class CElectionEditor {
	private final String cssResource = "/edu/pse/beast/ceditor.css";
	private final String cssLockedClassName = "locked";

	private ListView<String> functionList;
	private ListView<String> loopBoundList;

	private CodeArea funcDeclArea;
	private CodeArea closingBracketArea;
	private CEditorElement electionCodeArea;

	private CElectionDescription currentDescr;

	private CodeGenOptions codeGenOptions;

	private int editableRangeStart;
	private int editableRangeEnd;

	public CElectionEditor(CodeGenOptions codeGenOptions,
			CEditorElement electionCodeArea, CodeArea funcDeclArea,
			CodeArea closingBracketArea, ListView<String> functionList,
			ListView<String> loopBoundList) {
		final String stylesheet = this.getClass().getResource(cssResource)
				.toExternalForm();
		this.codeGenOptions = codeGenOptions;

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

		initListViews();
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
				return "unsigned int VAR[AMT_VOTERS][AMT_CANDIDATES]"
						.replaceAll("VAR", varname)
						.replaceAll("AMT_VOTERS",
								codeGenOptions.getCbmcAmountVotersVarName())
						.replaceAll("AMT_CANDIDATES", codeGenOptions
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
		loadFunction(descr.getVotingFunction());
	}

	public void addLoopBound() {
		String functionName = functionList.getSelectionModel()
				.getSelectedItem();

		TextField indexField = new TextField();
		TextField boundField = new TextField();

		Optional<String> res = DialogHelper
				.generateDialog(List.of("index", "bound"),
						List.of(indexField, boundField))
				.showAndWait();

		if (res.isPresent()) {
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
		Optional<String> res = DialogHelper
				.generateDialog(List.of("name"), List.of(nameField))
				.showAndWait();
		if (res.isPresent()) {
			String name = nameField.getText();
			currentDescr.createNewVotingSigFunctionAndAdd(name);
			populateFunctionList(currentDescr);
			functionList.getSelectionModel().select(name);
		}
	}

	public void removeFunction() {
		String functionName = functionList.getSelectionModel()
				.getSelectedItem();

		currentDescr.removeFunction(functionName);
		populateFunctionList(currentDescr);
	}

}
