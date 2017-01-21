/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.InputToCode;

import edu.pse.beast.toolbox.SortedIntegerList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

/**
 * This class saves all occurences of a char in a styleddocument. It implements
 * the  DocumentListener interface to update the position data whenever a
 * change occurs in the document
 * @author Holger-Desktop
 */
public class CharacterPositionSaver implements DocumentListener {
    private JTextPane pane;
    private StyledDocument doc;
    private char c;
    private SortedIntegerList list = new SortedIntegerList();
    
    public CharacterPositionSaver(char c, JTextPane pane) {
        this.pane = pane;
        this.c = c;
        this.doc = this.pane.getStyledDocument();
        this.doc.addDocumentListener(this);
        recompile();
    }
    
    public int getAmountOfOccurencesBefore(int absPos) {
        return list.getAmountBefore(absPos);
    }
    
    /**
     * @param pos
     * @return the last occurence of c before or at pos
     */
    public int getClosestCharPosTo(int pos) {
        return list.getBiggestSmallerOrEqual(pos);
    }
    
    /**
     * if the added string contains c those positions are added to
     * the linepositions list. Latter positions will be updated.
     * @param de 
     */
    @Override
    public void insertUpdate(DocumentEvent de) {
        
        try {
            String addedText = doc.getText(de.getOffset(), de.getLength());
            for(int i = 0; i < de.getLength(); ++i) {
                if(addedText.charAt(i) == c) list.add(de.getOffset() + i);
            }
        } catch (BadLocationException ex) {
            System.err.println("recompiling document");
            recompile();
        }
        list.addIfBigger(de.getOffset() + de.getLength(), de.getLength());
    }
    

    /**
     * if the removed string contained c those will be removed from the
     * list. Latter positions will be updated.
     * @param de 
     */
    @Override
    public void removeUpdate(DocumentEvent de) {
        list.removeBetween(de.getOffset(), de.getOffset() + de.getLength());
        list.subtractIfBigger(de.getOffset(), de.getLength());
    }

    @Override
    public void changedUpdate(DocumentEvent de) {}

    private void recompile() {
        list.clear();
        String code = pane.getText();
        for(int i = 0; i < code.length(); ++i) {
            if(code.charAt(i) == c) {
                list.add(i);
            }
        }
    }
    
    @Override
    public String toString() {
        return list.toString();
    }

    
}
