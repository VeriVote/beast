//package edu.pse.beast.codearea.codeinput.lineinserter;
//
//import javax.swing.JTextPane;
//import javax.swing.text.BadLocationException;
//
//import edu.pse.beast.codearea.codeinput.LineBeginningTabsHandler;
//import edu.pse.beast.codearea.codeinput.TabInserter;
//
///**
// * Inserts newlines between two curly braces by adding two new lines
// * and centering the caret position between them:
// * {|} becomes
// * {
// *     |
// * }
// * @author Holger Klein
// */
//public class BetweenCurlyBracesNewlineInserter implements NewlineInserter {
//    private StandardNewlineInserter standInserter;
//
//    public BetweenCurlyBracesNewlineInserter(StandardNewlineInserter standInserter) {
//        this.standInserter = standInserter;
//    }
//
//    @Override
//    public void insertNewlineAtCurrentPosition(
//            JTextPane pane, TabInserter tabInserter,
//            LineBeginningTabsHandler beginningTabsHandler,
//            int pos) throws BadLocationException {
//        standInserter.insertNewlineAtCurrentPosition(pane,
//                                                     tabInserter,
//                                                     beginningTabsHandler,
//                                                     pos);
//        pane.setCaretPosition(pos);
//        standInserter.insertNewlineAtCurrentPosition(pane, tabInserter,
//                                                     beginningTabsHandler,
//                                                     pos);
//    }
//}