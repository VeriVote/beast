package edu.pse.beast.booleanexpeditor.booleanExpCodeArea;

import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.autocompletion.BooleanExpAutoCompletionSrc;
import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.errorFinder.BooleanExpEditorGrammarErrorFinder;
import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.errorFinder.BooleanExpEditorVariableErrorFinder;
import edu.pse.beast.codearea.CodeArea;

import java.util.ArrayList;

/**
 * Class extending CodeArea Class of package codearea and giving it extra functionality specific to BooleanExpEditor.
 * @author Nikolai
 */
public class BooleanExpCodeArea extends CodeArea {
    private BooleanExpANTLRHandler antlrHandler;
    private BooleanExpEditorGrammarErrorFinder grammarErrorFinder;
    private BooleanExpEditorVariableErrorFinder variableErrorFinder;
    private BooleanExpAutoCompletionSrc autoCompletionSrc;

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
    }

    public ArrayList<Error> getErrors() {
        return new ArrayList<Error>();
    }
}