package edu.pse.beast.codearea.actionadder;

/**
 * The listener interface for receiving actionlist events. The class that is
 * interested in processing a actionlist event implements this interface, and
 * the object created with that class is registered with a component using the
 * component's <code>addActionlistListener</code> method. When the actionlist
 * event occurs, that object's appropriate method is invoked.
 *
 * @author Holger Klein
 */
public interface ActionlistListener {

    /**
     * Undoing action.
     */
    void undoingAction();

    /**
     * Finished undoing action.
     */
    void finishedUndoingAction();
}
