/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.InputToCode.NewlineInserter;

import edu.pse.beast.codearea.InputToCode.JTextPaneToolbox;
import edu.pse.beast.codearea.InputToCode.LineBeginningTabsHandler;
import edu.pse.beast.codearea.InputToCode.TabInserter;
import edu.pse.beast.codearea.InputToCode.UserInsertToCode;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

/**
 *
 * @author Holger-Desktop
 */
public class LockedLineNewlineInserter implements NewlineInserter {
    
  
    @Override
    public void insertNewlineAtCurrentPosition(
            JTextPane pane, TabInserter tabInserter,
            LineBeginningTabsHandler beginningTabsHandler,
            int pos) throws BadLocationException {
            
            int lineBeginning = pos - JTextPaneToolbox.getDistanceToClosestLineBeginning(pane, pos);
            
            pane.getStyledDocument().insertString(lineBeginning + 1, "\n", null);
    }
    
}
