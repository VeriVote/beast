package edu.pse.beast.codearea.codeinput;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;

/**
 * This class is a collection of useful functionality often needed when working
 * with JTextPanes.
 *
 * @author Holger Klein
 */
public final class JTextPaneToolbox {
    private JTextPaneToolbox() { }

    /**
     * returns the text represented by the JTextPane in a non-OS dependent manner.
     * In Windows for example, line breaks are represented by an additional \r before
     * the \n. These will not be returned by this function. This is very useful since
     * the pane's caret position uses these indices instead of the OS-specific ones.
     * Meaning a string such as 0123\r\n|78 would return caret position 5 even
     * though it "should" be position 6.
     *
     * @param pane the pane who's text should be retrieved
     * @return the text shown by the pane in an OS-independent manner
     */
    public static String getText(JTextPane pane) {
        try {
            return pane.getStyledDocument().getText(0, pane.getStyledDocument().getLength());
        } catch (BadLocationException ex) {
            Logger.getLogger(JTextPaneToolbox.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    /**
     * calculates the distance to the closest line break before the given position.
     * If the position is in the first line, it returns position.
     *
     * @param pane the pane who's text should be considered
     * @param pos  the starting position
     * @return the distance of the closest line break before the position
     */
    public static int getDistanceToClosestLineBeginning(JTextPane pane, int pos) {
        String code = getText(pane);
        for (int i = pos - 1; i >= 0; --i) {
            if (code.charAt(i) == '\n') {
                return pos - i - 1;
            }
        }
        return pos;
    }

    /**
     * takes a given absolute position and transforms it into the line number
     *
     * @param pane   the pane who's text should be considered
     * @param absPos the absolute position to be converted into a line number
     * @return the line number of the line containing absPos
     */
    public static int transformToLineNumber(JTextPane pane, int absPos) {
        return transformToLineNumber(getText(pane), absPos);
    }

    /**
     * takes a given absolute position and transforms it into the line number
     *
     * @param code   The text in which the line number should be found
     * @param absPos the absolute position to be converted into a line number
     * @return the line number of the line containing absPos
     */
    public static int transformToLineNumber(String code, int absPos) {
        int amt = 0;
        for (int i = 0; i < absPos && i < code.length(); ++i) {
            if (code.charAt(i) == '\n') {
                amt++;
            }
        }
        return amt;
    }

    /**
     * finds and returns the absolute position of the beginning of the given line
     *
     * @param pane the pane who's code should be searched for the position
     * @param line the line who's beginning should be found
     * @return the absolute position of the line beginning of line
     */
    public static int getLineBeginning(JTextPane pane, int line) {
        String code = getText(pane);
        int absPos = 0;
        int lineNumber = 0;
        for (; absPos < code.length() && lineNumber < line; ++absPos) {
            if (code.charAt(absPos) == '\n') {
                ++lineNumber;
            }
        }
        return absPos;
    }

    /**
     * finds the closest line break after the given absolute position
     *
     * @param pane   the pane who's code should be searched for the line break
     * @param absPos the absolute position after which the first line break is to be found
     * @return the first line break after absolute position
     */
    public static int getClosestLineBeginningAfter(JTextPane pane, int absPos) {
        String code = getText(pane);
        int pos = absPos;
        ++pos;
        for (; pos < code.length(); ++pos) {
            if (code.charAt(pos) == '\n') {
                return pos;
            }
        }
        return pos;
    }

    /**
     * finds all line numbers between an absolute start and end position. example:
     * ("i like\nyou very\nmuch", 0,17) would return 0,1,2
     *
     * @param pane  the pane in who's text the line positions need to be searched
     * @param start the starting position
     * @param end   the last absolute position
     * @return the line numbers between the absolute position start and end
     * @throws BadLocationException when location does not exist
     */
    public static ArrayList<Integer> getLinesBetween(JTextPane pane,
                                                     int start,
                                                     int end) throws BadLocationException {
        int startingline = transformToLineNumber(pane, start);
        ArrayList<Integer> lines = new ArrayList<>();
        lines.add(startingline);
        String code = getText(pane);
        int s = start;
        for (; s < end; ++s) {
            if (code.charAt(s) == '\n') {
                startingline++;
                lines.add(startingline);
            }
        }
        return lines;
    }

    /**
     * Returns the first character to the left of the caret or "" if there is no such character.
     * position 0: asd|asd returns d
     *
     * @param pane the pane from who's text the char should be extracted
     * @return the first char to the left of the caret position
     */
    public static String getCharToTheLeftOfCaret(JTextPane pane) {
        String code = getText(pane);
        int caretPos = pane.getCaretPosition();
        if (caretPos == 0) {
            return "";
        } else {
            return code.substring(caretPos - 1, caretPos);
        }
    }

    /**
     * returns the first character to the right of the caret or "" if there is no such character.
     * position 0: asd|asd returns s
     *
     * @param pane the pane from who's text the char should be extracted
     * @return the first char to the right of the caret position
     */
    public static String getCharToTheRightOfCaret(JTextPane pane) {
        String code = getText(pane);
        int caretPos = pane.getCaretPosition();
        if (caretPos == code.length()) {
            return "";
        } else {
            return code.substring(caretPos, caretPos + 1);
        }
    }

    /**
     * returns the position of the first non white-space character in the given
     * line. If no such character exists, it returns the end of the line.
     *
     * @param pane the pane in which the character position should be found
     * @param line the line number in which the first non-whitespace char should be
     *             found
     * @return the absolute position of the first non-whitespace character in line
     */
    public static int getFirstCharPosInLine(JTextPane pane, int line) {
        String code = getText(pane);
        int absPos = getLineBeginning(pane, line);
        for (; absPos < code.length()
                && code.charAt(absPos) == ' '
                && code.charAt(absPos) != '\n';
                ++absPos) {
        }
        return absPos;
    }

    /**
     * finds the absolute position of the beginning of the word in which the current
     * caret position is: "asd 12|asd asd" returns 3
     *
     * @param pane the text pane
     * @return the absolute position of the beginning of the word on which the caret
     *         is centered
     */
    public static int getWordBeginningAtCursor(JTextPane pane) {
        String code = getText(pane);
        for (int pos = pane.getCaretPosition() - 1; pos >= 0; pos--) {
            if (code.charAt(pos) == ' ' || code.charAt(pos) == '\n') {
                return pos + 1;
            }
        }
        return 0;
    }
}