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

/**
 *
 * @author Holger-Desktop
 */
public class JTextPaneToolbox {
    
    public static String getText(JTextPane pane) {
        try {
            return pane.getStyledDocument().getText(0, pane.getStyledDocument().getLength());
        } catch (BadLocationException ex) {
            Logger.getLogger(JTextPaneToolbox.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    public static int getDistanceToClosestLineBeginning(JTextPane pane, int pos) {
        String code = getText(pane);
        for(int i = pos - 1; i >= 0; --i) {
            if(code.charAt(i) == '\n') return pos - i - 1; 
        }
        return pos;
    }
    
    public static int transformToLineNumber(JTextPane pane, int absPos) {
        return transformToLineNumber(getText(pane), absPos);
    }
    
    public static int transformToLineNumber(String code, int absPos) {
        int amt = 0;
        for(int i = 0; i < absPos; ++i) {
            if(code.charAt(i) == '\n') amt++;
        }
        return amt;
    }
        
    public static int getLineBeginning(JTextPane pane, int line) {
        String code = getText(pane);
        int absPos = 0; 
        int lineNumber = 0;
        for(; absPos < code.length() && lineNumber < line; ++absPos) {
            if(code.charAt(absPos) == '\n') ++lineNumber;           
        }
        return absPos;
    }
    
    public static int getClosestLineBeginningAfter(JTextPane pane, int absPos) {
        String code = getText(pane);
        ++absPos;
            for(; absPos < code.length(); ++absPos) {
                if(code.charAt(absPos) == '\n')
                    return absPos;
            }
        return absPos;
    }
    
    
    public static ArrayList<Integer> getLinesBetween(JTextPane pane, int start, int end) throws BadLocationException {
        int startingline = transformToLineNumber(pane, start);
        ArrayList<Integer> lines = new ArrayList<>();
        lines.add(startingline);
        String code = getText(pane);
        for(; start < end; ++start) {
            if(code.charAt(start) == '\n') {
                startingline++;
                lines.add(startingline);            
            }
        }    
        return  lines;    
    }
    
    public static String getCharToTheLeftOfCaret(JTextPane pane) {
        String code = getText(pane);
        int caretPos = pane.getCaretPosition();
        if(caretPos == 0) return "";
        else {
            return code.substring(caretPos - 1, caretPos);
        }
    }
    
    public static String getCharToTheRightOfCaret(JTextPane pane) {
        String code = getText(pane);
        int caretPos = pane.getCaretPosition();
        if(caretPos == code.length()) return "";
        else {
            return code.substring(caretPos, caretPos + 1);
        }
    }
}
