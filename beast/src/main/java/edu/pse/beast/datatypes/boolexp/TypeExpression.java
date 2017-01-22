package edu.pse.beast.datatypes.boolexp;

import toBeImplemented.InternalTypeRep;

/**
 * 
 * @author Lukas
 *
 */
public abstract class TypeExpression extends BooleanExpressionNode {

    private final InternalTypeRep internalTypeRep;

    /**
     * 
     * @param internalTypeRep the type of this quantor
     */
    public TypeExpression(InternalTypeRep internalTypeRep) {
        this.internalTypeRep = internalTypeRep;
    }

    /**
     * 
     * @return the type of this quantor
     */
    public InternalTypeRep getInternalTypeRep() {
        return internalTypeRep;
    }
}
