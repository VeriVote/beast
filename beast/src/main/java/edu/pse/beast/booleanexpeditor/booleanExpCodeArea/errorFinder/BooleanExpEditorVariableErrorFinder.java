package edu.pse.beast.booleanexpeditor.booleanExpCodeArea.errorFinder;

import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.BooleanExpANTLRHandler;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;

/**
 * CLass for finding typeerrors in symbolic variable comparison operations in the BooleanExpression(s) of the CodeArea
 * this class is an attribute of.
 * @author Nikolai
 */
public class BooleanExpEditorVariableErrorFinder {
    private BooleanExpANTLRHandler antlrHandler;
    private PostAndPrePropertiesDescription description;

    /**
     * Constructor
     * @param antlrHandler BooleanExpEditorANTLRHandler object
     */
    public BooleanExpEditorVariableErrorFinder(BooleanExpANTLRHandler antlrHandler) {
        this.antlrHandler = antlrHandler;
    }
}
