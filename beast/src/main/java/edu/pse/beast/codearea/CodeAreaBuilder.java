/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea;

import edu.pse.beast.codearea.ActionAdder.TextChangedActionAdder;
import edu.pse.beast.codearea.Actionlist.Actionlist;
import edu.pse.beast.codearea.Autocompletion.AutocompletionController;
import edu.pse.beast.codearea.ErrorHandling.ErrorController;
import edu.pse.beast.codearea.InputToCode.CodeInputHandler;
import edu.pse.beast.codearea.InputToCode.OpenCloseCharList;
import edu.pse.beast.codearea.InputToCode.ShortcutHandler;
import edu.pse.beast.codearea.InputToCode.UserInputHandler;
import edu.pse.beast.codearea.InputToCode.UserInsertToCode;
import edu.pse.beast.codearea.SyntaxHL.RegexAndColor;
import edu.pse.beast.codearea.SyntaxHL.SyntaxHL;
import edu.pse.beast.codearea.UserActions.CodeAreaUserActions;
import edu.pse.beast.toolbox.ObjectRefsForBuilder;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

/**
 *
 * @author Holger-Desktop
 */
public class CodeAreaBuilder {

    public CodeArea createCodeArea(JTextPane codeArea, JScrollPane codeAreaScroll, ObjectRefsForBuilder refs) {
                
        OpenCloseCharList occL = new OpenCloseCharList();
        
        ShortcutHandler shortcutHandler = new ShortcutHandler();
        
        Actionlist actionList = new Actionlist();
        
        SaveTextBeforeRemove textBeforeRemove = new SaveTextBeforeRemove(codeArea, actionList);
        
        UserInsertToCode insertToCode = new UserInsertToCode(codeArea, occL, textBeforeRemove);    
        
        CodeInputHandler codeInputHandler = new CodeInputHandler(insertToCode);
        
        UserInputHandler userInputHandler = new UserInputHandler(codeArea, codeInputHandler, shortcutHandler);
        
        TextLineNumber tln = new TextLineNumber(codeArea); 
        codeAreaScroll.setRowHeaderView(tln);
        
        StoppedTypingContinuouslyMessager stoppedTypingContinuouslyMessager = 
                new StoppedTypingContinuouslyMessager(codeArea);
        ErrorController error = new ErrorController(
                codeArea, stoppedTypingContinuouslyMessager);
        
        AutocompletionController autocompletion = new AutocompletionController();
        
        
        TextChangedActionAdder actionAdder = new TextChangedActionAdder(codeArea, actionList, textBeforeRemove);

        SyntaxHL syntaxHL = new SyntaxHL(codeArea);

        CodeArea created = new CodeArea(codeArea, tln, userInputHandler, insertToCode, actionList, error,
                autocompletion, syntaxHL, stoppedTypingContinuouslyMessager);
        CodeAreaUserActions userActions = new CodeAreaUserActions(created);
        created.setUserActionList(userActions);


        //maybe change it up so user can change it?
        shortcutHandler.addAction(getKeyCodeFor('z'), userActions.getActionById("undo"));
        shortcutHandler.addAction(getKeyCodeFor('r'), userActions.getActionById("redo"));
        return created;
    }
    
    private int getKeyCodeFor(char c) {
        switch(c) {
                case 'z':
                    return 90;
                case 'r':
                    return 82;
                default:
                    return -1;                            
        }                    
    }
}
