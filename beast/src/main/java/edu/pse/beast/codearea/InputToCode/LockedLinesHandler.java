/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.InputToCode;

import edu.pse.beast.codearea.SaveTextBeforeRemove;
import edu.pse.beast.toolbox.SortedIntegerList;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

/**
 *
 * @author Holger-Desktop
 */
public class LockedLinesHandler implements DocumentListener {
    
    private SortedIntegerList lockedLines = new SortedIntegerList();
    private LineHandler lineHandler;
    private StyledDocument doc;
    private SaveTextBeforeRemove saveBeforeRemove;
    
    public LockedLinesHandler(StyledDocument doc,
            LineHandler lineHandler,
            SaveTextBeforeRemove saveBeforeRemove) {
        this.doc = doc;
        doc.addDocumentListener(this);
        this.lineHandler = lineHandler;
        this.saveBeforeRemove = saveBeforeRemove;
    }

    public void lockLine(int line) {
        lockedLines.add(line);
    }
    
    public void unlockLine(int line) {
        lockedLines.remove(line);
    }
    
    public boolean isLineLocked(int line) {
        return lockedLines.contains(line);
    }
    
    @Override
    public void insertUpdate(DocumentEvent de) {   
        try {
            int amtNewline = 0;
            String added = doc.getText(de.getOffset(), de.getLength());
            for(int i = 0; i < de.getLength(); ++i) {
                if(added.charAt(i) == '\n') {
                    amtNewline++;
                }
            }
            lockedLines.addIfBigger(lineHandler.transformToLineNumber(de.getOffset()) - 1, amtNewline);
        } catch (BadLocationException ex) {
            Logger.getLogger(LockedLinesHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(toString());
    }
    
    @Override
    public void removeUpdate(DocumentEvent de) {
            String removed = saveBeforeRemove.getRemoveString(de.getOffset(), de.getLength());
            
            int amtNewline = 0;
            for(int i = 0; i < removed.length(); ++i) {
                if(removed.charAt(i) == '\n') {
                    amtNewline++;
                }
            }
            lockedLines.subtractIfBigger(lineHandler.transformToLineNumber(de.getOffset()) - 1, amtNewline);
            System.out.println(toString());
        
    }

    @Override
    public void changedUpdate(DocumentEvent de) {            
    }
    
    public String toString() {
        String s = "locked lines: ";
        for(int i = 0; i < lockedLines.size(); ++i) {
            s += lockedLines.get(i) + ", ";
        }
        return s;
    }
}
