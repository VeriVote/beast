package edu.pse.beast.toolbox;

import java.awt.event.ActionListener;

/**
 *
 * @author Holger Klein, Lukas Stapelbroek
 */
public class ActionIdAndListener {
    private final String id;
    private final ActionListener listener;

    /**
     *
     * @param idString       the id
     * @param actionListener the listener to be save here
     */
    public ActionIdAndListener(final String idString,
                               final ActionListener actionListener) {
        this.listener = actionListener;
        this.id = idString;
    }

    /**
     *
     * @return the id
     */
    public String getId() {
        return this.id;
    }

    /**
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
