package edu.pse.beast.booleanexpeditor.booleanExpCodeArea;

import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.autocompletion.BooleanExpAutoCompletionSrc;
import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.errorFinder.BooleanExpEditorGrammarErrorFinder;
import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.errorFinder.BooleanExpEditorVariableErrorFinder;
import edu.pse.beast.codearea.CodeArea;

/**
 * Class extending CodeArea Class of package codearea and giving it extra functionality specific to BooleanExpEditor.
 * @author Nikolai
 */
public class BooleanExpCodeArea extends CodeArea {
    private final BooleanExpANTLRHandler antlrHandler;
    private final BooleanExpEditorGrammarErrorFinder grammarErrorFinder;
    private final BooleanExpEditorVariableErrorFinder variableErrorFinder;
    private final BooleanExpAutoCompletionSrc autoCompletionSrc;
    private final BooleanExpSyntaxHL booleanExpSyntaxHL;

    /**
     * Constructor
     * @param codeArea the codeArea object the super constructor is called with
     * @param antlrHandler BooleanExpAntlrHandler object
     * @param variableErrorFinder the BooleanExpEditorVariableErrorFinder object
     * @param grammarErrorFinder the BooleanExpEditorGrammarErrorFinder object
     * @param autoCompletionSrc the BooleanExpEditorAutocompletionSrc object
     */
    BooleanExpCodeArea(CodeArea codeArea,
                              BooleanExpANTLRHandler antlrHandler,
                              BooleanExpEditorVariableErrorFinder variableErrorFinder,
                              BooleanExpEditorGrammarErrorFinder grammarErrorFinder,
    BooleanExpAutoCompletionSrc autoCompletionSrc) {
        super(codeArea);
        this.antlrHandler = antlrHandler;
        this.grammarErrorFinder = grammarErrorFinder;
        this.variableErrorFinder = variableErrorFinder;
        this.autoCompletionSrc = autoCompletionSrc;
        errorCtrl.addErrorFinder(grammarErrorFinder);
        errorCtrl.addErrorFinder(variableErrorFinder);
        booleanExpSyntaxHL = new BooleanExpSyntaxHL(antlrHandler, syntaxHL);
    }

    /**
     * Getter for the BooleanExpEditorGrammarErrorFinder object
     * @return grammarErrorFinder
     */
    public BooleanExpEditorGrammarErrorFinder getGrammarErrorFinder() {
        return grammarErrorFinder;
    }

    /**
     * Getter for the BooleanExpEditorVariableErrorFinder object
     * @return variableErrorFinder
     */
    public BooleanExpEditorVariableErrorFinder getVariableErrorFinder() {
        return variableErrorFinder;
    }    
     
}