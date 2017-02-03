/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.celectiondescriptioneditor.UserActions;

import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
import edu.pse.beast.saverloader.SaverLoaderInterface;
import edu.pse.beast.toolbox.UserAction;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 *
 * @author Holger-Desktop
 */
public class LoadElectionUserAction extends UserAction {

    private CElectionDescriptionEditor cElectionDescriptionEditor;
    private SaverLoaderInterface saverLoaderInterface;

    public LoadElectionUserAction(CElectionDescriptionEditor cElectionDescriptionEditor, SaverLoaderInterface saverLoaderInterface) {
        super("load");
        this.cElectionDescriptionEditor = cElectionDescriptionEditor;
        this.saverLoaderInterface = saverLoaderInterface;
    }
    
    @Override
    public void perform() {
        if (cElectionDescriptionEditor.getSaveBeforeChangeHandler().ifHasChangedOpenDialog(
                cElectionDescriptionEditor.getElectionDescription().getName())) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    if (file.getName().matches("(.)+(\\.)c") || file.isDirectory()) {
                        return true;
                    } else {
                        return false;
                    }
                }

                @Override
                public String getDescription() {
                    return "ElectionDescription (*.c)";
                }
            });
            if (fileChooser.showOpenDialog(cElectionDescriptionEditor.getGui()) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                //TODO implement OmniSaverLoader call and cElectionDescriptionEditor.loadPostAndPreProperties
                cElectionDescriptionEditor.getSaveBeforeChangeHandler().setHasBeensaved(true);
            }
        }
    }
    
}
