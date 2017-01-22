package edu.pse.beast.booleanexpeditor.UserActions;

import edu.pse.beast.booleanexpeditor.BooleanExpEditorConst;
import edu.pse.beast.toolbox.UserAction;

/**
 * @author NikolaiLMS
 */
public class AddConstUserAction extends UserAction {
    private BooleanExpEditorConst booleanExpEditorConst;
    public AddConstUserAction(BooleanExpEditorConst booleanExpEditorConst) {
        super("numberOf" + booleanExpEditorConst.getConstantString());
        this.booleanExpEditorConst = booleanExpEditorConst;
    }

    public BooleanExpEditorConst getBooleanExpEditorConst() {
        return booleanExpEditorConst;
    }

    @Override
    public void perform() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
