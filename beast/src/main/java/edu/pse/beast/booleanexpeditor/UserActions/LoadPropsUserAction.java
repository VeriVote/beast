package edu.pse.beast.booleanexpeditor.UserActions;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.propertylist.PLControllerInterface;
import edu.pse.beast.propertylist.Model.PropertyItem;
import edu.pse.beast.toolbox.UserAction;

/**
 * UserAction subclass responsible loading a PostAndPrePropertiesDescription object from the file system into the editor
 * /propertylist.
 * @author NikolaiLMS
 */
public class LoadPropsUserAction extends UserAction {

    private final BooleanExpEditor booleanExpEditor;
    private PLControllerInterface propertyList;

    public LoadPropsUserAction(BooleanExpEditor booleanExpEditor) {
        super("load");
        this.booleanExpEditor = booleanExpEditor;
    }

    public LoadPropsUserAction(BooleanExpEditor booleanExpEditor, PLControllerInterface controller) {
        super("load");
        this.booleanExpEditor = booleanExpEditor;
        this.propertyList = controller;
    }

    @Override
    public void perform() {
        if (booleanExpEditor.getChangeHandler().hasChanged() && booleanExpEditor.getLoadedFromPropertyList()) {
            if (!booleanExpEditor.getFileChooser().openSaveChangesDialog(booleanExpEditor.getCurrentlyLoadedPostAndPreProp())) {
                return;
            }
        }
        PostAndPrePropertiesDescription loadedPostAndPrePropertiesDescription
                = (PostAndPrePropertiesDescription) booleanExpEditor.getFileChooser().loadObject();
        if (loadedPostAndPrePropertiesDescription != null) {
            booleanExpEditor.loadNewProperties(loadedPostAndPrePropertiesDescription);
            booleanExpEditor.getPropertyListController().addDescription(new PropertyItem(loadedPostAndPrePropertiesDescription));
        }
    }

    public void loadIntoPropertyList() {
        PostAndPrePropertiesDescription loadedPostAndPrePropertiesDescription
                = (PostAndPrePropertiesDescription) booleanExpEditor.getFileChooser().loadObject();
        if (loadedPostAndPrePropertiesDescription != null) {
            booleanExpEditor.loadNewProperties(loadedPostAndPrePropertiesDescription);
            propertyList.addDescription(new PropertyItem(loadedPostAndPrePropertiesDescription));
        }
    }
}
