package edu.pse.beast.toolbox;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;

/**
 *
 * @author Holger Klein
 */
public class RepaintThread implements Runnable {
    private static final int SIXTY = 60;
    private static final int THOUSAND = 1000;

    private final JFrame frame;
    private volatile boolean run = true;

    public RepaintThread(final JFrame jFrame) {
        this.frame = jFrame;
    }

    public void stop() {
        run = false;
    }

    @Override
    public void run() {
        while (run) {
            try {
                Thread.sleep(THOUSAND / SIXTY);
            } catch (InterruptedException ex) {
                Logger.getLogger(RepaintThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            frame.repaint();
        }
    }
}
