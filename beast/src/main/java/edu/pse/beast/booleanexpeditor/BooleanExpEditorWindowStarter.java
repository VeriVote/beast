package edu.pse.beast.booleanexpeditor;

/**
 * Starts the given BooleanExpEditorWindow Class in a new thread.
 * @author Nikolai
 */
public class BooleanExpEditorWindowStarter{
    private BooleanExpEditorWindow window;

    /**
     * Constructor
     * @param window BooleanExpEditorWindow object this class starts
     */
    protected BooleanExpEditorWindowStarter(BooleanExpEditorWindow window) {
        this.window = window;
    }

    /**
     * Getter
     * @return BooleanExpEditorWindow instance of this class
     */
    public BooleanExpEditorWindow getBooleanExpEditorWindow() {
        return window;
    }

    /**
     * Starts the BooleanExpEditorWindow instance "window" in a new thread.
     */
    protected void showWindow(){

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                window.setVisible(true);
            }
        });

    }
}
