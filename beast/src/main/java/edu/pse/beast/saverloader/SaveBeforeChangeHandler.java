package edu.pse.beast.saverloader;

import edu.pse.beast.booleanexpeditor.View.BooleanExpEditorWindow;
import edu.pse.beast.booleanexpeditor.UserActions.SavePropsUserAction;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariableList;

import javax.swing.*;
import java.util.LinkedList;

/**
 * @author NikolaiLMS
 */
public class SaveBeforeChangeHandler {
    private LinkedList<SymbolicVariable> preSymbolicVariableList;
    private final SymbolicVariableList symbolicVariableList;
    private String preString = "";
    private JTextPane prePane;
    private JTextPane postPane;
    private BooleanExpEditorWindow booleanExpEditorWindow;
    private SavePropsUserAction savePropsUserAction;

    /**
     * Constructor
     * @param prePane JTextPane of the preProp BooleanExpCodeArea
     * @param postPane JTextPane of the postProp BooleanExpCodeArea
     * @param symbolicVariableList SymbolicVariableList of a newly loaded or saved PostAndPrePropertiesDescription object.
     */
    public SaveBeforeChangeHandler(JTextPane prePane, JTextPane postPane, SymbolicVariableList symbolicVariableList,
                                   BooleanExpEditorWindow booleanExpEditorWindow) {
        this.symbolicVariableList = symbolicVariableList;
        this.prePane = prePane;
        this.postPane = postPane;
        this.booleanExpEditorWindow = booleanExpEditorWindow;
        updateHasChanged();
    }

    /**
     * Updates the "pre" variables used for comparison.
     * Called after a new PostAndPrePropertiesDescription object is loaded or saved.
     */
    public void updateHasChanged() {
        preString = prePane.getText() + postPane.getText();
        preSymbolicVariableList = new LinkedList<>(symbolicVariableList.getSymbolicVariables());
    }

    /**
     * @return true if the currently loaded PostAndPrePropertiesDescription object differs from what is currently displayed
     * in BooleanExpEditorWindow, false otherwise
     */
    public boolean hasChanged() {
        return !(preString.equals(prePane.getText() + postPane.getText()) &&
                preSymbolicVariableList.equals(symbolicVariableList.getSymbolicVariables()));
    }

    /**
     * Method that opens a dialog asking the user whether he wants to save his changes, before loading a new
     * PostAndPreProsObject into the editor.
     * @return false if the user pressed "Cancel" on the dialog, thus cancelling any previous load action
     *          true otherwise
     */
    public boolean ifHasChangedOpenSaveDialog(String currentlyLoadedPropName) {
        if (hasChanged()) {
            int option = booleanExpEditorWindow.showOptionPane(currentlyLoadedPropName);
            if (option == JOptionPane.YES_OPTION) {
                savePropsUserAction.perform();
            } else if (option == JOptionPane.CANCEL_OPTION) {
                return false;
            }
        }
        return true;
    }

    /**
     * Setter
     * @param savePropsUserAction given this class so it can call it when the user wants to save his
     *                            changes upon being shown the dialog from ifHasChangedOpenSaveDialog()
     */
    public void setSavePropsUserAction(SavePropsUserAction savePropsUserAction) {
        this.savePropsUserAction = savePropsUserAction;
    }
}
