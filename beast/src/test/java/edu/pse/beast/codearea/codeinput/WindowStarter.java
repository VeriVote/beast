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

    /**
     * Instantiates a new window starter.
     *
     * @param jFrame
     *            the jFrame
     */
    public WindowStarter(final JFrame jFrame) {
        this.frame = jFrame;
    }

    /**
     * Show the window starter.
     */
    public void show() {
        java.awt.EventQueue.invokeLater(this);
    }

    @Override
    public void run() {
        frame.setVisible(true);
    }
}
