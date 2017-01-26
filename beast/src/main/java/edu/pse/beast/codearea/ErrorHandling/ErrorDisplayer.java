/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.ErrorHandling;

import com.sun.org.apache.bcel.internal.classfile.Code;
import edu.pse.beast.codearea.InputToCode.LineHandler;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.View;

/**
 *
 * @author Holger-Desktop
 */
public class ErrorDisplayer implements CaretListener {
    private JTextPane pane;
    private SquigglePainter painter;
    private LineHandler lineHandler;
    private HashMap<Integer, CodeError> absPosToError;
    
    public ErrorDisplayer(JTextPane pane) {
        absPosToError = new HashMap<>();
        this.pane = pane;
        pane.addCaretListener(this);
        this.painter = new SquigglePainter(Color.red);        
        this.lineHandler = new LineHandler(pane);
    }
    
    public void showErrors(ArrayList<CodeError> errors) {
        absPosToError = new HashMap<>();
        pane.getHighlighter().removeAllHighlights();
        for(CodeError err : errors) {
            int abs = lineHandler.getLineBeginning(err.getLine() - 1)
                    + err.getPosInLine();
            absPosToError.put(abs, err);
            try {
                pane.getHighlighter().addHighlight(abs, abs + 5, painter);
            } catch (BadLocationException ex) {
                Logger.getLogger(ErrorDisplayer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void caretUpdate(CaretEvent ce) {
        CodeError err = absPosToError.get(ce.getDot());
        if(err == null) return;
        System.err.println(err.getId());
    }

    
}
