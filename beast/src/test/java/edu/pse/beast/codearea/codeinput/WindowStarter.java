package edu.pse.beast.codearea.codeinput;

import javax.swing.JFrame;

/**
 *
 * @author Holger-Desktop
 */
public class WindowStarter implements Runnable {
    JFrame frame;

    public WindowStarter(JFrame frame){
        this.frame = frame;
    }

    public void show() {
        java.awt.EventQueue.invokeLater(this);
    }

    @Override
    public void run() {
        frame.setVisible(true);
    }
}