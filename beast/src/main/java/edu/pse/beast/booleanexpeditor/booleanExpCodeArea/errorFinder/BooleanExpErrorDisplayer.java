/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.booleanexpeditor.booleanExpCodeArea.errorFinder;

import edu.pse.beast.codearea.ErrorHandling.CodeError;
import edu.pse.beast.codearea.ErrorHandling.ErrorDisplayer;
import edu.pse.beast.stringresource.StringLoaderInterface;
import java.util.ArrayList;
import javax.swing.JTextPane;

/**
 * Class for displaying CodeErrors found in the BooleanExpEditor dynamically, triggered by mouse hovering.
 * @author Holger-Desktop
 */
public class BooleanExpErrorDisplayer extends ErrorDisplayer {

    /**
     * Constructor
     * @param pane the JTextPane to display the errors in
     * @param stringResIF the String
     */
    public BooleanExpErrorDisplayer(
            JTextPane pane,
            StringLoaderInterface stringResIF) {
        super(pane, stringResIF.getBooleanExpEditorStringResProvider().getBooleanExpErrorStringRes());
    }

    @Override
    public void showErrors(ArrayList<CodeError> errors) {
        super.showErrors(errors);
        for (CodeError er : errors) {
            showError(er, createMsg(er));
        }
    }

    /**
     * Creates meaningful message in form of String based on a given CodeError object.
     * @param codeError the CodeError object
     * @return the message
     */
    public String createMsg(CodeError codeError) {
        if (codeError.getId().equals("antlr")) {
            String template = getTemplateString("antlr");
            return template += ": " + codeError.getExtraInfo("msg");
        } else if (codeError.getId().equals("var_not_decl")) {
            String template = getTemplateString("var_not_decl");
            return template.replace("VAR", codeError.getExtraInfo("var_name"));
        } else if (codeError.getId().equals("too_many_vars_passed")) {
            String template = getTemplateString("too_many_vars_passed");
            template = template.replace("VAR", codeError.getExtraInfo("var_name"));
            return template;
        } else if (codeError.getId().equals("wrong_var_type_passed")) {
            String template = getTemplateString("wrong_var_type_passed");
            template = template.replace("VAR", codeError.getExtraInfo("var_name"));
            template = template.replace("EXPECTEDTYPE", codeError.getExtraInfo("passed_type"));
            template = template.replace("GIVENTYPE", codeError.getExtraInfo("expected_type"));
            return template;
        } else if (codeError.getId().equals("incomparable_types")) {
            String template = getTemplateString("incomparable_types");
            template = template.replace("LHSVAR", codeError.getExtraInfo("lhs_type"));
            template = template.replace("RHSVAR", codeError.getExtraInfo("rhs_type"));
            return template;
        } else if (codeError.getId().equals("incomparable_list_sizes")) {
            String template = getTemplateString("incomparable_list_sizes");
            template = template.replace("LHSDEPTH", codeError.getExtraInfo("lhs_list_size"));
            template = template.replace("RHSDEPTH", codeError.getExtraInfo("rhs_list_size"));
            return template;
        } else if (codeError.getId().equals("wrong_var_passed_to_votesum")) {
            String template = getTemplateString("wrong_var_passed_to_votesum");
            template = template.replace("VAR", codeError.getExtraInfo("var_name"));
            template = template.replace("GIVENTYPE", codeError.getExtraInfo("var_type"));
            return template;
        }
        return "";
    }

    private String getTemplateString(String id) {
        return currentStringResLoader.getStringFromID(id + "_descr");
    }

    @Override
    public void updateStringRes(StringLoaderInterface stringResIF) {
        this.currentStringResLoader = stringResIF.getBooleanExpEditorStringResProvider().getBooleanExpErrorStringRes();
    }

}
