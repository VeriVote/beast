package edu.pse.beast.propertylist;

import javax.swing.JToolBar;

import edu.pse.beast.propertylist.View.PropertyListWindow;
import edu.pse.beast.stringresource.StringResourceLoader;
import edu.pse.beast.toolbox.ActionIdAndListener;
import edu.pse.beast.toolbox.ImageResourceProvider;
import edu.pse.beast.toolbox.ToolbarHandler;

/**
 * Handles toolbar events for PropertyList.
 * @author Justin
 *
 */
public class PropertyListToolbarHandler extends ToolbarHandler {

    private PropertyListWindow window;

    /**
     * Constructor
     * @param imageRes
     * @param stringRes
     * @param actionIdsAndListener
     * @param toolbar
     * @param window
     */
    public PropertyListToolbarHandler(ImageResourceProvider imageRes, StringResourceLoader stringRes,
            ActionIdAndListener[] actionIdsAndListener, JToolBar toolbar, PropertyListWindow window) {
        super(imageRes, stringRes, actionIdsAndListener, toolbar);
        this.setWindow(window);
    }

	public PropertyListWindow getWindow() {
		return window;
	}

	public void setWindow(PropertyListWindow window) {
		this.window = window;
	}

}
