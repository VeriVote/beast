package edu.pse.beast.booleanexpeditor.UserActions;

import edu.pse.beast.toolbox.UserAction;

/**
 * @author NikolaiLMS
 */
public class AddMakroUserAction extends UserAction {
    private BooleanExpEditorMakro makro;
    public AddMakroUserAction(BooleanExpEditorMakro makro) {
        super(makro.toString());
        this.makro = makro;
    }

    @Override
    public void perform() {

    }

    public BooleanExpEditorMakro getMakro() {
        return makro;
    }
}
