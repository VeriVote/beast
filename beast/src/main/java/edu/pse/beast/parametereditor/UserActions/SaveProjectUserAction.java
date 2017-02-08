package edu.pse.beast.parametereditor.UserActions;

import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
import edu.pse.beast.datatypes.Project;
import edu.pse.beast.parametereditor.ParameterEditor;
import edu.pse.beast.propertylist.PropertyList;
import edu.pse.beast.saverloader.SaverLoaderInterface;
import edu.pse.beast.toolbox.UserAction;
import java.io.File;

/**
 * UserAction for saving a project
 * @author Jonas
 */
public class SaveProjectUserAction extends UserAction {
    private final PropertyList propertyList;
    private final CElectionDescriptionEditor cElectionEditor;
    private final ParameterEditor paramEditor;
    private File file;
    /**
     * Constructor
     * @param propertyList PropertyList
     * @param cElectionEditor CElectionDescriptionEditor
     * @param paramEditor ParameterEditor
     */
    public SaveProjectUserAction(PropertyList propertyList, 
            CElectionDescriptionEditor cElectionEditor, ParameterEditor paramEditor) {
        super("save");
        this.propertyList = propertyList;
        this.cElectionEditor = cElectionEditor;
        this.paramEditor = paramEditor;
    }

    @Override
    public void perform() {
        if(paramEditor.getReacts()) {
            Project project = new Project(paramEditor.getParameter(), propertyList.getModel(),
                    cElectionEditor.getElectionDescription());
            if (cElectionEditor.getFileChooser().saveObject(project, false)) {
                //TODO SaveBeforeChangeHandler call
            }
        }
    }
}
