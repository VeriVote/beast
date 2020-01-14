//package edu.pse.beast.codearea.codeinput;
//
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//import javax.swing.JTextPane;
//import javax.swing.text.BadLocationException;
//
//import edu.pse.beast.toolbox.SortedIntegerList;
//
///**
// * Handles inserting and removing tabs in a given jtextpane. This is because
// * the pane cannot by itself add tabs as spaces.
// * TODO: make it so amount of spaces per tab can be changed after code generation
// * @author Holger Klein
// */
//public class TabInserter {
//    private JTextPane pane;
//    private SortedIntegerList tabPositions = new SortedIntegerList();
//    private int spacesPerTab = 8;
//
//    public TabInserter(JTextPane pane) {
//        this.pane = pane;
//    }
//
//    /**
//     * inserts the required amount of spaces to move the caret psoition
//     * to the next multiple of the amount of spaces per tab
//     * @param pos the position at which a tab should be inserted
//     * @throws BadLocationException if the position is invalid
//     */
//    public void insertTabAtPos(int pos) throws BadLocationException {
//        int distToLineBeginning = JTextPaneToolbox.getDistanceToClosestLineBeginning(pane, pos);
//        int nextTabPos = 0;
//
//        while (nextTabPos <= distToLineBeginning) {
//            nextTabPos += spacesPerTab;
//        }
//        int distToNextTabPos = nextTabPos - distToLineBeginning;
//        String spacesToInsert = "";
//        for(int i = 0; i < distToNextTabPos; ++i) spacesToInsert += " ";
//        pane.getStyledDocument().insertString(pos, spacesToInsert, null);
//        tabPositions.add(pos);
//    }
//
//    /**
//     * removes a tab at the position if there is nothing but spaces to the left
//     * of it
//     * @param pos the position at which a tab should be removed
//     */
//    public void removeTabAtPos(int pos) {
//        if(!onlySpacesBetweenPosAndLinesBeginning(pos)) return;
//        int distToLineBeginning = JTextPaneToolbox.getDistanceToClosestLineBeginning(pane, pos);
//        int closestMultipleOfSpacesPerTab = 0;
//        while(closestMultipleOfSpacesPerTab + spacesPerTab < distToLineBeginning) {
//            closestMultipleOfSpacesPerTab += spacesPerTab;
//        }
//        int absPosClosestMultiple = pos - distToLineBeginning + closestMultipleOfSpacesPerTab;
//        try {
//            pane.getStyledDocument().remove(absPosClosestMultiple, pos - absPosClosestMultiple);
//        } catch (BadLocationException ex) {
//            Logger.getLogger(TabInserter.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    public int getSpacesPerTab() {
//        return spacesPerTab;
//    }
//
//    private boolean onlySpacesBetweenPosAndLinesBeginning(int pos) {
//        int distToLineBeginning = JTextPaneToolbox.getDistanceToClosestLineBeginning(pane, pos);
//        String spaces = "";
//        for(int i = 0; i < distToLineBeginning; ++i) {
//            spaces += " ";
//        }
//        try {
//            return pane.getStyledDocument()
//                       .getText(pos - distToLineBeginning,
//                                distToLineBeginning)
//                   .equals(spaces);x
//        } catch (BadLocationException ex) {
//            return false;
//        }
//    }
//
//    public void setAmountSpacesPerTab(int numberTabs) {
//        this.spacesPerTab = numberTabs;
//    }
//}
