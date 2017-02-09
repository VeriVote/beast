/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.InputToCode;

import edu.pse.beast.codearea.InputToCode.NewlineInserter.NewlineInserter;
import edu.pse.beast.codearea.InputToCode.NewlineInserter.NewlineInserterChooser;
import edu.pse.beast.codearea.SaveTextBeforeRemove;
import edu.pse.beast.codearea.StoppedTypingContinuouslyListener;
import edu.pse.beast.codearea.StoppedTypingContinuouslyMessager;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

/**
 * This class is responsible for translating input from the user into code 
 * visible in the given JTextPane. It is used by the class CodeInputHandler.
 * One of its main functions is to make sure locked lines dont get changed.
 * It also uses several other classes to provide functionallity such as
 * closing brackets and insert newlines
 * @author Holger-Desktop
 */
public class UserInsertToCode implements CaretListener, StoppedTypingContinuouslyListener {
    
    private JTextPane pane;
    private StyledDocument styledDoc;
    private OpenCloseCharList openCloseCharList;
    private LockedLinesHandler lockedLines;
    private NewlineInserterChooser newlineInserterChooser;
    private NewlineInserter currentInserter;
    private int currentCaretPosition;
    private TabInserter tabInserter;
    private LineBeginningTabsHandler lineBeginningTabsHandler;
    private SaveTextBeforeRemove saveBeforeRemove;
    private StoppedTypingContinuouslyMessager stoppedTypingContMsger;
    private boolean dontTypeClosingChar;
    private LockedLinesDisplay lockedLinesDisplay;
    
    public UserInsertToCode(JTextPane pane, OpenCloseCharList openCloseCharList,
            SaveTextBeforeRemove saveBeforeRemove) {
        this.pane = pane;
        this.pane.addCaretListener(this);
        this.styledDoc = pane.getStyledDocument(); 
        this.openCloseCharList = openCloseCharList; 
        this.saveBeforeRemove = saveBeforeRemove;
        setupObjects();
    }
    
    
    private void setupObjects() {
        stoppedTypingContMsger = new StoppedTypingContinuouslyMessager(pane);
        stoppedTypingContMsger.addListener(this);
        this.tabInserter = new TabInserter(this.pane);
        this.lockedLines = new LockedLinesHandler(this.pane, saveBeforeRemove);
        this.lineBeginningTabsHandler = new CurlyBracesLineBeginningTabHandler(pane);
        this.newlineInserterChooser = new NewlineInserterChooser(pane, lockedLines);
        this.currentInserter = this.newlineInserterChooser.getNewlineInserter();       
        this.lockedLinesDisplay = new LockedLinesDisplay(pane, lockedLines);
    }

    public SaveTextBeforeRemove getSaveBeforeRemove() {
        return saveBeforeRemove;
    }
    
    public void insertNewline() throws BadLocationException {  
        currentInserter.insertNewlineAtCurrentPosition(pane, tabInserter, 
                lineBeginningTabsHandler, currentCaretPosition);               
        newlineInserterChooser.getNewlineInserter();
    }

