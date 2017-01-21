/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.ActionAdder;

import edu.pse.beast.codearea.Actionlist.Actionlist;
import edu.pse.beast.codearea.Actionlist.EmptyActionList;
import edu.pse.beast.codearea.Actionlist.TextAction.TextDelta;
import edu.pse.beast.codearea.Actionlist.TextAction.TextRemovedAction;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JTextPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Holger-Desktop
 */
public class TextChangedActionAdder implements ActionAdder, CaretListener, DocumentListener, KeyListener {
    private JTextPane pane;
    private Actionlist actionList;
    private EmptyActionList emptyList = new EmptyActionList();
    private Actionlist activeList;
    private int currentCaretPosition = 0;
    private StringBuilder currentAdded = new StringBuilder();
    private String prevText; //nasty 
    
    public TextChangedActionAdder(JTextPane pane, Actionlist list) {
        this.pane = pane;
        this.actionList = list;
        list.addActionAdder(this);
        this.activeList = list;
        prevText = pane.getText();
    }

    @Override
    public void stopListening() {
        this.activeList = emptyList;
    }

    @Override
    public void resumeListening() {
        this.activeList = actionList;
    }

    @Override
    public void caretUpdate(CaretEvent ce) {
        currentCaretPosition = ce.getDot();
    }

    @Override
    public void insertUpdate(DocumentEvent de) {
        
    }

    @Override
    public void removeUpdate(DocumentEvent de) {
        String rem = prevText.substring(de.getOffset(), de.getLength());
        TextDelta td = new TextDelta(de.getOffset(), rem);
        TextRemovedAction ac = new TextRemovedAction(td, pane.getStyledDocument());
        activeList.add(ac);
    }

    @Override
    public void changedUpdate(DocumentEvent de) {
        
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        
    }
    
}
