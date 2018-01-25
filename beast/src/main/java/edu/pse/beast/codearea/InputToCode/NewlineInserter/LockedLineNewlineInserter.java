/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.InputToCode.NewlineInserter;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;

import edu.pse.beast.codearea.InputToCode.JTextPaneToolbox;
import edu.pse.beast.codearea.InputToCode.LineBeginningTabsHandler;
import edu.pse.beast.codearea.InputToCode.TabInserter;

/**
 * Inesrts newlines into locked lines by inserting it at their beginning,
 * thus shifting the entire line down by one
 * @author Holger-Desktop
 */
public class LockedLineNewlineInserter implements NewlineInserter {
    
  
    @Override
    public void insertNewlineAtCurrentPosition(
            JTextPane pane, TabInserter tabInserter,
            LineBeginningTabsHandler beginningTabsHandler,
            int pos) throws BadLocationException {
            
            int lineBeginning = pos - JTextPaneToolbox.getDistanceToClosestLineBeginning(pane, pos);
            
            pane.getStyledDocument().insertString(lineBeginning, "\n", null);
    }
    
}
