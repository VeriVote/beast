package edu.pse.beast.booleanexpeditor.UserActions;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.booleanexpeditor.SaveBeforeChangeHandler;
import edu.pse.beast.datatypes.propertydescription.FormalPropertiesDescription;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariableList;
import edu.pse.beast.toolbox.BooleanExpCodeToASTConverter;
import edu.pse.beast.toolbox.UserAction;

import javax.swing.*;

/**
 * @author NikolaiLMS
 */
public class NewPropsUserAction extends UserAction {
    private BooleanExpEditor booleanExpEditor;
    private SaveBeforeChangeHandler saveBeforeChangeHandler;
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
        if (saveBeforeChangeHandler.hasChanged()) {
            // TEMPORARY
            int option = booleanExpEditor.getWindow().showOptionPane(booleanExpEditor.getCurrentlyLoadedPostAndPreProp()
                    .getName());
            if (option == JOptionPane.NO_OPTION) {
                booleanExpEditor.loadPostAndPreProperties(createEmptyPostAndPropObject());
            } else if (option == JOptionPane.YES_OPTION) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        } else {
            booleanExpEditor.loadPostAndPreProperties(createEmptyPostAndPropObject());
        }
    }
}
