/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.InputToCode.NewlineInserter;

import edu.pse.beast.codearea.InputToCode.LineBeginningTabsHandler;
import edu.pse.beast.codearea.InputToCode.TabInserter;
import edu.pse.beast.codearea.InputToCode.TextPaneSystemInfo;
import edu.pse.beast.codearea.InputToCode.UserInsertToCode;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

/**
 *
 * @author Holger-Desktop
 */
public class StandardNewlineInserter implements NewlineInserter {
        
    @Override
    public void insertNewlineAtCurrentPosition(
            JTextPane pane, TabInserter tabInserter,
            LineBeginningTabsHandler beginningTabsHandler,
            int pos) throws BadLocationException {
        
        pane.getStyledDocument().insertString(pos, "\n", null);
        
        int newLinePos = pos + 1;
        
        int tabs = beginningTabsHandler.getTabsForLine(newLinePos);
        
        for(int i = 0; i < tabs; ++i) {
            tabInserter.insertTabAtPos(newLinePos + i * tabInserter.getSpacesPerTab());
        }      
        
        
    }
    
}
