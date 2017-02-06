package edu.pse.beast.booleanexpeditor.UserActions;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.saverloader.OmniSaverLoader;
import edu.pse.beast.saverloader.PostAndPrePropertiesDescriptionSaverLoader;
import edu.pse.beast.saverloader.ProjectSaverLoader;
import edu.pse.beast.toolbox.UserAction;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * @author NikolaiLMS
 */
public class SaveAsPropsUserAction extends UserAction {
    private BooleanExpEditor booleanExpEditor;
    public SaveAsPropsUserAction(BooleanExpEditor booleanExpEditor) {
        super("save_as");
        this.booleanExpEditor = booleanExpEditor;
    }

    @Override
    public void perform() {
        booleanExpEditor.updatePostAndPrePropObject();
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
                return "FormalProperty (*.props)";
            }
        });
        if (fileChooser.showSaveDialog(booleanExpEditor.getWindow()) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            PostAndPrePropertiesDescription currentlyLoaded = booleanExpEditor.getCurrentlyLoadedPostAndPreProp();
            String content = null;
            try {
                content = PostAndPrePropertiesDescriptionSaverLoader.createSaveString(currentlyLoaded);
            } catch (Exception e) {
                System.out.println("Invalid file format.");
            }
            try {
                FileWriter fw = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(content);
                try {
                        bw.close();
                        fw.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } catch (IOException e) {
                // TODO OPEN ERROR DIALOG
            }

            booleanExpEditor.getSaveBeforeChangeHandler().setHasBeensaved(true);
            booleanExpEditor.getSaveBeforeChangeHandler().updatePreValues();
        }
    }
}
