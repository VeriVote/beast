package edu.pse.beast.booleanexpeditor.UserActions;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.toolbox.UserAction;


/**
 * Responsible for adding BooleanExpEditorMakro objects to the last focused BooleanExpEditorCodeArea.
 * Subclass of UserAction.
 * @author NikolaiLMS
 */
public class AddMakroUserAction extends UserAction {
    private final BooleanExpEditorMakro makro;
    private final BooleanExpEditor booleanExpEditor;

    /**
     * Constructor
     * @param makro the BooleanExpEditorMakro object this UserAction adds
     * @param booleanExpEditor the BooleanExpEditor object this UserAction belongs to
     * @param id the ID of this UserAction
     */
    public AddMakroUserAction(String id, BooleanExpEditorMakro makro, BooleanExpEditor booleanExpEditor) {
        super(id);
        this.makro = makro;
        this.booleanExpEditor = booleanExpEditor;
    }

    @Override
    public void perform() {
        booleanExpEditor.getCodeAreaFocusListener().getLastFocused().
                insertString(makro.toString());
    }
}
