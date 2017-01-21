/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.celectiondescriptioneditor;

import edu.pse.beast.celectiondescriptioneditor.GUI.CCodeEditorGUI;
import edu.pse.beast.highlevel.DisplaysStringsToUser;
import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.toolbox.ActionIdAndListener;
import edu.pse.beast.toolbox.MenuBarHandler;
import java.util.ArrayList;

/**
 *
 * @author Holger-Desktop
 */
public class CElectionEditorMenubarHandler extends MenuBarHandler {
    
    CCodeEditorGUI cgui;
    
    public CElectionEditorMenubarHandler(
            String[] headingIds,
            CCodeEditorGUI cgui,
            ArrayList<ArrayList<ActionIdAndListener>> actionIdAndListener,
            StringLoaderInterface stringif) {
        super(headingIds, actionIdAndListener, stringif.getCElectionEditorStringResProvider().getMenuStringRes());
        this.cgui = cgui;
        this.cgui.setMenuBar(createdMenuBar);
    }
    
    @Override
    public void updateStringRes(StringLoaderInterface stringResIF) {
        updateStringResLoader(stringResIF.getCElectionEditorStringResProvider().getMenuStringRes());
        cgui.setMenuBar(createdMenuBar);
    }
    
}
