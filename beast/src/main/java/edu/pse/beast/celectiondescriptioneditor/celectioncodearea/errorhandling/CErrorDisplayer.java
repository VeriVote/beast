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
        if (er.getId().equals("antlr")) {
            int line = er.getLine();
            int start = JTextPaneToolbox.getLineBeginning(getJTextPane(), line - 2);
            int end = JTextPaneToolbox.getClosestLineBeginningAfter(getJTextPane(), start);
            er.setStartPos(start);
            er.setEndPos(end);
            return er.getExtraInfo("msg");
        } else if (er.getId().equals("compilererror")) {
            int line = er.getLine();
            int start = JTextPaneToolbox.getLineBeginning(getJTextPane(), line - 2);
            int end = JTextPaneToolbox.getClosestLineBeginningAfter(getJTextPane(), start);
            er.setStartPos(start);
            er.setEndPos(end);
            String msg = er.getExtraInfo("msg");
            String var = er.getExtraInfo("var");
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
