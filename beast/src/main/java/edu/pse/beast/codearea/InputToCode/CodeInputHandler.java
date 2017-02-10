/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.InputToCode;

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
    
    public void handleSpecialKey(KeyEvent ke) {
        if(ke.getKeyCode() == KeyEvent.VK_END) {
            insertToCode.moveToEndOfCurrentLine();
        } else if(ke.getKeyCode() == KeyEvent.VK_HOME) {
            insertToCode.moveToStartOfCurrentLine();
        }  
    }
    
    public void handleKey(KeyEvent ke) {        
        try{
            if(ke.getKeyCode()== KeyEvent.VK_ENTER || ke.getKeyChar() == '\n') {
                insertToCode.insertNewline();
            } else if(ke.getKeyCode() == KeyEvent.VK_TAB || ke.getKeyChar() == '\t') {
                if(ke.isShiftDown()) {
                    insertToCode.removeTab();
                } else {                    
                    insertToCode.insertTab();
                }
            } else if(ke.getKeyCode() == KeyEvent.VK_BACK_SPACE || ke.getKeyChar() == '\b') {
                insertToCode.removeToTheLeft();
            } else if(ke.getKeyCode() == KeyEvent.VK_SPACE || ke.getKeyChar() != ''){            
                insertToCode.insertChar(ke.getKeyChar());                
            }
        } catch(BadLocationException ex) {
            ex.printStackTrace();
        }        
    }

    void delete() {
        insertToCode.removeToTheRight();
    }
}
