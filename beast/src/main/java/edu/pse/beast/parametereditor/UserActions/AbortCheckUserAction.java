package edu.pse.beast.parametereditor.UserActions;

import edu.pse.beast.parametereditor.ParameterEditor;
import edu.pse.beast.toolbox.UserAction;

/**
 * UserAction for stopping a check
 * @author Jonas
 */
public class AbortCheckUserAction extends UserAction {
    private final ParameterEditor editor;
    /**
     * Constructor
     * @param editor ParameterEditor
     */
    public AbortCheckUserAction(ParameterEditor editor) {
        super("stop");
        this.editor = editor;
    }

    @Override
    public void perform() {
    }
}
