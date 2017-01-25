
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
    public void addCheckListener(CheckListener l);
    /**
     * Adds ActionListener for closing all windows.
     * @param l listener for closing
     */
    public void addCloseListener(ActionListener l);
    /**
     * Adds ActionListener for saving the whole project.
     * @param l listener for saving project
     */
    public void addSaveListener(ActionListener l);
}
