/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.ActionAdder;

import edu.pse.beast.codearea.Actionlist.Actionlist;
import edu.pse.beast.codearea.Actionlist.EmptyActionList;
import edu.pse.beast.codearea.Actionlist.TextAction.TextAddedAction;
import edu.pse.beast.codearea.Actionlist.TextAction.TextDelta;
import edu.pse.beast.codearea.Actionlist.TextAction.TextRemovedAction;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

/**
 *
 * @author Holger-Desktop
 */
public class TextChangedActionAdder implements ActionAdder, CaretListener, DocumentListener, KeyListener {
    private boolean listen = true;
    private JTextPane pane;
    private Actionlist actionList;
    private int currentCaretPosition = 0;
    private int recordingStartPos = 0;
    private String recordingString = "";
    private StringBuilder currentAdded = new StringBuilder();
    private String prevText; //nasty 
    
    public TextChangedActionAdder(JTextPane pane, Actionlist list) {
        this.pane = pane;
        this.actionList = list;
        list.addActionAdder(this);
        prevText = pane.getText();
        pane.addCaretListener(this);
        pane.addKeyListener(this);
        pane.getStyledDocument().addDocumentListener(this);
    }

    @Override
    public void stopListening() {
        addCurrentRecording();
        listen = false;
    }

    @Override
    public void resumeListening() {
        listen = true;
    }

    @Override
    public void caretUpdate(CaretEvent ce) {
        if(ce.getDot() != currentCaretPosition + 1) {            
            addCurrentRecording();
            recordingStartPos = ce.getDot();
        }
        currentCaretPosition = ce.getDot();
    }

    @Override
    public void insertUpdate(DocumentEvent de) {
        if(!listen) return;
        try {
            String text = pane.getStyledDocument().getText(de.getOffset(), de.getLength());
            if(de.getOffset() == currentCaretPosition && de.getLength() == 1 &&
                    !text.equals("\n")) {
                System.out.println(text);
                recordingString += text;            
            } else {
                addCurrentRecording();
                actionList.add(new TextAddedAction(
                        new TextDelta(de.getOffset(), text, currentCaretPosition),
                        pane.getStyledDocument()));
            }
        } catch(BadLocationException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void removeUpdate(DocumentEvent de) {
        if(!listen) return;     
        addCurrentRecording();
        String text = prevText.substring(
                de.getOffset(), 
                de.getOffset() + de.getLength());

        actionList.add(new TextRemovedAction(
                new TextDelta(de.getOffset(), text, currentCaretPosition),
                pane.getStyledDocument()));
    }

    @Override
    public void changedUpdate(DocumentEvent de) {
    }

    @Override
    public void keyTyped(KeyEvent ke) {        
        prevText = pane.getText();
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        
    }

    @Override
    public void keyReleased(KeyEvent ke) {        
    }

    private void addCurrentRecording() {
        if(recordingString.length() != 0) {
            TextDelta td = new TextDelta(
                    recordingStartPos, recordingString, currentCaretPosition);
            actionList.add(new TextAddedAction(td, pane.getStyledDocument()));
            recordingString = "";
        }
    }
    
}
