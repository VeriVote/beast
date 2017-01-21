package edu.pse.beast.datatypes.boolexp;

import toBeImplemented.BooleanExpConstant;
import toBeImplemented.InternalTypeRep;

/**
 * 
 * @author Lukas
 *
 */
public class ConstantExp extends TypeExpression {

    private final BooleanExpConstant constant;
    
    /**
     * 
     * @param internalTypeRep the type of this node
     * @param constant the constant saved in this node
     */
    public ConstantExp(InternalTypeRep internalTypeRep, BooleanExpConstant constant) {
        super(internalTypeRep);
        this.constant = constant;
    }

    /**
     * 
     * @return the constant saved in this node
     */
    public BooleanExpConstant getConstant() {
        return constant;
    }
}
