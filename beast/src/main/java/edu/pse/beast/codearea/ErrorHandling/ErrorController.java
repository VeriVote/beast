/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.ErrorHandling;

import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Holger-Desktop
 */
public class ErrorController implements DocumentListener {
    private ErrorFinderList errorFinderList;
    private JTextPane pane;
    private ErrorDisplayer displayer;
    
    public ErrorController(JTextPane pane) {
        this.pane = pane;
        pane.getStyledDocument().addDocumentListener(this);
        errorFinderList = new ErrorFinderList();
        displayer = new ErrorDisplayer(pane);
    }
    
    public void addErrorFinder(ErrorFinder finder) {
        errorFinderList.add(finder);
    }

    @Override
    public void insertUpdate(DocumentEvent de) {
        System.out.println("searching for errors");
        errorFinderList.getErrors();
    }

    @Override
    public void removeUpdate(DocumentEvent de) {
        errorFinderList.getErrors();
    }

    @Override
    public void changedUpdate(DocumentEvent de) {
        
    }
}
