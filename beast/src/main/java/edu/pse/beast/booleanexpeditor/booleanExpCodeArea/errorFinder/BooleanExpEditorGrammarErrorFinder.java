package edu.pse.beast.booleanexpeditor.booleanExpCodeArea.errorFinder;

import java.util.ArrayList;
import java.util.BitSet;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;

import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.BooleanExpANTLRHandler;
import edu.pse.beast.codearea.ErrorHandling.CodeError;

/**
 * Class that finds errors which conflict with the grammar in BooleanExpGrammar.g in the BooleanExpression(s) of the
 * CodeArea this class is an attribute of.
 * @author Nikolai
 */
public class BooleanExpEditorGrammarErrorFinder implements ANTLRErrorListener {
    private final ArrayList<CodeError> errors = new ArrayList<>();
    private static BooleanExpEditorGrammarErrorFinder finder;

    public BooleanExpEditorGrammarErrorFinder(BooleanExpANTLRHandler antlrHandler) {
		antlrHandler.getParser().addErrorListener(this);
	}

	public static ArrayList<CodeError> getErrors(BooleanExpANTLRHandler antlrHandler) {
    	
    	finder = new BooleanExpEditorGrammarErrorFinder(antlrHandler);
    	
    	antlrHandler.getParser().addErrorListener(finder);
    	
        finder.clearErrors();
        antlrHandler.getParseTree();
        return finder.getErrors();
    }
	
	public void clearErrors() {
		errors.clear();
	}
	
	public ArrayList<CodeError> getErrors() {
		return errors;
	}

    @Override
    public void syntaxError(Recognizer<?, ?> rcgnzr, Object o, int line, int charInline, String msg,
                            RecognitionException re) {
        errors.add(BooleanExpErrorFactory.createAntlrError(line, charInline, msg));
    }

    @Override
    public void reportAmbiguity(Parser parser, DFA dfa, int i, int i1, boolean bln, BitSet bitset, ATNConfigSet atncs) {
       
    }

    @Override
    public void reportAttemptingFullContext(Parser parser, DFA dfa, int i, int i1, BitSet bitset, ATNConfigSet atncs) {
       
    }
    @Override
    public void reportContextSensitivity(Parser parser, DFA dfa, int i, int i1, int i2, ATNConfigSet atncs) {
        
    }

   
}
