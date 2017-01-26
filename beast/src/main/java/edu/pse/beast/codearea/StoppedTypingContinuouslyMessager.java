/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JTextPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

/**
 *
 * @author Holger-Desktop
 */
public class StoppedTypingContinuouslyMessager implements KeyListener, CaretListener {
    private JTextPane pane;
    private ArrayList<StoppedTypingContinuouslyListener> listener = 
            new ArrayList();
    private int currentCaretPos = 0;
    
    public StoppedTypingContinuouslyMessager(JTextPane pane) {
        this.pane = pane;
        pane.addKeyListener(this);
        pane.addCaretListener(this);
    }
    
    public void addListener(StoppedTypingContinuouslyListener l) {
        listener.add(l);
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        if( ke.getKeyCode() == ke.VK_ENTER ||
            ke.getKeyCode() == ke.VK_DELETE ||
            ke.getKeyCode() == ke.VK_RIGHT) {
            msgAllListener();
        }
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        if( ke.getKeyCode() == ke.VK_ENTER ||
            ke.getKeyCode() == ke.VK_DELETE ||
            ke.getKeyCode() == ke.VK_RIGHT) {
            msgAllListener();
        }
    }

    @Override
    public void caretUpdate(CaretEvent ce) {
        if(ce.getDot() != currentCaretPos + 1 &&
                ce.getDot() != currentCaretPos) {
            msgAllListener();
        }
        currentCaretPos = ce.getDot();
    }

    private void msgAllListener() {
        System.out.println("msgAllListener()");
        for(StoppedTypingContinuouslyListener l : listener)
            l.StoppedTypingContinuously();
    }
}
