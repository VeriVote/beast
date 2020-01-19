package edu.pse.beast.booleanexpeditor;

//import java.util.ArrayList;
//
//import edu.pse.beast.booleanexpeditor.view.BooleanExpEditorWindow;
//import edu.pse.beast.highlevel.DisplaysStringsToUser;
//import edu.pse.beast.stringresource.StringLoaderInterface;
//import edu.pse.beast.toolbox.ActionIdAndListener;
//import edu.pse.beast.toolbox.MenuBarHandler;
//
///**
// * MenuBarHandler for the BooleanExpEditor, inherits from edu.pse.beast.toolbox.MenuBarHandler.
// * @author Nikolai Schnell
// */
//public class BooleanExpEditorMenubarHandler
//        extends MenuBarHandler implements DisplaysStringsToUser {
//    private final BooleanExpEditorWindow window;
//
//    /**
//     * Constructor
//     * @param headerIds           the String Array of header IDs for the Menus
//     * @param window              the BooleanExpEditorWindow object whose menu this class handles
//     * @param actionIdAndListener the ActionIdAndListener ArrayListArrayList
//     *                            which contains user actions and action listeners
//     *                            for this menu
//     * @param stringif            the StringLoaderInterface to load the language
//     *                            dependent strings for this menu
//     */
//    BooleanExpEditorMenubarHandler(String[] headerIds, BooleanExpEditorWindow window,
//                                   ArrayList<ArrayList<ActionIdAndListener>>
//                                       actionIdAndListener,
//                                   StringLoaderInterface stringif) {
//        super(headerIds, actionIdAndListener,
//              stringif.getBooleanExpEditorStringResProvider()
//              .getMenuStringRes());
//        this.window = window;
//        this.window.setJMenuBar(createdMenuBar);
//    }
//
//    @Override
//    public void updateStringRes(StringLoaderInterface stringResIF) {
//        updateStringResLoader(stringResIF.getBooleanExpEditorStringResProvider()
//                              .getMenuStringRes());
//        window.setJMenuBar(createdMenuBar);
//    }
//}
