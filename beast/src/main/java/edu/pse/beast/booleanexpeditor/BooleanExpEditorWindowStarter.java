package edu.pse.beast.booleanexpeditor;

/**
 * Starts the given BooleanExpEditorWindow Class in a new thread.
 * @author Nikolai
 */
public class BooleanExpEditorWindowStarter{
    private BooleanExpEditorWindow window;

    /**
     * Constructor
     * @param window Window this class starts
     */
    public BooleanExpEditorWindowStarter(BooleanExpEditorWindow window) {
        this.window = window;
    }
    
    public BooleanExpEditorWindow getBooleanExpEditorWindow() {
        return window;
    }
    
    public void showWindow(){

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                window.setVisible(true);
            }
        });

    }
}
