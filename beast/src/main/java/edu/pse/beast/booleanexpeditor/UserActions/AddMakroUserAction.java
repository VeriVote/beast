package edu.pse.beast.booleanexpeditor.UserActions;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.toolbox.UserAction;


/**
 * @author NikolaiLMS
 */
public class AddMakroUserAction extends UserAction {
    private final BooleanExpEditorMakro makro;
    private final BooleanExpEditor booleanExpEditor;

    /**
     * Constructor
     * @param makro the BooleanExpEditorMakro object this UserAction adds
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
