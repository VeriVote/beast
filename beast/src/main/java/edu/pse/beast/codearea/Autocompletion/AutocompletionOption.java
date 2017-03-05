/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.Autocompletion;

import edu.pse.beast.codearea.InputToCode.JTextPaneToolbox;
import edu.pse.beast.codearea.InputToCode.UserInsertToCode;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class represents an autocompletion option. Once the user chooses a
 * specific option, this class will insert its insertstring into the given
 * JTextPane. It also provides functionality to move the cartet position
 * afterwards
 *
 * @author Holger-Desktop
 */
public class AutocompletionOption {

    private final String similarString;
    private final String insertString;
    private int moveCaretAfter = 0;

    public AutocompletionOption(String similarString, String insertString) {
        this.insertString = insertString;
        this.similarString = similarString;
    }

    public AutocompletionOption(String similarString, String insertString, int moveCaretAfter) {
        this.insertString = insertString;
        this.similarString = similarString;
        this.moveCaretAfter = moveCaretAfter;
    }

    public boolean equals(AutocompletionOption other) {
        return other.similarString.equals(similarString) && other.insertString.equals(insertString);
    }

    public String getInsertString() {
        return insertString;
    }

    public String getSimilarString() {
        return similarString;
    }

    /**
     * inserts the given string, char by char, into the inserttocode thus
     * ensuring that all chars are entered correctly
     *
     * @param pane the pane in which the string will be inserted
     * @param caretPosition the caretposition at which the string should be
     * inserted
     * @param insertToCode the insertToCode which controlls translateing input
     * into code
     */
    void insertInto(JTextPane pane, int caretPosition, UserInsertToCode insertToCode) {
        try {
            int wordBeginning = JTextPaneToolbox.getWordBeginningAtCursor(pane);
            int dist = pane.getCaretPosition() - wordBeginning;
            String removedWord = pane.getStyledDocument().getText(wordBeginning, dist);
            for (int i = 0; i < removedWord.length(); ++i) {
                insertToCode.getSaveBeforeRemove().save();
                insertToCode.removeToTheLeft();
            }
            for (int i = 0; i < insertString.length(); i++) {
                if (insertString.charAt(i) == '\n') {
                    insertToCode.insertNewline();
                } else {
                    insertToCode.insertChar(insertString.charAt(i));
                }
            }

            pane.setCaretPosition(pane.getCaretPosition() + moveCaretAfter);
        } catch (BadLocationException ex) {
            Logger.getLogger(AutocompletionOption.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
