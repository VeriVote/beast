package edu.pse.beast.booleanexpeditor;

import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariableList;

import javax.swing.*;
import java.util.LinkedList;

/**
 * Class for checking whether the loaded PreAndPostConditionsDescription object has been modified since it was loaded.
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
     * @param prePane JTextPane of the preCondition BooleanExpCodeArea
     * @param postPane JTextPane of the postCondition BooleanExpCodeArea
     * @param symbolicVariableList
     *        SymbolicVariableList of a newly loaded or saved PreAndPostConditionsDescription object.
     */
    public ChangeHandler(JTextPane prePane, JTextPane postPane, SymbolicVariableList symbolicVariableList) {
        this.symbolicVariableList = symbolicVariableList;
        this.prePane = prePane;
        this.postPane = postPane;
        updatePreValues();
    }

    /**
     * Method that adds new JTextPanes to the changeHandler incase the BooleanExpEditor created new ones.
     * @param prePane the new preConditions JTextPane
     * @param postPane the new postConditions JTextPane
     */
    public void addNewTextPanes(JTextPane prePane, JTextPane postPane) {
        this.prePane = prePane;
        this.postPane = postPane;
        updatePreValues();
    }

    /**
     * Updates the "pre" variables used for comparison.
     * Called after a new PreAndPostConditionsDescription object is loaded or saved.
     */
    public void updatePreValues() {
        preString = prePane.getText() + postPane.getText();
        preSymbolicVariableList =
                new LinkedList<SymbolicVariable>(symbolicVariableList.getSymbolicVariables());
    }

    /**
     * Method to check whether the input in the BooleanExpEditor has changed.
     * @return true if it has changed, false otherwise
     */
    public boolean hasChanged() {
        return !(preString.equals((String) prePane.getText() + postPane.getText())
                && preSymbolicVariableList.equals(symbolicVariableList.getSymbolicVariables()));
    }
}
