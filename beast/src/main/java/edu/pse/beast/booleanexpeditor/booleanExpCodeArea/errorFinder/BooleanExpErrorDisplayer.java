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
