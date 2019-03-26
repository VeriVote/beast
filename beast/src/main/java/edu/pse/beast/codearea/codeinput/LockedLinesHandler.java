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
public class LockedLinesHandler implements DocumentListener {
    private SortedIntegerList lockedLines = new SortedIntegerList();
    private StyledDocument doc;
    private JTextPane pane;
    private SaveTextBeforeRemove saveBeforeRemove;
    private ArrayList<LockedLinesListener> listeners = new ArrayList<>();

    public LockedLinesHandler(JTextPane pane, SaveTextBeforeRemove saveBeforeRemove) {
        this.pane = pane;
        this.doc = pane.getStyledDocument();
        doc.addDocumentListener(this);
        this.saveBeforeRemove = saveBeforeRemove;
    }

    public void lockLine(int line) {
        lockedLines.add(line);
        for (LockedLinesListener l : listeners) {
            l.lockedLine(line);
        }
    }

    public void unlockLine(int line) {
        lockedLines.remove(line);
        for (LockedLinesListener l : listeners) {
            l.unlockedLine(line);
        }
    }

    public boolean isLineLocked(int line) {
        return lockedLines.contains(line);
    }

    public int getFirstLockedLine() {
        return lockedLines.get(0);
    }

    @Override
    public void insertUpdate(DocumentEvent de) {
        try {
            int amtNewline = 0;
            String added = doc.getText(de.getOffset(), de.getLength());
            for (int i = 0; i < de.getLength(); ++i) {
                if (added.charAt(i) == '\n') {
                    amtNewline++;
                }
            }
            int firstLineAffected = JTextPaneToolbox.transformToLineNumber(pane, de.getOffset());
            if (lockedLines.contains(firstLineAffected)
                    && doc.getText(de.getOffset() + 1, 1).equals("\n")
                    && !doc.getText(de.getOffset() - 1, 1).equals("\n")) {
                ++firstLineAffected;
            }

            lockedLines.addIfBigger(firstLineAffected - 1, amtNewline, (prevNum, newNum) -> {
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
            Logger.getLogger(LockedLinesHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void removeUpdate(DocumentEvent de) {

        String removed = saveBeforeRemove.getRemoveString(de.getOffset(), de.getLength());

        int amtNewline = 0;
        for (int i = 0; i < removed.length(); ++i) {
            if (removed.charAt(i) == '\n') {
                amtNewline++;
            }
        }
        int firstLineAffected =
            JTextPaneToolbox.transformToLineNumber(saveBeforeRemove.getPrevText(),
                                                   de.getOffset() + de.getLength());

        lockedLines.subtractIfBigger(firstLineAffected - 1, amtNewline, (prevNum, newNum) -> {
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
    public void changedUpdate(DocumentEvent de) {
    }

    public void addLockedLinesListener(LockedLinesListener l) {
        listeners.add(l);
    }

    public void unlockAll() {
        for (int i = 0; i < lockedLines.size(); ++i) {
            for (LockedLinesListener l : listeners) {
                l.unlockedLine(lockedLines.get(i));
            }
        }
        lockedLines.clear();
    }

    public List<Integer> getLockedLines() {
        return lockedLines.getCopiedList();
    }
}