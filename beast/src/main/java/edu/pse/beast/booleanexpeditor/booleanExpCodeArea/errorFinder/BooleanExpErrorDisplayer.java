/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.booleanexpeditor.booleanExpCodeArea.errorFinder;

import edu.pse.beast.codearea.ErrorHandling.CodeError;
import edu.pse.beast.codearea.ErrorHandling.ErrorDisplayer;
import edu.pse.beast.highlevel.DisplaysStringsToUser;
import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.stringresource.StringResourceLoader;
import java.util.ArrayList;
import javax.swing.JTextPane;

/**
 *
 * @author Holger-Desktop
 */
public class BooleanExpErrorDisplayer extends ErrorDisplayer {
    
    public BooleanExpErrorDisplayer(
            JTextPane pane,
            StringLoaderInterface stringResIF) {
        super(pane, stringResIF.getBooleanExpEditorStringResProvider().getBooleanExpErrorStringRes());
    }
    
    @Override
    public void showErrors(ArrayList<CodeError> errors) {
        super.showErrors(errors);
        for(CodeError er : errors) {            
            showError(er, createMsg(er));
        }
    }
    
    private String createMsg(CodeError er) {
        if(er.getId().equals("antlr")) {
            String template = getTemplateString("antlr");
            return template += ": " + er.getExtraInfo("msg");
        } else if(er.getId().equals("var_not_decl")) {
            String template = getTemplateString("var_not_decl");
            return template.replace("VAR", er.getExtraInfo("var_name"));
        } else if(er.getId().equals("too_many_vars_passed")) {
            String template = getTemplateString("too_many_vars_passed");
            template = template.replace("VAR", er.getExtraInfo("var_name"));
            return template;
        } else if(er.getId().equals("wrong_var_type_passed")) {
            String template = getTemplateString("wrong_var_type_passed");
            template = template.replace("VAR", er.getExtraInfo("var_name"));
            template = template.replace("EXPECTEDTYPE", er.getExtraInfo("passed_type"));
            template = template.replace("GIVENTYPE", er.getExtraInfo("expected_type"));
            return template;
        } else if(er.getId().equals("incomparable_types")) {
            String template = getTemplateString("incomparable_types");
            template = template.replace("LHSVAR", er.getExtraInfo("lhs_type"));
            template = template.replace("RHSVAR", er.getExtraInfo("rhs_type"));
            return template;
        } else if(er.getId().equals("incomparable_list_sizes")) {
            String template = getTemplateString("incomparable_list_sizes");
            template = template.replace("LHSDEPTH", er.getExtraInfo("lhs_list_size"));
            template = template.replace("RHSDEPTH", er.getExtraInfo("rhs_list_size"));
            return template;
        } else if(er.getId().equals("wrong_var_passed_to_votesum")) {
            String template = getTemplateString("wrong_var_passed_to_votesum");
            template = template.replace("VAR", er.getExtraInfo("var_name"));
            template = template.replace("GIVENTYPE", er.getExtraInfo("var_type"));
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
