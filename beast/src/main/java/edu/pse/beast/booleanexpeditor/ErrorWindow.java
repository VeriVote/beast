package edu.pse.beast.booleanexpeditor;

import edu.pse.beast.stringresource.StringLoaderInterface;

import javax.swing.*;
import java.util.ArrayList;

/**
 * @author Nikolai
 */
public class ErrorWindow {
    private StringLoaderInterface stringLoaderInterface;
    private JTextPane textPane;

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
}
