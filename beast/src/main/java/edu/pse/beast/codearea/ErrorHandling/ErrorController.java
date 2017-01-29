/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.ErrorHandling;

import edu.pse.beast.codearea.InputToCode.LineHandler;
import edu.pse.beast.codearea.StoppedTypingContinuouslyListener;
import edu.pse.beast.codearea.StoppedTypingContinuouslyMessager;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Holger-Desktop
 */
public class ErrorController implements StoppedTypingContinuouslyListener {
    private ErrorFinderList errorFinderList;
    private JTextPane pane;
    private ErrorDisplayer displayer;
    
    public ErrorController(JTextPane pane,
            StoppedTypingContinuouslyMessager msg) {
        this.pane = pane;
        msg.addListener(this);
        errorFinderList = new ErrorFinderList();
        displayer = new ErrorDisplayer(pane);
    }
    
    public void addErrorFinder(ErrorFinder finder) {
        errorFinderList.add(finder);
    }

    @Override
    public void StoppedTypingContinuously(int newPos) {
        displayer.showErrors(errorFinderList.getErrors());
    }
}
