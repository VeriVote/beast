/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.ErrorHandling;

import edu.pse.beast.codearea.StoppedTypingContinuouslyListener;
import edu.pse.beast.codearea.StoppedTypingContinuouslyMessager;
import java.util.ArrayList;
import javax.swing.JTextPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Holger-Desktop
 */
public class ErrorController {
    private ErrorFinderList errorFinderList;
    private JTextPane pane;
    private ErrorDisplayer displayer;
    private boolean changed = false;
    private int currentCaretPos;
    private ErrorFinderThread t;
    
    public ErrorController(JTextPane pane,
            ErrorDisplayer displayer) {
        this.pane = pane;
        errorFinderList = new ErrorFinderList();
        this.displayer = displayer;
        t = new ErrorFinderThread(errorFinderList, pane, this);
        int i = 0;
    }
    
    public void stopThread() {
        t.stop();
    }
    
    public void addErrorFinder(ErrorFinder finder) {
        errorFinderList.add(finder);
    }

    public ErrorDisplayer getDisplayer() {
        return displayer;
    }

    public ErrorFinderList getErrorFinderList() {
        return errorFinderList;
    }

    void foundNewErrors(ArrayList<CodeError> lastFoundErrors) {
        displayer.showErrors(lastFoundErrors);
    }
}
