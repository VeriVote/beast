/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.celectiondescriptioneditor;

import edu.pse.beast.celectiondescriptioneditor.GUI.CEditorWindowStarter;

/**
 *
 * @author Holger-Desktop
 */
public class CElectionDescriptionEditorBuilder {
    
    private final String[] menuHeadingIds = {"file", "edit", "editor", "code"};
    
    public CElectionDescriptionEditor createCElectionDescriptionEditor() {
        CEditorWindowStarter starter = new CEditorWindowStarter();
        CElectionEditorMenubarHandler menuBarHandler = 
                new CElectionEditorMenubarHandler(
                        menuHeadingIds,
                        starter.getGUIWindow(),
                        actionIdAndListener,
                        stringif)
    }
    
    
}
