package edu.pse.beast.booleanexpeditor.view;

import java.util.ArrayList;

import javax.swing.JTextPane;

import edu.pse.beast.booleanexpeditor.booleanexpcodearea.errorfinder.BooleanExpErrorDisplayer;
import edu.pse.beast.codearea.errorhandling.CodeError;
import edu.pse.beast.stringresource.StringLoaderInterface;

/**
 * Controller of the JTextPane for displaying errors in a BooleanExpEditorWindow
 *
 * @author Nikolai Schnell
 */
public class ErrorWindow {
    private static final String ERROR = "error";
    private static final String LINE = "line";
    private static final String PRECONDITIONS = "preConditions";
    private static final String POSTCONDITIONS = "postConditions";

    private final JTextPane textPane;
    private String errorString;
    private String lineString;
    private String preConditionsString;
    private String postConditionsString;

    /**
     * Constructor
     *
     * @param textPane              JTextPane for ErrorWindow
     * @param stringLoaderInterface stringLoaderInterface to load needed Strings
     */
    public ErrorWindow(JTextPane textPane, StringLoaderInterface stringLoaderInterface) {
        this.textPane = textPane;
        updateStringRes(stringLoaderInterface);
    }

    /**
     * Method to display ArrayList of Errors in the ErrorWindow.
     *
     * @param preConditionErrors       An ArrayList<CodeError> instance with all the
     *                                 CodeErrors from the preCondition text pane
     * @param postConditionErrors      An ArrayList<CodeError> instance with all the
     *                                 CodeErrors from the postCondition text pane
     * @param booleanExpErrorDisplayer the ErrorDisplayer that creates the messages
     *                                 given CodeError objects
     */
    public void displayErrors(ArrayList<CodeError> preConditionErrors,
                              ArrayList<CodeError> postConditionErrors,
                              BooleanExpErrorDisplayer booleanExpErrorDisplayer) {
        int numberOfErrors = postConditionErrors.size() + preConditionErrors.size();
        String errorsAsString = errorString + ": " + numberOfErrors + "\n";

        for (int i = 0; i < preConditionErrors.size(); i++) {
            errorsAsString +=
                i + 1 + ": "
                + booleanExpErrorDisplayer.createMsg(preConditionErrors.get(i))
                + " (" + lineString + " " + preConditionErrors.get(i).getLine()
                + ", " + preConditionsString + ")" + "\n";
        }

        for (int i = 0; i < postConditionErrors.size(); i++) {
            errorsAsString +=
                i + 1 + ": "
                + booleanExpErrorDisplayer.createMsg(postConditionErrors.get(i))
                + " (" + lineString + " " + postConditionErrors.get(i).getLine()
                + ", " + postConditionsString + ")" + "\n";
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
                .getBooleanExpErrorStringRes().getStringFromID(ERROR);
        lineString
              = stringLoaderInterface.getBooleanExpEditorStringResProvider()
                .getBooleanExpErrorStringRes().getStringFromID(LINE);
        preConditionsString
              = stringLoaderInterface.getBooleanExpEditorStringResProvider()
                .getBooleanExpErrorStringRes().getStringFromID(PRECONDITIONS);
        postConditionsString
              = stringLoaderInterface.getBooleanExpEditorStringResProvider()
                .getBooleanExpErrorStringRes().getStringFromID(POSTCONDITIONS);
    }
}