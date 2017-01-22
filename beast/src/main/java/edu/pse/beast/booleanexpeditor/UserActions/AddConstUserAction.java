package edu.pse.beast.booleanexpeditor.UserActions;

import edu.pse.beast.booleanexpeditor.BooleanExpEditorConst;
import edu.pse.beast.toolbox.UserAction;

/**
 * @author NikolaiLMS
 */
public class AddConstUserAction extends UserAction {
    private BooleanExpEditorConst booleanExpEditorConst;

    /**
     * Constructor
     * @param booleanExpEditorConst the BooleanExpEditorConst object this UserAction adds
     */
    public AddConstUserAction(BooleanExpEditorConst booleanExpEditorConst) {
        super("numberOf" + booleanExpEditorConst.getConstantString());
        this.booleanExpEditorConst = booleanExpEditorConst;
    }

    /**
     * Getter
     * @return booleanExpEditorConst
     */
    public BooleanExpEditorConst getBooleanExpEditorConst() {
        return booleanExpEditorConst;
    }

    @Override
    public void perform() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
