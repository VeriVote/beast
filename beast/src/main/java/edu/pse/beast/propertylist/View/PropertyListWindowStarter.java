package edu.pse.beast.propertylist.View;

import edu.pse.beast.celectiondescriptioneditor.GUI.CCodeEditorGUI;
import edu.pse.beast.toolbox.ObjectRefsForBuilder;

/**
*
* @author Justin
*/
public class PropertyListWindowStarter implements Runnable {
	
	private PropertyListWindow window = new PropertyListWindow();
	
	public PropertyListWindow getPropertyListWindow() {
		return window;
	}


	public void start() {

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(this);
    }

    @Override
    public void run() {
        window.setVisible(true);
    }
}
