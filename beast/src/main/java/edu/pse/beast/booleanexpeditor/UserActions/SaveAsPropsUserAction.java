package edu.pse.beast.booleanexpeditor.UserActions;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.saverloader.FileChooser;
import edu.pse.beast.toolbox.UserAction;

/**
 * @author NikolaiLMS
 */
public class SaveAsPropsUserAction extends UserAction {
    private final BooleanExpEditor booleanExpEditor;
    private final FileChooser fileChooser;

    public SaveAsPropsUserAction(BooleanExpEditor booleanExpEditor) {
        super("save_as");
        this.booleanExpEditor = booleanExpEditor;
        this.fileChooser = booleanExpEditor.getFileChooser();
    }

    @Override
    public void perform() {
        PostAndPrePropertiesDescription currentlyLoaded = booleanExpEditor.getCurrentlyLoadedPostAndPreProp();
        if (fileChooser.saveObject(currentlyLoaded, true)) {
            booleanExpEditor.getChangeHandler().updatePreValues();
            booleanExpEditor.getView().setWindowTitle(booleanExpEditor.getCurrentlyLoadedPostAndPreProp().getName());
            booleanExpEditor.findErrorsAndDisplayThem();
        }
    }
}
