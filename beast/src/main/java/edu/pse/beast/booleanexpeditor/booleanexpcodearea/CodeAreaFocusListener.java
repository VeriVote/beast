package edu.pse.beast.booleanexpeditor.booleanexpcodearea;

//import java.awt.event.FocusEvent;
//import java.awt.event.FocusListener;
//
///**
// * FocusListener given to the JTextPanes of the BooleanExpEditorWindow JFrame.
// * getLastFocused returns the CodeArea belonging to the last focused JTextPane.
// * Implements FocusListener so it can be applied to the JTextPanes of the CodeAreas.
// * @author Nikolai Schnell
// */
//public class CodeAreaFocusListener implements FocusListener {
//    private BooleanExpCodeArea lastFocused;
//    private BooleanExpCodeArea preConditionCodeArea;
//    private BooleanExpCodeArea postConditionCodeArea;
//
//    /**
//     * Constructor
//     * @param preConditionCodeArea CodeArea for preconditions
//     * @param postConditionCodeArea CodeArea for postconditions
//     */
//    public CodeAreaFocusListener(BooleanExpCodeArea preConditionCodeArea,
//                                 BooleanExpCodeArea postConditionCodeArea) {
//        lastFocused = preConditionCodeArea;
//        this.preConditionCodeArea = preConditionCodeArea;
//        this.postConditionCodeArea = postConditionCodeArea;
//    }
//
//    /**
//     * Method that sets new BooleanExpCodeAreas
//     * @param preConditionCodeArea the new BooleanExpCodeArea for preconditions
//     * @param postConditionCodeArea the new BooleanExpCodeArea for postconditions
//     */
//    public void addNewCodeAreas(BooleanExpCodeArea preConditionCodeArea,
//                                BooleanExpCodeArea postConditionCodeArea) {
//        this.preConditionCodeArea = preConditionCodeArea;
//        this.postConditionCodeArea = postConditionCodeArea;
//        lastFocused = preConditionCodeArea;
//    }
//
//    @Override
//    public void focusGained(FocusEvent focusEvent) {
//        if ((focusEvent.getComponent()).equals(preConditionCodeArea.getPane())) {
//            lastFocused = preConditionCodeArea;
//        } else {
//            lastFocused = postConditionCodeArea;
//        }
//    }
//
//    @Override
//    public void focusLost(FocusEvent focusEvent) {
//    }
//
//    /**
//     * Method that
//     * @return s the last focused JTextPane.
//     */
//    public BooleanExpCodeArea getLastFocused() {
//        return lastFocused;
//    }
//}
