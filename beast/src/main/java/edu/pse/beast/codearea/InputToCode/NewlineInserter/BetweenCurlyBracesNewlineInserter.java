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
public class BetweenCurlyBracesNewlineInserter extends NewlineInserter {
    @Override
    public void insertNewlineAtCurrentPosition(UserInsertToCode insertToCode, int pos) throws BadLocationException {
        System.out.println("curlies");
        insertToCode.getTextPane().getStyledDocument().insertString(pos, "\n\n", null);
        insertToCode.getTextPane().setCaretPosition(pos + 1);        
        setTabLevel(insertToCode, pos);
    }    

}
