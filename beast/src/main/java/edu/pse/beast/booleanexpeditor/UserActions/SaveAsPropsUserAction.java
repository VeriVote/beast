package edu.pse.beast.booleanexpeditor.UserActions;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.saverloader.OmniSaverLoader;
import edu.pse.beast.toolbox.UserAction;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;
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
            PostAndPrePropertiesDescription postAndPrePropertiesDescription = new PostAndPrePropertiesDescription(file.getName(),
                    currentlyLoaded.getPrePropertiesDescription(), currentlyLoaded.getPostPropertiesDescription(),
                    currentlyLoaded.getSymVarList());
            List<String> propertyStringList = OmniSaverLoader.createSaveFormat(postAndPrePropertiesDescription);
            for (Iterator iterator = propertyStringList.iterator(); iterator.hasNext();) {
                String line = (String) iterator.next();
                System.out.println(line);
            }
            booleanExpEditor.getSaveBeforeChangeHandler().setHasBeensaved(true);
            booleanExpEditor.getSaveBeforeChangeHandler().updatePreValues();
        }
    }
}
