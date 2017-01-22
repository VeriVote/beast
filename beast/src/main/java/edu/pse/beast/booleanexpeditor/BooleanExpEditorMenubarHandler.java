package edu.pse.beast.booleanexpeditor;

import edu.pse.beast.celectiondescriptioneditor.GUI.CCodeEditorGUI;
import edu.pse.beast.highlevel.DisplaysStringsToUser;
import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.toolbox.ActionIdAndListener;
import edu.pse.beast.toolbox.MenuBarHandler;

import java.util.ArrayList;

/**
 * MenuBarHandler for the BooleanExpEditor.
 * @author NikolaiLMS
 */
public class BooleanExpEditorMenubarHandler extends MenuBarHandler implements DisplaysStringsToUser{
    private BooleanExpEditorWindow window;

    public BooleanExpEditorMenubarHandler(String[] headingIds, BooleanExpEditorWindow window,
            ArrayList<ArrayList<ActionIdAndListener>> actionIdAndListener, StringLoaderInterface stringif) {
        super(headingIds, actionIdAndListener, stringif.getBooleanExpEditorStringResProvider().getMenuStringRes());
        this.window = window;
        this.window.setJMenuBar(createdMenuBar);
    }

    @Override
    public void updateStringRes(StringLoaderInterface stringResIF) {
        updateStringResLoader(stringResIF.getBooleanExpEditorStringResProvider().getMenuStringRes());
        window.setJMenuBar(createdMenuBar);
    }
}
