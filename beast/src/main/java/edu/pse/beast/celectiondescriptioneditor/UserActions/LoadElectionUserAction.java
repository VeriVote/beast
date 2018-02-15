/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.celectiondescriptioneditor.UserActions;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.text.BadLocationException;

import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.toolbox.UserAction;

/**
 *
 * @author Holger-Desktop
 */
public class LoadElectionUserAction extends UserAction {

    private final CElectionDescriptionEditor cElectionDescriptionEditor;

    public LoadElectionUserAction(CElectionDescriptionEditor cElectionDescriptionEditor) {
        super("load");
        this.cElectionDescriptionEditor = cElectionDescriptionEditor;
    }
    
    @Override
    public void perform() {
        if (cElectionDescriptionEditor.getChangeHandler().hasChanged()) {
            if (!cElectionDescriptionEditor.getFileChooser().openSaveChangesDialog(cElectionDescriptionEditor.getElectionDescription())) {
                return;
            }
        }
        ElectionDescription loadedElectionDescription =
                (ElectionDescription) cElectionDescriptionEditor.getFileChooser().loadObject();
        if (loadedElectionDescription != null) {
            try {
                cElectionDescriptionEditor.loadElectionDescription(loadedElectionDescription);
                cElectionDescriptionEditor.getFileChooser().setHasBeenSaved(true);
            } catch (BadLocationException e) {
                Logger.getLogger(NewElectionUserAction.class.getName()).log(Level.SEVERE, null, e);
            }
        }

    }
    
}
