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

    public SaveElectionUserAction(CElectionDescriptionEditor cElectionDescriptionEditor) {
        super("save");
        this.cElectionDescriptionEditor = cElectionDescriptionEditor;
    }
    
    @Override
    public void perform() {
        if (cElectionDescriptionEditor.getFileChooser().saveObject(cElectionDescriptionEditor.getElectionDescription(), false)) {
            cElectionDescriptionEditor.getChangeHandler().updatePreValue();
            cElectionDescriptionEditor.getView().setWindowTitle(cElectionDescriptionEditor.getElectionDescription().getName());
        }
    }
    
}
