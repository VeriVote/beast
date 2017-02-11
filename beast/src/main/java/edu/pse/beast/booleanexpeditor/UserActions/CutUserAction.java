package edu.pse.beast.booleanexpeditor.UserActions;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.toolbox.UserAction;

/**
 * @author NikolaiLMS
 */
public class CutUserAction extends UserAction{
    private final BooleanExpEditor editor;

    public CutUserAction(BooleanExpEditor editor) {
        super("cut");
        this.editor = editor;
    }

    @Override
    public void perform() {
        editor.getCodeAreaFocusListener().getLastFocused().getUserActionList().getActionById("cut").perform();
    }
}
