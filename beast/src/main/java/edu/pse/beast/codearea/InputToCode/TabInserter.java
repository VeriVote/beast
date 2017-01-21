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
    private LineHandler lineHandler;
    private SortedIntegerList tabPositions = new SortedIntegerList();
    private int spacesPerTab = 8;   
    
    public TabInserter(JTextPane pane, LineHandler lineHandler) {
        this.pane = pane;
        this.lineHandler = lineHandler;
    }
    
    public void insertTabAtPos(int pos) throws BadLocationException {
        int absPos = lineHandler.caretPosToAbsPos(pos);
        int distToLineBeginning = lineHandler.getDistanceToLineBeginning(absPos);
        
        int nextTabPos = 0;
        
        while(nextTabPos <= distToLineBeginning) {
            nextTabPos += spacesPerTab;
        }     
        int distToNextTabPos = nextTabPos - distToLineBeginning;
                 
        String spacesToInsert = "";
        
        for(int i = 0; i < distToNextTabPos; ++i) spacesToInsert += " ";
        pane.getStyledDocument().insertString(pos, spacesToInsert, null);
        System.out.println(spacesToInsert.length());   
        
        tabPositions.add(absPos);
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
