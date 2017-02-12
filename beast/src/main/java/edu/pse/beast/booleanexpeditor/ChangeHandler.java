package edu.pse.beast.booleanexpeditor;

import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariableList;
import edu.pse.beast.saverloader.FileChooser;

import javax.swing.*;
import java.util.LinkedList;

/**
 * Class for checking whether the loaded PostAndPrePropertiesDescription object has been modified since it was loaded.
 * @author NikolaiLMS
 */
public class ChangeHandler {
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
    public ChangeHandler(JTextPane prePane, JTextPane postPane, SymbolicVariableList symbolicVariableList) {
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

    public boolean hasChanged() {
        return !(preString.equals((String) prePane.getText() + postPane.getText()) 
                && preSymbolicVariableList.equals(symbolicVariableList.getSymbolicVariables()));
    }
}
