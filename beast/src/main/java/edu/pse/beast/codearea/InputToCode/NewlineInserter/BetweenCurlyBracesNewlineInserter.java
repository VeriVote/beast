/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.InputToCode.NewlineInserter;

import edu.pse.beast.codearea.InputToCode.LineBeginningTabsHandler;
import edu.pse.beast.codearea.InputToCode.TabInserter;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;

/**
 * Inserts newlines between two curly braces by adding two new lines
 * and centering the caret position between them:
 * {|} becomes
 * {
 *     |
 * }
 * @author Holger-Desktop
 */
public class BetweenCurlyBracesNewlineInserter implements NewlineInserter {
    private StandardNewlineInserter standInserter;
    
    public BetweenCurlyBracesNewlineInserter(StandardNewlineInserter standInserter) {
        this.standInserter = standInserter;
    }

    @Override
    public void insertNewlineAtCurrentPosition(
            JTextPane pane, TabInserter tabInserter,
            LineBeginningTabsHandler beginningTabsHandler,
            int pos) throws BadLocationException {
        standInserter.insertNewlineAtCurrentPosition(pane, tabInserter, beginningTabsHandler, pos);
        pane.setCaretPosition(pos);
        standInserter.insertNewlineAtCurrentPosition(pane, tabInserter, beginningTabsHandler, pos);
        
    }    

}
