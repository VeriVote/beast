package edu.pse.beast.codearea;

import java.awt.Color;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTextPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;

import edu.pse.beast.codearea.codeinput.JTextPaneToolbox;
import edu.pse.beast.codearea.codeinput.OpenCloseChar;
import edu.pse.beast.codearea.codeinput.OpenCloseCharList;

/**
 * This class highlights open/close chars as well as their counterparts in code.
 * Example: The caret is either left or right of a '{'. It will highlight the
 * bracket as well as the corresponding '}' if it exists.
 *
 * @author Holger Klein
 */
public class OpenCloseCharHighlighter implements CaretListener {

    /** The char list. */
    private final OpenCloseCharList charList;

    /** The pane. */
    private final JTextPane pane;

    /** The highlighter. */
    private final DefaultHighlighter highlighter;

    /** The h painter. */
    private final DefaultHighlighter.DefaultHighlightPainter hPainter;

    /** The added hls. */
    private final ArrayList<Object> addedHls = new ArrayList<>();

    /**
     * Instantiates a new open close char highlighter.
     *
     * @param chars
     *            the chars
     * @param textPane
     *            the text pane
     */
    public OpenCloseCharHighlighter(final OpenCloseCharList chars,
                                    final JTextPane textPane) {
        this.charList = chars;
        this.pane = textPane;
        textPane.addCaretListener(this);
        highlighter = (DefaultHighlighter) textPane.getHighlighter();
        hPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.LIGHT_GRAY);
    }

    @Override
    public void caretUpdate(final CaretEvent ce) {
        removeAllHighlights();
        char[] surroundingChars = {' ', ' '};
        try {
            surroundingChars[0] = JTextPaneToolbox.getCharToTheLeftOfCaret(pane).charAt(0);
            surroundingChars[1] = JTextPaneToolbox.getCharToTheRightOfCaret(pane).charAt(0);
        } catch (StringIndexOutOfBoundsException ex) {
        }
        for (int i = 0; i < surroundingChars.length; i++) {
            char c = surroundingChars[i];
            if (c == ' ') {
                continue;
            }
            if (charList.isOpenChar(c)) {
                highlightChar(ce.getDot() + i);
                highlightCorrespondingCloseChar(ce.getDot() + i, charList.getOpenCloseChar(c));
                return;
            } else if (charList.isCloseChar(c)) {
                highlightChar(ce.getDot() + i);
                highlightCorrespondingOpenChar(ce.getDot() + i, charList.getOpenCloseChar(c));
                return;
            }
        }
    }

    /**
     * Highlight corresponding open char.
     *
     * @param pos
     *            the pos
     * @param openCloseChar
     *            the open close char
     */
    private void highlightCorrespondingOpenChar(final int pos,
                                                final OpenCloseChar openCloseChar) {
        char open = openCloseChar.getOpen();
        char close = openCloseChar.getClose();
        String code = JTextPaneToolbox.getText(pane);
        int lvl = 1;
        int p = pos - 2;
        for (; p >= 0 && lvl > 0; --p) {
            char c = code.charAt(p);
            if (c == close) {
                lvl++;
            } else if (c == open) {
                lvl--;
            }
        }
        if (lvl == 0) {
            highlightChar(p + 2);
        }
    }

    /**
     * Highlight corresponding close char.
     *
     * @param pos
     *            the pos
     * @param openCloseChar
     *            the open close char
     */
    private void highlightCorrespondingCloseChar(final int pos,
                                                 final OpenCloseChar openCloseChar) {
        char open = openCloseChar.getOpen();
        char close = openCloseChar.getClose();
        String code = JTextPaneToolbox.getText(pane);
        int lvl = 1;
        int p = pos;
        for (; p < code.length() && lvl > 0; ++p) {
            char c = code.charAt(p);
            if (c == open) {
                lvl++;
            } else if (c == close) {
                lvl--;
            }
        }
        if (lvl == 0) {
            highlightChar(p);
        }
    }

    /**
     * Highlight char.
     *
     * @param pos
     *            the pos
     */
    private void highlightChar(final int pos) {
        try {
            addedHls.add(highlighter.addHighlight(pos - 1, pos, hPainter));
        } catch (BadLocationException ex) {
            Logger.getLogger(OpenCloseCharHighlighter.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Removes the all highlights.
     */
    private void removeAllHighlights() {
        for (Object o : addedHls) {
            highlighter.removeHighlight(o);
        }
    }
}
