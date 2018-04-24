//package edu.pse.beast.parametereditor;
//
//import java.util.ArrayList;
//
//import edu.pse.beast.parametereditor.View.ParameterEditorWindow;
//import edu.pse.beast.stringresource.StringLoaderInterface;
//import edu.pse.beast.stringresource.StringResourceLoader;
//import edu.pse.beast.toolbox.ActionIdAndListener;
//import edu.pse.beast.toolbox.MenuBarHandler;
///**
// * The ParameterEditorMenuBarHandler creates the menubar for the ParameterEditor.
// * @author Jonas
// */
//public class ParameterEditorMenuBarHandler extends MenuBarHandler {
//    private final ParameterEditorWindow window;
//    /**
//     * Creates the menubar and applies it to the ParameterEditorWindow
//     * @param headingIds headings for the different slots on the menu
//     * @param actionIDAndListener list of ActionIdAndListeners
//     * @param resLoader loader for the resources
//     * @param window ParameterEditorWindow
//     */
//    public ParameterEditorMenuBarHandler(String[] headingIds,
//    ArrayList<ArrayList<ActionIdAndListener>> actionIDAndListener, 
//    StringResourceLoader resLoader, ParameterEditorWindow window) {
//        super(headingIds, actionIDAndListener, resLoader);
//                this.window = window;
//                this.window.setJMenuBar(createdMenuBar);
//    }
//    @Override
//    public void updateStringRes(StringLoaderInterface stringResIF) {
//        updateStringResLoader(stringResIF.getParameterEditorStringResProvider().getMenuStringRes());
//        window.setJMenuBar(createdMenuBar);
//    }
//    
//}
