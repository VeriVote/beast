package edu.pse.beast.codearea.codeinput;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

import edu.pse.beast.codearea.SaveTextBeforeRemove;
import edu.pse.beast.toolbox.SortedIntegerList;

/**
 * This class handles keeping track of the position of lines locked by the user.
 * It listens to removed and insert updates from the styleddocument of the
 * JTExtPane in which the lines are locked. If a linebreak is inserted before a
 * locked line, the line gets unlocked and the next one locked. Conversely, if a
 * linebreak is removed in front of a locked line, said line gets unlocked and
 * the line before it locked Every time a line gets locked/unlocked, it messages
 * all registered LockedLinesListener of this fact
 *
 * @author Holger Klein
 */
public final class LockedLinesHandler implements DocumentListener {
    /** The Constant LINE_BREAK. */
    private static final String LINE_BREAK = "\n";
    /** The Constant LINE_BREAK_CHAR. */
    private static final char LINE_BREAK_CHAR = LINE_BREAK.charAt(0);

    /** The locked lines. */
    private SortedIntegerList lockedLines = new SortedIntegerList();

    /** The doc. */
    private StyledDocument doc;

    /** The pane. */
    private JTextPane pane;

    /** The save before remove. */
    private SaveTextBeforeRemove saveBeforeRemove;

    /** The listeners. */
    private ArrayList<LockedLinesListener> listeners = new ArrayList<LockedLinesListener>();

    /**
     * Instantiates a new locked lines handler.
     *
     * @param textPane
     *            the text pane
     * @param saveBeforeRemoveListener
     *            the save before remove listener
     */
    public LockedLinesHandler(final JTextPane textPane,
                              final SaveTextBeforeRemove saveBeforeRemoveListener) {
        this.pane = textPane;
        this.doc = textPane.getStyledDocument();
        doc.addDocumentListener(this);
        this.saveBeforeRemove = saveBeforeRemoveListener;
    }

    /**
     * Lock line.
     *
     * @param line
     *            the line
     */
    public void lockLine(final int line) {
        lockedLines.add(line);
        for (LockedLinesListener l : listeners) {
            l.lockedLine(line);
        }
    }

    /**
     * Unlock line.
     *
     * @param line
     *            the line
     */
    public void unlockLine(final int line) {
        lockedLines.remove(line);
        for (LockedLinesListener l : listeners) {
            l.unlockedLine(line);
        }
    }

    /**
     * Checks if is line locked.
     *
     * @param line
     *            the line
     * @return true, if is line locked
     */
    public boolean isLineLocked(final int line) {
        return lockedLines.contains(line);
    }

    /**
     * Gets the first locked line.
     *
     * @return the first locked line
     */
    public int getFirstLockedLine() {
        return lockedLines.get(0);
    }

    @Override
    public void insertUpdate(final DocumentEvent de) {
        try {
            int amtNewline = 0;
            final String added = doc.getText(de.getOffset(), de.getLength());
            for (int i = 0; i < de.getLength(); ++i) {
                if (added.charAt(i) == LINE_BREAK_CHAR) {
                    amtNewline++;
                }
            }
            int firstLineAffected =
                    JTextPaneToolbox.transformToLineNumber(pane,
                                                           de.getOffset());
            if (lockedLines.contains(firstLineAffected)
                    && doc.getText(de.getOffset() + 1, 1).equals(LINE_BREAK)
                    && !doc.getText(de.getOffset() - 1, 1).equals(LINE_BREAK)) {
                ++firstLineAffected;
            }

            lockedLines.addIfBigger(firstLineAffected - 1, amtNewline,
                (prevNum, newNum) -> {
                    for (LockedLinesListener l : listeners) {
                        l.unlockedLine(prevNum);
                    }
                });

            for (int i = 0; i < lockedLines.size(); ++i) {
                for (LockedLinesListener l : listeners) {
                    l.lockedLine(lockedLines.get(i));
                }
            }
        } catch (BadLocationException ex) {
            Logger.getLogger(LockedLinesHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void removeUpdate(final DocumentEvent de) {
        final String removed =
                saveBeforeRemove.getRemoveString(de.getOffset(),
                                                 de.getLength());
        int amtNewline = 0;
        for (int i = 0; i < removed.length(); ++i) {
            if (removed.charAt(i) == '\n') {
                amtNewline++;
            }
        }
        final int firstLineAffected =
                JTextPaneToolbox.transformToLineNumber(
                        saveBeforeRemove.getPrevText(),
                        de.getOffset() + de.getLength()
                );
        lockedLines.subtractIfBigger(firstLineAffected - 1, amtNewline,
            (prevNum, newNum) -> {
                for (LockedLinesListener l : listeners) {
                    l.unlockedLine(prevNum);
                }
            });
        for (int i = 0; i < lockedLines.size(); ++i) {
            for (LockedLinesListener l : listeners) {
                l.lockedLine(lockedLines.get(i));
            }
        }
    }

    @Override
    public void changedUpdate(final DocumentEvent de) {
    }

    /**
     * Adds the locked lines listener.
     *
     * @param l
     *            the l
     */
    public void addLockedLinesListener(final LockedLinesListener l) {
        listeners.add(l);
    }

    /**
     * Unlock all.
     */
    public void unlockAll() {
        for (int i = 0; i < lockedLines.size(); ++i) {
            for (LockedLinesListener l : listeners) {
                l.unlockedLine(lockedLines.get(i));
            }
        }
        lockedLines.clear();
    }

    /**
     * Gets the locked lines.
     *
     * @return the locked lines
     */
    public List<Integer> getLockedLines() {
        return lockedLines.getCopiedList();
    }
}
