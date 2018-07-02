package edu.pse.beast.booleanexpeditor.booleanExpCodeArea.errorFinder;

import java.util.ArrayList;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.BooleanExpANTLRHandler;
import edu.pse.beast.codearea.ErrorHandling.CodeError;
import edu.pse.beast.codeareaJAVAFX.NewCodeArea;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariableList;

/**
 * Class for finding type-errors in symbolic variable usage in the
 * BooleanExpression(s) of the CodeArea this class is an attribute of.
 *
 * @author Nikolai
 */
public class BooleanExpEditorVariableErrorFinder {

	public static ArrayList<CodeError> getErrors(BooleanExpANTLRHandler antlrHandler, SymbolicVariableList list, NewCodeArea codeArea) {
		
		FormalExpErrorFinderTreeListener listener = new FormalExpErrorFinderTreeListener(list, codeArea, codeArea.getElectionDescription());
		
		ParseTree tree = antlrHandler.getParseTree();
		ParseTreeWalker walker = new ParseTreeWalker();
		try {
			walker.walk(listener, tree);
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<CodeError>();
		}

		return listener.getErrors();
	}
}
