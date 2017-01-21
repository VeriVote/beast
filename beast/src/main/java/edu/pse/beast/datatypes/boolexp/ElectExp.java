package edu.pse.beast.datatypes.boolexp;

import toBeImplemented.InternalTypeRep;

/**
 * 
 * @author Lukas
 *
 */
public class ElectExp extends TypeExpression {

    private final int count;
    
    /**
     * 
     * @param internalTypeRep the type of this node
     * @param count the count of this vote expression
     */
    public ElectExp(InternalTypeRep internalTypeRep, int count) {
        super(internalTypeRep);
        this.count = count;
    }
    
    /**
     * 
     * @return the count of this vote expression
     */
    public int getCount() {
        return count;
    }

}
