package edu.pse.beast.parametereditor.UserActions;

import edu.pse.beast.toolbox.UserAction;

import javax.swing.*;

/**
 * UserAction to toggle visibility of the PropertyList window
 * @author NikolaiLMS
 */
public class ShowHidePropertyList extends UserAction {
    private final JFrame propertyListWindow;
    /**
     * Constructor
     * @param propertyListWindow PropertyList window
     */
    public ShowHidePropertyList(JFrame propertyListWindow) {
        super("showPropertyList");
        this.propertyListWindow = propertyListWindow;
    }

    @Override
    public void perform() {
        if (propertyListWindow.isVisible()) {
            propertyListWindow.setVisible(false);
        } else {
            propertyListWindow.setVisible(true);
        }
    }
}
