/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.ErrorHandling;

import edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.Antlr.CAntlrHandler;
import edu.pse.beast.codearea.ErrorHandling.ErrorFinder;
import java.util.ArrayList;
import org.antlr.v4.runtime.ANTLRErrorStrategy;
import org.antlr.v4.runtime.DefaultErrorStrategy;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Token;

/**
 *
 * @author Holger-Desktop
 */
public class CGrammarErrorFinder extends DefaultErrorStrategy implements ErrorFinder {
    private CAntlrHandler antlrHandler;
    private ArrayList<Error> errors = new ArrayList<>();
    
    public CGrammarErrorFinder(CAntlrHandler antlrHandler) {
        this.antlrHandler = antlrHandler;
        antlrHandler.getParser().setErrorHandler(this);
    }

    @Override
    public ArrayList<Error> getErrors() {        
        return errors;
    }

    @Override
    public void reportError(Parser recognizer, RecognitionException e) {
        System.err.println("hello");
    }
    
}
