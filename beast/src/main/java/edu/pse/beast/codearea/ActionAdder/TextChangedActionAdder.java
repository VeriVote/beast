/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.ActionAdder;

import edu.pse.beast.codearea.Actionlist.Actionlist;
import edu.pse.beast.codearea.Actionlist.TextAction.TextAddedAction;
import edu.pse.beast.codearea.Actionlist.TextAction.TextDelta;
import edu.pse.beast.codearea.Actionlist.TextAction.TextRemovedAction;
import edu.pse.beast.codearea.SaveTextBeforeRemove;
import edu.pse.beast.codearea.StoppedTypingContinuouslyListener;
import edu.pse.beast.codearea.StoppedTypingContinuouslyMessager;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

/**
 *
 * @author Holger-Desktop
 */
public class TextChangedActionAdder implements 
        ActionlistListener, 
        DocumentListener, 
        StoppedTypingContinuouslyListener {
    private int caretPos = 0;
    private boolean listen = true;
    private JTextPane pane;
    private Actionlist actionList;
    private int recordingStartPos;
    private String recordingString = "";
    private SaveTextBeforeRemove saveBeforeRemove;    
    private StoppedTypingContinuouslyMessager typingContinuouslyMessager;
    
    public TextChangedActionAdder(
            JTextPane pane,
            Actionlist list,
            SaveTextBeforeRemove saveBeforeRemove) {
        this.pane = pane;
        this.actionList = list;
        list.addActionAdder(this);
        this.saveBeforeRemove = saveBeforeRemove;
        pane.getStyledDocument().addDocumentListener(this);
        typingContinuouslyMessager = new StoppedTypingContinuouslyMessager(pane);
        this.typingContinuouslyMessager.addListener(this);
    }

    @Override
    public void undoingAction() {
        listen = false;
    }

    @Override
    public void finishedUndoingAction() {
        listen = true;
    }


    @Override
    public void insertUpdate(DocumentEvent de) {       
        if(!listen) return; 
        try {
            String added = pane.getStyledDocument().getText(de.getOffset(), de.getLength());
                      
            TextAddedAction action = new TextAddedAction(
                    new TextDelta(de.getOffset(), added),
                    pane.getStyledDocument());
            actionList.add(action);
        } catch (BadLocationException ex) {
            Logger.getLogger(TextChangedActionAdder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void removeUpdate(DocumentEvent de) {
        if(!listen) return;            
        TextRemovedAction action = new TextRemovedAction(
                new TextDelta(de.getOffset(), saveBeforeRemove.getRemoveString(de.getOffset(), de.getLength())),
                pane.getStyledDocument());
        actionList.add(action);              
    }

    @Override
    public void changedUpdate(DocumentEvent de) {
    }

    @Override
    public void StoppedTypingContinuously(int newPos) {   
        recordingStartPos = newPos;
    }
    

}
