/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea;

import edu.pse.beast.codearea.Actionlist.Actionlist;
import edu.pse.beast.codearea.Autocompletion.AutocompletionController;
import edu.pse.beast.codearea.ErrorHandling.ErrorController;
import edu.pse.beast.codearea.InputToCode.CodeInputHandler;
import edu.pse.beast.codearea.InputToCode.OpenCloseCharList;
import edu.pse.beast.codearea.InputToCode.ShortcutHandler;
import edu.pse.beast.codearea.InputToCode.UserInputHandler;
import edu.pse.beast.codearea.InputToCode.UserInsertToCode;
import edu.pse.beast.codearea.UserActions.CodeAreaUserActions;
import edu.pse.beast.toolbox.ObjectRefsForBuilder;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

/**
 *
 * @author Holger-Desktop
 */
public class CodeAreaBuilder {
    public CodeArea createCodeArea(JTextPane codeArea, JScrollPane codeAreaScroll, ObjectRefsForBuilder refs) {
                
        codeArea.setFont(new Font("Inconsolata", Font.PLAIN, 14));
        
        OpenCloseCharList occL = new OpenCloseCharList();
        UserInsertToCode insertToCode = new UserInsertToCode(codeArea, occL);    
        
        CodeInputHandler codeInputHandler = new CodeInputHandler(insertToCode);
        ShortcutHandler shortcutHandler = new ShortcutHandler();
        
        UserInputHandler userInputHandler = new UserInputHandler(codeArea, codeInputHandler, shortcutHandler);
        
        TextLineNumber tln = new TextLineNumber(codeArea); 
        codeAreaScroll.setRowHeaderView(tln);
        
        ErrorController error = new ErrorController();
        AutocompletionController autocompletion = new AutocompletionController();
        Actionlist actionList = new Actionlist();
        
        CodeArea created = new CodeArea(tln, userInputHandler, insertToCode, actionList, error, autocompletion);
        CodeAreaUserActions userActions = new CodeAreaUserActions(created);
        created.setUserActionList(userActions);
        
        return created;
    }
}
