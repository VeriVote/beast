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
public class CurlyBracesLineBeginningTabHandler implements LineBeginningTabsHandler {
    private JTextPane pane;
    private LineHandler lineHandler;
    
    public CurlyBracesLineBeginningTabHandler(JTextPane pane, LineHandler lineHandler) {
        this.pane = pane;
        this.lineHandler = lineHandler;
    }

    @Override
    public int getTabsForLine(int caretPos) {
        int absPos = lineHandler.caretPosToAbsPos(caretPos);
        int amt = 0;
        String code = pane.getText();
        for(int pos = absPos - 1; pos >= 0; --pos) {
            if(code.charAt(pos) == '{') {
                ++amt;
                while(pos >= 0 && code.charAt(pos) != '\n') --pos;
            }
            else if(code.charAt(pos) == '}') --amt;
        }
        
        for(int pos = absPos; pos < code.length() && code.charAt(pos) == '}'; ++pos) {
            --amt;
            return amt;
        }
        return amt;
    }
    
    
}
