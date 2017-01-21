/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.InputToCode;

import edu.pse.beast.codearea.InputToCode.NewlineInserter.NewlineInserter;
import edu.pse.beast.codearea.InputToCode.NewlineInserter.NewlineInserterChooser;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

/**
 *
 * @author Holger-Desktop
 */
public class UserInsertToCode implements CaretListener {
    
    private JTextPane pane;
    private StyledDocument styledDoc;
    private OpenCloseCharList openCloseCharList;
    private LockedLinesHandler lockedLines;
    private NewlineInserterChooser newlineInserterChooser;
    private NewlineInserter currentInserter;
    private int currentCaretPosition;
    private LineHandler lineHandler;
    private TabInserter tabInserter;
    private LineBeginningTabsHandler lineBeginningTabsHandler;
    
    public UserInsertToCode(JTextPane pane, OpenCloseCharList openCloseCharList) {
        this.pane = pane;
        this.pane.addCaretListener(this);
        this.styledDoc = pane.getStyledDocument(); 
        this.openCloseCharList = openCloseCharList;       
        setupObjects();
    }

    public void insertNewline() throws BadLocationException {  
        currentInserter.insertNewlineAtCurrentPosition(pane, tabInserter, 
                lineBeginningTabsHandler, currentCaretPosition);               
        newlineInserterChooser.getNewlineInserter();
    }
    
    public void insertTab() throws BadLocationException {
        tabInserter.insertTabAtPos(currentCaretPosition);
    }

    public void insertChar(char keyChar) throws BadLocationException {
        if(openCloseCharList.isOpenChar(keyChar)) {
            OpenCloseChar occ = openCloseCharList.getOpenCloseChar(keyChar);
            occ.insertIntoDocument(pane, currentCaretPosition);
        } else {
            styledDoc.insertString(currentCaretPosition, Character.toString(keyChar), null);
        }
    }
    
    @Override
    public void caretUpdate(CaretEvent ce) {
        currentCaretPosition = ce.getDot();
        currentInserter = newlineInserterChooser.getNewlineInserter();        
    }
    
    private void setupObjects() {
        this.lineHandler = new LineHandler(this.pane);
        this.tabInserter = new TabInserter(this.pane, lineHandler);
        this.lockedLines = new LockedLinesHandler(styledDoc, lineHandler);
        this.lineBeginningTabsHandler = new CurlyBracesLineBeginningTabHandler(pane, lineHandler);
        this.newlineInserterChooser = new NewlineInserterChooser(pane, lockedLines);
        this.currentInserter = this.newlineInserterChooser.getNewlineInserter();          
    }
}
