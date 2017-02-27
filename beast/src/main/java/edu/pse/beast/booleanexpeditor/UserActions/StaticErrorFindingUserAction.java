package edu.pse.beast.booleanexpeditor.UserActions;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.toolbox.UserAction;

/**
 * UserAction subclass responsible for static error finding in the BooleanExpEditor.
 * @author NikolaiLMS
 */
public class StaticErrorFindingUserAction extends UserAction {

    private final BooleanExpEditor editor;

    /**
     * Constructor
     * @param editor reference to the GUI controller.
     */
    public StaticErrorFindingUserAction(BooleanExpEditor editor) {
        super("staticErrorFinding");
        this.editor = editor;
    }

    @Override
    public void perform() {
        editor.findErrorsAndDisplayThem();
    }
}
