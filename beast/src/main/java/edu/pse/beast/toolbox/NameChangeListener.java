package edu.pse.beast.toolbox;

/**
 * The listener interface for receiving nameChange events. The class that is
 * interested in processing a nameChange event implements this interface, and
 * the object created with that class is registered with a component using the
 * component's <code>addNameChangeListener</code> method. When the nameChange
 * event occurs, that object's appropriate method is invoked.
 *
 * @see NameChangeEvent
 */
public interface NameChangeListener {

    /**
     * Notify on name change.
     */
    void notifyNameChange();
}
