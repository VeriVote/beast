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
import edu.pse.beast.highlevel.DisplaysStringsToUser;
import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.stringresource.StringResourceLoader;
import edu.pse.beast.toolbox.CCodeHelper;
import edu.pse.beast.toolbox.UserAction;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.text.BadLocationException;
/**
 *
 * @author Holger-Desktop
 */
public class NewElectionUserAction extends UserAction implements DisplaysStringsToUser {

    private SaveBeforeChangeHandler saveBeforeChangeHandler;
    private CElectionDescriptionEditor editor;
    private ElectionTemplateHandler templateHandler = new ElectionTemplateHandler();
    private ElectionTemplateChooser electionTemplateDialog;
    private StringResourceLoader currentLoader;
    private CCodeHelper cCodeHelper = new CCodeHelper();
    
    public NewElectionUserAction(
            CElectionDescriptionEditor editor,
            StringLoaderInterface stringResIF) {
        super("new");
        this.saveBeforeChangeHandler = editor.getSaveBeforeChangeHandler();
        this.editor = editor;
        this.currentLoader = stringResIF.getCElectionEditorStringResProvider().getElectionStringRes();
    }
    
    @Override
    public void perform() {
        if (saveBeforeChangeHandler.hasChanged()) {
            int option = editor.getGui().showOptionPane(editor.getElectionDescription().getName());
            if (option == JOptionPane.YES_OPTION) {
                throw new UnsupportedOperationException("Not supported yet.");
            } else if (option == JOptionPane.CANCEL_OPTION) {
                return;
            }
        }
        electionTemplateDialog = new ElectionTemplateChooser(
                this,
                templateHandler,
                currentLoader, currentLoader.getStringFromID("emptyNameTextFieldError"));
        electionTemplateDialog.setDefaultCloseOperation(ElectionTemplateChooser.DISPOSE_ON_CLOSE);
        electionTemplateDialog.setVisible(true);
    }

    public void create(String input, String res, String name) {
        try {
            ElectionDescription description = cCodeHelper.generateElectionDescription(
                    input, res, name, 
                    templateHandler, currentLoader);
            editor.letUserEditElectionDescription(description);
        } catch (BadLocationException ex) {
            Logger.getLogger(NewElectionUserAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void updateStringRes(StringLoaderInterface stringResIF) {
    }
}
