///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package edu.pse.beast.codearea;
//
//import javax.swing.JScrollPane;
//import javax.swing.JTextPane;
//
//import edu.pse.beast.codearea.ActionAdder.TextChangedActionAdder;
//import edu.pse.beast.codearea.Actionlist.Actionlist;
//import edu.pse.beast.codearea.Autocompletion.AutocompletionController;
//import edu.pse.beast.codearea.ErrorHandling.ErrorController;
//import edu.pse.beast.codearea.ErrorHandling.ErrorDisplayer;
//import edu.pse.beast.codearea.InputToCode.CodeInputHandler;
//import edu.pse.beast.codearea.InputToCode.OpenCloseCharList;
//import edu.pse.beast.codearea.InputToCode.ShortcutHandler;
//import edu.pse.beast.codearea.InputToCode.UserInputHandler;
//import edu.pse.beast.codearea.InputToCode.UserInsertToCode;
//import edu.pse.beast.codearea.SyntaxHL.SyntaxHL;
//import edu.pse.beast.codearea.UserActions.CodeAreaUserActions;
//import edu.pse.beast.toolbox.ObjectRefsForBuilder;
//
///**
// * This class builds a codearea and inititalizes all the classes needed by the
// * codearea.
// *
// * @author Holger-Desktop
// */
//public class CodeAreaBuilder {
//
//    /**
//     * creates a codearea object
//     *
//     * @param codeArea the JTextPane used to display code
//     * @param codeAreaScroll The Scrollpane used to scroll in the jtextpane
//     * @param refs The references to interfaces needed to build objects
//     * @param errorDisplayer The specific errordisplayer to be used by the
//     * errorcontroller
//     * @return The created codearea object
//     */
//    public CodeArea createCodeArea(JTextPane codeArea, JScrollPane codeAreaScroll,
//                                   ObjectRefsForBuilder refs, ErrorDisplayer errorDisplayer) {
//        //simply create all necessary objects        
//        OpenCloseCharList occL = new OpenCloseCharList();
//
//        ShortcutHandler shortcutHandler = new ShortcutHandler();
//
//        Actionlist actionList = new Actionlist();
//
//        SaveTextBeforeRemove textBeforeRemove = new SaveTextBeforeRemove(codeArea, actionList);
//
//        UserInsertToCode insertToCode = new UserInsertToCode(codeArea, occL, textBeforeRemove);
//
//        CodeInputHandler codeInputHandler = new CodeInputHandler(insertToCode);
//
//        UserInputHandler userInputHandler =
//                new UserInputHandler(codeArea, codeInputHandler, shortcutHandler);
//
//        TextLineNumber tln = new TextLineNumber(codeArea);
//        codeAreaScroll.setRowHeaderView(tln);
//        StoppedTypingContinuouslyMessager stoppedTypingContinuouslyMessager
//                = new StoppedTypingContinuouslyMessager(codeArea);
//        ErrorController error = new ErrorController(
//                codeArea, errorDisplayer);
//
//        AutocompletionController autocompletion = new AutocompletionController(codeArea, insertToCode);
//
//        TextChangedActionAdder actionAdder =
//                new TextChangedActionAdder(codeArea, actionList, textBeforeRemove);
//
//        SyntaxHL syntaxHL = new SyntaxHL(codeArea);
//
//        CodeArea created =
//                new CodeArea(codeArea, tln, userInputHandler, insertToCode, actionList, error,
//                             autocompletion, syntaxHL, stoppedTypingContinuouslyMessager);
//        CodeAreaUserActions userActions = new CodeAreaUserActions(created);
//        created.setUserActionList(userActions);
//
//        shortcutHandler.addAction('z', userActions.getActionById("undo"));
//        shortcutHandler.addAction('r', userActions.getActionById("redo"));
//        return created;
//    }
//
//}
