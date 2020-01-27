package edu.pse.beast.booleanexpeditor.view;

import static edu.pse.beast.toolbox.CCodeHelper.colon;
import static edu.pse.beast.toolbox.CCodeHelper.comma;
import static edu.pse.beast.toolbox.CCodeHelper.lineBreak;
import static edu.pse.beast.toolbox.CCodeHelper.parenthesize;

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
                lineBreak(colon(errorString, Integer.toString(numberOfErrors)));

        for (int i = 0; i < preConditionErrors.size(); i++) {
            errorsAsString +=
                    lineBreak(colon(Integer.toString(i + 1),
                                    booleanExpErrorDisplayer
                                        .createMsg(preConditionErrors.get(i))) + BLANK
                            + parenthesize(lineString + BLANK
                                            + comma(preConditionErrors.get(i).getLine())
                                            + preConditionsString));
        }
        for (int i = 0; i < postConditionErrors.size(); i++) {
            errorsAsString +=
                    lineBreak(colon(Integer.toString(i + 1),
                                    booleanExpErrorDisplayer
                                        .createMsg(postConditionErrors.get(i))) + BLANK
                            + parenthesize(lineString + BLANK
                                            + comma(postConditionErrors.get(i).getLine())
                                            + postConditionsString));
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
