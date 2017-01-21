/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea;

import edu.pse.beast.codearea.Actionlist.Actionlist;
import edu.pse.beast.codearea.Autocompletion.AutocompletionController;
import edu.pse.beast.codearea.ErrorHandling.ErrorController;
import edu.pse.beast.codearea.InputToCode.UserInputHandler;
import edu.pse.beast.codearea.InputToCode.UserInsertToCode;

/**
 *
 * @author Niels
 */
public class CodeArea {
    
    protected TextLineNumber tln;
    protected UserInputHandler userInputHandler;
    protected UserInsertToCode insertToCode;
    protected Actionlist actionList;
    protected ErrorController errorCtrl;
    protected AutocompletionController autoComplCtrl;
                    
    public CodeArea(
            TextLineNumber tln, 
            UserInputHandler userInputHandler, 
            UserInsertToCode insertToCode,
            Actionlist actionList,
            ErrorController errorCtrl, 
            AutocompletionController autoComplCtrl) {
        this.tln = tln; 
        this.userInputHandler = userInputHandler;
        this.insertToCode = insertToCode;
        this.actionList = actionList;
        this.errorCtrl = errorCtrl;
        this.autoComplCtrl = autoComplCtrl;
    }
}
