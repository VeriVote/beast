package edu.pse.beast.booleanexpeditor.view;

import java.util.ArrayList;

import javax.swing.JTextPane;

import edu.pse.beast.booleanexpeditor.booleanexpcodearea.errorfinder.BooleanExpErrorDisplayer;
import edu.pse.beast.codearea.errorhandling.CodeError;
import edu.pse.beast.stringresource.StringLoaderInterface;

/**
 * Controller of the JTextPane for displaying errors in a
 * BooleanExpEditorWindow.
 *
 * @author Nikolai Schnell
 */
public class ErrorWindow {
    /** The Constant BLANK. */
    private static final String BLANK = " ";
    /** The Constant PARENR_STRING. */
    private static final String PARENR = ")";
    /** The Constant COMMA_STRING. */
    private static final String COMMA = ",";
    /** The Constant PARENL_STRING. */
    private static final String PARENL = "(";
    /** The Constant LINE_BREAK. */
    private static final String LINE_BREAK = "\n";
    /** The Constant COLON_STRING. */
    private static final String COLON = ":" + BLANK;

    /** The Constant ERROR. */
    private static final String ERROR = "error";

    /** The Constant LINE. */
    private static final String LINE = "line";

    /** The Constant PRECONDITIONS. */
    private static final String PRECONDITIONS = "preConditions";

    /** The Constant POSTCONDITIONS. */
    private static final String POSTCONDITIONS = "postConditions";

    /** The text pane. */
    private final JTextPane textPane;

    /** The error string. */
    private String errorString;

    /** The line string. */
    private String lineString;

    /** The pre conditions string. */
    private String preConditionsString;

    /** The post conditions string. */
    private String postConditionsString;

    /**
     * Constructor.
     *
     * @param pane
     *            JTextPane for ErrorWindow
     * @param stringLoaderInterface
     *            Interface to load needed Strings
     */
    public ErrorWindow(final JTextPane pane,
                       final StringLoaderInterface stringLoaderInterface) {
        this.textPane = pane;
        updateStringRes(stringLoaderInterface);
    }

    /**
     * Method to display ArrayList of Errors in the ErrorWindow.
     *
     * @param preConditionErrors
     *            An {@link ArrayList} instance with all the CodeErrors from
     *            the preCondition text pane
     * @param postConditionErrors
     *            An {@link ArrayList} instance with all the CodeErrors from
     *            the postCondition text pane
     * @param booleanExpErrorDisplayer
     *            the ErrorDisplayer that creates the messages given CodeError
     *            objects
     */
    public void displayErrors(final ArrayList<CodeError> preConditionErrors,
                              final ArrayList<CodeError> postConditionErrors,
                              final BooleanExpErrorDisplayer booleanExpErrorDisplayer) {
        final int numberOfErrors =
                postConditionErrors.size() + preConditionErrors.size();
        String errorsAsString =
                errorString + COLON + BLANK + numberOfErrors + LINE_BREAK;

        for (int i = 0; i < preConditionErrors.size(); i++) {
            errorsAsString += i + 1 + COLON + BLANK
                    + booleanExpErrorDisplayer
                            .createMsg(preConditionErrors.get(i))
                    + BLANK + PARENL + lineString + BLANK
                    + preConditionErrors.get(i).getLine() + COMMA
                    + BLANK + preConditionsString + PARENR + LINE_BREAK;
        }
        for (int i = 0; i < postConditionErrors.size(); i++) {
            errorsAsString += i + 1 + COLON + BLANK
                    + booleanExpErrorDisplayer
                            .createMsg(postConditionErrors.get(i))
                    + BLANK + PARENL + lineString + BLANK
                    + postConditionErrors.get(i).getLine() + COMMA
                    + BLANK  + postConditionsString + PARENR + LINE_BREAK;
        }
        textPane.setText(errorsAsString);
    }

    /**
     * Update the language dependent displayed Strings in this class.
     *
     * @param stringLoaderInterface
     *            the new stringLoaderInterface
     */
    public void updateStringRes(final StringLoaderInterface stringLoaderInterface) {
        errorString = stringLoaderInterface
                .getBooleanExpEditorStringResProvider()
                .getBooleanExpErrorStringRes().getStringFromID(ERROR);
        lineString = stringLoaderInterface
                .getBooleanExpEditorStringResProvider()
                .getBooleanExpErrorStringRes().getStringFromID(LINE);
        preConditionsString = stringLoaderInterface
                .getBooleanExpEditorStringResProvider()
                .getBooleanExpErrorStringRes().getStringFromID(PRECONDITIONS);
        postConditionsString = stringLoaderInterface
                .getBooleanExpEditorStringResProvider()
                .getBooleanExpErrorStringRes().getStringFromID(POSTCONDITIONS);
    }
}
