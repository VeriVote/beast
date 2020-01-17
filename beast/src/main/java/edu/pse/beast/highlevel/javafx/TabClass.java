package edu.pse.beast.highlevel.javafx;

import javafx.scene.control.Tab;

/**
 * The Class TabClass.
 */
public abstract class TabClass {

    /** The has focus. */
    private boolean hasFocus = false;

    /** The associated tab. */
    private final Tab associatedTab;

    /**
     * Instantiates a new tab class.
     *
     * @param assocTab
     *            the assoc tab
     */
    public TabClass(final Tab assocTab) {
        this.associatedTab = assocTab;
    }

    /**
     * Gain focus.
     */
    public void gainFocus() {
        this.hasFocus = true;
    }

    /**
     * Lose focus.
     */
    public void loseFocus() {
        this.hasFocus = false;
    }

    /**
     * Checks for focus.
     *
     * @return true, if successful
     */
    public boolean hasFocus() {
        return hasFocus;
    }

    /**
     * Gets the tab.
     *
     * @return the tab
     */
    public Tab getTab() {
        return associatedTab;
    }
}
