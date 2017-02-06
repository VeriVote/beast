package edu.pse.beast.parametereditor.UserActions;

import edu.pse.beast.datatypes.Project;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.saverloader.PostAndPrePropertiesDescriptionSaverLoader;
import edu.pse.beast.saverloader.ProjectSaverLoader;
import edu.pse.beast.toolbox.UserAction;
import edu.pse.beast.parametereditor.ParameterEditor;
import edu.pse.beast.propertylist.PropertyList;
import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
import edu.pse.beast.saverloader.SaverLoaderInterface;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.BadLocationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

/**
 * UserAction for loading a project
 * @author Jonas
 */
public class LoadProjectUserAction extends UserAction {
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
    public LoadProjectUserAction(PropertyList propertyList, 
            CElectionDescriptionEditor cElectionEditor, ParameterEditor paramEditor, 
            SaverLoaderInterface saverLoaderIf) {
        super("load");
        this.propertyList = propertyList;
        this.cElectionEditor = cElectionEditor;
        this.paramEditor = paramEditor;
        this.saverLoaderIf = saverLoaderIf;
    }

    @Override
    public void perform() {
        if (paramEditor.getReacts()) {
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
            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

                File file = fileChooser.getSelectedFile();
                String content = "";
                try {
                    content = new String(Files.readAllBytes(file.toPath()));
                    System.out.println(content);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {

                }
                Project loadedProject = null;

                try {
                    loadedProject = ProjectSaverLoader.createFromSaveString(content);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Wrong file format!");
                }


                try {
                    cElectionEditor.loadElectionDescription(loadedProject.getElecDescr());
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
                propertyList.setPLModel(loadedProject.getPropList());
                paramEditor.setParameter(loadedProject.getElectionCheckParameter());
            }
        }
    }
}
