package edu.pse.beast.booleanexpeditor.UserActions;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.toolbox.UserAction;

/**
 * @author NikolaiLMS
 */
public class CopyUserAction extends UserAction {
    private BooleanExpEditor editor;

    public CopyUserAction(BooleanExpEditor editor) {
        super("copy");
        this.editor = editor;
    }

    @Override
    public void perform() {
        editor.getCodeAreaFocusListener().getLastFocused().getUserActionList().getActionById("copy").perform();
    }

}
