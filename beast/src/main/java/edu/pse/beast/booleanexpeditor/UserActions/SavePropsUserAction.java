package edu.pse.beast.booleanexpeditor.UserActions;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.toolbox.UserAction;

/**
 * @author NikolaiLMS
 */
public class SavePropsUserAction extends UserAction{
    private final BooleanExpEditor booleanExpEditor;

    public SavePropsUserAction(BooleanExpEditor booleanExpEditor) {
        super("save");
        this.booleanExpEditor = booleanExpEditor;
    }

    @Override
    public void perform() {
        if (booleanExpEditor.getFileChooser().saveObject(booleanExpEditor.getCurrentlyLoadedPostAndPreProp(), false)) {
            booleanExpEditor.getChangeHandler().updatePreValues();
            booleanExpEditor.getView().setWindowTitle(booleanExpEditor.getCurrentlyLoadedPostAndPreProp().getName());
            booleanExpEditor.findErrorsAndDisplayThem();
        }
    }
}
