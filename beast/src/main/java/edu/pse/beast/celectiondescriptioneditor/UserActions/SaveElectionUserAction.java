/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.celectiondescriptioneditor.UserActions;

import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
import edu.pse.beast.toolbox.UserAction;


/**
 *
 * @author Holger-Desktop
 */
public class SaveElectionUserAction extends UserAction {
    private CElectionDescriptionEditor cElectionDescriptionEditor;
    private SaveAsElectionUserAction saveAsElectionUserAction;

    public SaveElectionUserAction(CElectionDescriptionEditor cElectionDescriptionEditor,
                                  SaveAsElectionUserAction saveAsElectionUserAction) {
        super("save");
        this.cElectionDescriptionEditor = cElectionDescriptionEditor;
        this.saveAsElectionUserAction = saveAsElectionUserAction;
    }
    
    @Override
    public void perform() {
        if (cElectionDescriptionEditor.getSaveBeforeChangeHandler().hasBeenSaved()) {
            cElectionDescriptionEditor.getSaveBeforeChangeHandler().updatePreValue();
            cElectionDescriptionEditor.getElectionDescription();
            // TODO implement saving file
        } else {
            saveAsElectionUserAction.perform();
        }
    }
    
}
