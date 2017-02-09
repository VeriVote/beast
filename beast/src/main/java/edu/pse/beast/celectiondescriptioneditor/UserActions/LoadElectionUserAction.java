/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.celectiondescriptioneditor.UserActions;

import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.saverloader.SaverLoaderInterface;
import edu.pse.beast.toolbox.UserAction;

import javax.swing.text.BadLocationException;

/**
 *
 * @author Holger-Desktop
 */
public class LoadElectionUserAction extends UserAction {

    private CElectionDescriptionEditor cElectionDescriptionEditor;

    public LoadElectionUserAction(CElectionDescriptionEditor cElectionDescriptionEditor) {
        super("load");
        this.cElectionDescriptionEditor = cElectionDescriptionEditor;
    }
    
    @Override
    public void perform() {
        if (cElectionDescriptionEditor.getSaveBeforeChangeHandler().ifHasChangedOpenDialog(
                cElectionDescriptionEditor.getElectionDescription().getName())) {
            ElectionDescription loadedElectionDescription =
                    (ElectionDescription) cElectionDescriptionEditor.getFileChooser().loadObject();
            if (loadedElectionDescription != null) {
                try {
                    cElectionDescriptionEditor.loadElectionDescription(loadedElectionDescription);
                    cElectionDescriptionEditor.getFileChooser().setHasBeenSaved(true);
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
}
