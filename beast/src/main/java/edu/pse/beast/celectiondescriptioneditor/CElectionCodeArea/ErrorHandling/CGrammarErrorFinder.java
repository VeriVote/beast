/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.ErrorHandling;

import edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.Antlr.CAntlrHandler;
import edu.pse.beast.codearea.ErrorHandling.CodeError;
import edu.pse.beast.codearea.ErrorHandling.ErrorFinder;
import java.util.ArrayList;
import java.util.BitSet;
import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.ANTLRErrorStrategy;
import org.antlr.v4.runtime.DefaultErrorStrategy;
import org.antlr.v4.runtime.FailedPredicateException;
import org.antlr.v4.runtime.InputMismatchException;
import org.antlr.v4.runtime.NoViableAltException;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;

/**
 *
 * @author Holger-Desktop
 */
public class CGrammarErrorFinder implements ErrorFinder, ANTLRErrorListener {
    private CAntlrHandler antlrHandler;
    private ArrayList<CodeError> errors = new ArrayList<>();
    
    public CGrammarErrorFinder(CAntlrHandler antlrHandler) {
        this.antlrHandler = antlrHandler;
        antlrHandler.getParser().removeErrorListeners();
        antlrHandler.getParser().addErrorListener(this);
    }

    @Override
    public ArrayList<CodeError> getErrors() {            
        antlrHandler.getCParseTree();
        return errors;
    }   

    @Override
    public void syntaxError(Recognizer<?, ?> rcgnzr, Object offendingSymbol, int line, int charPosInLine, String msg, RecognitionException e) {
        CodeError err = new CodeError(line, charPosInLine, "antlr", 0, 0, 0);
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
