package edu.pse.beast.booleanexpeditor.UserActions;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.saverloader.FileChooser;
import edu.pse.beast.toolbox.UserAction;

/**
 * UserAction subclass responsible for saving Properties while forcing a save dialog.
 * @author NikolaiLMS
 */
public class SaveAsPropsUserAction extends UserAction {
    private final BooleanExpEditor editor;
    private final FileChooser fileChooser;

    /**
     * Constructor
     * @param editor reference to the GUI controller.
     */
    public SaveAsPropsUserAction(BooleanExpEditor editor) {
        super("save_as");
        this.editor = editor;
        this.fileChooser = editor.getFileChooser();
    }

    @Override
    public void perform() {
        PostAndPrePropertiesDescription currentlyLoaded = editor.getCurrentlyLoadedPostAndPreProp();
        if (fileChooser.saveObject(currentlyLoaded, true)) {
            editor.getChangeHandler().updatePreValues();
            editor.getView().setWindowTitle(editor.getCurrentlyLoadedPostAndPreProp().getName());
            editor.findErrorsAndDisplayThem();
        }
    }
}
