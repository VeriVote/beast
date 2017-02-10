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
import javax.swing.text.BadLocationException;

/**
 *
 * @author Holger-Desktop
 */
public class TabInserter {
    private JTextPane pane; 
    private SortedIntegerList tabPositions = new SortedIntegerList();
    private int spacesPerTab = 8;   
    
    public TabInserter(JTextPane pane) {
        this.pane = pane;
    }
    
    public void insertTabAtPos(int pos) throws BadLocationException {
        int distToLineBeginning = JTextPaneToolbox.getDistanceToClosestLineBeginning(pane, pos);
        
        int nextTabPos = 0;
        
        while(nextTabPos <= distToLineBeginning) {
            nextTabPos += spacesPerTab;
        }     
        int distToNextTabPos = nextTabPos - distToLineBeginning;
                 
        String spacesToInsert = "";
        
        for(int i = 0; i < distToNextTabPos; ++i) spacesToInsert += " ";
        pane.getStyledDocument().insertString(pos, spacesToInsert, null);
        
        tabPositions.add(pos);
    }
    
    
    
    public void removeTabAtPos(int pos) {
        if(!onlySpacesBetweenPosAndLinesBeginning(pos)) return;
        int distToLineBeginning = JTextPaneToolbox.getDistanceToClosestLineBeginning(pane, pos);
        int closestMultipleOfSpacesPerTab = 0;
        while(closestMultipleOfSpacesPerTab + spacesPerTab < distToLineBeginning) {
            closestMultipleOfSpacesPerTab += spacesPerTab;
        }
        int absPosClosestMultiple = pos - distToLineBeginning + closestMultipleOfSpacesPerTab;
        try {
            pane.getStyledDocument().remove(absPosClosestMultiple, pos - absPosClosestMultiple);
        } catch (BadLocationException ex) {
            Logger.getLogger(TabInserter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    public int getSpacesPerTab() {
        return spacesPerTab;
    }
    
    public void setSpacesPerTab(int spaces) {
        spacesPerTab = spaces;
    }

    private boolean onlySpacesBetweenPosAndLinesBeginning(int pos) {
        int distToLineBeginning = JTextPaneToolbox.getDistanceToClosestLineBeginning(pane, pos);
        String spaces = "";
        for(int i = 0; i < distToLineBeginning; ++i) {
            spaces += " ";
        }
        try {
            return pane.getStyledDocument().getText(pos - distToLineBeginning, distToLineBeginning).equals(spaces);
        } catch (BadLocationException ex) {
            return false;
        }
    }
    
}
