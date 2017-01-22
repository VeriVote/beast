package edu.pse.beast.parametereditor;

/**
 * The ParameterEditorWindowStarter is used to open the ParameterEditorWindow.
 * @author Jonas
 */
public class ParameterEditorWindowStarter implements Runnable{
    private ParameterEditorWindow window = new ParameterEditorWindow();
    /**
     * Constructor
     */
    public ParameterEditorWindowStarter() {
        
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
    public void start() {
        java.awt.EventQueue.invokeLater(this);
    }
    @Override
    public void run() {
        window.setVisible(true);
    }
}
