
package edu.pse.beast.highlevel;

/**
 * The CheckStartStopNotifier notifies the BEASTCommunicator when the user wants
 * to start or stop a check.
 * @author Jonas
 */
public interface CheckStartStopNotifier {
    /**
     * Adds a CheckListener.
     * @param l CheckListener
     */
    public void addCheckListener(CheckListener l);
}
