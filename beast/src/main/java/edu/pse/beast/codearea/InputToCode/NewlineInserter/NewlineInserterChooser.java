/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pse.beast.codearea.InputToCode.NewlineInserter;

import com.pse.beast.codearea.InputToCode.LockedLinesHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;

/**
 *
 * @author Holger-Desktop
 */
public class NewlineInserterChooser {
    private JTextPane pane;
    private StandardNewlineInserter standardInserter = new StandardNewlineInserter();
    private LockedLineNewlineInserter lockedInserter = new LockedLineNewlineInserter();
    private BetweenCurlyBracesNewlineInserter curlyBracesInserter = new BetweenCurlyBracesNewlineInserter();
    private LockedLinesHandler lockedLinesHandler;
    
    public NewlineInserterChooser(JTextPane pane, LockedLinesHandler lockedLinesHandler) {
        this.pane = pane;
        this.lockedLinesHandler = lockedLinesHandler;
    }
    
    public NewlineInserter getNewlineInserter() {
        NewlineInserter found = chooseNewlineInserter();
        return findMoreSpecializedInserter(found);
    }   
    
    protected NewlineInserter findMoreSpecializedInserter(NewlineInserter current) {
        return current;
    }

    private NewlineInserter chooseNewlineInserter() {
        int lineNumber = absPosToLineNumber(pane.getCaretPosition());
        if(lockedLinesHandler.isLineLocked(lineNumber)) {
            return lockedInserter;
        }
        try {
            String surroundingChars = findSurroundingChars();
            if(isCurlyBracesSurround(surroundingChars)) {
                return curlyBracesInserter;
            }
        } catch (BadLocationException ex) {                 
        }
        return standardInserter;       
    }
    
    private int absPosToLineNumber(int caretPosition) {
        int line = 0;
        for(int i = 0; i < caretPosition; ++i) {
            if(pane.getText().charAt(i) == '\n') ++line;
        }
        return line;
    }
    
    private String findSurroundingChars() throws BadLocationException {
        int pos = pane.getCaretPosition();
        if(pos == 0) {
            return pane.getText(pos, 1);            
        } else if(pos == pane.getText().length()) {
            return pane.getText(pos - 1, 1);
        } else {
            return pane.getText(pos - 1, 2);
        }
    }

    private boolean isCurlyBracesSurround(String surroundingChars) {
        return surroundingChars.equals("{}");
    }

    
    
    
}
