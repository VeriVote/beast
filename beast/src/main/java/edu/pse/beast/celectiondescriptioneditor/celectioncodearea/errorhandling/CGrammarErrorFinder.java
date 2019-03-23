package edu.pse.beast.celectiondescriptioneditor.celectioncodearea.errorhandling;

import java.util.ArrayList;
import java.util.BitSet;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;

import edu.pse.beast.celectiondescriptioneditor.celectioncodearea.antlr.CAntlrHandler;
import edu.pse.beast.codearea.errorhandling.CodeError;
import edu.pse.beast.codearea.errorhandling.ErrorFinder;

/**
 * This class uses antlr to find syntax errors in the C code
 *
 * @author Holger-Desktop
 */
public class CGrammarErrorFinder implements ErrorFinder, ANTLRErrorListener {
    private final CAntlrHandler antlrHandler;
    private final ArrayList<CodeError> errors = new ArrayList<>();

    /**
     * constructor
     *
     * @param antlrHandler the handle to AntLR used for error finding
     */
    public CGrammarErrorFinder(CAntlrHandler antlrHandler) {
        this.antlrHandler = antlrHandler;
        antlrHandler.getParser().removeErrorListeners();
        antlrHandler.getParser().addErrorListener(this);
    }

    @Override
    public ArrayList<CodeError> getErrors() {
        errors.clear();
        antlrHandler.getCParseTree();
        return errors;
    }

    @Override
    public void syntaxError(Recognizer<?, ?> rcgnzr, Object offendingSymbol, int line, int charPosInLine, String msg,
            RecognitionException e) {
        CodeError err = new CodeError(line, charPosInLine, "antlr", 0, ((Token) offendingSymbol).getStartIndex(),
                ((Token) offendingSymbol).getStopIndex());
        err.setExtraInfo("msg", msg);
        errors.add(err);
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
