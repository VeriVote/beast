package edu.pse.beast.parametereditor.UserActions;

import edu.pse.beast.parametereditor.ParameterEditor;
import edu.pse.beast.toolbox.UserAction;

import javax.swing.*;

/**
 * UserAction to toggle visibility of the PropertyList window
 * @author NikolaiLMS
 */
public class ShowHidePropertyList extends UserAction {
    private final JFrame propertyListWindow;
    private final ParameterEditor editor;
    /**
     * Constructor
     * @param editor ParameterEditor which this UserAction belongs to
     * @param propertyListWindow PropertyList window
     */
    public ShowHidePropertyList(ParameterEditor editor, JFrame propertyListWindow) {
        super("showPropertyList");
        this.editor = editor;
        this.propertyListWindow = propertyListWindow;
    }

    @Override
    public void perform() {
        if (editor.getReacts()) {
            if (propertyListWindow.isVisible()) {
                propertyListWindow.setVisible(false);
            } else {
                propertyListWindow.setVisible(true);
            }
        }
    }
}
