/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.InputToCode;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
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
            else if(code.charAt(i) == '\r') absPos++;
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
    
    public int getClosestLineBeginning(int abspos) {
        try {
            String code = pane.getStyledDocument().getText(0, abspos);
            for(int i = abspos - 1; i >= 0; --i) {
                if(code.charAt(i) == '\n')
                    return i;
            }
        } catch (BadLocationException ex) {
            Logger.getLogger(LineHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public int getClosestLineBeginningAfter(int absPos) {
        String code = pane.getText().replaceAll("\r", "");
        ++absPos;
            for(; absPos < code.length(); ++absPos) {
                if(code.charAt(absPos) == '\n')
                    return absPos;
            }
        return absPos;
    }
    
    public ArrayList<Integer> getLinesBetween(int start, int end) {
        int startingline = transformToLineNumber(start);
        ArrayList<Integer> lines = new ArrayList<>();
        lines.add(startingline);
        String code = pane.getText();
        for(; start < end; ++start) {
            if(code.charAt(start) == '\n') {
                startingline++;
                lines.add(startingline);
            } else if(code.charAt(start) == '\r')
                ++end;
        }
        return  lines;
    }
}
