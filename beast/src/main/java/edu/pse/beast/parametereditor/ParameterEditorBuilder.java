/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.parametereditor;

import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
import edu.pse.beast.propertylist.PropertyList;
import edu.pse.beast.toolbox.ObjectRefsForBuilder;

/**
 *
 * @author Holger-Desktop
 */
public class ParameterEditorBuilder {
    
    
    public ParameterEditor createParameterEditor(
            ObjectRefsForBuilder refs,
            CElectionDescriptionEditor cElectionDescriptionEditor,
            PropertyList propertyList) {
        
        //create actions and so on...
        return new ParameterEditor(cElectionDescriptionEditor, propertyList);
    }
}
