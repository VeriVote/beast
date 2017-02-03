package edu.pse.beast.celectiondescriptioneditor;

import edu.pse.beast.codearea.ErrorHandling.ErrorDisplayer;
import edu.pse.beast.stringresource.StringLoaderInterface;

import javax.swing.*;
import java.util.ArrayList;

/**
 * @author NikolaiLMS
 */
public class ErrorWindow extends ErrorDisplayer {
    private final JTextPane textPane;
    private ArrayList<Error> errors;
    private String errorString;

    /**
     * Constructor
     * @param textPane JTextPane for ErrorWindow
     * @param stringLoaderInterface stringLoaderInterface to load needed Strings
     */
    ErrorWindow(JTextPane textPane, StringLoaderInterface stringLoaderInterface) {
        super(textPane, stringLoaderInterface.getCElectionEditorStringResProvider().getCErrorStringRes());
        this.textPane = textPane;
    }

    /**
     * Method to display ArrayList of Errors in the ErrorWindow.
     * @param errors ArrayList of Errors
     */
    void displayErrors(ArrayList<Error> errors) {
        this.errors = errors;
        String errorsAsString = errorString + ": " + errors.size() + "\n";
        for (Error error : this.errors) {
            errorsAsString += error + "\n";
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
