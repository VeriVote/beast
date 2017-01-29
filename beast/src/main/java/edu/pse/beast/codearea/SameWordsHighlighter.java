/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JTextPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;
import javax.swing.text.DefaultStyledDocument;

/**
 * Taken from http://stackoverflow.com/questions/19765489/jtextpane-highlighting-issue
 * @author Holger-Desktop
 */
public class SameWordsHighlighter implements CaretListener {
    private JTextPane pane;
    private DefaultHighlighter highlighter;
    private DefaultHighlightPainter hPainter;
    private ArrayList<Object> addedHls = new ArrayList<>();
    public SameWordsHighlighter(JTextPane pane) {
        this.pane = pane;
        pane.addCaretListener(this);
        highlighter = (DefaultHighlighter) pane.getHighlighter();
        hPainter = new DefaultHighlightPainter(new Color(0xEEEEEE));
    }

    @Override
    public void caretUpdate(CaretEvent ce) {
        if(ce.getDot() == ce.getMark()) return;
        for(Object o : addedHls) {
            highlighter.removeHighlight(o);
        }
        
        String selText = pane.getSelectedText();
        String contText = "";// = jTextPane1.getText();

        DefaultStyledDocument document = (DefaultStyledDocument) pane.getDocument();

        try {
            contText = document.getText(0, document.getLength());
        } catch (BadLocationException ex) {
        }

        int index = 0;

        while((index = contText.indexOf(selText, index)) > -1){
            try {
                addedHls.add(highlighter.addHighlight(index, selText.length()+index, hPainter));
                index = index + selText.length();
            } catch (BadLocationException ex) {
            }
        }
    }    
}
