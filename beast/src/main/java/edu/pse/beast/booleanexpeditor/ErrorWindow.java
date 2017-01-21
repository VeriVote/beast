package edu.pse.beast.booleanexpeditor;

import edu.pse.beast.highlevel.DisplaysStringsToUser;
import edu.pse.beast.stringresource.StringLoaderInterface;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Controller of the JTextPane for displayng errors in a BooleanExpEditorWindow.
 * @author Nikolai
 */
public class ErrorWindow implements DisplaysStringsToUser{
    private StringLoaderInterface stringLoaderInterface;
    private JTextPane textPane;
    private ArrayList<Error> errors;
    /**
     * Constructor
     * @param textPane JTextPane for ErrorWindow
     * @param stringLoaderInterface stringLoaderInterface to load needed Strings
     */
    public ErrorWindow(JTextPane textPane, StringLoaderInterface stringLoaderInterface) {
        this.stringLoaderInterface = stringLoaderInterface;
        this.textPane = textPane;
    }

    /**
     * Method to display ArrayList of Errors in the ErrorWindow.
     * @param errors ArrayList of Errors
     */
    public void displayErrors(ArrayList<Error> errors) {
        //TODO implement
    }

    /**
     * Update the language dependent displayed Strings in this class.
     * @param stringLoaderInterface the new stringLoaderInterface
     */
    public void updateStringRes(StringLoaderInterface stringLoaderInterface) {
    }
}
