/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.toolbox;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author Holger-Desktop
 */
public class RepaintThread implements Runnable {
    private final JFrame frame;
    public RepaintThread(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(1000/60);
            } catch (InterruptedException ex) {
                Logger.getLogger(RepaintThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            frame.repaint();
        }
    }
}
