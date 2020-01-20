package edu.pse.beast.celectiondescriptioneditor;

//import java.util.ArrayList;
//
//import edu.pse.beast.celectiondescriptioneditor.view.CCodeEditorWindow;
//import edu.pse.beast.stringresource.StringLoaderInterface;
//import edu.pse.beast.toolbox.ActionIdAndListener;
//import edu.pse.beast.toolbox.MenuBarHandler;
//
///**
// * Creates and updates the menu bar of a C election description editor. It also
// * links the user actions to the menu items.
// *
// * @author Holger Klein
// */
//public class CElectionEditorMenubarHandler extends MenuBarHandler {
//
//    private final CCodeEditorWindow cgui;
//
//    /**
//     * Constructor for the menu bar handler.
//     *
//     * @param headingIds the ids for the headings
//     * @param cgui the code editor gui
//     * @param actionIdAndListener the action id listener
//     * @param stringif the string interface
//     */
//    public CElectionEditorMenubarHandler(String[] headingIds, CCodeEditorWindow cgui,
//                                         ArrayList<ArrayList<ActionIdAndListener>>
//                                             actionIdAndListener,
//                                         StringLoaderInterface stringif) {
//        super(headingIds, actionIdAndListener,
//              stringif.getCElectionEditorStringResProvider().getMenuStringRes());
//        this.cgui = cgui;
//        this.cgui.setMenuBar(createdMenuBar);
//    }
//
//    @Override
//    public void updateStringRes(StringLoaderInterface stringResIF) {
//        updateStringResLoader(stringResIF.getCElectionEditorStringResProvider()
//                              .getMenuStringRes());
//        cgui.setMenuBar(createdMenuBar);
//    }
//}
