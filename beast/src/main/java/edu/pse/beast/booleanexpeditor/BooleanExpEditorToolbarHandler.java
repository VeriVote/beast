//package edu.pse.beast.booleanexpeditor;
//
//import edu.pse.beast.booleanexpeditor.view.BooleanExpEditorWindow;
//import edu.pse.beast.highlevel.DisplaysStringsToUser;
//import edu.pse.beast.stringresource.StringLoaderInterface;
//import edu.pse.beast.stringresource.StringResourceLoader;
//import edu.pse.beast.toolbox.ActionIdAndListener;
//import edu.pse.beast.toolbox.ImageResourceProvider;
//import edu.pse.beast.toolbox.ToolbarHandler;
//
///**
// * Class that extends ToolbarHandler.
// * Its purpose is the handling of a BooleanExpEditorWindows toolbar.
// * @author Nikolai Schnell
// */
//public class BooleanExpEditorToolbarHandler
//            extends ToolbarHandler implements DisplaysStringsToUser {
//    /**
//     * Constructor
//     * @param window               the BooleanExpEditorWindow containing
//     *                             the ToolBar which this class handles
//     * @param imageRes             the ImageResourceProvider that provides
//     *                             the images necessary for the tool-bar buttons
//     * @param stringRes            the StringResourceLoader that provides
//     *                             the String necessary for the ToolTips
//     *                             of the tool-bar
//     * @param actionIdsAndListener the array of ActionIdAndListeners that
//     *                             contains UserActions and ActionListeners
//     *                             for this tool-bar's Buttons
//     */
//    BooleanExpEditorToolbarHandler(BooleanExpEditorWindow window,
//                                   ImageResourceProvider imageRes,
//                                   StringResourceLoader stringRes,
//                                   ActionIdAndListener[] actionIdsAndListener) {
//        super(imageRes, stringRes, actionIdsAndListener, window.getToolbar());
//    }
//
//    @Override
//    public void updateStringRes(StringLoaderInterface stringResIF) {
//        updateTooltips(
//            stringResIF.getBooleanExpEditorStringResProvider()
//            .getToolbarTipStringRes()
//        );
//    }
//}