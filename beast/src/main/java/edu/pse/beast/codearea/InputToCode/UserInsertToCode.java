/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.InputToCode;

import edu.pse.beast.codearea.InputToCode.NewlineInserter.NewlineInserter;
import edu.pse.beast.codearea.InputToCode.NewlineInserter.NewlineInserterChooser;
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
    private TabInserter tabHandler;
    
    
    public UserInsertToCode(JTextPane pane, OpenCloseCharList openCloseCharList) {
        this.pane = pane;
        this.pane.addCaretListener(this);
        this.styledDoc = pane.getStyledDocument();
        this.lineHandler = new LineHandler(pane);
        this.tabHandler = new TabInserter(this.pane, lineHandler);
        this.openCloseCharList = openCloseCharList;
        this.lockedLines = new LockedLinesHandler(styledDoc);
        this.newlineInserterChooser = new NewlineInserterChooser(pane, lockedLines);
        this.currentInserter = this.newlineInserterChooser.getNewlineInserter();
                
    }

    void insertNewline() throws BadLocationException {    
        currentInserter.insertNewlineAtCurrentPosition(pane, currentCaretPosition);               
        newlineInserterChooser.getNewlineInserter();
    }
    
    void insertTab() throws BadLocationException {
        tabHandler.insertTabAtPos(currentCaretPosition);
    }

    void insertChar(char keyChar) throws BadLocationException {
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
}
