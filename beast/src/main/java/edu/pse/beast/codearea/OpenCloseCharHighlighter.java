/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea;

import edu.pse.beast.codearea.InputToCode.JTextPaneToolbox;
import edu.pse.beast.codearea.InputToCode.OpenCloseChar;
import edu.pse.beast.codearea.InputToCode.OpenCloseCharList;
import java.awt.Color;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;

/**
 *
 * @author Holger-Desktop
 */
public class OpenCloseCharHighlighter implements CaretListener {
    
    private OpenCloseCharList charList;
    private JTextPane pane;
    private DefaultHighlighter highlighter;
    private DefaultHighlighter.DefaultHighlightPainter hPainter;
    private ArrayList<Object> addedHls = new ArrayList<>();
    
    public OpenCloseCharHighlighter(OpenCloseCharList charList, JTextPane pane) {
        this.charList = charList;
        this.pane = pane;pane.addCaretListener(this);
        highlighter = (DefaultHighlighter) pane.getHighlighter();
        hPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.RED);
    }

    @Override
    public void caretUpdate(CaretEvent ce) {  
        removeAllHighlights();
        char surroundingChars[] = new char[2];
        surroundingChars[0] = JTextPaneToolbox.getCharToTheLeftOfCaret(pane).charAt(0);
        surroundingChars[1] = JTextPaneToolbox.getCharToTheRightOfCaret(pane).charAt(0);
        for (int i = 0; i < surroundingChars.length; i++) {
            char c = surroundingChars[i];
            if(charList.isOpenChar(c)) {
                highlightChar(ce.getDot() + i);
                highlightCorrespondingCloseChar(ce.getDot() + i, charList.getOpenCloseChar(c));
                return;
            } else if(charList.isCloseChar(c)) {
                highlightChar(ce.getDot() + i);
                highlightCorrespondingOpenChar(ce.getDot() + i, charList.getOpenCloseChar(c));
                return;
            }         
        }
    }

    private void highlightCorrespondingOpenChar(int pos, OpenCloseChar openCloseChar) {
        char open = openCloseChar.getOpen();
        char close = openCloseChar.getClose();
        String code = JTextPaneToolbox.getText(pane);
        int lvl = 1;
        pos -= 2;
        for(; pos >= 0 && lvl > 0; --pos) {
            char c = code.charAt(pos);
            if(c == close) lvl++;
            else if(c == open) lvl--;
            
        }
        if(lvl == 0) {
            highlightChar(pos + 2);
        }
    }

    private void highlightCorrespondingCloseChar(int pos, OpenCloseChar openCloseChar) {
        char open = openCloseChar.getOpen();
        char close = openCloseChar.getClose();
        String code = JTextPaneToolbox.getText(pane);
        int lvl = 1;
        
        for(; pos < code.length() && lvl > 0; ++pos) {
            char c = code.charAt(pos);
            if(c == open) lvl++;
            else if(c == close) 
                lvl--;
        }
        if(lvl == 0) {
            highlightChar(pos);
        }
    }

    private void highlightChar(int pos) {
        try {
            addedHls.add(highlighter.addHighlight(pos - 1, pos, hPainter));
        } catch (BadLocationException ex) {
            Logger.getLogger(OpenCloseCharHighlighter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void removeAllHighlights() {
       for(Object o : addedHls) highlighter.removeHighlight(o);
    }

    
    
    
}
