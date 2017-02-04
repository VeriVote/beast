package edu.pse.beast.parametereditor.UserActions;

import edu.pse.beast.toolbox.UserAction;

import javax.swing.*;

/**
 * @author NikolaiLMS
 */
public class ShowHideBooleanExpEditor extends UserAction{
    private JFrame booleanExpEditorWindow;

    public ShowHideBooleanExpEditor(JFrame booleanExpEditorWindow) {
        super("showBooleanExpEditor");
        this.booleanExpEditorWindow = booleanExpEditorWindow;
    }

    @Override
    public void perform() {
        if (booleanExpEditorWindow.isVisible()) {
            booleanExpEditorWindow.setVisible(false);
        } else {
            booleanExpEditorWindow.setVisible(true);
        }
    }
}