    public void insertString(String string) {
        try {
            if (isLineContainingCaretPosLocked()) {
                return;
            } else {
                if (pane.getSelectedText() == null) {
                    pane.getStyledDocument().insertString(pane.getCaretPosition(), string, null);
                } else {
                    int selectionStart = pane.getSelectionStart();
                    int selectionEnd = pane.getSelectionEnd();
                    try {
                        pane.getStyledDocument().remove(selectionStart, selectionEnd - selectionStart);
                        pane.getStyledDocument().insertString(selectionStart, string, null);
                    } catch (BadLocationException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public void insertTab() throws BadLocationException {
        if(selectionIncludesLockedLines()) return;       
        if(isLineContainingCaretPosLocked()) return;        
        tabInserter.insertTabAtPos(currentCaretPosition);
    }

    private boolean isLineContainingCaretPosLocked() {
        return lockedLines.isLineLocked(JTextPaneToolbox.transformToLineNumber(pane, pane.getCaretPosition()));
    }

    public void insertChar(char keyChar) throws BadLocationException {
        if(selectionIncludesLockedLines()) return;
        if(isLineContainingCaretPosLocked()) return;
        if(pane.getSelectedText() != null) {
            pane.getStyledDocument().remove(
                    pane.getSelectionStart(),
                    pane.getSelectionEnd() - pane.getSelectionStart());
        }
        if(openCloseCharList.isOpenChar(keyChar)) {
            OpenCloseChar occ = openCloseCharList.getOpenCloseChar(keyChar);dontTypeClosingChar = true;
            occ.insertIntoDocument(pane, currentCaretPosition);
            dontTypeClosingChar = true;                        
        } else if(dontTypeClosingChar && openCloseCharList.isCloseChar(keyChar)) {
            dontTypeClosingChar = false;
            pane.setCaretPosition(currentCaretPosition + 1);
            return;
        } else {
            styledDoc.insertString(currentCaretPosition, Character.toString(keyChar), null);
        }
    }
    
    public void lockLine(int line) {
        lockedLines.lockLine(line);        
        this.currentInserter = this.newlineInserterChooser.getNewlineInserter();        
    }

    public int getFirstLockedLine() {
        return lockedLines.getFirstLockedLine();
    }

    @Override
    public void caretUpdate(CaretEvent ce) {
        currentCaretPosition = ce.getDot();
        currentInserter = newlineInserterChooser.getNewlineInserter();        
    }
    

    void moveToEndOfCurrentLine() {
        int end = JTextPaneToolbox.getClosestLineBeginningAfter(pane, currentCaretPosition);
        pane.setCaretPosition(end);
    }

    void moveToStartOfCurrentLine() {
        int dist = JTextPaneToolbox.getDistanceToClosestLineBeginning(pane, currentCaretPosition);
        pane.setCaretPosition(pane.getCaretPosition() - dist);
    }

    void removeToTheRight() {
        if(selectionIncludesLockedLines()) return;
        if(!isCaretInEmptyLine()) {
            if(isTheFollowingLineLocked()) {
                return;
            }
        } 
        if(!isCaretAtEndOfLine() && isLineContainingCaretPosLocked()) {
            return;
        }        
            
        try {
            if(pane.getSelectedText() != null) {
                pane.getStyledDocument().remove(
                        pane.getSelectionStart(),
                        pane.getSelectionEnd() - pane.getSelectionStart());
                return;
            }
            pane.getStyledDocument().remove(currentCaretPosition, 1);
        } catch (BadLocationException ex) {
            Logger.getLogger(UserInsertToCode.class.getName()).log(Level.SEVERE, null, ex);
        }           
        
    }

    private boolean isCaretAtEndOfLine() {
        return JTextPaneToolbox.getCharToTheRightOfCaret(pane).equals("\n");
    }

    private boolean isCaretInEmptyLine() {
        return isCaretAtEndOfLine() && 
                JTextPaneToolbox.getCharToTheLeftOfCaret(pane).equals("\n");
    }

    private boolean isTheFollowingLineLocked() {
        return lockedLines.isLineLocked(JTextPaneToolbox.transformToLineNumber(pane, currentCaretPosition) + 1);
    }

    public void removeToTheLeft() {
        if(selectionIncludesLockedLines()) return;
        if(currentCaretPosition == 0) return;        
        
        try {
            if(isLineContainingCaretPosLocked()) {
            if(!pane.getStyledDocument().getText(currentCaretPosition - 2, 2).equals("\n\n")) 
                return;
            }
            if(pane.getSelectedText() != null) {
                pane.getStyledDocument().remove(
                        pane.getSelectionStart(),
                        pane.getSelectionEnd() - pane.getSelectionStart());
                return;
            }
            
            pane.getStyledDocument().remove(currentCaretPosition - 1, 1);
        } catch (BadLocationException ex) {
            Logger.getLogger(UserInsertToCode.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean selectionIncludesLockedLines() {
        if(pane.getSelectedText() == null) return false;
        try {
            ArrayList<Integer> lines = JTextPaneToolbox.getLinesBetween(pane, pane.getSelectionStart(), pane.getSelectionEnd());
            for(int i : lines) {
                if(lockedLines.isLineLocked(i)) return true;
            }
            return false;
        } catch (BadLocationException ex) {
            Logger.getLogger(UserInsertToCode.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public void StoppedTypingContinuously(int newPos) {
        dontTypeClosingChar = false;
    }

    public OpenCloseCharList getOccList() {
        return this.openCloseCharList;
    }

    public void unlockAll() {
        lockedLines.unlockAll();
    }
}
