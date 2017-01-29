package edu.pse.beast.parametereditor.UserActions;

import edu.pse.beast.parametereditor.ParameterEditor;
import edu.pse.beast.toolbox.UserAction;

/**
 * 
 * @author Jonas
 */
public class SaveProjectUserAction extends UserAction {
    private final ParameterEditor editor;
    //TODO: complete
    
    public SaveProjectUserAction(ParameterEditor editor) {
        super("save");
        this.editor = editor;
    }

    @Override
    public void perform() {
        //editor.abortCheck(); //TODO: implement
    }
}
