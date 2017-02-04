package edu.pse.beast.booleanexpeditor.UserActions;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.saverloader.SaverLoaderInterface;
import edu.pse.beast.toolbox.UserAction;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * @author NikolaiLMS
 */
public class LoadPropsUserAction extends UserAction {
    private BooleanExpEditor booleanExpEditor;
    private SaverLoaderInterface saverLoaderInterface;
    private SaveBeforeChangeHandler saveBeforeChangeHandler;

    public LoadPropsUserAction(BooleanExpEditor booleanExpEditor, SaverLoaderInterface saverLoaderInterface,
                               SaveBeforeChangeHandler saveBeforeChangeHandler) {
        super("load");
        this.booleanExpEditor = booleanExpEditor;
        this.saverLoaderInterface = saverLoaderInterface;
        this.saveBeforeChangeHandler = saveBeforeChangeHandler;
    }

    @Override
    public void perform() {
        if (saveBeforeChangeHandler.ifHasChangedOpenSaveDialog(booleanExpEditor.getCurrentlyLoadedPostAndPreProp().getName())) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    if (file.getName().matches("(.)+(\\.)props") || file.isDirectory()) {
                        return true;
                    } else {
                        return false;
                    }
                }

                @Override
                public String getDescription() {
                    return "BEASTFormalProperty (*.props)";
                }
            });
            if (fileChooser.showOpenDialog(booleanExpEditor.getWindow()) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                //TODO implement OmniSaverLoader call and booleanExpEditor.loadPostAndPreProperties

                saveBeforeChangeHandler.setHasBeensaved(true);
            }
        }
    }
}
