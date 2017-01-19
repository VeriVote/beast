/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pse.beast.codearea.InputToCode.NewlineInserter;

import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

/**
 *
 * @author Holger-Desktop
 */
public class StandardNewlineInserter implements NewlineInserter {

    @Override
    public void insertNewlineAtCurrentPosition(StyledDocument doc, int pos) throws BadLocationException {
        doc.insertString(pos, Character.toString('\r'), null);
    }
    
}
