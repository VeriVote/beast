/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.InputToCode;

import edu.pse.beast.codearea.InputToCode.LineHandler;
import edu.pse.beast.codearea.InputToCode.LockedLinesHandler;
import edu.pse.beast.codearea.InputToCode.LockedLinesListener;
import java.awt.Color;
import java.awt.color.ColorSpace;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;

/**
 *
 * @author Holger-Desktop
 */
public class LockedLinesDisplay implements LockedLinesListener {
    private JTextPane pane; 
    private LineHandler lineHandler;
    private LockedLinesHandler lockedLinesHandler;
    private DefaultHighlighter highlighter;
    private DefaultHighlightPainter hPainter;
    private HashMap<Integer, Object> highlights = new HashMap<>();
    
    public LockedLinesDisplay(JTextPane pane, LineHandler lineHandler, LockedLinesHandler lockedLinesHandler) {
        this.pane = pane;
        this.lineHandler = lineHandler;
        this.lockedLinesHandler = lockedLinesHandler;
        lockedLinesHandler.addLockedLinesListener(this);
        highlighter = (DefaultHighlighter) pane.getHighlighter();
        hPainter = new DefaultHighlightPainter(Color.GRAY);
    }

    @Override
    public void lockedLine(int lineNumber) {
        int lineStart = lineHandler.getLineBeginning(lineNumber);
        int lineEnd = lineHandler.getClosestLineBeginningAfter(lineStart);
        try {
            System.out.println("locked: " + pane.getStyledDocument().getText(lineStart, lineEnd - lineStart));
        } catch (BadLocationException ex) {
            Logger.getLogger(LockedLinesDisplay.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        try {
            highlights.put(lineNumber, highlighter.addHighlight(lineStart, lineEnd, hPainter));
        } catch (BadLocationException ex) {
            Logger.getLogger(LockedLinesDisplay.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void unlockedLine(int lineNumber) {
        int lineStart = lineHandler.getLineBeginning(lineNumber);
        int lineEnd = lineHandler.getClosestLineBeginningAfter(lineStart);
        try {
            System.out.println(pane.getStyledDocument().getText(lineStart, lineEnd - lineStart));
        } catch (BadLocationException ex) {
            Logger.getLogger(LockedLinesDisplay.class.getName()).log(Level.SEVERE, null, ex);
        }
        highlighter.removeHighlight(highlights.get(lineNumber));
        highlights.remove(lineNumber);
    }
}
