package edu.pse.beast.booleanexpeditor.UserActions;

import edu.pse.beast.toolbox.UserAction;

/**
 * @author NikolaiLMS
 */
public class AddMakroUserAction extends UserAction {
    private BooleanExpEditorMakro makro;

    /**
     * Constructor
     * @param makro the BooleanExpEditorMakro object this UserAction adds
     */
    public AddMakroUserAction(BooleanExpEditorMakro makro) {
        super(makro.toString());
        this.makro = makro;
    }

    @Override
    public void perform() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Getter
     * @return makro
     */
    public BooleanExpEditorMakro getMakro() {
        return makro;
    }
}
