package edu.pse.beast.booleanexpeditor.booleanExpCodeArea;

import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.autocompletion.BooleanExpAutoCompletionSrc;
import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.errorFinder.BooleanExpEditorGrammarErrorFinder;
import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.errorFinder.BooleanExpEditorVariableErrorFinder;
import edu.pse.beast.codearea.CodeArea;
import edu.pse.beast.codearea.CodeAreaBuilder;
import edu.pse.beast.codearea.TextLineNumber;
import edu.pse.beast.toolbox.ObjectRefsForBuilder;

import javax.swing.*;

/**
 * Class extending CodeArea Class of package codearea and giving it extra functionality specific to BooleanExpEditor.
 * @author Nikolai
 */
public class BooleanExpCodeArea extends CodeArea{
    private BooleanExpANTLRHandler antlrHandler;
    private BooleanExpEditorGrammarErrorFinder grammarErrorFinder;
    private BooleanExpEditorVariableErrorFinder variableErrorFinder;
    private BooleanExpAutoCompletionSrc autoCompletionSrc;
    /**
     * Constructor //TODO JAVA DOC
     * @param textPane s
     * @param scrollPane s
     * @param objectRefs s
     * @param antlrHandler s
     * @param variableErrorFinder s
     * @param grammarErrorFinder s
     */
    public BooleanExpCodeArea(CodeArea codeArea,
                              BooleanExpANTLRHandler antlrHandler,
                              BooleanExpEditorVariableErrorFinder variableErrorFinder,
                              BooleanExpEditorGrammarErrorFinder grammarErrorFinder,
    BooleanExpAutoCompletionSrc autoCompletionSrc) {
        super(codeArea);
        this.antlrHandler = antlrHandler;
        this.grammarErrorFinder = grammarErrorFinder;
        this.variableErrorFinder = variableErrorFinder;
        this.autoCompletionSrc = autoCompletionSrc;
    }
}