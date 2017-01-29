package edu.pse.beast.parametereditor.UserActions;

import edu.pse.beast.toolbox.UserAction;
import edu.pse.beast.parametereditor.ParameterEditor;

/**
 *
 * @author Jonas
 */
public class AbortCheckUserAction extends UserAction {
    private final ParameterEditor editor;
    
    public AbortCheckUserAction(ParameterEditor editor) {
        super("stop");
        this.editor = editor;
    }

    @Override
    public void perform() {
        editor.abortCheck();
    }
}
