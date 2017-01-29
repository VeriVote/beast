/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea;

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
        for(Object o : addedHls) {
            highlighter.removeHighlight(o);
        }
        
        int pos = ce.getDot();
        String surroundingChars = "";
        try{
            if(pos == 0) {
            surroundingChars = pane.getStyledDocument().getText(0, 1);
            } else if(pos == pane.getStyledDocument().getLength()) {
                surroundingChars = pane.getStyledDocument().getText(pos - 1, 1);
            } else {
                surroundingChars = pane.getStyledDocument().getText(pos - 1, 2);
            }
        } catch (BadLocationException ex) { 
            Logger.getLogger(OpenCloseCharHighlighter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("surrounding chars: " + surroundingChars);
        
        for(int i = 0; i < surroundingChars.length(); ++i) {
            if(charList.isCloseChar(surroundingChars.charAt(i))) {
                highlightCorrespondingOpenChar(
                        pos + i,
                        charList.getOpenCloseChar(surroundingChars.charAt(i)));
                return;
            } else if(charList.isOpenChar(surroundingChars.charAt(i))) {
                highlightCorrespondingCloseChar(
                        pos + i,
                        charList.getOpenCloseChar(surroundingChars.charAt(i)));
                return;
            }
        }
    }

    private void highlightCorrespondingOpenChar(int pos, OpenCloseChar openCloseChar) {
        try {
            addedHls.add(highlighter.addHighlight(pos - 1, pos, hPainter));
            String code = pane.getStyledDocument().getText(0, pos - 1);
            pos -= 2;            
            int lvl = 1;            
            for(; pos >= 0 && lvl > 0; --pos) {
                if(code.charAt(pos) == openCloseChar.getClose()) ++lvl;
                if(code.charAt(pos) == openCloseChar.getOpen()) --lvl;
            }
            
            addedHls.add(highlighter.addHighlight(pos + 1, pos + 2, hPainter));
        } catch (BadLocationException ex) {
            //Logger.getLogger(OpenCloseCharHighlighter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void highlightCorrespondingCloseChar(int pos, OpenCloseChar openCloseChar) {
        try {
            addedHls.add(highlighter.addHighlight(pos - 1, pos, hPainter));
            String code = pane.getStyledDocument().getText(pos, pane.getStyledDocument().getLength());
            pos += 1;            
            int lvl = 1;            
            for(; pos < code.length() && lvl > 0; ++pos) {
                if(code.charAt(pos) == openCloseChar.getClose()) ++lvl;
                if(code.charAt(pos) == openCloseChar.getOpen()) --lvl;
            }
            
            addedHls.add(highlighter.addHighlight(pos - 1, pos, hPainter));
        } catch (BadLocationException ex) {
            //Logger.getLogger(OpenCloseCharHighlighter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
}
