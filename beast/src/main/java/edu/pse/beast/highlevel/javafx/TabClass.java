package edu.pse.beast.highlevel.javafx;

import javafx.scene.control.Tab;

public abstract class TabClass {

    private boolean hasFocus = false;

    private final Tab associatedTab;

    public TabClass(Tab associatedTab) {
        this.associatedTab = associatedTab;
    }

    public void gainFocus() {
        this.hasFocus = true;
    }

    public void loseFocus() {
        this.hasFocus = false;
    }

    public boolean hasFocus() {
        return hasFocus;
    }

    public Tab getTab() {
        return associatedTab;
    }
}
