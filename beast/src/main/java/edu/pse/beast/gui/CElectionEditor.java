package edu.pse.beast.gui;

import static edu.pse.beast.toolbox.CCodeHelper.lineBreak;

import java.util.ArrayList;
import java.util.List;

import org.fxmisc.richtext.CodeArea;

import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.electiondescription.VotingInputTypes;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;
import edu.pse.beast.api.electiondescription.VotingSigFunction;
import edu.pse.beast.toolbox.TextStyle;
import javafx.css.Style;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class CElectionEditor extends CodeArea {
	private final String cssResource = "/edu/pse/beast/ceditor.css";
	private final String cssLockedClassName = "locked";
	private CodeArea funcDeclArea;
	private CodeArea closingBracketArea;

	private CodeGenOptions codeGenOptions;

	private int editableRangeStart;
	private int editableRangeEnd;

	public CElectionEditor(CodeGenOptions codeGenOptions, CodeArea funcDeclArea,
			CodeArea closingBracketArea) {
		final String stylesheet = this.getClass().getResource(cssResource)
				.toExternalForm();
		this.codeGenOptions = codeGenOptions;
		
		this.funcDeclArea = funcDeclArea;
		this.closingBracketArea = closingBracketArea;
		this.funcDeclArea.setEditable(false);
		this.closingBracketArea.setEditable(false);
		
		funcDeclArea.getStylesheets().add(stylesheet);
		closingBracketArea.getStylesheets().add(stylesheet);
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

	public void loadFunction(VotingSigFunction func) {
		clear();
		funcDeclArea.clear();
		closingBracketArea.clear();

		funcDeclArea.insertText(0, votingSigFuncToCString(func) + "{");
		insertText(getCaretPosition(), func.getCodeAsString());
		closingBracketArea.insertText(0, "}");
		setLockedColor();
	}

}
