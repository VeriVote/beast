//package edu.pse.beast.parametereditor.view;
//
///**
// * The ParameterEditorWindowStarter is used to open the ParameterEditorWindow.
// * @author Jonas Wohnig
// */
//public class ParameterEditorWindowStarter implements Runnable {
//    private final ParameterEditorWindow window = new ParameterEditorWindow();
//    /**
//     * Constructor
//     */
//    public ParameterEditorWindowStarter() {
//    }
//
//    /**
//     * Getter for the corresponding ParameterEditorWindow
//     * @return window
//     */
//    public ParameterEditorWindow getParameterEditorWindow() {
//        return window;
//    }
//
//    /**
//     * Makes the corresponding window visible.
//     */
//    public void start() {
//        java.awt.EventQueue.invokeLater(this);
//    }
//
//    @Override
//    public void run() {
//        window.setVisible(true);
//    }
//}