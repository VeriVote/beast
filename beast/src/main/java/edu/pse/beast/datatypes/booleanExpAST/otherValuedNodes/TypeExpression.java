package edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes;

import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.BooleanExpressionNode;
import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;

/**
 *
 * @author Holger Klein
 *
 */
public abstract class TypeExpression extends BooleanExpressionNode {

    private final InternalTypeContainer container;

    /**
     *
     * @param container the type of this quantor
     */
    public TypeExpression(InternalTypeContainer container) {
        this.container = container;
    }

    /**
     *
     * @return the type of this quantor
     */
    public InternalTypeContainer getInternalTypeContainer() {
        return container;
    }

    public SymbolicVariable[] getAccessVar() {
        return null;
    }
}
