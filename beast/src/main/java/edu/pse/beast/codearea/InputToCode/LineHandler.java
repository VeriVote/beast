/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.InputToCode;

import javax.swing.JTextPane;
import javax.swing.text.StyledDocument;

/**
 * Enables one to think about newlines as a single character independant of the
 * os
 * @author Holger-Desktop
 */
public class LineHandler {
    private JTextPane pane;
    
    public LineHandler(JTextPane pane) {
        this.pane = pane;
    }
    
    public int getDistanceToLineBeginning(int pos) {
        String code = pane.getText();
        for(int i = pos - 1; i >= 0; --i) {
            if(code.charAt(i) == '\n') return pos - i - 1; 
        }
        return pos;
    }
    
    public int transformToLineNumber(int absPos) {
        String code = pane.getText();
        int amt = 0;
        for(int i = 0; i < absPos; ++i) {
            if(code.charAt(i) == '\n') amt++;
        }
        return amt;
    }
    
    public int caretPosToAbsPos(int caretPos) {
        String code = pane.getText();
        for(int i = 0; i < caretPos; ++i) {
            if(code.charAt(i) == '\r') caretPos++;
        }
        return caretPos;
    }

    public int getLineBeginning(int line) {
        String code = pane.getText();
        int absPos = 0; 
        int lineNumber = 0;
        for(; absPos < code.length() && lineNumber < line; ++absPos) {
            if(code.charAt(absPos) == '\n') ++lineNumber;
        }
        return absPos;
    }
}
