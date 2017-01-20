/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.InputToCode;

import javax.swing.JTextPane;

/**
 *
 * @author Holger-Desktop
 */
public class LineBeginningTabsHandler {
    private LineHandler lineHandler;
    private JTextPane pane;
    
    public LineBeginningTabsHandler(JTextPane pane, LineHandler lineHandler) {
        this.pane = pane;
        this.lineHandler = lineHandler;
    }
    
    public int getTabsForLine(int absPos) {
        String code = pane.getText();
        int amtTabs = 0;
        for(int i = absPos - 1; i >= 0; --i) {
            if(code.charAt(i) == '{') {
                amtTabs++;
                while(i >= 0 && code.charAt(i) != '\n') ++i;
            } else if(code.charAt(i) == '}') {
                amtTabs--;
                while(i >= 0 && code.charAt(i) != '\n') ++i;
            } 
        }
        for(int i = absPos + 1; i < code.length() && code.charAt(i) == '{'; ++i) {
            amtTabs--;
        }
        return amtTabs;
    }
}
