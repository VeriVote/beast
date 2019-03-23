//package edu.pse.beast.propertylist;
//
//import java.util.ArrayList;
//
//import edu.pse.beast.propertylist.view.PropertyListWindow;
//import edu.pse.beast.stringresource.StringLoaderInterface;
//import edu.pse.beast.stringresource.StringResourceLoader;
//import edu.pse.beast.toolbox.ActionIdAndListener;
//import edu.pse.beast.toolbox.MenuBarHandler;
//
///**
// * Handles menu events for PropertyList.
// * @author Justin
// */
//public class PropertyListMenuBarHandler extends MenuBarHandler {
//
//    private PropertyListWindow window;
//
//    /**
//     * Constructor
//     * @param headingIds The headings of the menu strings
//     * @param actionIDAndListener The action IDs and the listener for those actions
//     * @param resLoader The string resource loader for the language specific strings
//     * @param window The view of PropertyList
//     */
//    public PropertyListMenuBarHandler(String[] headingIds,
//            ArrayList<ArrayList<ActionIdAndListener>> actionIDAndListener, StringResourceLoader resLoader, PropertyListWindow window) {
//        super(headingIds, actionIDAndListener, resLoader);
//        this.window = window;
//        this.window.setJMenuBar(createdMenuBar);
//    }
//
//    @Override
//    public void updateStringRes(StringLoaderInterface stringResIF) {
//        updateStringResLoader(stringResIF.getPropertyListStringResProvider().getMenuStringRes());
//        window.setJMenuBar(createdMenuBar);
//    }
//
//}
