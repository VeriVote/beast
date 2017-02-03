package edu.pse.beast.booleanexpeditor.UserActions;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.datatypes.propertydescription.FormalPropertiesDescription;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariableList;
import edu.pse.beast.toolbox.UserAction;

/**
 * @author NikolaiLMS
 */
public class NewPropsUserAction extends UserAction {
    private final BooleanExpEditor booleanExpEditor;
    private final SaveBeforeChangeHandler saveBeforeChangeHandler;
    public NewPropsUserAction(BooleanExpEditor booleanExpEditor, SaveBeforeChangeHandler saveBeforeChangeHandler) {
        super("new");
        this.booleanExpEditor = booleanExpEditor;
        this.saveBeforeChangeHandler = saveBeforeChangeHandler;
    }

    public static PostAndPrePropertiesDescription createEmptyPostAndPropObject() {
        FormalPropertiesDescription preDesc = new FormalPropertiesDescription("");
        FormalPropertiesDescription postDesc = new FormalPropertiesDescription("");
        SymbolicVariableList symbolicVariableList = new SymbolicVariableList();
        return new PostAndPrePropertiesDescription("NewFormalProperty", preDesc, postDesc,
                        symbolicVariableList);
    }

    @Override
    public void perform() {
        booleanExpEditor.letUserEditPostAndPreProperties(createEmptyPostAndPropObject());
    }
}
