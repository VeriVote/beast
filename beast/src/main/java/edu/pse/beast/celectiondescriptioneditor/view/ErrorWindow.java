package edu.pse.beast.celectiondescriptioneditor.view;

import java.util.ArrayList;

import javax.swing.JTextPane;

import edu.pse.beast.celectiondescriptioneditor.celectioncodearea.errorhandling.CErrorDisplayer;
import edu.pse.beast.codearea.errorhandling.CodeError;
import edu.pse.beast.stringresource.StringLoaderInterface;

/**
 * @author Nikolai Schnell
 */
public class ErrorWindow {
    private final JTextPane textPane;
    private String errorString;
    private String lineString;

    /**
     * Constructor
     *
     * @param textPane              JTextPane for ErrorWindow
     * @param stringLoaderInterface stringLoaderInterface to load needed Strings
     */
    public ErrorWindow(JTextPane textPane,
                       StringLoaderInterface stringLoaderInterface) {
        updateStringRes(stringLoaderInterface);
        this.textPane = textPane;
    }

    /**
     * Method to display ArrayList of Errors in the ErrorWindow.
     *
     * @param errors          ArrayList of Errors
     * @param cErrorDisplayer Error displayer
     */
    public void displayErrors(ArrayList<CodeError> errors, CErrorDisplayer cErrorDisplayer) {
        String errorsAsString = errorString + ": " + errors.size() + "\n";
        for (int i = 0; i < errors.size(); i++) {
            errorsAsString +=
                (i + 1) + ": " + cErrorDisplayer.createMsg(errors.get(i))
                + " (" + lineString + " " + (errors.get(i).getLine() - 1) + ")"
                + "\n";
        }
        textPane.setText(errorsAsString);
    }

    /**
     * Update the language dependent displayed Strings in this class.
     *
     * @param stringLoaderInterface the new stringLoaderInterface
     */
    public void updateStringRes(StringLoaderInterface stringLoaderInterface) {
        errorString
            = stringLoaderInterface.getBooleanExpEditorStringResProvider()
              .getBooleanExpErrorStringRes().getStringFromID("error");
        lineString
            = stringLoaderInterface.getBooleanExpEditorStringResProvider()
              .getBooleanExpErrorStringRes().getStringFromID("line");
    }
}