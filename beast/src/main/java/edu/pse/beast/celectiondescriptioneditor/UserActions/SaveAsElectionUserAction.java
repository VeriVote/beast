/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.celectiondescriptioneditor.UserActions;

import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
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
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File file) {
                if (file.getName().matches("(.)+(\\.)elec") || file.isDirectory()) {
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public String getDescription() {
                return "BEASTElectionDescription (*.elec)";
            }
        });
        if (fileChooser.showSaveDialog(electionDescriptionEditor.getGui()) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            // TODOsave to file
            electionDescriptionEditor.getSaveBeforeChangeHandler().setHasBeensaved(true);
        }
    }

}
