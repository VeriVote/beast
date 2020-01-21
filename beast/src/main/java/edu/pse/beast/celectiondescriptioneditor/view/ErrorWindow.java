package edu.pse.beast.celectiondescriptioneditor.view;

import java.util.ArrayList;

import javax.swing.JTextPane;

import edu.pse.beast.celectiondescriptioneditor.celectioncodearea.errorhandling.CErrorDisplayer;
import edu.pse.beast.codearea.errorhandling.CodeError;
import edu.pse.beast.stringresource.StringLoaderInterface;

/**
 * The Class ErrorWindow.
 *
 * @author Nikolai Schnell
 */
public class ErrorWindow {
    /** The Constant BLANK. */
    private static final String BLANK = " ";
    /** The Constant LINE_BREAK. */
    private static final String LINE_BREAK = "\n";
    /** The Constant COLON. */
    private static final String COLON = ":";

    /** The Constant OPENING_PARENTHESES. */
    private static final String OPENING_PARENTHESES = "(";
    /** The Constant CLOSING_PARENTHESES. */
    private static final String CLOSING_PARENTHESES = ")";

    /** The Constant ERROR_ID. */
    private static final String ERROR_ID = "error";
    /** The Constant LINE_ID. */
    private static final String LINE_ID = "line";

    /** The text pane. */
    private final JTextPane textPane;

    /** The error string. */
    private String errorString;

    /** The line string. */
    private String lineString;

    /**
     * Constructor.
     *
     * @param pane
     *            JTextPane for ErrorWindow
     * @param stringLoaderInterface
     *            stringLoaderInterface to load needed Strings
     */
    public ErrorWindow(final JTextPane pane,
                       final StringLoaderInterface stringLoaderInterface) {
        updateStringRes(stringLoaderInterface);
        this.textPane = pane;
    }

    /**
     * Method to display ArrayList of Errors in the ErrorWindow.
     *
     * @param errors
     *            ArrayList of Errors
     * @param cErrorDisplayer
     *            Error displayer
     */
    public void displayErrors(final ArrayList<CodeError> errors,
                              final CErrorDisplayer cErrorDisplayer) {
        String errorsAsString =
                errorString + COLON + BLANK + errors.size() + LINE_BREAK;
        for (int i = 0; i < errors.size(); i++) {
            errorsAsString += (i + 1) + COLON + BLANK
                    + cErrorDisplayer.createMsg(errors.get(i)) + BLANK
                    + OPENING_PARENTHESES + lineString + BLANK
                    + (errors.get(i).getLine() - 1)
                    + CLOSING_PARENTHESES + LINE_BREAK;
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
                .getBooleanExpErrorStringRes().getStringFromID(ERROR_ID);
        lineString = stringLoaderInterface
                .getBooleanExpEditorStringResProvider()
                .getBooleanExpErrorStringRes().getStringFromID(LINE_ID);
    }
}
