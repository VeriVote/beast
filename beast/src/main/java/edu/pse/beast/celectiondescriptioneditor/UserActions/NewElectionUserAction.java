/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.celectiondescriptioneditor.UserActions;

import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
import edu.pse.beast.celectiondescriptioneditor.ElectionTemplates.ElectionTemplateChooser;
import edu.pse.beast.celectiondescriptioneditor.ElectionTemplates.ElectionTemplateHandler;
import edu.pse.beast.datatypes.descofvoting.ElectionDescription;
import edu.pse.beast.datatypes.descofvoting.ElectionTypeContainer;
import edu.pse.beast.toolbox.UserAction;
/**
 *
 * @author Holger-Desktop
 */
public class NewElectionUserAction extends UserAction {

    private SaveBeforeChangeHandler saveBeforeChangeHandler;
    private CElectionDescriptionEditor editor;
    private ElectionTemplateHandler templateHandler = new ElectionTemplateHandler();
    private ElectionTemplateChooser electionTemplateDialog;
    
    
    public NewElectionUserAction(
            SaveBeforeChangeHandler saveBeforeChangeHandler,
            CElectionDescriptionEditor editor) {
        super("new");
        this.saveBeforeChangeHandler = saveBeforeChangeHandler;
        this.editor = editor;
    }
    
    @Override
    public void perform() {
        electionTemplateDialog = new ElectionTemplateChooser(this, templateHandler);
        electionTemplateDialog.setVisible(true);
    }

    public void create(String input, String res, String name) {
        ElectionDescription description = new ElectionDescription(
                name,
                templateHandler.getById(input),
                templateHandler.getById(res),
                2);
        editor.letUserEditElectionDescription(description);
    }
    
}
