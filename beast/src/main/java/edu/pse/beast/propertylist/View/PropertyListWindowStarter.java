package edu.pse.beast.propertylist.View;

import edu.pse.beast.propertylist.PLControllerInterface;
import edu.pse.beast.propertylist.PropertyList;
import edu.pse.beast.propertylist.Model.PLModelInterface;
import edu.pse.beast.toolbox.ObjectRefsForBuilder;

/**
*
* @author Justin
*/
public class PropertyListWindowStarter implements Runnable {
	
	private PropertyListWindow window;
	
	public PropertyListWindowStarter(PLControllerInterface controller, PLModelInterface model) {
		window = new PropertyListWindow(controller, model);
	}
	
	public PropertyListWindow getPropertyListWindow() {
		return window;
	}
	public PropertyListWindow getPropertyListWindow(PropertyList list) {
		//window.setPropertyList(list);
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
