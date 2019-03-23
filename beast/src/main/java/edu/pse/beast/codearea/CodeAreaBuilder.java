//package edu.pse.beast.codearea;
//
//import javax.swing.JScrollPane;
//import javax.swing.JTextPane;
//
//import edu.pse.beast.codearea.actionadder.TextChangedActionAdder;
//import edu.pse.beast.codearea.actionlist.Actionlist;
//import edu.pse.beast.codearea.autocompletion.AutocompletionController;
//import edu.pse.beast.codearea.errorhandling.ErrorController;
//import edu.pse.beast.codearea.errorhandling.ErrorDisplayer;
//import edu.pse.beast.codearea.codeinput.CodeInputHandler;
//import edu.pse.beast.codearea.codeinput.OpenCloseCharList;
//import edu.pse.beast.codearea.codeinput.ShortcutHandler;
//import edu.pse.beast.codearea.codeinput.UserInputHandler;
//import edu.pse.beast.codearea.codeinput.UserInsertToCode;
//import edu.pse.beast.codearea.syntaxhighlighting.SyntaxHL;
//import edu.pse.beast.codearea.useractions.CodeAreaUserActions;
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
