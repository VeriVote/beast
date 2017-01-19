/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pse.beast.codearea.InputToCode;

import com.pse.beast.codearea.InputToCode.NewlineInserter.NewlineInserter;
import com.pse.beast.codearea.InputToCode.NewlineInserter.NewlineInserterChooser;
import javax.swing.JTextPane;
import javax.swing.text.StyledDocument;

/**
 *
 * @author Holger-Desktop
 */
public class UserInsertToCode {
    
    private JTextPane pane;
    private StyledDocument styledDoc;
    private OpenCloseCharList openCloseCharList;
    private LockedLinesHandler lockedLines;
    private NewlineInserterChooser newlineInserterChooser;
    private NewlineInserter currentInserter;
    
    public UserInsertToCode(JTextPane pane, OpenCloseCharList openCloseCharList) {
        this.pane = pane;
        this.styledDoc = pane.getStyledDocument();
        this.openCloseCharList = openCloseCharList;
        this.lockedLines = new LockedLinesHandler(styledDoc);
        this.newlineInserterChooser = new NewlineInserterChooser(pane, lockedLines);
        this.currentInserter = this.newlineInserterChooser.getNewlineInserter();
    }

    void insertNewline() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void insertChar(char keyChar) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
