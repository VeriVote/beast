package edu.pse.beast.celectiondescriptioneditor;

//import edu.pse.beast.celectiondescriptioneditor.view.CCodeEditorWindow;
//import edu.pse.beast.highlevel.DisplaysStringsToUser;
//import edu.pse.beast.stringresource.StringLoaderInterface;
//import edu.pse.beast.toolbox.ActionIdAndListener;
//import edu.pse.beast.toolbox.ImageResourceProvider;
//import edu.pse.beast.toolbox.ToolbarHandler;
//
///**
// * Creates and updates the toolbar of a C election description editor. It also
// * links the user actions to the buttons.
// *
// * @author Holger Klein
// */
//public class CElectionEditorToolbarHandler
//        extends ToolbarHandler implements DisplaysStringsToUser {
//    private final CCodeEditorWindow cgui;
//
//    /**
//     * Constructor.
//     *
//     * @param actionIdAndListener the listener
//     * @param imageRes the image provider
//     * @param stringif the string interface
//     * @param cgui the c editor gui
//     */
//    public CElectionEditorToolbarHandler(ActionIdAndListener[] actionIdAndListener,
//                                         ImageResourceProvider imageRes,
//                                         StringLoaderInterface stringif,
//                                         CCodeEditorWindow cgui) {
//        super(imageRes,
//              stringif.getCElectionEditorStringResProvider().getToolbarTipStringRes(),
//              actionIdAndListener,
//              cgui.getToolBar());
//        this.cgui = cgui;
//    }
//
//    @Override
//    public void updateStringRes(StringLoaderInterface stringResIF) {
//        updateTooltips(stringResIF.getCElectionEditorStringResProvider()
//                       .getToolbarTipStringRes());
//    }
//}
