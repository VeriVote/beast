package edu.pse.beast.parametereditor.UserActions;

import edu.pse.beast.toolbox.UserAction;

import javax.swing.*;

/**
 * @author NikolaiLMS
 */
public class ShowHideCElectionEditor extends UserAction{
    private JFrame cElectionEditorWindow;

    public ShowHideCElectionEditor(JFrame cElectionEditorWindow) {
        super("showCElectionEditor");
        this.cElectionEditorWindow = cElectionEditorWindow;
    }


    @Override
    public void perform() {
        if (cElectionEditorWindow.isVisible()) {
            cElectionEditorWindow.setVisible(false);
        } else {
            cElectionEditorWindow.setVisible(true);
        }
    }
}
