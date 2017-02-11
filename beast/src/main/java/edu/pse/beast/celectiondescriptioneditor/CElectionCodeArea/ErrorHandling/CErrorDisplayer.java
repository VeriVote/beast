/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.ErrorHandling;

import edu.pse.beast.codearea.ErrorHandling.CodeError;
import edu.pse.beast.codearea.ErrorHandling.ErrorDisplayer;
import edu.pse.beast.codearea.InputToCode.JTextPaneToolbox;
import edu.pse.beast.stringresource.StringLoaderInterface;
import java.util.ArrayList;
import javax.swing.JTextPane;

/**
 *
 * @author Holger-Desktop
 */
public class CErrorDisplayer extends ErrorDisplayer {
    
      public CErrorDisplayer(
            JTextPane pane,
            StringLoaderInterface stringResIF) {
        super(pane, stringResIF.getCElectionEditorStringResProvider().getCErrorStringRes());
    }
    
    @Override
    public void showErrors(ArrayList<CodeError> errors) {
        super.showErrors(errors);
        for(CodeError er : errors) {            
            showError(er, createMsg(er));
        }
    }
    
    public String createMsg(CodeError er) {
        if(er.getId().equals("antlr")) {
            int line =  er.getLine();
            int start = JTextPaneToolbox.getLineBeginning(pane, line - 1);
            int end = JTextPaneToolbox.getClosestLineBeginningAfter(pane, start);
            er.setStartPos(start);
            er.setEndPos(end);
            return er.getExtraInfo("msg");
        } else if(er.getId().equals("compilererror")) {
            int line =  er.getLine();
            int start = JTextPaneToolbox.getLineBeginning(pane, line - 1);
            int end = JTextPaneToolbox.getClosestLineBeginningAfter(pane, start);
            er.setStartPos(start);
            er.setEndPos(end);
            String msg = er.getExtraInfo("msg");
            String var = er.getExtraInfo("var");
            if(!msg.contains(var)) msg = var + ": " + msg; 
            return msg;
        }
        return "";
    }
    
    @Override
    public void updateStringRes(StringLoaderInterface stringResIF) {
        this.currentStringResLoader = stringResIF.getCElectionEditorStringResProvider().getCErrorStringRes();
    }
    
}
