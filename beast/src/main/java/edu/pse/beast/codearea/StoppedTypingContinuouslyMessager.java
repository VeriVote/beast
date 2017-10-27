/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * This class messages all its listeners whenever the user stopped typing
 * continuously. This happens if either he pressed a directional key, typed a
 * newline, or the caret position changed by more than + 1. It also occurs if
 * the user deletes text
 *
 * @author Holger-Desktop
 */
public class StoppedTypingContinuouslyMessager implements KeyListener, CaretListener {

    private final JTextPane pane;
    private final ArrayList<StoppedTypingContinuouslyListener> listener
            = new ArrayList<>();
    private int currentCaretPos = 0;

    public StoppedTypingContinuouslyMessager(JTextPane pane) {
        this.pane = pane;
        pane.addKeyListener(this);
        pane.addCaretListener(this);
    }

    /**
     * adds the supplied listener so it will in future be notified if the user
     * stops typing continuously
     *
     * @param l the object to be notified
     */
    public void addListener(StoppedTypingContinuouslyListener l) {
        listener.add(l);
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        if (ke.getKeyCode() == KeyEvent.VK_ENTER
                || ke.getKeyCode() == KeyEvent.VK_RIGHT) {
            msgAllListener(pane.getCaretPosition() + 1);
        } else if (ke.getKeyCode() == KeyEvent.VK_DELETE) {
            msgAllListener(pane.getCaretPosition());
        }
    }

    @Override
    public void keyPressed(KeyEvent ke) {
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }

    @Override
    public void caretUpdate(CaretEvent ce) {
        if (ce.getDot() != currentCaretPos + 1
                && ce.getDot() != currentCaretPos) {
            msgAllListener(ce.getDot());
        }
        currentCaretPos = ce.getDot();
    }

    private void msgAllListener(int newCaretPos) {
        for (StoppedTypingContinuouslyListener l : listener) {
            l.StoppedTypingContinuously(newCaretPos);
        }
    }
}
