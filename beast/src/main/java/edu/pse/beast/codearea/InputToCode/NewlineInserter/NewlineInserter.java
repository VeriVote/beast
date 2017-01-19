/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.InputToCode.NewlineInserter;

import edu.pse.beast.codearea.InputToCode.TabInserter;
import edu.pse.beast.codearea.InputToCode.UserInsertToCode;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;

/**
 *
 * @author Holger-Desktop
 */
public abstract class NewlineInserter {
    public abstract void insertNewlineAtCurrentPosition(UserInsertToCode insertToCode, int pos) throws BadLocationException; 
    
    protected void setTabLevel(UserInsertToCode insertToCode, int pos) throws BadLocationException {
        int scopeLvl = getScopeLevel(insertToCode.getTextPane().getText(), pos);
        for(int i = 0; i < scopeLvl; ++i) {
            insertToCode.insertTab();
        }
    }
    
    private int getScopeLevel(String text, int pos) {
        int lvl = 0;
        for(int i = 0; i < pos; ++i) {
            if(text.charAt(i) == '{') ++lvl;
            else if(text.charAt(i) == '}') --lvl;
        }
        return lvl;
    }
}
