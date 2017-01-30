package edu.pse.beast.booleanexpeditor.UserActions;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.toolbox.UserAction;

/**
 * @author NikolaiLMS
 */
public class AddConstUserAction extends UserAction {
    private BooleanExpEditorConst booleanExpEditorConst;
    private BooleanExpEditor booleanExpEditor;

    /**
     * Constructor
     * @param booleanExpEditorConst the BooleanExpEditorConst object this UserAction adds
     */
    public AddConstUserAction(String id, BooleanExpEditorConst booleanExpEditorConst, BooleanExpEditor booleanExpEditor) {
        super("numberOf" + id);
        this.booleanExpEditor = booleanExpEditor;
        this.booleanExpEditorConst = booleanExpEditorConst;
    }

    @Override
    public void perform() {
        booleanExpEditor.getCodeAreaFocusListener().getLastFocused().
                insertString(booleanExpEditorConst.toString());
    }
}
