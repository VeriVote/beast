package edu.pse.beast.booleanexpeditor.booleanExpCodeArea;

import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.errorFinder.BooleanExpEditorGrammarErrorFinder;
import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.errorFinder.BooleanExpEditorVariableErrorFinder;
import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.errorFinder.BooleanExpErrorDisplayer;
import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
import edu.pse.beast.codearea.Autocompletion.AutocompletionOption;
import edu.pse.beast.codearea.CodeArea;
import edu.pse.beast.codearea.CodeAreaBuilder;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariableList;
import edu.pse.beast.toolbox.ObjectRefsForBuilder;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Builder class to create a BooleanExpCodeArea object.
 * @author Nikolai
 */
public class BooleanExpCodeAreaBuilder extends CodeAreaBuilder {

    /**
     * Creates a BooleanExpCodeArea object with the given parameters
     * @param objectRefs the ObjectRefsForBuilder instance providing the references to needed loading interfaces
     * @param textPane the JTextPane this CodeArea controls
     * @param scrollPane the ScrollPane of said JTextPane
     * @param symbolicVariableList the SymbolicVariableList
     * @param ceditor the CElectionDescriptionEditor object
     * @return a BooleanExpCodeArea object
     */
    public BooleanExpCodeArea createBooleanExpCodeAreaObject(ObjectRefsForBuilder objectRefs,
                                                             JTextPane textPane, JScrollPane scrollPane,
                                                             SymbolicVariableList symbolicVariableList,
                                                             CElectionDescriptionEditor ceditor) {

        BooleanExpErrorDisplayer errorDisplayer = new BooleanExpErrorDisplayer(textPane, objectRefs.getStringIF());
        CodeArea tempCodeArea = super.createCodeArea(textPane, scrollPane, objectRefs, errorDisplayer);
        BooleanExpANTLRHandler antlrHandler = new BooleanExpANTLRHandler(textPane.getStyledDocument());
        BooleanExpCodeArea created =  new BooleanExpCodeArea(tempCodeArea, antlrHandler,
                new BooleanExpEditorVariableErrorFinder(antlrHandler, symbolicVariableList, ceditor),
                new BooleanExpEditorGrammarErrorFinder(antlrHandler));
        for (AutocompletionOption opt : createAutocompletionOptions()) {
            created.getAutoComplCtrl().add(opt);
        }
        return created;
    }
    
    private ArrayList<AutocompletionOption> createAutocompletionOptions() {
        ArrayList<AutocompletionOption> created = new ArrayList<>();
        created.add(new AutocompletionOption("FOR_ALL_VOTERS", "FOR_ALL_VOTERS() :", -3));
        created.add(new AutocompletionOption("FOR_ALL_CANDIDATES", "FOR_ALL_CANDIDATES() :", -3));
        created.add(new AutocompletionOption("FOR_ALL_SEATS", "FOR_ALL_SEATS() :", -3));       
        created.add(new AutocompletionOption("EXISTS_ONE_VOTER", "EXISTS_ONE_VOTER() :", -3));        
        created.add(new AutocompletionOption("EXISTS_ONE_CANDIDATE", "EXISTS_ONE_CANDIDATE() :", -3));        
        created.add(new AutocompletionOption("EXISTS_ONE_SEAT", "EXISTS_ONE_SEAT() :", -3));     
        created.add(new AutocompletionOption("VOTES", "VOTES"));     
        created.add(new AutocompletionOption("ELECT", "ELECT"));     
        created.add(new AutocompletionOption("==>", "==>"));     
        created.add(new AutocompletionOption("<==>", "<==>"));     
        created.add(new AutocompletionOption("VOTE_SUM_FOR_CANDIDATE", "VOTE_SUM_FOR_CANDIDATE()", -1));
        return created;
    }
}