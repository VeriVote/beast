package edu.pse.beast.codearea.codeinput;

import javax.swing.JTextPane;

/**
 * This TabHandler calculates the amount of tabs needed at the beginning of any
 * given line by the amount of open curly braces in lines before. This is
 * sufficient for 99% of all cases.
 *
 * @author Holger Klein
 */
public class CurlyBracesLineBeginningTabHandler
        implements LineBeginningTabsHandler {

    /** The Constant BRACE_LEFT. */
    private static final char BRACE_LEFT = '{';

    /** The Constant BRACE_RIGHT. */
    private static final char BRACE_RIGHT = '}';

    /** The Constant NEW_LINE. */
    private static final char NEW_LINE = '\n';

    /** The pane. */
    private JTextPane pane;

    /**
     * Instantiates a new curly braces line beginning tab handler.
     *
     * @param textPane
     *            the text pane
     */
    public CurlyBracesLineBeginningTabHandler(final JTextPane textPane) {
        this.pane = textPane;
    }

    @Override
    public int getTabsForLine(final int caretPos) {
        int amt = 0;
        String code = JTextPaneToolbox.getText(pane);
        int pos = caretPos - 1;
        while (0 <= pos) {
            if (code.charAt(pos) == BRACE_LEFT) {
                ++amt;
                while (0 <= pos && code.charAt(pos) != NEW_LINE) {
                    --pos;
                }
            } else if (code.charAt(pos) == BRACE_RIGHT) {
                --amt;
            }
            --pos;
        }
        if (caretPos < code.length() && code.charAt(caretPos) == BRACE_RIGHT) {
            --amt;
        }
        return amt;
    }
}
