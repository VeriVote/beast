/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.InputToCode;

import edu.pse.beast.toolbox.SortedIntegerList;
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
        System.out.println(spacesToInsert.length());   
        
        tabPositions.add(pos);
    }
    
    
    
    public void removeTabAtPos(int pos) {
        
    }
        
    public int getSpacesPerTab() {
        return spacesPerTab;
    }
    
    public void setSpacesPerTab(int spaces) {
        spacesPerTab = spaces;
    }
    
}
