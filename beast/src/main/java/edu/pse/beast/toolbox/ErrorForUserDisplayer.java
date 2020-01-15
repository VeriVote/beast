package edu.pse.beast.toolbox;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public final class ErrorForUserDisplayer {
    private static final int THOUSAND = 1000;

    private static int currentlyDisplayedErrors = 0;

    private ErrorForUserDisplayer() {
    }

    /**
     * displays the message on the users screen. if multiple messages are displayed
     * the method will only return when all messages are read.
     *
     * @param message the message to be displayed
     */
    public static void displayError(final String message) {
        increment();
        JOptionPane.showMessageDialog(new JFrame(), message,
                                      "Error", JOptionPane.ERROR_MESSAGE);
        decrement();
        while (true) {
            if (currentlyDisplayedErrors == 0) {
                return;
            }
            try {
                Thread.sleep(THOUSAND);
            } catch (InterruptedException e) {

            }
        }
    }

    private static synchronized void increment() {
        currentlyDisplayedErrors++;
    }

    private static synchronized void decrement() {
        currentlyDisplayedErrors--;
    }
}
