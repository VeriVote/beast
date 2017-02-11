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
 * This interface is implemented by all classes which insert linebreaks
 * in different ways
 * @author Holger-Desktop
 */
public interface NewlineInserter {
    public void insertNewlineAtCurrentPosition(
            JTextPane pane, TabInserter tabInserter,
            LineBeginningTabsHandler tabsHandler,
            int pos) throws BadLocationException;    
}
