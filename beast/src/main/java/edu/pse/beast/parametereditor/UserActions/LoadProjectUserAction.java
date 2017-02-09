package edu.pse.beast.parametereditor.UserActions;

import edu.pse.beast.datatypes.Project;
import edu.pse.beast.saverloader.ProjectSaverLoader;
import edu.pse.beast.stringresource.StringResourceLoader;
import edu.pse.beast.toolbox.UserAction;
import edu.pse.beast.parametereditor.ParameterEditor;
import edu.pse.beast.propertylist.PropertyList;
import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;

import javax.swing.text.BadLocationException;

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
            if (paramEditor.hasChanged() || cElectionEditor.getSaveBeforeChangeHandler().hasChanged() ||
                    propertyList.getSaveBeforeChangeHandler().isChangedSinceSave()){
                
            }
            Project loadedProject = (Project) paramEditor.getFileChooser().loadObject();
            if (loadedProject != null) {
                propertyList.setPLModel(loadedProject.getPropList());
                propertyList.getView().setVisible(true);
                paramEditor.setParameter(loadedProject.getElectionCheckParameter());
                paramEditor.getView().setWindowTitle(loadedProject.getName());
                try {
                    cElectionEditor.letUserEditElectionDescription(loadedProject.getElecDescr());
                    cElectionEditor.setVisible(true);
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
                paramEditor.setHasChanged(false);
            }
        }
    }
}
