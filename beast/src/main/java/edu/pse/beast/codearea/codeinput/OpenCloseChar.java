package edu.pse.beast.codearea.codeinput;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;

/**
 * This class is utilized for user-inserted code to automatically close
 * characters which have a closing complements, such as { and }.
 *
 * @author Holger Klein
 */
public class OpenCloseChar {

    /** The open. */
    private final char open;

    /** The close. */
    private final char close;

    /**
     * Instantiates a new open close char.
     *
     * @param openChar
     *            the open char
     * @param closeChar
     *            the close char
     */
    public OpenCloseChar(final char openChar, final char closeChar) {
        this.open = openChar;
        this.close = closeChar;
    }

    /**
     * Gets the open.
     *
     * @return the open
     */
    public char getOpen() {
        return this.open;
    }

    /**
     * Gets the close.
     *
     * @return the close
     */
    public char getClose() {
        return this.close;
    }

    /**
     * The open char is inserted into the given pane at the given position. It
     * also automatically inserts the closing char and centers the caret
     * position between them asd | becomes asd {|}
     *
     * @param pane
     *            the JTextPane in which the open and close chars should be
     *            inserted
     * @param pos
     *            the position at which the chars should be inserted
     * @throws BadLocationException
     *             if the position is not valid
     */
    public void insertIntoDocument(final JTextPane pane, final int pos)
            throws BadLocationException {
        String stringToInsert =
                Character.toString(open) + Character.toString(close);
        pane.getStyledDocument().insertString(pos, stringToInsert, null);
        pane.setCaretPosition(pos + 1);
    }
}
