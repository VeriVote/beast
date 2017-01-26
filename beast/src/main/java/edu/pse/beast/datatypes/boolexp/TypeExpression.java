package edu.pse.beast.datatypes.boolexp;

import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import toBeImplemented.InternalTypeRep;

/**
 * 
 * @author Lukas
 *
 */
public abstract class TypeExpression extends BooleanExpressionNode {

    private final InternalTypeContainer container;

    /**
     * 
     * @param internalTypeRep the type of this quantor
     */
    public TypeExpression(InternalTypeContainer container) {
        this.container = container;
    }

    /**
     * 
     * @return the type of this quantor
     */
    public InternalTypeContainer getInternalTypeRep() {
        return container;
    }
}
