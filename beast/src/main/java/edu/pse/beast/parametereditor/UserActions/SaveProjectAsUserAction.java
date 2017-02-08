package edu.pse.beast.parametereditor.UserActions;

import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
import edu.pse.beast.datatypes.Project;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.parametereditor.ParameterEditor;
import edu.pse.beast.propertylist.PropertyList;
import edu.pse.beast.saverloader.*;
import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.stringresource.StringResourceLoader;
import edu.pse.beast.toolbox.FileChooser;
import edu.pse.beast.toolbox.UserAction;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.*;
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
    private StringResourceLoader stringResourceLoader;
    private final ProjectSaverLoader saverLoader;

    /**
     * Constructor
     * @param propertyList PropertyList
     * @param cElectionEditor CElectionDescriptionEditor
     * @param paramEditor ParameterEditor
     * @param stringResourceLoader StringResourceLoader
     */
    public SaveProjectAsUserAction(PropertyList propertyList,
                                   CElectionDescriptionEditor cElectionEditor, ParameterEditor paramEditor,
                                   StringResourceLoader stringResourceLoader) {
        super("save_as");
        this.propertyList = propertyList;
        this.cElectionEditor = cElectionEditor;
        this.paramEditor = paramEditor;
        this.stringResourceLoader = stringResourceLoader;
        this.saverLoader = new ProjectSaverLoader();
    }

    @Override
    public void perform() {
        if(paramEditor.getReacts()) {
            Project project = new Project(paramEditor.getParameter(), propertyList.getModel(),
                    cElectionEditor.getElectionDescription());
            if (paramEditor.getFileChooser().saveObject(project, true)) {
                //TODO SaveBeforeChangeHandler call
            }
        }
    }
}
