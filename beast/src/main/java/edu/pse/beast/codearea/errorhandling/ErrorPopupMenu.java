package edu.pse.beast.codearea.errorhandling;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextPane;

/**
 * This class is a simple popupmenu which displays an error message
 *
 * @author Holger-Desktop
 */
public class ErrorPopupMenu extends JPopupMenu implements KeyListener {
    private static final long serialVersionUID = 1L;
    private JMenuItem errorItem;
    private JTextPane pane;

    public ErrorPopupMenu(JTextPane pane) {
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
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
    }
}