/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.InputToCode.NewlineInserter;

import edu.pse.beast.codearea.InputToCode.UserInsertToCode;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

/**
 *
 * @author Holger-Desktop
 */
public class StandardNewlineInserter extends NewlineInserter {
    @Override
    public void insertNewlineAtCurrentPosition(UserInsertToCode insertToCode, int pos) throws BadLocationException {
        insertToCode.getTextPane().getStyledDocument().insertString(pos, "\n", null);
        int tabs = getScopeLevel(insertToCode.getTextPane().getText(), pos + 1);
        System.err.println(tabs);
        for(int i = 0; i < tabs; ++i) {
            insertToCode.getTabInserter().insertTabAtPos(pos + 1);
        }
    }
    
}
