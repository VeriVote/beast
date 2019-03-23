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
     * @param id       the id
     * @param listener the listener to be save here
     */
    public ActionIdAndListener(String id, ActionListener listener) {
        this.listener = listener;
        this.id = id;
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