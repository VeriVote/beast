package edu.pse.beast.parametereditor;

/**
 * The ParameterEditorWindowStarter is used to open the ParameterEditorWindow.
 * @author Jonas
 */
public class ParameterEditorWindowStarter {
    private ParameterEditorWindow window;
    /**
     * Constructor
     * @param window corresponding editor window
     */
    ParameterEditorWindowStarter(ParameterEditorWindow window) {
        this.window = window;
    }
    /**
     * Getter for the corresponding ParameterEditorWindow
     * @return window
     */
    ParameterEditorWindow getParameterEditorWindow() {
        return window;
    }
    /**
     * Makes the corresponding window visible.
     */
    void showWindow() {
        window.setVisible(true);
    }
}
