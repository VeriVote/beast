/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.celectiondescriptioneditor.UserActions;

import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
import edu.pse.beast.datatypes.descofvoting.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.saverloader.ElectionDescriptionSaverLoader;
import edu.pse.beast.saverloader.SaverLoader;
import edu.pse.beast.toolbox.FileChooser;
import edu.pse.beast.toolbox.UserAction;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 *
 * @author Holger-Desktop
 */
public class SaveAsElectionUserAction extends UserAction {
    private CElectionDescriptionEditor electionDescriptionEditor;

    public SaveAsElectionUserAction(CElectionDescriptionEditor electionDescriptionEditor) {
        super("save_as");
        this.electionDescriptionEditor = electionDescriptionEditor;
    }

    @Override
    public void perform() {
        ElectionDescription currentlyLoaded = electionDescriptionEditor.getElectionDescription();
        if (electionDescriptionEditor.getFileChooser().saveObject(currentlyLoaded, true)) {
            electionDescriptionEditor.getSaveBeforeChangeHandler().updatePreValue();
        }
    }

}
