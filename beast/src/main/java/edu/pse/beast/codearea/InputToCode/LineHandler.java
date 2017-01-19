/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.InputToCode;

import javax.swing.JTextPane;
import javax.swing.text.StyledDocument;

/**
 *
 * @author Holger-Desktop
 */
public class LineHandler {
    private JTextPane pane;
    public LineHandler(JTextPane pane) {
        this.pane = pane;
    }
    
    public int getClosestLineBeginning(int pos) {
        String code = pane.getText();
        for(int i = pos; i >= 0; --i) {
            if(code.charAt(i) == '\n' || code.charAt(i) == '\r') return i;
        }
        return 0;
    }
    
    public int getDistanceToLineBeginning(int pos) {
        String code = pane.getText();
        for(int i = pos - 1; i >= 0; --i) {
            if(code.charAt(i) == '\n' || code.charAt(i) == '\r') return pos - i;
        }
        System.out.println(pos);
        return pos;
    }
    
}
