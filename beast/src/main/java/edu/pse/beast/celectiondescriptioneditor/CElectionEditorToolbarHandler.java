/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.celectiondescriptioneditor;

import edu.pse.beast.celectiondescriptioneditor.View.CCodeEditorWindow;
import edu.pse.beast.highlevel.DisplaysStringsToUser;
import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.toolbox.ActionIdAndListener;
import edu.pse.beast.toolbox.ImageResourceProvider;
import edu.pse.beast.toolbox.ToolbarHandler;

/**
 * creates and updates the toolbar of a celectiondescriptioneditor. It
 * also links the useractions to the buttons
 * @author Holger-Desktop
 */
public class CElectionEditorToolbarHandler extends ToolbarHandler
        implements DisplaysStringsToUser {
    private final CCodeEditorWindow cgui;
    
    public CElectionEditorToolbarHandler(
            ActionIdAndListener[] actionIdAndListener,
            ImageResourceProvider imageRes,
            StringLoaderInterface stringif,
            CCodeEditorWindow cgui) {
        super(imageRes,
                stringif.getCElectionEditorStringResProvider().getToolbarTipStringRes(),
                actionIdAndListener, cgui.getToolBar());        
        this.cgui = cgui;
    }

    @Override
    public void updateStringRes(StringLoaderInterface stringResIF) {
        updateTooltips(
                stringResIF.getCElectionEditorStringResProvider().
                        getToolbarTipStringRes());        
    }
}
