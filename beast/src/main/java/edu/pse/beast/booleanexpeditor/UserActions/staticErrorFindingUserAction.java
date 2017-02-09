package edu.pse.beast.booleanexpeditor.UserActions;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.toolbox.UserAction;

/**
 * @author NikolaiLMS
 */
public class staticErrorFindingUserAction extends UserAction{
    private BooleanExpEditor booleanExpEditor;
    public staticErrorFindingUserAction(BooleanExpEditor booleanExpEditor) {
        super("staticErrorFinding");
        this.booleanExpEditor = booleanExpEditor;
    }

    @Override
    public void perform() {
        booleanExpEditor.findErrorsAndDisplayThem();
    }
}
