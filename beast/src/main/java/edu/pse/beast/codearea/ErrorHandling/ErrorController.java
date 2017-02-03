/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.ErrorHandling;

import edu.pse.beast.codearea.InputToCode.LineHandler;
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
public class ErrorController implements 
        StoppedTypingContinuouslyListener, DocumentListener, CaretListener {
    private ErrorFinderList errorFinderList;
    private JTextPane pane;
    private ErrorDisplayer displayer;
    private boolean changed = false;
    private int currentCaretPos;
    
    public ErrorController(JTextPane pane,
            StoppedTypingContinuouslyMessager msg,
            ErrorDisplayer displayer) {
        this.pane = pane;
        msg.addListener(this);
        errorFinderList = new ErrorFinderList();
        this.displayer = displayer;
        pane.getStyledDocument().addDocumentListener(this);
        pane.addCaretListener(this);
    }
    
    public void addErrorFinder(ErrorFinder finder) {
        errorFinderList.add(finder);
    }

    @Override
    public void StoppedTypingContinuously(int newPos) {
        if(!changed) return;
        System.out.println("formalPropEditor get errors");
        ArrayList<CodeError> foundErrors = errorFinderList.getErrors();
        displayer.showErrors(foundErrors);
        changed = false;
    }

    public ErrorDisplayer getDisplayer() {
        return displayer;
    }

    public ErrorFinderList getErrorFinderList() {
        return errorFinderList;
    }

    @Override
    public void insertUpdate(DocumentEvent de) {
        changed = true;
    }

    @Override
    public void removeUpdate(DocumentEvent de) {
         changed = true;
    }

    
    @Override
    public void changedUpdate(DocumentEvent de) {
    }

    @Override
    public void caretUpdate(CaretEvent ce) {
        currentCaretPos = ce.getDot();
    }
}
