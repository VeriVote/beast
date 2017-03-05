/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.celectiondescriptioneditor.UserActions;

import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
import edu.pse.beast.celectiondescriptioneditor.ElectionTemplates.ElectionTemplateChooser;
import edu.pse.beast.celectiondescriptioneditor.ElectionTemplates.ElectionTemplateHandler;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.toolbox.CCodeHelper;
import edu.pse.beast.toolbox.UserAction;

import javax.swing.text.BadLocationException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Holger-Desktop
 */
public class NewElectionUserAction extends UserAction {

    private final CElectionDescriptionEditor editor;
    private final ElectionTemplateHandler templateHandler = new ElectionTemplateHandler();
    private final CCodeHelper cCodeHelper = new CCodeHelper();
    
    public NewElectionUserAction(
            CElectionDescriptionEditor editor) {
        super("new");
        this.editor = editor;
    }
    
    @Override
    public void perform() {
        ElectionTemplateChooser electionTemplateDialog = new ElectionTemplateChooser(
                this,
                templateHandler,
                editor.getStringInterface().getCElectionEditorStringResProvider().getElectionStringRes(),
                editor.getStringInterface().getCElectionEditorStringResProvider().getElectionStringRes().getStringFromID("emptyNameTextFieldError"));
        electionTemplateDialog.setDefaultCloseOperation(ElectionTemplateChooser.DISPOSE_ON_CLOSE);
        electionTemplateDialog.setVisible(true);
    }

    public void create(String input, String res, String name) {
        try {
            ElectionDescription description = cCodeHelper.generateElectionDescription(
                    input, res, name, 
                    templateHandler, editor.getStringInterface().getCElectionEditorStringResProvider().getElectionStringRes());
            editor.letUserEditElectionDescription(description);
            editor.getFileChooser().setHasBeenSaved(false);
        } catch (BadLocationException ex) {
            Logger.getLogger(NewElectionUserAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
