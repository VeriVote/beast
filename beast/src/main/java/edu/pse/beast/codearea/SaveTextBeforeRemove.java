/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JTextPane;

/**
 *
 * @author Holger-Desktop
 */
public class SaveTextBeforeRemove implements KeyListener {
    private JTextPane pane;
    private String prevText;
    
    public SaveTextBeforeRemove(JTextPane pane) {
        this.pane = pane;
        pane.addKeyListener(this);
    }

    public String getPrevText() {
        return prevText;
    }
    
    public String getRemoveString(int offset, int length) {
        prevText = prevText.replaceAll("\r", "");
        return prevText.substring(offset, offset + length - 1);
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
            prevText = pane.getText();
        }
        System.out.println(prevText);
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if(ke.getKeyCode() == KeyEvent.VK_LEFT ||
                ke.getKeyCode() == KeyEvent.VK_RIGHT ||
                ke.getKeyCode() == KeyEvent.VK_UP ||
                ke.getKeyCode() == KeyEvent.VK_DOWN) {
            return;
        }          
        prevText = pane.getText();
        
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        
    }
}
