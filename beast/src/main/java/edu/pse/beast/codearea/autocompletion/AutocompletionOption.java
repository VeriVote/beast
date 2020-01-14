//package edu.pse.beast.codearea.autocompletion;
//
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//import javax.swing.JTextPane;
//import javax.swing.text.BadLocationException;
//
//import edu.pse.beast.codearea.codeinput.JTextPaneToolbox;
//import edu.pse.beast.codearea.codeinput.UserInsertToCode;
//
///**
// * This class represents an auto completion option. Once the user chooses a
// * specific option, this class will insert its insert string into the given
// * JTextPane. It also provides functionality to move the caret position
// * afterwards
// *
// * @author Holger Klein
// */
//public class AutocompletionOption {
//
//    private final String similarString;
//    private final String insertString;
//    private int moveCaretAfter = 0;
//
//    public AutocompletionOption(String similarString, String insertString) {
//        this.insertString = insertString;
//        this.similarString = similarString;
//    }
//
//    public AutocompletionOption(String similarString, String insertString, int moveCaretAfter) {
//        this.insertString = insertString;
//        this.similarString = similarString;
//        this.moveCaretAfter = moveCaretAfter;
//    }
//
//    public boolean equals(AutocompletionOption other) {
//        return other.similarString.equals(similarString)
//               && other.insertString.equals(insertString);
//    }
//
//    public String getInsertString() {
//        return insertString;
//    }
//
//    public String getSimilarString() {
//        return similarString;
//    }
//
//    /**
//     * inserts the given string, char by char, into the insert-to-code thus
//     * ensuring that all chars are entered correctly
//     *
//     * @param pane the pane in which the string will be inserted
//     * @param caretPosition the caretposition at which the string should be
//     * inserted
//     * @param insertToCode the insertToCode which controlls translating input
//     * into code
//     */
//    void insertInto(JTextPane pane, int caretPosition, UserInsertToCode insertToCode) {
//        try {
//            int wordBeginning = JTextPaneToolbox.getWordBeginningAtCursor(pane);
//            int dist = pane.getCaretPosition() - wordBeginning;
//            String removedWord = pane.getStyledDocument().getText(wordBeginning, dist);
//            for (int i = 0; i < removedWord.length(); ++i) {
//                insertToCode.getSaveBeforeRemove().save();
//                insertToCode.removeToTheLeft();
//            }
//            for (int i = 0; i < insertString.length(); i++) {
//                if (insertString.charAt(i) == '\n') {
//                    insertToCode.insertNewline();
//                } else {
//                    insertToCode.insertChar(insertString.charAt(i));
//                }
//            }
//            pane.setCaretPosition(pane.getCaretPosition() + moveCaretAfter);
//        } catch (BadLocationException ex) {
//            Logger.getLogger(AutocompletionOption.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//}
