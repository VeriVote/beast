package edu.pse.beast.booleanexpeditor;

import edu.pse.beast.highlevel.DisplaysStringsToUser;
import edu.pse.beast.stringresource.StringLoaderInterface;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Controller of the JTextPane for displayng errors in a BooleanExpEditorWindow.
 * @author Nikolai
 */
public class ErrorWindow implements DisplaysStringsToUser{
    private StringLoaderInterface stringLoaderInterface;
    private JTextPane textPane;
    private ArrayList<Error> errors;
    private String errorString;
    /**
     * Constructor
     * @param textPane JTextPane for ErrorWindow
     * @param stringLoaderInterface stringLoaderInterface to load needed Strings
     */
    ErrorWindow(JTextPane textPane, StringLoaderInterface stringLoaderInterface) {
        this.stringLoaderInterface = stringLoaderInterface;
        this.textPane = textPane;
        errorString = stringLoaderInterface.getBooleanExpEditorStringResProvider().
                getBooleanExpErrorStringRes().getStringFromID("error");
    }

    /**
     * Method to display ArrayList of Errors in the ErrorWindow.
     * @param errors ArrayList of Errors
     */
    void displayErrors(ArrayList<Error> errors) {
        String errorString = "";
        for (Error error : errors) {
            errorString += error.getMessage() + "\n";
        }
        textPane.setText(errorString);
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
