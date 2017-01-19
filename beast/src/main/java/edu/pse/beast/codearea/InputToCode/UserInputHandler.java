/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pse.beast.codearea.InputToCode;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JTextPane;

/**
 *
 * @author Holger-Desktop
 */
public class UserInputHandler implements KeyListener {
    JTextPane pane;
    CodeInputHandler codeInputHandler;
    ShortcutHandler shortcutHandler;
    public UserInputHandler(JTextPane pane, CodeInputHandler codeInputHandler, ShortcutHandler shortcutHandler) {
        this.pane = pane;
        this.codeInputHandler = codeInputHandler;
        this.shortcutHandler = shortcutHandler;
        this.pane.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        if(isArrowKey(ke)) return;
        else if(!isShortcut(ke)) {
            codeInputHandler.handleKey(ke);
        }
        ke.consume();
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if(isArrowKey(ke)) return;   
        ke.consume();
    }

    @Override
    public void keyReleased(KeyEvent ke) {  
        if(isArrowKey(ke)) return;
        else if(isShortcut(ke)) {
            shortcutHandler.handleKey(ke);
            return;
        }
        ke.consume();
    }
    
    private boolean isShortcut(KeyEvent ke) {
        return ke.isControlDown();
    }
    
    private boolean isArrowKey(KeyEvent ke) {
        return (ke.getKeyCode() == KeyEvent.VK_LEFT ||
                ke.getKeyCode() == KeyEvent.VK_RIGHT ||
                ke.getKeyCode() == KeyEvent.VK_UP ||
                ke.getKeyCode() == KeyEvent.VK_DOWN);
    }
}
