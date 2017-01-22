package edu.pse.beast.booleanexpeditor.booleanExpCodeArea.errorFinder;

import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.BooleanExpANTLRHandler;

/**
 * Class that finds errors which conflict with the grammar in BooleanExpGrammar.g in the BooleanExpression(s) of the
 * CodeArea this class is an attribute of.
 * @author Nikolai
 */
public class BooleanExpEditorGrammarErrorFinder {
    private BooleanExpANTLRHandler antlrHandler;

    /**
     * Constructor
     * @param antlrHandler the BooleanExpAntlrHandler this class uses to find grammar errors
     */
    public BooleanExpEditorGrammarErrorFinder(BooleanExpANTLRHandler antlrHandler) {
        this.antlrHandler = antlrHandler;
    }
}
