package edu.pse.beast.celectiondescriptioneditor.View;

import edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.ErrorHandling.CErrorDisplayer;
import edu.pse.beast.codearea.ErrorHandling.CodeError;
import edu.pse.beast.stringresource.StringLoaderInterface;

import javax.swing.*;
import java.util.ArrayList;

/**
 * @author NikolaiLMS
 */
public class ErrorWindow {
    private final JTextPane textPane;
    private ArrayList<Error> errors;
    private String errorString;
    private String lineString;

    /**
     * Constructor
     * @param textPane JTextPane for ErrorWindow
     * @param stringLoaderInterface stringLoaderInterface to load needed Strings
     */
    public ErrorWindow(JTextPane textPane, StringLoaderInterface stringLoaderInterface) {
        updateStringRes(stringLoaderInterface);
        this.textPane = textPane;
    }

    /**
     * Method to display ArrayList of Errors in the ErrorWindow.
     * @param errors ArrayList of Errors
     */
    public void displayErrors(ArrayList<CodeError> errors, CErrorDisplayer cErrorDisplayer) {
        String errorsAsString = errorString + ": " + errors.size() + "\n";
        for (int i = 0; i < errors.size(); i++) {
            errorsAsString += i+1 + ": " + cErrorDisplayer.createMsg(errors.get(i)) + " (" + lineString +
                    (errors.get(i).getLine() - 1) + ")" + "\n";
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
        lineString = stringLoaderInterface.getBooleanExpEditorStringResProvider().
                getBooleanExpErrorStringRes().getStringFromID("line");
    }
}
