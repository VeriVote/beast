package edu.pse.beast.toolbox;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;

/**
 *
 * @author Holger Klein
 */
public class RepaintThread implements Runnable {
    private final JFrame frame;
    private volatile boolean run = true;

    public RepaintThread(JFrame frame) {
        this.frame = frame;
    }

    public void stop() {
        run = false;
    }

    @Override
    public void run() {
        while (run) {
            try {
                Thread.sleep(1000 / 60);
            } catch (InterruptedException ex) {
                Logger.getLogger(RepaintThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            frame.repaint();
        }
    }
}
