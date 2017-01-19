/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.InputToCode;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

/**
 *
 * @author Holger-Desktop
 */
public class LineHandler implements DocumentListener {
    private JTextPane pane;
    private StyledDocument doc;
    private ArrayList<Integer> newLinePos = new ArrayList<Integer>();
    
    public LineHandler(JTextPane pane) {
        this.pane = pane;
        this.doc = pane.getStyledDocument();
        this.doc.addDocumentListener(this);
    }
    
    public int getClosestLineBeginning(int pos) {
        String code = pane.getText();
        for(int i = pos; i >= 0; --i) {
            if(code.charAt(i) == '\n') return i;
        }
        return 0;
    }
    
    public int getDistanceToLineBeginning(int pos) {
        int minDist = pos;
        for(int i = 0; i < newLinePos.size() && minDist > 0; ++i) {
            if(newLinePos.get(i) <= pos) {
                int dist = pos - newLinePos.get(i) - 1;
                if(dist < minDist) minDist = dist;
            }
        }
        return minDist;
    }

    @Override
    public void insertUpdate(DocumentEvent de) {
        try {
            String addedText = doc.getText(de.getOffset(), de.getLength());
            for(int i = 0; i < de.getLength(); ++i) {
                if(addedText.charAt(i) == '\n' || addedText.charAt(i) == '\r') newLinePos.add(de.getOffset() + i);
            }
        } catch (BadLocationException ex) {
            Logger.getLogger(LineHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void removeUpdate(DocumentEvent de) {
        for(int i = 0; i < newLinePos.size(); ) {
            if(newLinePos.get(i) > de.getOffset() && newLinePos.get(i) <= de.getOffset() + de.getLength()) {
                newLinePos.remove(i);
            } else {
                ++i;
            }
        }        
    }

    @Override
    public void changedUpdate(DocumentEvent de) {
       
    }
    
}
