package edu.pse.beast.celectiondescriptioneditor.celectioncodearea.errorhandling;

import java.util.ArrayList;

import javax.swing.JTextPane;

import edu.pse.beast.codearea.codeinput.JTextPaneToolbox;
import edu.pse.beast.codearea.errorhandling.CodeError;
import edu.pse.beast.codearea.errorhandling.ErrorDisplayer;
import edu.pse.beast.stringresource.StringLoaderInterface;

/**
 * This class prepares CodeErrors so its parent class error displayer can
 * display them. As such, it mainly creates error messages, depending on the
 * code errors id
 *
 * @author Holger Klein
 */
public final class CErrorDisplayer extends ErrorDisplayer {
    /** The Constant ANTLR. */
    private static final String ANTLR = "antlr";
    /** The Constant COMPILER_ERROR. */
    private static final String COMPILER_ERROR = "compilererror";
    /** The Constant MSG. */
    private static final String MSG = "msg";
    /** The Constant VAR. */
    private static final String VAR = "var";

    /**
     * Constructor.
     *
     * @param pane
     *            the pane the errors get shown in
     * @param stringResIF
     *            the string resource interface
     */
    public CErrorDisplayer(final JTextPane pane,
                           final StringLoaderInterface stringResIF) {
        super(pane, stringResIF.getCElectionEditorStringResProvider().getCErrorStringRes());
    }

    @Override
    public void showErrors(final ArrayList<CodeError> errors) {
        super.showErrors(errors);
        for (CodeError er : errors) {
            showError(er, createMsg(er));
        }
    }

    /**
     * Creates a message to the user detailing an error.
     *
     * @param er
     *            the code Error to be turned into a readable form
     * @return the code error, formatted to a string
     */
    public String createMsg(final CodeError er) {
        if (er.getId().equals(ANTLR)) {
            final int line = er.getLine();
            final int start = JTextPaneToolbox.getLineBeginning(getJTextPane(), line - 2);
            final int end = JTextPaneToolbox.getClosestLineBeginningAfter(getJTextPane(), start);
            er.setStartPos(start);
            er.setEndPos(end);
            return er.getExtraInfo(MSG);
        } else if (er.getId().equals(COMPILER_ERROR)) {
            final int line = er.getLine();
            final int start = JTextPaneToolbox.getLineBeginning(getJTextPane(), line - 2);
            final int end = JTextPaneToolbox.getClosestLineBeginningAfter(getJTextPane(), start);
            er.setStartPos(start);
            er.setEndPos(end);
            String msg = er.getExtraInfo(MSG);
            final String var = er.getExtraInfo(VAR);
            if (!msg.contains(var)) {
                msg = var + ": " + msg;
            }
            return msg;
        }
        return "";
    }

    @Override
    public void updateStringRes(final StringLoaderInterface stringResIF) {
        setStringResourceLoader(
                stringResIF.getCElectionEditorStringResProvider().getCErrorStringRes()
        );
    }
}
