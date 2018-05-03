package edu.pse.beast.booleanexpeditor.booleanExpCodeArea.errorFinder;

import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.BooleanExpANTLRHandler;
import edu.pse.beast.codearea.ErrorHandling.CodeError;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
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
		
		PreAndPostConditionsDescription property = parentTreeItem.getPreAndPostPropertie();
		
		//pre cond error finder
		
		BooleanExpANTLRHandler preAntlrHandler = new BooleanExpANTLRHandler(property.getPreConditionsDescription().getCode());	
		
		combinedErrors.addAll(BooleanExpEditorGrammarErrorFinder.getErrors(preAntlrHandler));
		combinedErrors.addAll(BooleanExpEditorVariableErrorFinder.getErrors(preAntlrHandler, property.getSymVarList(), GUIController.getController().getCodeArea()));
		
		
		//post cond error finder
		
		BooleanExpANTLRHandler postAntlrHandler = new BooleanExpANTLRHandler(property.getPostConditionsDescription().getCode());	
		combinedErrors.addAll(BooleanExpEditorGrammarErrorFinder.getErrors(postAntlrHandler));
		combinedErrors.addAll(BooleanExpEditorVariableErrorFinder.getErrors(postAntlrHandler, property.getSymVarList(), GUIController.getController().getCodeArea()));

		
		return combinedErrors;
		
	}
	
}
