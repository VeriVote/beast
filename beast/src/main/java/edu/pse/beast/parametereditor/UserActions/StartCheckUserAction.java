package edu.pse.beast.parametereditor.UserActions;

import edu.pse.beast.toolbox.UserAction;
import edu.pse.beast.parametereditor.ParameterEditor;
/**
 *
 * @author Jonas
 */
public class StartCheckUserAction extends UserAction {
    private final ParameterEditor editor;
    
    public StartCheckUserAction(ParameterEditor editor) {
        super("start");
        this.editor = editor;
    }

    @Override
    public void perform() {
        editor.startCheck();
    }
}
