/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pse.beast.codearea.InputToCode;

import java.awt.event.KeyEvent;

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
        if(ke.getKeyChar() == '\n') insertToCode.insertNewline();
        insertToCode.insertChar(ke.getKeyChar());
    }
    
}
