package edu.pse.beast.booleanexpeditor.UserActions;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.toolbox.UserAction;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * @author NikolaiLMS
 */
public class SavePropsUserAction extends UserAction{
    private BooleanExpEditor booleanExpEditor;
    private SaveAsPropsUserAction saveAsPropsUserAction;

    public SavePropsUserAction(BooleanExpEditor booleanExpEditor, SaveAsPropsUserAction saveAsPropsUserAction) {
        super("save");
        this.booleanExpEditor = booleanExpEditor;
        this.saveAsPropsUserAction = saveAsPropsUserAction;
    }

    @Override
    public void perform() {
        if (booleanExpEditor.getSaveBeforeChangeHandler().hasBeenSaved()) {
            booleanExpEditor.updatePostAndPrePropObject();
            booleanExpEditor.getSaveBeforeChangeHandler().updatePreValues();
            // TODO implement saving file
        } else {
            saveAsPropsUserAction.perform();
        }
    }
}
