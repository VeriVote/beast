/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea;

import edu.pse.beast.codearea.Actionlist.Actionlist;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JTextPane;
import edu.pse.beast.codearea.ActionAdder.ActionlistListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;

/**
 * This class saves the text of a styled document before a removed event is
 * triggered. This is needed because the remove event does not carry the information
 * of which text was removed. This info is needed eg by the textchangedactionadder.
 * It listens to keystrokes such as backspace which indicate that text is about to be
 * removed from the pane. It also listens to the actionlist so it can save the 
 * text when an action is about to be undone / redone
 * @author Holger-Desktop
 */
public class SaveTextBeforeRemove implements KeyListener, ActionlistListener {
    private JTextPane pane;
    private String prevText;
    private Actionlist actionlist;
    
    /**
     * @param pane The JTextPane of whose StyledDocument the text should be saved
     * before remove commands
     * @param actionlist the actionlist which performs redo and undo action in the
     * pane. This is needed to save before the actionlist redos or undoes actions
     * which remove text from the pane
     */
    public SaveTextBeforeRemove(JTextPane pane, Actionlist actionlist) {
        this.pane = pane;
        pane.addKeyListener(this);
        this.actionlist = actionlist;
        actionlist.addActionlistListener(this);
        try {
            prevText = pane.getStyledDocument().getText(0, pane.getStyledDocument().getLength());
        } catch (BadLocationException ex) {
            Logger.getLogger(SaveTextBeforeRemove.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getPrevText() {        
        return prevText;
    }
    
    /**
     * returns the String starting at offset of length length before the last 
     * textRemovedEvent
     * @param offset the offset of the first character of the removed string
     * @param length the lengt of the removed string
     * @return the removed string
     */
    public String getRemoveString(int offset, int length) {
        if (prevText.length() >= length) {
            try{
                return prevText.substring(offset, offset + length);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            return "";
        } else {
            return "";
        }
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
                ke.getKeyChar()== KeyEvent.VK_BACK_SPACE ||
                (ke.isShiftDown() && ke.getKeyChar() == KeyEvent.VK_TAB)) {            
            try {
                prevText = pane.getStyledDocument().getText(0, pane.getStyledDocument().getLength());
            } catch (BadLocationException ex) {
                Logger.getLogger(SaveTextBeforeRemove.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        try {
            prevText = pane.getStyledDocument().getText(0, pane.getStyledDocument().getLength());
        } catch (BadLocationException ex) {
            Logger.getLogger(SaveTextBeforeRemove.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    }

    /**
     * saves the current text of the given jtextpane
     * @throws BadLocationException should never be thrown
     */
    public void save() throws BadLocationException {
        prevText = pane.getStyledDocument().getText(0, pane.getStyledDocument().getLength());
    }
    
    @Override
    public void finishedUndoingAction() {        
    }
}
