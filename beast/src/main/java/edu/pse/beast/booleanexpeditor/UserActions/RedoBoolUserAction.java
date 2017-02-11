package edu.pse.beast.booleanexpeditor.UserActions;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.toolbox.UserAction;

/**
 * @author NikolaiLMS
 */
public class RedoBoolUserAction extends UserAction {
    private final BooleanExpEditor editor;
    public RedoBoolUserAction(BooleanExpEditor editor) {
        super("redo");
        this.editor = editor;
    }

    @Override
    public void perform() {
        editor.getCodeAreaFocusListener().getLastFocused().getUserActionList().getActionById("redo").perform();
    }
}
