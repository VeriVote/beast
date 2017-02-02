package edu.pse.beast.booleanexpeditor.UserActions;

import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariableList;
import javax.swing.JTextPane;
import java.util.LinkedList;

/**
 * Class for checking whether the loaded PostAndPrePropertiesDescription object has been modified since it was loaded.
 * @author NikolaiLMS
 */
public class SaveBeforeChangeHandler {
    private LinkedList<SymbolicVariable> preSymbolicVariableList;
    private final SymbolicVariableList symbolicVariableList;
    private String preString = "";
    private JTextPane prePane;
    private JTextPane postPane;

    /**
     * Constructor
     * @param prePane JTextPane of the preProp BooleanExpCodeArea
     * @param postPane JTextPane of the postProp BooleanExpCodeArea
     * @param symbolicVariableList SymbolicVariableList of a newly loaded or saved PostAndPrePropertiesDescription object.
     */
    public SaveBeforeChangeHandler(JTextPane prePane, JTextPane postPane, SymbolicVariableList symbolicVariableList) {
        this.symbolicVariableList = symbolicVariableList;
        this.prePane = prePane;
        this.postPane = postPane;
        updatePreValues();
    }

    public void addNewTextPanes(JTextPane prePane, JTextPane postPane) {
        this.prePane = prePane;
        this.postPane = postPane;
        updatePreValues();
    }

    /**
     * Updates the "pre" variables used for comparison.
     * Called after a new PostAndPrePropertiesDescription object is loaded or saved.
     */
    public void updatePreValues() {
        preString = prePane.getText() + postPane.getText();
        preSymbolicVariableList = new LinkedList<SymbolicVariable>(symbolicVariableList.getSymbolicVariables());
    }

    /**
     * @return true if the currently loaded PostAndPrePropertiesDescription object differs from what is currently displayed
     * in BooleanExpEditorWindow, false otherwise
     */
    public boolean hasChanged() {
        return !(preString.equals((String) prePane.getText() + postPane.getText()) &&
                preSymbolicVariableList.equals(symbolicVariableList.getSymbolicVariables()));
    }

}
