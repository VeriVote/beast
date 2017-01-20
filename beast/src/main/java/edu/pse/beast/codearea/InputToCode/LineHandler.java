/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.InputToCode;

import edu.pse.beast.toolbox.SortedIntegerList;
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
 * This class keeps a record of where each newline is placed. It is used since
 * the classes provided by swing only use absolute char positions. Coding how
 * ever is very relient on line placement for sorting code
 * @author Holger-Desktop
 */
public class LineHandler implements DocumentListener {
    private JTextPane pane;
    private StyledDocument doc;
    private SortedIntegerList linePositions = new SortedIntegerList();
    
    public LineHandler(JTextPane pane) {
        this.pane = pane;
        this.doc = pane.getStyledDocument();
        this.doc.addDocumentListener(this);
    }
    
    /**
     * @param pos
     * @return the linebreak position closest to but before pos
     */
    public int getClosestLineBeginning(int pos) {
        return linePositions.getBiggestSmallerOrEqual(pos);
    }
    
    /**
     * @param pos
     * @return the distance to the linebreak position closest to but before pos
     */
    public int getDistanceToLineBeginning(int pos) {
        return pos - linePositions.getBiggestSmallerOrEqual(pos);
    }

    /**
     * if the added string contains linebreaks those positions are added to
     * the linepositions list
     * @param de 
     */
    @Override
    public void insertUpdate(DocumentEvent de) {
        try {
            String addedText = doc.getText(de.getOffset(), de.getLength());
            for(int i = 0; i < de.getLength(); ++i) {
                if(addedText.charAt(i) == '\n' || addedText.charAt(i) == '\r') linePositions.add(de.getOffset() + i);
            }
        } catch (BadLocationException ex) {
            Logger.getLogger(LineHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * if the removed string contained linebreaks those will be removed from the
     * list
     * @param de 
     */
    @Override
    public void removeUpdate(DocumentEvent de) {
        linePositions.removeBetween(de.getOffset(), de.getOffset() + de.getLength());
        linePositions.subtractIfBigger(de.getOffset(), de.getLength());
    }

    @Override
    public void changedUpdate(DocumentEvent de) {}
    
    public void printLineNumbers() {
        for(int i = 0; i < linePositions.size(); ++i) {
            System.out.print(linePositions.get(i) + " ");
        }
        System.out.println("");
    }
    
}
