/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.InputToCode.NewlineInserter;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

/**
 *
 * @author Holger-Desktop
 */
public class BetweenCurlyBracesNewlineInserter extends NewlineInserter {
    @Override
    public void insertNewlineAtCurrentPosition(JTextPane pane, int pos) throws BadLocationException {
        System.out.println("curlies");
        pane.getStyledDocument().insertString(pos, "\n", null);
        pane.setCaretPosition(pos);        
        setTabLevel(pane, pos);
    }    

}
