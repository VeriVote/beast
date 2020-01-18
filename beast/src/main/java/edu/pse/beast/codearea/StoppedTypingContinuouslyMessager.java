package edu.pse.beast.codearea;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JTextPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

/**
 * This class messages all its listeners whenever the user stopped typing
 * continuously. This happens if either he pressed a directional key, typed a
 * newline, or the caret position changed by more than + 1. It also occurs if
 * the user deletes text.
 *
 * @author Holger Klein
 */
public final class StoppedTypingContinuouslyMessager
        implements KeyListener, CaretListener {
    /** The pane. */
    private final JTextPane pane;

    /** The listener. */
    private final ArrayList<StoppedTypingContinuouslyListener> listener =
            new ArrayList<StoppedTypingContinuouslyListener>();

    /** The current caret pos. */
    private int currentCaretPos = 0;

    /**
     * Instantiates a new stopped typing continuously messager.
     *
     * @param textPane
     *            the text pane
     */
    public StoppedTypingContinuouslyMessager(final JTextPane textPane) {
        this.pane = textPane;
        textPane.addKeyListener(this);
        textPane.addCaretListener(this);
    }

    /**
     * Adds the supplied listener so it will in future be notified if the user
     * stops typing continuously.
     *
     * @param l
     *            the object to be notified
     */
    public void addListener(final StoppedTypingContinuouslyListener l) {
        listener.add(l);
    }

    @Override
    public void keyTyped(final KeyEvent ke) {
        if (ke.getKeyCode() == KeyEvent.VK_ENTER
                || ke.getKeyCode() == KeyEvent.VK_RIGHT) {
            msgAllListener(pane.getCaretPosition() + 1);
        } else if (ke.getKeyCode() == KeyEvent.VK_DELETE) {
            msgAllListener(pane.getCaretPosition());
        }
    }

    @Override
    public void keyPressed(final KeyEvent ke) {
    }

    @Override
    public void keyReleased(final KeyEvent ke) {
    }

    @Override
    public void caretUpdate(final CaretEvent ce) {
        if (ce.getDot() != currentCaretPos + 1
                && ce.getDot() != currentCaretPos) {
            msgAllListener(ce.getDot());
        }
        currentCaretPos = ce.getDot();
    }

    /**
     * Msg all listener.
     *
     * @param newCaretPos
     *            the new caret pos
     */
    private void msgAllListener(final int newCaretPos) {
        for (StoppedTypingContinuouslyListener l : listener) {
            l.stoppedTypingContinuously(newCaretPos);
        }
    }
}
