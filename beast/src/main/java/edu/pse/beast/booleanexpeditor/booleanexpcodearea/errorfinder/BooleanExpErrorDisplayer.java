package edu.pse.beast.booleanexpeditor.booleanexpcodearea.errorfinder;

import java.util.ArrayList;

import javax.swing.JTextPane;

import edu.pse.beast.codearea.errorhandling.CodeError;
import edu.pse.beast.codearea.errorhandling.ErrorDisplayer;
import edu.pse.beast.stringresource.StringLoaderInterface;

/**
 * Class for displaying CodeErrors found in the BooleanExpEditor dynamically,
 * triggered by mouse hovering.
 *
 * @author Holger Klein
 */
public final class BooleanExpErrorDisplayer extends ErrorDisplayer {
    /**
     * Constructor.
     *
     * @param pane
     *            the JTextPane to display the errors in
     * @param stringResIF
     *            the String
     */
    public BooleanExpErrorDisplayer(final JTextPane pane,
                                    final StringLoaderInterface stringResIF) {
        super(pane,
              stringResIF.getBooleanExpEditorStringResProvider()
                  .getBooleanExpErrorStringRes());
    }

    @Override
    public void showErrors(final ArrayList<CodeError> errors) {
        super.showErrors(errors);
        for (CodeError er : errors) {
            showError(er, createMsg(er));
        }
    }

    /**
     * Creates meaningful message in form of String based on a given CodeError
     * object.
     *
     * @param codeError
     *            the CodeError object
     * @return the message
     */
    public String createMsg(final CodeError codeError) {
        String template;
        if (codeError.getId().equals("antlr")) {
            template = getTemplateString("antlr") + ": " + codeError.getExtraInfo("msg");
        } else if (codeError.getId().equals("var_not_decl")) {
            template = getTemplateString("var_not_decl");
            template = template.replace("VAR", codeError.getExtraInfo("var_name"));
        } else if (codeError.getId().equals("too_many_vars_passed")) {
            template = getTemplateString("too_many_vars_passed");
            template = template.replace("VAR", codeError.getExtraInfo("var_name"));
        } else if (codeError.getId().equals("wrong_var_type_passed")) {
            template = getTemplateString("wrong_var_type_passed");
            template = template.replace("VAR", codeError.getExtraInfo("var_name"));
            template = template.replace("EXPECTEDTYPE", codeError.getExtraInfo("passed_type"));
            template = template.replace("GIVENTYPE", codeError.getExtraInfo("expected_type"));
        } else if (codeError.getId().equals("incomparable_types")) {
            template = getTemplateString("incomparable_types");
            template = template.replace("LHSVAR", codeError.getExtraInfo("lhs_type"));
            template = template.replace("RHSVAR", codeError.getExtraInfo("rhs_type"));
        } else if (codeError.getId().equals("incomparable_list_sizes")) {
            template = getTemplateString("incomparable_list_sizes");
            template = template.replace("LHSDEPTH", codeError.getExtraInfo("lhs_list_size"));
            template = template.replace("RHSDEPTH", codeError.getExtraInfo("rhs_list_size"));
        } else if (codeError.getId().equals("wrong_var_passed_to_votesum")) {
            template = getTemplateString("wrong_var_passed_to_votesum");
            template = template.replace("GIVENTYPE", codeError.getExtraInfo("var_type"));
        } else if (codeError.getId().equals("wrong_var_passed_to_votesum_unique")) {
            template = getTemplateString("wrong_var_passed_to_votesum_unique");
            template = template.replace("GIVENTYPE", codeError.getExtraInfo("var_type"));
        } else if (codeError.getId().equals("number_must_be_greater_0")) {
            template = getTemplateString("number_must_be_greater_0");
        } else {
            template = "";
        }
        return template;
    }

    /**
     * Gets the template string.
     *
     * @param id
     *            the id
     * @return the template string
     */
    private String getTemplateString(final String id) {
        return getStringResourceLoader().getStringFromID(id + "_descr");
    }

    @Override
    public void updateStringRes(final StringLoaderInterface stringResIF) {
        setStringResourceLoader(stringResIF.getBooleanExpEditorStringResProvider()
                                .getBooleanExpErrorStringRes());
    }
}
