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

    public SavePropsUserAction(BooleanExpEditor booleanExpEditor) {
        super("save");
        this.booleanExpEditor = booleanExpEditor;
    }

    @Override
    public void perform() {
        if (booleanExpEditor.getFileChooser().saveObject(booleanExpEditor.getCurrentlyLoadedPostAndPreProp(), false)) {
            booleanExpEditor.getSaveBeforeChangeHandler().updatePreValues();
            booleanExpEditor.getView().setWindowTitle(booleanExpEditor.getCurrentlyLoadedPostAndPreProp().getName());
        }
    }
}
