/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.InputToCode;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;

/**
 *
 * @author Holger-Desktop
 */
public class TabInserter {
    private JTextPane pane; 
    private LineHandler lineHandler;
    private int spacesPerTab = 8;   
    
    TabInserter(JTextPane pane, LineHandler lineHandler) {
        this.pane = pane;
        this.lineHandler = lineHandler;
    }
    
    public void insertTabAtPos(int pos) throws BadLocationException {   
        int distToLineBeginning = lineHandler.getDistanceToLineBeginning(pos);
        
        int nextTabPos = 0;
        
        while(nextTabPos <= distToLineBeginning) {
            nextTabPos += spacesPerTab;
        }
        
        int distToNextTabPos = nextTabPos - distToLineBeginning;
         
        String spacesToInsert = "";
        System.out.println(distToNextTabPos);
        
        for(int i = 0; i < distToNextTabPos - 1; ++i) spacesToInsert += " ";
        spacesToInsert += "|";
        pane.getStyledDocument().insertString(pos, spacesToInsert, null);
    }
        
}
