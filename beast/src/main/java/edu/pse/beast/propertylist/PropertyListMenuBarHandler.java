package edu.pse.beast.propertylist;

import java.util.ArrayList;

import edu.pse.beast.propertylist.View.PropertyListWindow;
import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.stringresource.StringResourceLoader;
import edu.pse.beast.toolbox.ActionIdAndListener;
import edu.pse.beast.toolbox.MenuBarHandler;

public class PropertyListMenuBarHandler extends MenuBarHandler {

    private PropertyListWindow window;

    public PropertyListMenuBarHandler(String[] headingIds,
            ArrayList<ArrayList<ActionIdAndListener>> actionIDAndListener, StringResourceLoader resLoader, PropertyListWindow window) {
        super(headingIds, actionIDAndListener, resLoader);
        this.window = window;
        this.window.setJMenuBar(createdMenuBar);
    }

    @Override
    public void updateStringRes(StringLoaderInterface stringResIF) {
        updateStringResLoader(stringResIF.getPropertyListStringResProvider().getMenuStringRes());
        window.setJMenuBar(createdMenuBar);

    }

}
