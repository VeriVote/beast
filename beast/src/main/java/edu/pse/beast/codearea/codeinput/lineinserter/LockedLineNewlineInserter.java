//package edu.pse.beast.codearea.codeinput.lineinserter;
//
//import javax.swing.JTextPane;
//import javax.swing.text.BadLocationException;
//
//import edu.pse.beast.codearea.codeinput.JTextPaneToolbox;
//import edu.pse.beast.codearea.codeinput.LineBeginningTabsHandler;
//import edu.pse.beast.codearea.codeinput.TabInserter;
//
///**
// * Inesrts newlines into locked lines by inserting it at their beginning,
// * thus shifting the entire line down by one
// * @author Holger Klein
// */
//public class LockedLineNewlineInserter implements NewlineInserter {
//    @Override
//    public void insertNewlineAtCurrentPosition(
//            JTextPane pane, TabInserter tabInserter,
//            LineBeginningTabsHandler beginningTabsHandler,
//            int pos) throws BadLocationException {
//            int lineBeginning =
//                pos - JTextPaneToolbox.getDistanceToClosestLineBeginning(pane, pos);
//            pane.getStyledDocument().insertString(lineBeginning, "\n", null);
//    }
//}