/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea;

import edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.Antlr.CAntlrHandler;
import edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.ErrorHandling.CErrorDisplayer;
import edu.pse.beast.codearea.Autocompletion.AutocompletionOption;
import edu.pse.beast.codearea.CodeAreaBuilder;
import edu.pse.beast.toolbox.ObjectRefsForBuilder;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

/**
 * creates a celectioncodearea object. It uses its parent class Codeareabuilder
 * to first create a simple codearea and use this to instantiate most
 * fields of the celectioncodearea
 * @author Holger-Desktop
 */
public class CElectionCodeAreaBuilder extends CodeAreaBuilder {
    
    private final ObjectRefsForBuilder refs;
    
    public CElectionCodeAreaBuilder(ObjectRefsForBuilder objectRefsForBuilder) {
        this.refs = objectRefsForBuilder;
    }
    
    /**
     * creates and returns a celectioncodearea
     * @param codeArea the JTextPane used to display text and communicate
     * with the user
     * @param codeAreaScrollPane the component which scrolls the pane
     * @param displayer the specific errordisplayer which knows
     * how to create errors created by the cerrorfinders
     * @return the created celectioncodearea
     */
    public CElectionCodeArea createCElectionCodeArea(JTextPane codeArea, JScrollPane codeAreaScrollPane, CErrorDisplayer displayer) {
        CElectionCodeArea created = new CElectionCodeArea(createCodeArea(codeArea, codeAreaScrollPane,
                refs, displayer));    
        refs.getLanguageOpts().addStringDisplayer(displayer);
        for(AutocompletionOption opt : createAutocompletionOptions()) {
            created.getAutoComplCtrl().add(opt);
        }
        return created;
    }
    
    private ArrayList<AutocompletionOption> createAutocompletionOptions() {
        ArrayList<AutocompletionOption> created = new ArrayList<>();
        created.add(new AutocompletionOption("for", "for(unsigned int i = 0; i < V; ++i) {\n"));
        created.add(new AutocompletionOption("for", "for(unsigned int i = 0; i < C; ++i) {\n"));
        created.add(new AutocompletionOption("for", "for(unsigned int i = 0; i < S; ++i) {\n"));   
        CAntlrHandler antlrHandler = new CAntlrHandler(new JTextPane());
        for(String s : antlrHandler.getTypeLiterals()) {
            created.add(new AutocompletionOption(s,s));
        }
        for(String s : antlrHandler.getControllLiterals()) {
            created.add(new AutocompletionOption(s,s));
        }
        return created;
    }

}
