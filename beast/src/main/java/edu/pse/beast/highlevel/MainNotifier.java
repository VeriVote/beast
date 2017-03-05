
package edu.pse.beast.highlevel;

/**
 * The MainNotifier notifies the BEASTCommunicator when the user wants
 * to start or stop a check.
 * @author Jonas
 */
public interface MainNotifier {
    /**
     * Adds a CheckListener that is notified when the user wants to start or
     * stop a check.
     * @param l CheckListener
     */
    void addCheckListener(CheckListener l);
}
