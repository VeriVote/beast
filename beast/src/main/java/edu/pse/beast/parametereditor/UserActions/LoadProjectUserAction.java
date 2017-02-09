package edu.pse.beast.parametereditor.UserActions;

import edu.pse.beast.datatypes.Project;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.propertylist.Model.PLModel;
import edu.pse.beast.saverloader.PostAndPrePropertiesDescriptionSaverLoader;
import edu.pse.beast.saverloader.ProjectSaverLoader;
import edu.pse.beast.saverloader.SaverLoader;
import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.stringresource.StringResourceLoader;
import edu.pse.beast.stringresource.StringResourceProvider;
import edu.pse.beast.toolbox.FileChooser;
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
    private StringResourceLoader stringResourceLoader;
    private ProjectSaverLoader projectSaverLoader;

    /**
     * Constructor
     * @param propertyList PropertyList
     * @param cElectionEditor CElectionDescriptionEditor
     * @param paramEditor ParameterEditor
     * @param stringResourceLoader StringResourceLoader
     */
    public LoadProjectUserAction(PropertyList propertyList,
                                 CElectionDescriptionEditor cElectionEditor, ParameterEditor paramEditor,
                                 StringResourceLoader stringResourceLoader) {
        super("load");
        this.projectSaverLoader = new ProjectSaverLoader();
        this.propertyList = propertyList;
        this.cElectionEditor = cElectionEditor;
        this.paramEditor = paramEditor;
        this.stringResourceLoader = stringResourceLoader;
        projectSaverLoader = new ProjectSaverLoader();
    }

    @Override
    public void perform() {
        if (paramEditor.getReacts()) {
            // TODO SaveBeforeChangeHandler call
            Project loadedProject = (Project) paramEditor.getFileChooser().loadObject();
            if (loadedProject != null) {
                propertyList.setPLModel(loadedProject.getPropList());
                propertyList.getView().setVisible(true);
                paramEditor.setParameter(loadedProject.getElectionCheckParameter());
                try {
                    cElectionEditor.letUserEditElectionDescription(loadedProject.getElecDescr());
                    cElectionEditor.setVisible(true);
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
                // TODO SaveBeforeChangeHandler call
            }
        }
    }
}
