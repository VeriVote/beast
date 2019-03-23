//package edu.pse.beast.codearea;
//
//import java.awt.Font;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.swing.JTextPane;
//import javax.swing.event.AncestorEvent;
//import javax.swing.event.AncestorListener;
//import javax.swing.text.BadLocationException;
//
//import edu.pse.beast.codearea.actionlist.Actionlist;
//import edu.pse.beast.codearea.autocompletion.AutocompletionController;
//import edu.pse.beast.codearea.errorhandling.ErrorController;
//import edu.pse.beast.codearea.codeinput.ShortcutHandler;
//import edu.pse.beast.codearea.codeinput.UserInputHandler;
//import edu.pse.beast.codearea.codeinput.UserInsertToCode;
//import edu.pse.beast.codearea.syntaxhighlighting.RegexAndColor;
//import edu.pse.beast.codearea.syntaxhighlighting.SyntaxHL;
//import edu.pse.beast.codearea.useractions.CodeAreaUserActions;
//import edu.pse.beast.toolbox.UserAction;
//
///**
// * This class is the Fassade to the package of the same name - CodeArea. 
// * It allows classes using this package to access its most commonly needed 
// * utilities without having to know about the classes which implement these.
// * The Package CodeArea uses a JTextPane to allow other classes to implement
// * code editor functionality without having to reimplement commonly needed features.
// * As such, it provides basic implementations for error finding and code completion.
// * Child classes such as booleanexpcodearea use this to add specific error finders
// * and autocompletion possiblitites. Thus, the code area can be specialized towards 
// * a certain language.
// * @author Holger Klein 
// */
//public class CodeArea implements AncestorListener {
//    
//    /**
//     * the JTextpane on which the code will be presented to the user and
//     * through which the user communicates with the codearea classes
//     */
//    protected JTextPane pane;
//    /**
//     * this class shows the line numbers
//     */
//    protected TextLineNumber tln;
//    /**
//     * handles input processing
//     */
//    protected UserInputHandler userInputHandler;   
//    /**
//     * handles mapping shortcuts to useractions
//     */
//    protected ShortcutHandler shortcutHandler;
//    /**
//     * this class is used by the userinputhandler. It translates user input 
//     * into changes in the code
//     */
//    protected UserInsertToCode insertToCode;
//    /**
//     * this class enables undoing and redoing actions
//     */
//    protected Actionlist actionList;
//    /**
//     * this class controlls error finding and displaying
//     */
//    protected ErrorController errorCtrl;
//    /**
//     * this class controlls finding and displaying autocompletion possibilities
//     */
//    protected AutocompletionController autoComplCtrl;
//    /**
//     * This class provides access to and generates all useractions which can
//     * be performed on a codearea
//     */
//    protected CodeAreaUserActions userActionList;
//    /**
//     * This class implements syntax highliting
//     */
//    protected SyntaxHL syntaxHL;
//    /**
//     * This class messages other classes once the user has stopped typing 
//     * continuously
//     */
//    protected StoppedTypingContinuouslyMessager stoppedTypingContinuouslyMessager;
//    /**
//     * this class highlight corresponding open/close chars
//     */
//    protected OpenCloseCharHighlighter openCloseCharHighlighter;
//    
//    /**
//     * The constructor simply sets all member variables to the supplied classes
//     * @param pane the JTextpane on which the code will be presented to the user and
//     * through which the user communicates with the codearea classes
//     * @param tln shows the line numbers
//     * @param userInputHandler handles input processing
//     * @param insertToCode used by the userinputhandler. It translates user input 
//     * into changes in the code
//     * @param actionList enables undoing and redoing actions
//     * @param errorCtrl controlls error finding and displaying
//     * @param autoComplCtrl controlls finding and displaying autocompletion possibilities
//     * @param syntaxHL implements syntax highliting
//     * @param stoppedTypingContinuouslyMessager messages other classes once the user has stopped typing 
//     * continuously
//     */
//    public CodeArea(
//            JTextPane pane,
//            TextLineNumber tln,
//            UserInputHandler userInputHandler,
//            UserInsertToCode insertToCode,
//            Actionlist actionList,
//            ErrorController errorCtrl,
//            AutocompletionController autoComplCtrl,
//            SyntaxHL syntaxHL,
//            StoppedTypingContinuouslyMessager stoppedTypingContinuouslyMessager) {
//        this.pane = pane;
//        this.tln = tln; 
//        this.userInputHandler = userInputHandler;
//        this.shortcutHandler = userInputHandler.getShortcutHandler();
//        this.insertToCode = insertToCode;
//        this.actionList = actionList;
//        this.errorCtrl = errorCtrl;
//        this.autoComplCtrl = autoComplCtrl;
//        this.syntaxHL = syntaxHL;
//        this.stoppedTypingContinuouslyMessager = stoppedTypingContinuouslyMessager;
//        this.openCloseCharHighlighter = new OpenCloseCharHighlighter(insertToCode.getOccList(), pane);
//        pane.addAncestorListener(this);
//    }
//
//    /**
//     * This constructor initialises all the fields with the fields of the 
//     * given codearea. Used to construct classes which inherit from codearea
//     * @param codeArea the fields of this codeare are taken to initialize the fields
//     * of the new codearea
//     */
//    public CodeArea(CodeArea codeArea) {
//        this.pane = codeArea.pane;
//        this.tln = codeArea.tln;
//        this.userInputHandler = codeArea.userInputHandler;
//        this.shortcutHandler = userInputHandler.getShortcutHandler();
//        this.insertToCode = codeArea.insertToCode;
//        this.actionList = codeArea.actionList;
//        this.errorCtrl = codeArea.errorCtrl;
//        this.autoComplCtrl = codeArea.autoComplCtrl;
//        this.userActionList = codeArea.userActionList;
//        this.syntaxHL = codeArea.syntaxHL;
//        this.stoppedTypingContinuouslyMessager = codeArea.stoppedTypingContinuouslyMessager;
//        this.openCloseCharHighlighter = codeArea.openCloseCharHighlighter;
//        pane.addAncestorListener(this);
//    }
//    
//    public ErrorController getErrorCtrl() {
//        return errorCtrl;
//    }
//    
//    /**
//     * locks the given line so the user cant edit it
//     * @param line the line to be locked, starting at 0
//     */
//    public void lockLine(int line) {
//        insertToCode.lockLine(line);
//    }
//
//    /**
//     * return the first line of the code which is locked. Throws an exception
//     * indexoutofbounds exception if no line is locked
//     * @return the first locked line
//     */
//    public int getFirstLockedLine() {
//        return insertToCode.getFirstLockedLine();
//    }
//    
//    public CodeAreaUserActions getUserActionList() {
//        return userActionList;
//    }
//
//    public void setUserActionList(CodeAreaUserActions userActionList) {
//        this.userActionList = userActionList;
//    }    
//    
//    public JTextPane getPane() {
//        return this.pane;
//    }
//
//    public Actionlist getActionlist() {
//        return actionList;
//    }
//
//    public void setSyntaxHLRegexAndColorList(ArrayList<RegexAndColor> regexAndColorList) {
//        syntaxHL.updateFilter(regexAndColorList);
//    }
//
//    /**
//     * inserts the supplied string at the position of the caret.
//     * @param string the string to be inserted
//     */
//    public void insertString(String string) {
//        insertToCode.insertString(string);
//    }
//    
//    public void replaceVotingDecline(List<String> code) throws BadLocationException {
//    	insertToCode.updateVotingDeclLine(code);
//    }
//
//    /**
//     * links the supplied action to the given char. If the user presses strg + char,
//     * the action will be performed
//     * @param c the char of the shortcut
//     * @param newAcc the action to be performed
//     */
//    public void linkActionToShortcut(char c, UserAction newAcc) {
//        shortcutHandler.addAction(c, newAcc);
//    }
//
//    public AutocompletionController getAutoComplCtrl() {
//        return autoComplCtrl;
//    }    
//
//    @Override
//    public void ancestorAdded(AncestorEvent ae) {
//    }
//
//    @Override
//    public void ancestorRemoved(AncestorEvent ae) {
//    }
//
//    @Override
//    public void ancestorMoved(AncestorEvent ae) {
//        pane.repaint();
//    }
//
//    public UserInsertToCode getInsertToCode() {
//        return insertToCode;
//    }
//
//    public UserInputHandler getUserInputHandler() {
//        return userInputHandler;
//    }
//
//    public void setFont(Font f) {
//        pane.setFont(f);
//        tln.setFont(f);
//    }
//
//    public void pauseErrorFinding() {
//        errorCtrl.pauseErrorFinding();
//    }
//
//    public void resumeErrorFinding() {
//        errorCtrl.resumeErrorFinding();
//    }
//    
//
//}
