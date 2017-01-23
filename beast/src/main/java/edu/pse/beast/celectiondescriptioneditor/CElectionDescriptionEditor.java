/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.celectiondescriptioneditor;

import edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.CElectionCodeArea;
import edu.pse.beast.celectiondescriptioneditor.GUI.CCodeEditorGUI;
import edu.pse.beast.codearea.SyntaxHL.RegexAndColor;
import edu.pse.beast.datatypes.descofvoting.ElectionDescription;
import edu.pse.beast.highlevel.ElectionDescriptionSource;

import java.awt.*;
import java.util.ArrayList;

/**
 *  
 * @author Holger Klein
 */
public class CElectionDescriptionEditor implements ElectionDescriptionSource{
    private CElectionCodeArea codeArea;
    private ElectionDescription currentDescription;
    private CCodeEditorGUI gui;
    
    public CElectionDescriptionEditor(CElectionCodeArea codeArea, CCodeEditorGUI gui) {
        this.codeArea = codeArea;
        this.gui = gui;

        // TEMPORARY EXAMPLE OF ADDING SYNTAXHL TO A CODEAREA
        ArrayList<RegexAndColor> regexAndColorList = new ArrayList<RegexAndColor>();
        regexAndColorList.add(new RegexAndColor("for|while|if|return", Color.RED));
        codeArea.setSyntaxHLRegexAndColorList(regexAndColorList);
    }

    @Override
    public ElectionDescription getElectionDescription() {
        updateCurrentDescription();
        return currentDescription;
    }
    
    
    private void updateCurrentDescription() {
        
    }
}
