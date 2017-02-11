package edu.pse.beast.booleanexpeditor.UserActions;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.toolbox.UserAction;

/**
 * @author NikolaiLMS
 */
public class PasteUserAction extends UserAction{
    private final BooleanExpEditor editor;

    public PasteUserAction(BooleanExpEditor editor) {
        super("paste");
        this.editor = editor;
    }

    @Override
    public void perform() {
        editor.getCodeAreaFocusListener().getLastFocused().getUserActionList().getActionById("paste").perform();
    }
}
