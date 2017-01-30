package edu.pse.beast.propertylist;

import javax.swing.JToolBar;

import edu.pse.beast.propertylist.View.PropertyListWindow;
import edu.pse.beast.stringresource.StringResourceLoader;
import edu.pse.beast.toolbox.ActionIdAndListener;
import edu.pse.beast.toolbox.ImageResourceProvider;
import edu.pse.beast.toolbox.ToolbarHandler;

public class PropertyListToolbarHandler extends ToolbarHandler {

	private PropertyListWindow window;
	
	public PropertyListToolbarHandler(ImageResourceProvider imageRes, StringResourceLoader stringRes,
			ActionIdAndListener[] actionIdsAndListener, JToolBar toolbar, PropertyListWindow window) {
		super(imageRes, stringRes, actionIdsAndListener, toolbar);
		this.window = window;
	}

}
