package edu.pse.beast.booleanexpeditor.UserActions;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.toolbox.UserAction;

/**
 * @author NikolaiLMS
 */
public class CheckErrorsUserAction extends UserAction{
    private BooleanExpEditor booleanExpEditor;
    public CheckErrorsUserAction(BooleanExpEditor booleanExpEditor) {
        super("staticAnalysis");
        this.booleanExpEditor = booleanExpEditor;
    }

    @Override
    public void perform() {
        booleanExpEditor.findErrorsAndDisplayThem();
    }
}
