package edu.pse.beast.booleanexpeditor.booleanexpcodearea.errorfinder;

import java.util.ArrayList;

import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import edu.pse.beast.booleanexpeditor.booleanexpcodearea.BooleanExpANTLRHandler;
import edu.pse.beast.codearea.errorhandling.CodeError;
import edu.pse.beast.codeareajavafx.NewCodeArea;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariableList;

/**
 * Class for finding type-errors in symbolic variable usage in the
 * BooleanExpression(s) of the CodeArea this class is an attribute of.
 *
 * @author Nikolai Schnell
 */
public final class BooleanExpEditorVariableErrorFinder {
    private BooleanExpEditorVariableErrorFinder() { }

    public static ArrayList<CodeError> getErrors(BooleanExpANTLRHandler antlrHandler,
                                                 SymbolicVariableList list,
                                                 NewCodeArea codeArea) {
        FormalExpErrorFinderTreeListener listener
              =  new FormalExpErrorFinderTreeListener(
                    list, codeArea,
                    codeArea.getElectionDescription()
                );
        ParseTree tree = antlrHandler.getParseTree();
        ParseTreeWalker walker = new ParseTreeWalker();
        try {
            walker.walk(listener, tree);
        } catch (RecognitionException e) {
            e.printStackTrace();
            return new ArrayList<CodeError>();
        }
        return listener.getErrors();
    }
}
