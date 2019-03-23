//package edu.pse.beast.codearea.codeinput.lineinserter;
//
//import javax.swing.JTextPane;
//import javax.swing.text.BadLocationException;
//
//import edu.pse.beast.codearea.codeinput.LineBeginningTabsHandler;
//import edu.pse.beast.codearea.codeinput.TabInserter;
//
///**
// *
// * @author Holger Klein
// */
//public class StandardNewlineInserter implements NewlineInserter {
//        
//    @Override
//    public void insertNewlineAtCurrentPosition(
//            JTextPane pane, TabInserter tabInserter,
//            LineBeginningTabsHandler beginningTabsHandler,
//            int pos) throws BadLocationException {
//        
//        pane.getStyledDocument().insertString(pos, "\n", null);
//        
//        int newLinePos = pos + 1;
//        
//        int tabs = beginningTabsHandler.getTabsForLine(newLinePos);
//        
//        for(int i = 0; i < tabs; ++i) {
//            tabInserter.insertTabAtPos(newLinePos + i * tabInserter.getSpacesPerTab());
//        }      
//        
//        
//    }
//    
//}
