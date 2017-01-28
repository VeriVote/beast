package edu.pse.beast.booleanexpeditor;

import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariableList;

import javax.swing.*;
import java.util.LinkedList;

/**
 * @author NikolaiLMS
 */
public class SaveBeforeChangeHandler {
    private LinkedList<SymbolicVariable> preSymbolicVariableList;
    private SymbolicVariableList symbolicVariableList;
    private String preString = "";
    private JTextPane prePane;
    private JTextPane postPane;

    SaveBeforeChangeHandler(JTextPane prePane, JTextPane postPane, SymbolicVariableList symbolicVariableList) {
        this.symbolicVariableList = symbolicVariableList;
        this.prePane = prePane;
        this.postPane = postPane;
        updatePreValues();
    }

    public void updatePreValues() {
        preString = prePane.getText() + postPane.getText();
        preSymbolicVariableList = new LinkedList<SymbolicVariable>(symbolicVariableList.getSymbolicVariables());
    }

    public boolean hasChanged() {
        return !(preString.equals((String) prePane.getText() + postPane.getText()) &&
                preSymbolicVariableList.equals(symbolicVariableList.getSymbolicVariables()));
    }

}
