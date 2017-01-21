package edu.pse.beast.booleanexpeditor.booleanExpCodeArea;

import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.autocompletion.BooleanExpAutoCompletionSrc;
import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.errorFinder.BooleanExpEditorGrammarErrorFinder;
import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.errorFinder.BooleanExpEditorVariableErrorFinder;
import edu.pse.beast.codearea.CodeArea;
import edu.pse.beast.codearea.CodeAreaBuilder;
import edu.pse.beast.toolbox.ObjectRefsForBuilder;

import javax.swing.*;

/**
 * Builder class to create a BooleanExpCodeArea object.
 * @author Nikolai
 */
public class BooleanExpCodeAreaBuilder extends CodeAreaBuilder{

    /**
     * Creates a BooleanExpCodeArea object with the given parameters
     * @param objectRefs
     * @param textPane
     * @param scrollPane
     * @return
     */
    public BooleanExpCodeArea createBooleanExpCodeAreaObject(ObjectRefsForBuilder objectRefs,
                                                                 JTextPane textPane, JScrollPane scrollPane) {
        CodeArea tempCodeArea = super.createCodeArea(textPane, scrollPane, objectRefs);
        BooleanExpANTLRHandler antlrHandler = new BooleanExpANTLRHandler(textPane.getStyledDocument());
        return new BooleanExpCodeArea(tempCodeArea, antlrHandler,
                new BooleanExpEditorVariableErrorFinder(antlrHandler),
                new BooleanExpEditorGrammarErrorFinder(antlrHandler),
                new BooleanExpAutoCompletionSrc());
    }
}