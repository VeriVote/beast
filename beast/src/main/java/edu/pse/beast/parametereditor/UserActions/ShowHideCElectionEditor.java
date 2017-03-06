package edu.pse.beast.parametereditor.UserActions;

import edu.pse.beast.toolbox.UserAction;

import javax.swing.*;

/**
 * UserAction to toggle visibility of the CElectionEditor window
 * @author NikolaiLMS
 */
public class ShowHideCElectionEditor extends UserAction {
    private final JFrame cElectionEditorWindow;
    /**
     * Constructor
     * @param cElectionEditorWindow CElectionEditor window
     */
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
