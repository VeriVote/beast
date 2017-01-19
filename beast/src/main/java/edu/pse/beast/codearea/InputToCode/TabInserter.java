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
    private int spacesPerTab = 4;   
    
    TabInserter(JTextPane pane, LineHandler lineHandler) {
        this.pane = pane;
        this.lineHandler = lineHandler;
    }
    
    public void insertTabAtPos(int pos) throws BadLocationException {        
        pane.getStyledDocument().insertString(pos, "TABS MOSAFUGGA", null);
    }
    
}
