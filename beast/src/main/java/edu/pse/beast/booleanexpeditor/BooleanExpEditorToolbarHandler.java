package edu.pse.beast.booleanexpeditor;

import edu.pse.beast.highlevel.DisplaysStringsToUser;
import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.stringresource.StringResourceLoader;
import edu.pse.beast.toolbox.ActionIdAndListener;
import edu.pse.beast.toolbox.ImageResourceProvider;
import edu.pse.beast.toolbox.ToolbarHandler;

import javax.swing.*;

/**
 * @author NikolaiLMS
 */
public class BooleanExpEditorToolbarHandler extends ToolbarHandler implements DisplaysStringsToUser{
    private BooleanExpEditorWindow window;
    BooleanExpEditorToolbarHandler(BooleanExpEditorWindow window, ImageResourceProvider imageRes,
                                          StringResourceLoader stringRes, ActionIdAndListener[] actionIdsAndListener) {
        super(imageRes, stringRes, actionIdsAndListener, window.getToolbar());
        this.window = window;
    }

    @Override
    public void updateStringRes(StringLoaderInterface stringResIF) {
        updateTooltips(
                stringResIF.getBooleanExpEditorStringResProvider().getToolbarTipStringRes());
    }
}
