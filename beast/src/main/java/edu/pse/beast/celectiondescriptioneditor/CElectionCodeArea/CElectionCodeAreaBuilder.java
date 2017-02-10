/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea;

import edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.ErrorHandling.CErrorDisplayer;
import edu.pse.beast.codearea.Autocompletion.AutocompletionOption;
import edu.pse.beast.codearea.CodeAreaBuilder;
import edu.pse.beast.codearea.ErrorHandling.ErrorDisplayer;
import edu.pse.beast.toolbox.ObjectRefsForBuilder;
import java.util.ArrayList;
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
        CElectionCodeArea created = new CElectionCodeArea(createCodeArea(codeArea, codeAreaScrollPane,
                refs, displayer));    
        for(AutocompletionOption opt : createAutocompletionOptions()) {
            created.getAutoComplCtrl().add(opt);
        }
        return created;
    }
    
    private ArrayList<AutocompletionOption> createAutocompletionOptions() {
        ArrayList<AutocompletionOption> created = new ArrayList<>();
        created.add(new AutocompletionOption("for", "for(unsigned int i = 0; i < V; ++i)"));
        created.add(new AutocompletionOption("for", "for(unsigned int i = 0; i < C; ++i)"));
        created.add(new AutocompletionOption("for", "for(unsigned int i = 0; i < S; ++i)"));        
        return created;
    }

}
