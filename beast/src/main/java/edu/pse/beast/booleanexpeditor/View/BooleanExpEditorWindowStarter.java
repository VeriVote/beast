//package edu.pse.beast.booleanexpeditor.View;
//
///**
// * Starts the given BooleanExpEditorWindow Class in a new thread.
// * @author Nikolai
// */
//public class BooleanExpEditorWindowStarter implements Runnable {
//    private final BooleanExpEditorWindow window;
//
//    /**
//     * Constructor
//     * @param window BooleanExpEditorWindow object this class starts
//     */
//    public BooleanExpEditorWindowStarter(BooleanExpEditorWindow window) {
//        this.window = window;
//    }
//
//    /**
//     * Getter
//     * @return BooleanExpEditorWindow instance of this class
//     */
//    public BooleanExpEditorWindow getBooleanExpEditorWindow() {
//        return window;
//    }
//
//    /**
//     * Starts the BooleanExpEditorWindow instance "window" in a new thread.
//     */
//    public void showWindow() {
//        java.awt.EventQueue.invokeLater(this);
//    }
//
//    @Override
//    public void run() {
//        window.setVisible(false);
//    }
//}
