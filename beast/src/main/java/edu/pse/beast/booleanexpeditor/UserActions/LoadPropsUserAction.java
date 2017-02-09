package edu.pse.beast.booleanexpeditor.UserActions;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.toolbox.UserAction;

/**
 * @author NikolaiLMS
 */
public class LoadPropsUserAction extends UserAction {
    private BooleanExpEditor booleanExpEditor;

    public LoadPropsUserAction(BooleanExpEditor booleanExpEditor) {
        super("load");
        this.booleanExpEditor = booleanExpEditor;
    }

    @Override
    public void perform() {
        if (booleanExpEditor.getSaveBeforeChangeHandler().
                ifHasChangedOpenSaveDialog(booleanExpEditor.getCurrentlyLoadedPostAndPreProp().getName())) {
            PostAndPrePropertiesDescription loadedPostAndPrePropertiesDescription =
                    (PostAndPrePropertiesDescription) booleanExpEditor.getFileChooser().loadObject();
            if (loadedPostAndPrePropertiesDescription != null) {
                booleanExpEditor.loadNewProperties(loadedPostAndPrePropertiesDescription);
                booleanExpEditor.getFileChooser().setHasBeenSaved(true);
            }
        }
    }
}
