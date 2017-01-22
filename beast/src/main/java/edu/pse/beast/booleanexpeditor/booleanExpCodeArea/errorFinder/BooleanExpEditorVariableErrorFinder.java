package edu.pse.beast.booleanexpeditor.booleanExpCodeArea.errorFinder;

import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.BooleanExpANTLRHandler;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;

/**
 * Class for finding type-errors in symbolic variable usage in the BooleanExpression(s) of the CodeArea
 * this class is an attribute of.
 * @author Nikolai
 */
public class BooleanExpEditorVariableErrorFinder {
    private BooleanExpANTLRHandler antlrHandler;
    private PostAndPrePropertiesDescription description;

    /**
     * Constructor
     * @param antlrHandler BooleanExpEditorANTLRHandler object this class uses to find errors
     */
    public BooleanExpEditorVariableErrorFinder(BooleanExpANTLRHandler antlrHandler) {
        this.antlrHandler = antlrHandler;
    }
}
