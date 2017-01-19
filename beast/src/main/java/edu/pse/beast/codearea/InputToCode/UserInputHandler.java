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
    }

    @Override
    public void keyPressed(KeyEvent ke) {
    }

    @Override
    public void keyReleased(KeyEvent ke) {      
        if(isCode(ke)) codeInputHandler.handleKey(ke);
        else if(isShortcut(ke)) shortcutHandler.handleKey(ke);
    }  

    private boolean isCode(KeyEvent ke) {
        return Character.isAlphabetic(ke.getKeyChar());
    }
    
    private boolean isShortcut(KeyEvent ke) {
        return ke.isControlDown();
    }
}
