/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.InputToCode;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;
import java.awt.*;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Highlights locked lines with a gray background. It listens to 
 * locked and unlocked messages from the lockedlineshandler
 * @author Holger-Desktop
 */
public class LockedLinesDisplay implements LockedLinesListener {
    private JTextPane pane; 
    private LockedLinesHandler lockedLinesHandler;
    private DefaultHighlighter highlighter;
    private DefaultHighlightPainter hPainter;
    private HashMap<Integer, Object> highlights = new HashMap<>();
    
    public LockedLinesDisplay(JTextPane pane, LockedLinesHandler lockedLinesHandler) {
        this.pane = pane;
        lockedLinesHandler.addLockedLinesListener(this);
        highlighter = (DefaultHighlighter) pane.getHighlighter();
        hPainter = new DefaultHighlightPainter(Color.GRAY);
    }

    @Override
    public void lockedLine(int lineNumber) {
        int lineStart = JTextPaneToolbox.getLineBeginning(pane, lineNumber);
        int lineEnd = JTextPaneToolbox.getClosestLineBeginningAfter(pane, lineStart);
       
        try {
            highlights.put(lineNumber, highlighter.addHighlight(lineStart, lineEnd - 1, hPainter));
        } catch (BadLocationException ex) {
            Logger.getLogger(LockedLinesDisplay.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void unlockedLine(int lineNumber) {
        int lineStart = JTextPaneToolbox.getLineBeginning(pane, lineNumber);
        int lineEnd = JTextPaneToolbox.getClosestLineBeginningAfter(pane, lineStart);
        highlighter.removeHighlight(highlights.get(lineNumber));
        highlights.remove(lineNumber);
    }
}
