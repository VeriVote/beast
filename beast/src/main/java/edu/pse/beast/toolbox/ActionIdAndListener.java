package edu.pse.beast.toolbox;

import java.awt.event.ActionListener;

/**
 * The listener interface for receiving actionIdAnd events. The class that is
 * interested in processing a actionIdAnd event implements this interface, and
 * the object created with that class is registered with a component using the
 * component's <code>addActionIdAndListener</code> method. When the actionIdAnd
 * event occurs, that object's appropriate method is invoked.
 *
 * @author Holger Klein, Lukas Stapelbroek
 */
public class ActionIdAndListener {

    /** The id. */
    private final String id;

    /** The listener. */
    private final ActionListener listener;

    /**
     * Instantiates a new action id and listener.
     *
     * @param idString
     *            the id
     * @param actionListener
     *            the listener to be save here
     */
    public ActionIdAndListener(final String idString,
                               final ActionListener actionListener) {
        this.listener = actionListener;
        this.id = idString;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public String getId() {
        return this.id;
    }

    /**
     * Gets the listener.
     *
     * @return the listener that got saved here
     */
    public ActionListener getListener() {
        return this.listener;
    }

    @Override
    public String toString() {
        return "ActionIdAndListener{" + "id='" + id + '\'' + '}';
    }
}
