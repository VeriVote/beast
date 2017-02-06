package edu.pse.beast.parametereditor.UserActions;

import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
import edu.pse.beast.datatypes.Project;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.parametereditor.ParameterEditor;
import edu.pse.beast.propertylist.PropertyList;
import edu.pse.beast.saverloader.OmniSaverLoader;
import edu.pse.beast.saverloader.PostAndPrePropertiesDescriptionSaverLoader;
import edu.pse.beast.saverloader.ProjectSaverLoader;
import edu.pse.beast.saverloader.SaverLoaderInterface;
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
 * UserAction for saving a project at a certain path
 * @author Jonas
 */
public class SaveProjectAsUserAction extends UserAction {
    private final PropertyList propertyList;
    private final CElectionDescriptionEditor cElectionEditor;
    private final ParameterEditor paramEditor;
    private final SaverLoaderInterface saverLoaderIf;
    /**
     * Constructor
     * @param propertyList PropertyList
     * @param cElectionEditor CElectionDescriptionEditor
     * @param paramEditor ParameterEditor
     * @param saverLoaderIf SaverLoaderInterface
     */
    public SaveProjectAsUserAction(PropertyList propertyList, 
            CElectionDescriptionEditor cElectionEditor, ParameterEditor paramEditor, 
            SaverLoaderInterface saverLoaderIf) {
        super("save_as");
        this.propertyList = propertyList;
        this.cElectionEditor = cElectionEditor;
        this.paramEditor = paramEditor;
        this.saverLoaderIf = saverLoaderIf;
    }

    @Override
    public void perform() {
        if(paramEditor.getReacts()) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileFilter() {

                @Override
                public boolean accept(File file) {
                    if (file.getName().matches("(.)+(\\.)beast") || file.isDirectory()) {
                        return true;
                    } else {
                        return false;
                    }
                }

                @Override
                public String getDescription() {
                    return "BEAST-Project-File (*.beast)";
                }
            });
            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();

                Project project = new Project(paramEditor.getParameter(), propertyList.getModel(),
                        cElectionEditor.getElectionDescription());
                String saveString = null;
                try {
                    saveString = ProjectSaverLoader.createSaveString(project);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    FileWriter fw = new FileWriter(file);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(saveString);
                    try {
                        bw.close();
                        fw.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } catch (IOException e) {
                    // TODO OPEN ERROR DIALOG
                }
            }
        }
    }
}
