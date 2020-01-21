package edu.pse.beast.booleanexpeditor.booleanexpcodearea.errorfinder;

import static edu.pse.beast.booleanexpeditor.booleanexpcodearea.errorfinder.BooleanExpErrorFactory.ANTLR;
import static edu.pse.beast.booleanexpeditor.booleanexpcodearea.errorfinder.BooleanExpErrorFactory.EXPECTED_TYPE;
import static edu.pse.beast.booleanexpeditor.booleanexpcodearea.errorfinder.BooleanExpErrorFactory.INCOMPARABLE_LIST_SIZES;
import static edu.pse.beast.booleanexpeditor.booleanexpcodearea.errorfinder.BooleanExpErrorFactory.INCOMPARABLE_TYPES;
import static edu.pse.beast.booleanexpeditor.booleanexpcodearea.errorfinder.BooleanExpErrorFactory.LHS_LIST_SIZE;
import static edu.pse.beast.booleanexpeditor.booleanexpcodearea.errorfinder.BooleanExpErrorFactory.LHS_TYPE;
import static edu.pse.beast.booleanexpeditor.booleanexpcodearea.errorfinder.BooleanExpErrorFactory.MSG;
import static edu.pse.beast.booleanexpeditor.booleanexpcodearea.errorfinder.BooleanExpErrorFactory.NUMBER_MUST_BE_GREATER_ZERO;
import static edu.pse.beast.booleanexpeditor.booleanexpcodearea.errorfinder.BooleanExpErrorFactory.PASSED_TYPE;
import static edu.pse.beast.booleanexpeditor.booleanexpcodearea.errorfinder.BooleanExpErrorFactory.RHS_LIST_SIZE;
import static edu.pse.beast.booleanexpeditor.booleanexpcodearea.errorfinder.BooleanExpErrorFactory.RHS_TYPE;
import static edu.pse.beast.booleanexpeditor.booleanexpcodearea.errorfinder.BooleanExpErrorFactory.TOO_MANY_VARS_PASSED;
import static edu.pse.beast.booleanexpeditor.booleanexpcodearea.errorfinder.BooleanExpErrorFactory.VAR_NAME;
import static edu.pse.beast.booleanexpeditor.booleanexpcodearea.errorfinder.BooleanExpErrorFactory.VAR_NOT_DECL;
import static edu.pse.beast.booleanexpeditor.booleanexpcodearea.errorfinder.BooleanExpErrorFactory.VAR_TYPE;
import static edu.pse.beast.booleanexpeditor.booleanexpcodearea.errorfinder.BooleanExpErrorFactory.WRONG_VAR_PASSED_TO_VOTESUM;
import static edu.pse.beast.booleanexpeditor.booleanexpcodearea.errorfinder.BooleanExpErrorFactory.WRONG_VAR_PASSED_TO_VOTESUM_UNIQUE;
import static edu.pse.beast.booleanexpeditor.booleanexpcodearea.errorfinder.BooleanExpErrorFactory.WRONG_VAR_TYPE_PASSED;

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
    /** The Constant VAR. */
    private static final String VAR = "VAR";

    /** The Constant RHS_DEPTH_STRING. */
    private static final String RHS_DEPTH_STRING = "RHSDEPTH";
    /** The Constant LHS_DEPTH_STRING. */
    private static final String LHS_DEPTH_STRING = "LHSDEPTH";
    /** The Constant RHS_VAR_STRING. */
    private static final String RHS_VAR_STRING = "RHSVAR";
    /** The Constant LHS_VAR_STRING. */
    private static final String LHS_VAR_STRING = "LHSVAR";
    /** The Constant GIVEN_TYPE_STRING. */
    private static final String GIVEN_TYPE_STRING = "GIVENTYPE";
    /** The Constant EXPECTED_TYPE_STRING. */
    private static final String EXPECTED_TYPE_STRING = "EXPECTEDTYPE";

    /** The Constant DESCR. */
    private static final String DESCR = "_descr";

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
        switch(codeError.getId()) {
        case ANTLR:
            template = getTemplateString(ANTLR) + ": "
                        + codeError.getExtraInfo(MSG);
            break;
        case VAR_NOT_DECL:
            template = getTemplateString(VAR_NOT_DECL);
            template = template.replace(VAR, codeError.getExtraInfo(VAR_NAME));
            break;
        case TOO_MANY_VARS_PASSED:
            template = getTemplateString(TOO_MANY_VARS_PASSED);
            template = template.replace(VAR, codeError.getExtraInfo(VAR_NAME));
            break;
        case WRONG_VAR_TYPE_PASSED:
            template = getTemplateString(WRONG_VAR_TYPE_PASSED);
            template = template.replace(VAR, codeError.getExtraInfo(VAR_NAME));
            template = template.replace(EXPECTED_TYPE_STRING,
                                        codeError.getExtraInfo(PASSED_TYPE));
            template = template.replace(GIVEN_TYPE_STRING,
                                        codeError.getExtraInfo(EXPECTED_TYPE));
            break;
        case INCOMPARABLE_TYPES:
            template = getTemplateString(INCOMPARABLE_TYPES);
            template = template.replace(LHS_VAR_STRING,
                                        codeError.getExtraInfo(LHS_TYPE));
            template = template.replace(RHS_VAR_STRING,
                                        codeError.getExtraInfo(RHS_TYPE));
            break;
        case INCOMPARABLE_LIST_SIZES:
            template = getTemplateString(INCOMPARABLE_LIST_SIZES);
            template = template.replace(LHS_DEPTH_STRING,
                                        codeError.getExtraInfo(LHS_LIST_SIZE));
            template = template.replace(RHS_DEPTH_STRING,
                                        codeError.getExtraInfo(RHS_LIST_SIZE));
            break;
        case WRONG_VAR_PASSED_TO_VOTESUM:
            template = getTemplateString(WRONG_VAR_PASSED_TO_VOTESUM);
            template = template.replace(GIVEN_TYPE_STRING,
                                        codeError.getExtraInfo(VAR_TYPE));
            break;
        case WRONG_VAR_PASSED_TO_VOTESUM_UNIQUE:
            template = getTemplateString(WRONG_VAR_PASSED_TO_VOTESUM_UNIQUE);
            template = template.replace(GIVEN_TYPE_STRING,
                                        codeError.getExtraInfo(VAR_TYPE));
            break;
        case NUMBER_MUST_BE_GREATER_ZERO:
            template = getTemplateString(NUMBER_MUST_BE_GREATER_ZERO);
            break;
        default:
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
        return getStringResourceLoader().getStringFromID(id + DESCR);
    }

    @Override
    public void updateStringRes(final StringLoaderInterface stringResIF) {
        setStringResourceLoader(stringResIF.getBooleanExpEditorStringResProvider()
                                .getBooleanExpErrorStringRes());
    }
}
