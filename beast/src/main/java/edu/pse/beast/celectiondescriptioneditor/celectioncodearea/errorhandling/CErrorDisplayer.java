package edu.pse.beast.celectiondescriptioneditor.celectioncodearea.errorhandling;

import java.util.ArrayList;

import javax.swing.JTextPane;

import edu.pse.beast.codearea.codeinput.JTextPaneToolbox;
import edu.pse.beast.codearea.errorhandling.CodeError;
import edu.pse.beast.codearea.errorhandling.ErrorDisplayer;
import edu.pse.beast.stringresource.StringLoaderInterface;

/**
 * This class prepares CodeErrors so its parent class error displayer can display
 * them. As such, it mainly creates error messages, depending on the code errors
 * id
 *
 * @author Holger Klein
 */
public class CErrorDisplayer extends ErrorDisplayer {
    /**
     * constructor
     *
     * @param pane        the pane the errors get shown in
     * @param stringResIF the string resource interface
     */
    public CErrorDisplayer(JTextPane pane, StringLoaderInterface stringResIF) {
        super(pane, stringResIF.getCElectionEditorStringResProvider().getCErrorStringRes());
    }

    @Override
    public void showErrors(ArrayList<CodeError> errors) {
        super.showErrors(errors);
        for (CodeError er : errors) {
            showError(er, createMsg(er));
        }
    }

    /**
     * creates a message to the user detailing an error
     *
     * @param er the code Error to be turned into a readable form
     * @return the code error, formatted to a string
     */
    public String createMsg(CodeError er) {
        if (er.getId().equals("antlr")) {
            int line = er.getLine();
            int start = JTextPaneToolbox.getLineBeginning(pane, line - 2);
            int end = JTextPaneToolbox.getClosestLineBeginningAfter(pane, start);
            er.setStartPos(start);
            er.setEndPos(end);
            return er.getExtraInfo("msg");
        } else if (er.getId().equals("compilererror")) {
            int line = er.getLine();
            int start = JTextPaneToolbox.getLineBeginning(pane, line - 2);
            int end = JTextPaneToolbox.getClosestLineBeginningAfter(pane, start);
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
    public void updateStringRes(StringLoaderInterface stringResIF) {
        this.currentStringResLoader
              = stringResIF.getCElectionEditorStringResProvider().getCErrorStringRes();
    }
}