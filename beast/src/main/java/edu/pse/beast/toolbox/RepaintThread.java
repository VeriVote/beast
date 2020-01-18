package edu.pse.beast.toolbox;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;

/**
 * The Class RepaintThread.
 *
 * @author Holger Klein
 */
public final class RepaintThread implements Runnable {
    /** The Constant SIXTY. */
    private static final int SIXTY = 60;

    /** The Constant THOUSAND. */
    private static final int THOUSAND = 1000;

    /** The frame. */
    private final JFrame frame;

    /** The run. */
    private volatile boolean run = true;

    /**
     * The constructor.
     *
     * @param jFrame
     *            the j frame
     */
    public RepaintThread(final JFrame jFrame) {
        this.frame = jFrame;
    }

    /**
     * Stop.
     */
    public void stop() {
        run = false;
    }

    @Override
    public void run() {
        while (run) {
            try {
                Thread.sleep(THOUSAND / SIXTY);
            } catch (InterruptedException ex) {
                Logger.getLogger(RepaintThread.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
            frame.repaint();
        }
    }
}
