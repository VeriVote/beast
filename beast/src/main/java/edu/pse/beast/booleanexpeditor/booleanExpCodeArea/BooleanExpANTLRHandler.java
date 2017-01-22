package edu.pse.beast.booleanexpeditor.booleanExpCodeArea;

import javax.swing.text.StyledDocument;

/**
 * Class that uses precompiled ANTLR classes to analyse the code in styledDocument.
 * Used by BooleanExpEditorVariableErrorFinder and BooleanExpEditorGrammarErrorFinder.
 * @author Nikolai
 */
public class BooleanExpANTLRHandler {
    private StyledDocument styledDocument;

    /**
     * Constructor
     * @param styledDocument the StyledDocument instance to analyse
     */
    BooleanExpANTLRHandler(StyledDocument styledDocument) {
        this.styledDocument = styledDocument;
    }
}
