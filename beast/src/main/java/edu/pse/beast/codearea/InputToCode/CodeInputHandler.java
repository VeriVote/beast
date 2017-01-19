/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pse.beast.codearea.InputToCode;

import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;

/**
 *
 * @author Holger-Desktop
 */
public class CodeInputHandler {

    private UserInsertToCode insertToCode;
    
    public CodeInputHandler(UserInsertToCode insertToCode) {
        this.insertToCode = insertToCode;
    }
    
    public void handleKey(KeyEvent ke) {
        if(ke.getKeyChar() == '\n') try {
            insertToCode.insertNewline();
        } catch (BadLocationException ex) {
            Logger.getLogger(CodeInputHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            insertToCode.insertChar(ke.getKeyChar());
        } catch (BadLocationException ex) {
            Logger.getLogger(CodeInputHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
