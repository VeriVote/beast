/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.ErrorHandling;

import edu.pse.beast.codearea.InputToCode.LineHandler;
import edu.pse.beast.highlevel.DisplaysStringsToUser;
import edu.pse.beast.stringresource.StringResourceLoader;
import edu.pse.beast.toolbox.Tuple;
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
public abstract class ErrorDisplayer implements DisplaysStringsToUser, MouseMotionListener {
    protected JTextPane pane;
    private SquigglePainter painter;
    private LineHandler lineHandler;
    private ArrayList<Tuple<Integer,Integer>> absPosToMsg;
    private ArrayList<String> msges;
    protected StringResourceLoader currentStringResLoader;
    private ArrayList<Object> highLights = new ArrayList<>();    
    private ErrorPopupMenu errorPopupMenu = new ErrorPopupMenu();
    
    public ErrorDisplayer(JTextPane pane, StringResourceLoader currentStringResLoader) {
        absPosToMsg = new ArrayList<>();
        msges = new ArrayList<>();
        this.pane = pane;
        pane.addMouseMotionListener(this);
        this.painter = new SquigglePainter(Color.red);        
        this.lineHandler = new LineHandler(pane);
        this.currentStringResLoader = currentStringResLoader;
    }
    
    public void showErrors(ArrayList<CodeError> errors) {
        absPosToMsg = new ArrayList<>();
        msges = new ArrayList<>();
        
        for(Object o : highLights) pane.getHighlighter().removeHighlight(o);
    }
    
    protected void showError(CodeError er, String msg) {
        System.err.println(getPosString(er) + ": " + msg);   
        int startpos = er.getStartPos();
        int endpos = er.getEndPos();
        if(startpos == endpos) endpos++;
        absPosToMsg.add(new Tuple<>(startpos, endpos));
        msges.add(msg);
        try {
            highLights.add(pane.getHighlighter().addHighlight(startpos, endpos + 1, painter));
        } catch (BadLocationException ex) {
            Logger.getLogger(ErrorDisplayer.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    public void mouseMoved(MouseEvent e) {
        Point pt = new Point(e.getX(), e.getY());
        int pos = pane.viewToModel(pt);
        //System.out.println("m abs " + pt.x + "," + pt.y + " m_pos: " + pos);
        if(Math.abs(errorPopupMenu.getLocation().x - pt.getX()) < 10 && Math.abs(errorPopupMenu.getLocation().x - pt.getX()) < 10 && errorPopupMenu.isVisible()) return;
        for(int i = 0; i < absPosToMsg.size(); ++i) {
            if(absPosToMsg.get(i).x <= pos && absPosToMsg.get(i).y >= pos) {
                errorPopupMenu.getErrorItem().setText(msges.get(i));
                errorPopupMenu.show(pane, e.getX(), e.getY());
                System.out.println("showing err window");
                return;
            }
        }
        if(errorPopupMenu.isVisible()) {            
            errorPopupMenu.setVisible(false);
            System.out.println("hiding err window");
        }
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        
    }

    
}
