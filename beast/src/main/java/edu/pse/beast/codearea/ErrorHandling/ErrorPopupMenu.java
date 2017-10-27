/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.ErrorHandling;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * This class is a simple popupmenu which displays an error message
 * @author Holger-Desktop
 */
public class ErrorPopupMenu extends JPopupMenu implements KeyListener {
    private static final long serialVersionUID = 1L;
    private JMenuItem errorItem;
    private JTextPane pane;

    public ErrorPopupMenu(JTextPane pane){
        errorItem = new JMenuItem("");
        errorItem.setBackground(Color.white);
        add(errorItem);
        errorItem.addKeyListener(this);
        this.addKeyListener(this);
    }


    public JMenuItem getErrorItem() {
        return errorItem;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        ;
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        ;
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        ;
    }
}
