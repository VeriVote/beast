/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea;

import edu.pse.beast.codearea.Actionlist.Actionlist;
import edu.pse.beast.codearea.InputToCode.ShortcutHandler;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JTextPane;
import edu.pse.beast.codearea.ActionAdder.ActionlistListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;

/**
 *
 * @author Holger-Desktop
 */
public class SaveTextBeforeRemove implements KeyListener, ActionlistListener {
    private JTextPane pane;
    private String prevText = "";
    private Actionlist actionlist;
    
    public SaveTextBeforeRemove(JTextPane pane, Actionlist actionlist) {
        this.pane = pane;
        pane.addKeyListener(this);
        this.actionlist = actionlist;
        actionlist.addActionAdder(this);
    }

    public String getPrevText() {        
        return prevText;
    }
    
    public String getRemoveString(int offset, int length) {
        try{            
            return prevText.substring(offset, offset + length);    
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return "";
    }
    
    @Override
    public void keyTyped(KeyEvent ke) {       
        if(ke.getKeyCode() == KeyEvent.VK_LEFT ||
                ke.getKeyCode() == KeyEvent.VK_RIGHT ||
                ke.getKeyCode() == KeyEvent.VK_UP ||
                ke.getKeyCode() == KeyEvent.VK_DOWN) {
            return;
        }
        
        if(pane.getSelectedText() != null ||  
                ke.getKeyChar()== KeyEvent.VK_DELETE ||
                ke.getKeyChar()== KeyEvent.VK_BACK_SPACE ) {            
            try {
                prevText = pane.getStyledDocument().getText(0, pane.getStyledDocument().getLength());
            } catch (BadLocationException ex) {
                Logger.getLogger(SaveTextBeforeRemove.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        System.out.println("previous text: " + prevText);
    }

    @Override
    public void keyPressed(KeyEvent ke) {
       
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        
    }

    @Override
    public void undoingAction() {
        try {
                prevText = pane.getStyledDocument().getText(0, pane.getStyledDocument().getLength());
            } catch (BadLocationException ex) {
                Logger.getLogger(SaveTextBeforeRemove.class.getName()).log(Level.SEVERE, null, ex);
            }
        System.out.println("previous text: " + prevText);
    }

    
    public void save() throws BadLocationException {
         prevText = pane.getStyledDocument().getText(0, pane.getStyledDocument().getLength());
    }
    
    @Override
    public void finishedUndoingAction() {
        
    }
}
