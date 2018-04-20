package edu.pse.beast.booleanexpeditor.booleanExpCodeArea.errorFinder;

import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.BooleanExpANTLRHandler;
import edu.pse.beast.codearea.ErrorHandling.CodeError;
import edu.pse.beast.highlevel.javafx.BooleanExpEditorNEW;
import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.highlevel.javafx.ParentTreeItem;

public class BooleanExpEditorGeneralErrorFinder {

	/**
	 * 
	 * @param parentTreeItem
	 * @return true, if there are errors, else false
	 */
	public static boolean hasErrors(ParentTreeItem parentTreeItem) {

		List<CodeError> combinedErrors = getErrors(parentTreeItem);
		
		if (combinedErrors.size() > 0) {
			parentTreeItem.addErrors(combinedErrors);
			return true;
		} else {
			return false;
		}
		
	}

	public static List<CodeError> getErrors(ParentTreeItem parentTreeItem) {

		List<CodeError> combinedErrors = new ArrayList<CodeError>();
		
		//pre cond error finder
		
		BooleanExpANTLRHandler preAntlrHandler = new BooleanExpANTLRHandler(parentTreeItem.getPreAndPostPropertie().getPreConditionsDescription().getCode());	
		
		combinedErrors.addAll(BooleanExpEditorGrammarErrorFinder.getErrors(preAntlrHandler));
		combinedErrors.addAll(BooleanExpEditorVariableErrorFinder.getErrors(preAntlrHandler, BooleanExpEditorNEW.getSymbVarList(), GUIController.getController().getCodeArea()));
		
		
		//post cond error finder
		
		BooleanExpANTLRHandler postAntlrHandler = new BooleanExpANTLRHandler(parentTreeItem.getPreAndPostPropertie().getPreConditionsDescription().getCode());	
		combinedErrors.addAll(BooleanExpEditorGrammarErrorFinder.getErrors(postAntlrHandler));
		combinedErrors.addAll(BooleanExpEditorVariableErrorFinder.getErrors(postAntlrHandler, BooleanExpEditorNEW.getSymbVarList(), GUIController.getController().getCodeArea()));

		
		return combinedErrors;
		
	}
	
}
