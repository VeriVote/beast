
package edu.pse.beast.highlevel;
import java.awt.event.ActionListener;

/**
 * The MainNotifier notifies the BEASTCommunicator when the user wants
 to start or stop a check.
 * @author Jonas
 */
public interface MainNotifier {
    /**
     * Adds a CheckListener.
     * @param l CheckListener
     */
    void addCheckListener(CheckListener l);
    /**
     * Adds ActionListener for closing all windows.
     * @param l listener for closing
     */
    void addCloseAllListener(ActionListener l);
}
