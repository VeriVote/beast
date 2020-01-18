package edu.pse.beast.codearea.codeinput;

import javax.swing.JFrame;

/**
 * The tests for WindowStarter.
 *
 * @author Holger Klein
 */
public final class WindowStarter implements Runnable {
    /** A frame instance. */
    private JFrame frame;

    public WindowStarter(final JFrame jFrame) {
        this.frame = jFrame;
    }

    public void show() {
        java.awt.EventQueue.invokeLater(this);
    }

    @Override
    public void run() {
        frame.setVisible(true);
    }
}
