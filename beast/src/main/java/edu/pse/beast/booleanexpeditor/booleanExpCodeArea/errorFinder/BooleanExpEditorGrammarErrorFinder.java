package edu.pse.beast.booleanexpeditor.booleanExpCodeArea.errorFinder;

import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.BooleanExpANTLRHandler;
import edu.pse.beast.codearea.ErrorHandling.CodeError;
import edu.pse.beast.codearea.ErrorHandling.ErrorFinder;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;

/**
 * Class that finds errors which conflict with the grammar in BooleanExpGrammar.g in the BooleanExpression(s) of the
 * CodeArea this class is an attribute of.
 * @author Nikolai
 */
public class BooleanExpEditorGrammarErrorFinder implements ANTLRErrorListener, ErrorFinder {
    private final BooleanExpANTLRHandler antlrHandler;
    private final ArrayList<CodeError> errors = new ArrayList<>();

    /**
     * Constructor
     * @param antlrHandler the BooleanExpAntlrHandler this class uses to find grammar errors
     */
    public BooleanExpEditorGrammarErrorFinder(BooleanExpANTLRHandler antlrHandler) {
        this.antlrHandler = antlrHandler;
        antlrHandler.getParser().addErrorListener(this);
    }


    @Override
    public ArrayList<CodeError> getErrors() {
        errors.clear();
        antlrHandler.getParseTree();
        return errors;
    }

    @Override
    public void syntaxError(Recognizer<?, ?> rcgnzr, Object o, int line, int charInline, String msg, RecognitionException re) {
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
