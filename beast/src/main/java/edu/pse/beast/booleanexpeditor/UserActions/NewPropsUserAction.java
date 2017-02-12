package edu.pse.beast.booleanexpeditor.UserActions;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.datatypes.propertydescription.FormalPropertiesDescription;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariableList;
import edu.pse.beast.propertylist.PLControllerInterface;
import edu.pse.beast.propertylist.Model.PropertyItem;
import edu.pse.beast.toolbox.UserAction;

/**
 * UserAction subclass responsible creating a new PostAndPrePropertiesDescription object and loading it into the editor
 * and propertylist.
 * @author NikolaiLMS
 */
public class NewPropsUserAction extends UserAction {
    private final BooleanExpEditor booleanExpEditor;

    public NewPropsUserAction(BooleanExpEditor booleanExpEditor) {
        super("new");
        this.booleanExpEditor = booleanExpEditor;
    }

    /**
     * Method that
     * @return s an empty PostAndPrePropertiesDescription object that can be loaded into the editor/propertylist.
     */
    public static PostAndPrePropertiesDescription createEmptyPostAndPropObject() {
        FormalPropertiesDescription preDesc = new FormalPropertiesDescription("");
        FormalPropertiesDescription postDesc = new FormalPropertiesDescription("");
        SymbolicVariableList symbolicVariableList = new SymbolicVariableList();
        return new PostAndPrePropertiesDescription("NewFormalProperty", preDesc, postDesc,
                        symbolicVariableList);
    }

    @Override
    public void perform() {
        if (booleanExpEditor.letUserEditPostAndPreProperties(createEmptyPostAndPropObject(), false)) {
            booleanExpEditor.getFileChooser().setHasBeenSaved(false);
            booleanExpEditor.getPropertyListController().addNewProperty();
        }
    }
}
