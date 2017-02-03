/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea;

import edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.ErrorHandling.CErrorDisplayer;
import edu.pse.beast.codearea.CodeAreaBuilder;
import edu.pse.beast.codearea.ErrorHandling.ErrorDisplayer;
import edu.pse.beast.toolbox.ObjectRefsForBuilder;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

/**
 *
 * @author Holger-Desktop
 */
public class CElectionCodeAreaBuilder extends CodeAreaBuilder {
    
    private ObjectRefsForBuilder refs;
    
    public CElectionCodeAreaBuilder(ObjectRefsForBuilder objectRefsForBuilder) {
        this.refs = refs;
    }
    
    public CElectionCodeArea createCElectionCodeArea(JTextPane codeArea, JScrollPane codeAreaScrollPane, CErrorDisplayer displayer) {
        return new CElectionCodeArea(createCodeArea(codeArea, codeAreaScrollPane,
                refs, displayer));
    }
}
