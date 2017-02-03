/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.ErrorHandling;

import edu.pse.beast.codearea.InputToCode.LineHandler;
import edu.pse.beast.highlevel.DisplaysStringsToUser;
import edu.pse.beast.stringresource.StringResourceLoader;
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
public abstract class ErrorDisplayer implements CaretListener, DisplaysStringsToUser {
    protected JTextPane pane;
    private SquigglePainter painter;
    private LineHandler lineHandler;
    private HashMap<Integer, CodeError> absPosToError;
    protected StringResourceLoader currentStringResLoader;
    
    public ErrorDisplayer(JTextPane pane, StringResourceLoader currentStringResLoader) {
        absPosToError = new HashMap<>();
        this.pane = pane;
        pane.addCaretListener(this);
        this.painter = new SquigglePainter(Color.red);        
        this.lineHandler = new LineHandler(pane);
        this.currentStringResLoader = currentStringResLoader;
    }
    
    public void showErrors(ArrayList<CodeError> errors) {
        absPosToError = new HashMap<>();
    }
    
    protected void showError(CodeError er, String msg) {
        System.err.println(getPosString(er) + ": " + msg);       
    }
    
    private String getPosString(CodeError er) {
        String template = "ERROR: ERRNO, LINE: LINO, CHAR: POSNO";
        template = template.replace("ERROR", currentStringResLoader.getStringFromID("error"));
        template = template.replace("LINE", currentStringResLoader.getStringFromID("line"));
        template = template.replace("CHAR", currentStringResLoader.getStringFromID("char"));
        template = template.replace("LINO", String.valueOf(er.getLine()));
        template = template.replace("POSNO", String.valueOf(er.getPosInLine()));
        template = template.replace("ERRNO", String.valueOf(er.getErrorNumber()));
        return template;
    }

    @Override
    public void caretUpdate(CaretEvent ce) {
        CodeError err = absPosToError.get(ce.getDot());
        if(err == null) return;
        System.err.println(err.getId());
    }

    
}
