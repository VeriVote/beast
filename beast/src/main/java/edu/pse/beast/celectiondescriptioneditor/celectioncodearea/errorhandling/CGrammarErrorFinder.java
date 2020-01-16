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
 * This class uses antlr to find syntax errors in the C code.
 *
 * @author Holger Klein
 */
public class CGrammarErrorFinder implements ErrorFinder, ANTLRErrorListener {

    /** The antlr handler. */
    private final CAntlrHandler antlrHandler;

    /** The errors. */
    private final ArrayList<CodeError> errors = new ArrayList<>();

    /**
     * Constructor.
     *
     * @param antlrHandlr the handle to AntLR used for error finding
     */
    public CGrammarErrorFinder(final CAntlrHandler antlrHandlr) {
        this.antlrHandler = antlrHandlr;
        antlrHandlr.getParser().removeErrorListeners();
        antlrHandlr.getParser().addErrorListener(this);
    }

    @Override
    public ArrayList<CodeError> getErrors() {
        errors.clear();
        antlrHandler.getCParseTree();
        return errors;
    }

    @Override
    public void syntaxError(final Recognizer<?, ?> rcgnzr,
                            final Object offendingSymbol,
                            final int line,
                            final int charPosInLine,
                            final String msg,
                            final RecognitionException e) {
        CodeError err
            = new CodeError(line, charPosInLine, "antlr", 0,
                            ((Token) offendingSymbol).getStartIndex(),
                            ((Token) offendingSymbol).getStopIndex());
        err.setExtraInfo("msg", msg);
        errors.add(err);
    }

    @Override
    public void reportAmbiguity(final Parser parser, final DFA dfa,
                                final int i, final int i1, final boolean bln,
                                final BitSet bitset, final ATNConfigSet atncs) {
    }

    @Override
    public void reportAttemptingFullContext(final Parser parser, final DFA dfa,
                                            final int i, final int i1,
                                            final BitSet bitset,
                                            final ATNConfigSet atncs) {
    }

    @Override
    public void reportContextSensitivity(final Parser parser, final DFA dfa,
                                         final int i, final int i1, final int i2,
                                         final ATNConfigSet atncs) {
    }
}
