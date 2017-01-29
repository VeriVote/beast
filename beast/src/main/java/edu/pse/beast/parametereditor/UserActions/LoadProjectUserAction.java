package edu.pse.beast.parametereditor.UserActions;

import edu.pse.beast.toolbox.UserAction;
import edu.pse.beast.parametereditor.ParameterEditor;

/**
 *
 * @author Jonas
 */
public class LoadProjectUserAction extends UserAction {
    private final ParameterEditor editor;
    //TODO: complete
    
    public LoadProjectUserAction(ParameterEditor editor) {
        super("load");
        this.editor = editor;
    }

    @Override
    public void perform() {
        //editor.abortCheck(); //TODO: implement
    }
}
