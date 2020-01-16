package edu.pse.beast.booleanexpeditor;

import java.util.LinkedList;

import javax.swing.JTextPane;

import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariableList;

/**
 * Class for checking whether the loaded PreAndPostConditionsDescription object
 * has been modified since it was loaded.
 *
 * @author Nikolai Schnell
 */
public class ChangeHandler {

    /** The pre symbolic variable list. */
    private LinkedList<SymbolicVariable> preSymbolicVariableList;

    /** The symbolic variable list. */
    private final SymbolicVariableList symbolicVariableList;

    /** The pre string. */
    private String preString = "";

    /** The pre pane. */
    private JTextPane prePane;

    /** The post pane. */
    private JTextPane postPane;

    /**
     * Constructor.
     *
     * @param preTextPane              JTextPane of the preCondition BooleanExpCodeArea
     * @param postTextPane             JTextPane of the postCondition BooleanExpCodeArea
     * @param symbolicVarList          SymbolicVariableList of a newly loaded or saved
     *                                     PreAndPostConditionsDescription object.
     */
    public ChangeHandler(final JTextPane preTextPane, final JTextPane postTextPane,
                         final SymbolicVariableList symbolicVarList) {
        this.symbolicVariableList = symbolicVarList;
        this.prePane = preTextPane;
        this.postPane = postTextPane;
        updatePreValues();
    }

    /**
     * Method that adds new JTextPanes to the changeHandler incase the
     * BooleanExpEditor created new ones.
     *
     * @param preTextPane  the new preConditions JTextPane
     * @param postTextPane the new postConditions JTextPane
     */
    public void addNewTextPanes(final JTextPane preTextPane,
                                final JTextPane postTextPane) {
        this.prePane = preTextPane;
        this.postPane = postTextPane;
        updatePreValues();
    }

    /**
     * Updates the "pre" variables used for comparison. Called after a new
     * PreAndPostConditionsDescription object is loaded or saved.
     */
    public void updatePreValues() {
        preString = prePane.getText() + postPane.getText();
        preSymbolicVariableList
            = new LinkedList<SymbolicVariable>(symbolicVariableList.getSymbolicVariables());
    }

    /**
     * Method to check whether the input in the BooleanExpEditor has changed.
     *
     * @return true if it has changed, false otherwise
     */
    public boolean hasChanged() {
        return !(preString.equals(prePane.getText() + postPane.getText())
                && preSymbolicVariableList.equals(symbolicVariableList.getSymbolicVariables()));
    }
}
