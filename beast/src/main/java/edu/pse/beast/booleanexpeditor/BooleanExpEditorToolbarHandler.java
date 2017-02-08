package edu.pse.beast.booleanexpeditor;

import edu.pse.beast.highlevel.DisplaysStringsToUser;
import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.stringresource.StringResourceLoader;
import edu.pse.beast.toolbox.ActionIdAndListener;
import edu.pse.beast.toolbox.ImageResourceProvider;
import edu.pse.beast.toolbox.ToolbarHandler;

import javax.swing.*;

/**
 * Class that extends ToolbarHandler.
 * Its purpose is the handling of a BooleanExpEditorWindows toolbar.
 * @author NikolaiLMS
 */
public class BooleanExpEditorToolbarHandler extends ToolbarHandler implements DisplaysStringsToUser{
    private final BooleanExpEditorWindow window;

    /**
     * Constructor
     * @param window the BooleanExpEditorWindow whos toolbar this class handles
     * @param imageRes the ImageResourceProvider that provides the images necessary for the toolbar buttons
     * @param stringRes the StringResourceLoader that provides the String necessary for the ToolTips of the toolbar
     * @param actionIdsAndListener the Array of ActionIdAndListeners that contains UserActions and ActionListeners for
     *                             this toolbars Buttons
     */
    BooleanExpEditorToolbarHandler(BooleanExpEditorWindow window, ImageResourceProvider imageRes,
                                          StringResourceLoader stringRes, ActionIdAndListener[] actionIdsAndListener) {
        super(imageRes, stringRes, actionIdsAndListener, window.getToolbar());
        this.window = window;
    }

    @Override
    public void updateStringRes(StringLoaderInterface stringResIF) {
        updateTooltips(stringResIF.getBooleanExpEditorStringResProvider().getToolbarTipStringRes());
    }
}
