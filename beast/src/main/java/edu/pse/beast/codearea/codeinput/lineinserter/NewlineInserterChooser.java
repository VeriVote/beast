//package edu.pse.beast.codearea.codeinput.lineinserter;
//
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//import javax.swing.JTextPane;
//import javax.swing.text.BadLocationException;
//
//import edu.pse.beast.codearea.codeinput.LockedLinesHandler;
//
///**
// * This class chooses the right newlineinserter implementation based on the chars
// * surrounding the caret position of the given pane. 
// * @author Holger-Desktop
// */
//public class NewlineInserterChooser {
//    private JTextPane pane;
//    private StandardNewlineInserter standardInserter = new StandardNewlineInserter();
//    private LockedLineNewlineInserter lockedInserter = new LockedLineNewlineInserter();
//    private BetweenCurlyBracesNewlineInserter curlyBracesInserter;
//    private LockedLinesHandler lockedLinesHandler;
//    
//    public NewlineInserterChooser(JTextPane pane, LockedLinesHandler lockedLinesHandler) {
//        this.pane = pane;
//        this.lockedLinesHandler = lockedLinesHandler;
//        this.curlyBracesInserter = new BetweenCurlyBracesNewlineInserter(standardInserter);
//    }
//    
//    /**
//     * finds the right implementation of newlineinserter depending on the chars 
//     * surrounding the panes currentcaretpositin. If the lines is locked,
//     * it returns lockedlinenewlineinserter. If the caret is between
//     * {}, it returns betweencurlybracesnewlineinserter. If it is none
//     * of the above, it returns standardnewlineinserter
//     * @return 
//     */
//    public NewlineInserter getNewlineInserter() {
//        NewlineInserter found = chooseNewlineInserter();
//        return findMoreSpecializedInserter(found);
//    }   
//    
//    protected NewlineInserter findMoreSpecializedInserter(NewlineInserter current) {
//        return current;
//    }
//
//    private NewlineInserter chooseNewlineInserter() {
//        int lineNumber = absPosToLineNumber(pane.getCaretPosition());
//        if(lockedLinesHandler.isLineLocked(lineNumber)) {
//            try {
//                if(pane.getStyledDocument().getText(pane.getCaretPosition(), 1).equals("\n")) {                    
//                    return standardInserter;
//                }
//            } catch (BadLocationException ex) {
//                Logger.getLogger(NewlineInserterChooser.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            return lockedInserter;
//        }
//        try {
//            String surroundingChars = findSurroundingChars();
//            if(isCurlyBracesSurround(surroundingChars)) {
//                return curlyBracesInserter;
//            }
//        } catch (BadLocationException ex) {                 
//        }
//        return standardInserter;       
//    }
//    
//    private int absPosToLineNumber(int caretPosition) {
//        int line = 0;
//        String code = pane.getText();
//        for(int i = 0; i < caretPosition; ++i) {
//            if(code.charAt(i) == '\n') ++line;
//            else if(code.charAt(i) == '\r') ++caretPosition;
//        }
//        return line;
//    }
//    
//    private String findSurroundingChars() throws BadLocationException {
//        int pos = pane.getCaretPosition();
//        if(pos == 0) {
//            return pane.getText(pos, 1);            
//        } else if(pos == pane.getText().length()) {
//            return pane.getText(pos - 1, 1);
//        } else {
//            return pane.getText(pos - 1, 2);
//        }
//    }
//
//    private boolean isCurlyBracesSurround(String surroundingChars) {
//        return surroundingChars.equals("{}");
//    }
//
//    
//    
//    
//}
