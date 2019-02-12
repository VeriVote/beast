/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.ErrorHandling;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTextPane;
import javax.swing.ToolTipManager;
import javax.swing.text.BadLocationException;

import edu.pse.beast.codearea.InputToCode.JTextPaneToolbox;
import edu.pse.beast.highlevel.DisplaysStringsToUser;
import edu.pse.beast.stringresource.StringResourceLoader;
import edu.pse.beast.toolbox.Tuple;

/**
 * This abstract class implements error displaying functionallity common to
 * all specialized error display subclasses
 * @author Holger-Desktop
 */
public abstract class ErrorDisplayer implements DisplaysStringsToUser, MouseMotionListener {
    protected JTextPane pane;
    private SquigglePainter painter;
    private ArrayList<Tuple<Integer, Integer>> absPosToMsg;
    private ArrayList<String> msges;
    protected StringResourceLoader currentStringResLoader;
    private ArrayList<Object> highLights = new ArrayList<>();    
    private ErrorPopupMenu errorPopupMenu;
    
    public ErrorDisplayer(JTextPane pane, StringResourceLoader currentStringResLoader) {
        absPosToMsg = new ArrayList<>();
        msges = new ArrayList<>();
        ToolTipManager.sharedInstance().setInitialDelay(1);
        ToolTipManager.sharedInstance().setDismissDelay(10000);
        this.pane = pane;
        pane.addMouseMotionListener(this);
        this.painter = new SquigglePainter(Color.red);  
        this.currentStringResLoader = currentStringResLoader;
        errorPopupMenu = new ErrorPopupMenu(pane);
    }


    /**
     * removes all previously shown errors and thus gets
     * ready to show the newly found ones. This method
     * must be overwritten by subclasses to do anything useful
     * @param errors the errors to be presented
     */
    public void showErrors(ArrayList<CodeError> errors) {
        absPosToMsg = new ArrayList<>();
        msges = new ArrayList<>();
        
        for (Object o : highLights) {
            pane.getHighlighter().removeHighlight(o);
        }
        pane.repaint();
    }
    
    protected void showError(CodeError er, String msg) {
        int startpos = er.getStartPos();
        int endpos = er.getEndPos();
        if (startpos == endpos) {
            endpos++;
        }
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

    /**
     * checks if the mouse position is over a part of the code which contains an error.
     * if so, it displays a popupmenu with the error message
     * @param e
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        Point pt = new Point(e.getX(), e.getY());
        int pos = pane.viewToModel2D(pt);
        if(pos == JTextPaneToolbox.getText(pane).length()) {
            pane.setToolTipText(null);
            //errorPopupMenu.setVisible(false);
            return;
        }
        if (Math.abs(errorPopupMenu.getLocation().x - pt.getX()) < 10 &&
                Math.abs(errorPopupMenu.getLocation().x - pt.getX()) < 10 &&
                errorPopupMenu.isVisible()) {
            return;
        }
        for (int i = 0; i < absPosToMsg.size(); ++i) {
            if (absPosToMsg.get(i).x <= pos && absPosToMsg.get(i).y >= pos) {
                pane.setToolTipText(msges.get(i));
                //errorPopupMenu.getErrorItem().setText(msges.get(i));
                //errorPopupMenu.show(pane, e.getX(), e.getY() + 20);
                return;
            }
        }
        pane.setToolTipText(null);
        /*if(errorPopupMenu.isVisible()) {            
            errorPopupMenu.setVisible(false);
        }*/
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        
    }

}
