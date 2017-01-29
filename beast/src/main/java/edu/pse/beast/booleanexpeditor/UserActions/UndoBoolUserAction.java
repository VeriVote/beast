package edu.pse.beast.booleanexpeditor.UserActions;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.toolbox.UserAction;

/**
 * @author NikolaiLMS
 */
public class UndoBoolUserAction extends UserAction{
    private BooleanExpEditor editor;

    public UndoBoolUserAction(BooleanExpEditor editor) {
        super("undo");
        this.editor = editor;
    }

    @Override
    public void perform() {
        editor.getCodeAreaFocusListener().getLastFocused().getUserActionList().getActionById("undo").perform();
    }
}
