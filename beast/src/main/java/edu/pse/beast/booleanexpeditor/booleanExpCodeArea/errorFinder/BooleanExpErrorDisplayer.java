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
import java.util.ArrayList;
import javax.swing.JTextPane;

/**
 *
 * @author Holger-Desktop
 */
public class BooleanExpErrorDisplayer extends ErrorDisplayer {
    
    public BooleanExpErrorDisplayer(JTextPane pane) {
        super(pane);
    }
    
    @Override
    public void showErrors(ArrayList<CodeError> errors) {
        super.showErrors(errors);
        for(CodeError er : errors) {            
            showError(er, createMsg(er));
        }
    }
    
    private String createMsg(CodeError er) {
        return er.getId();
    }

    @Override
    public void updateStringRes(StringLoaderInterface stringResIF) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
