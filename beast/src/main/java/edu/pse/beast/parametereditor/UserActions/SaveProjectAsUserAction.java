package edu.pse.beast.parametereditor.UserActions;

import edu.pse.beast.parametereditor.ParameterEditor;
import edu.pse.beast.toolbox.UserAction;

/**
 *
 * @author Jonas
 */
public class SaveProjectAsUserAction extends UserAction {
    private final ParameterEditor editor;
    //TODO: complete
    
    public SaveProjectAsUserAction(ParameterEditor editor) {
        super("saveAs");
        this.editor = editor;
    }

    @Override
    public void perform() {
        //TODO: implement
    }
}
