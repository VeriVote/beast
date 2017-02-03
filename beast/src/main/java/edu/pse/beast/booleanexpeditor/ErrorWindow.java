package edu.pse.beast.booleanexpeditor;

import edu.pse.beast.codearea.ErrorHandling.CodeError;
import edu.pse.beast.codearea.ErrorHandling.ErrorDisplayer;
import edu.pse.beast.highlevel.DisplaysStringsToUser;
import edu.pse.beast.stringresource.StringLoaderInterface;
import javax.swing.JTextPane;
import java.util.ArrayList;

/**
 * Controller of the JTextPane for displayng errors in a BooleanExpEditorWindow.
 * @author Nikolai
 */
public class ErrorWindow {
    private final JTextPane textPane;
    private ArrayList<CodeError> errors;
    private String errorString;
    /**
     * Constructor
     * @param textPane JTextPane for ErrorWindow
     * @param stringLoaderInterface stringLoaderInterface to load needed Strings
     */
    ErrorWindow(JTextPane textPane, StringLoaderInterface stringLoaderInterface) {
        this.textPane = textPane;
        updateStringRes(stringLoaderInterface);
    }

    /**
     * Method to display ArrayList of Errors in the ErrorWindow.
     * @param errors ArrayList of Errors
     */
    void displayErrors(ArrayList<String> errors) {
        String errorsAsString = errorString + ": " + errors.size() + "\n";
        for (int i = 0; i < errors.size(); i++) {
            errorsAsString += i+1 + ": " + errors.get(i) + "\n";
        }
        textPane.setText(errorsAsString);
    }

    /**
     * Update the language dependent displayed Strings in this class.
     * @param stringLoaderInterface the new stringLoaderInterface
     */
    public void updateStringRes(StringLoaderInterface stringLoaderInterface) {
        errorString = stringLoaderInterface.getBooleanExpEditorStringResProvider().
                getBooleanExpErrorStringRes().getStringFromID("error");
    }
}
