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
public class TabHandler {
    private JTextPane pane;
    private int spacesPerTab = 4;    
    
    TabHandler(JTextPane pane) {
        this.pane = pane;
    }
    
    public void insertTabAtPos(int pos) throws BadLocationException {
        System.out.println("Tab");
        pane.getStyledDocument().insertString(pos, "TABS MOSAFUGGA", null);
    }
    
}
