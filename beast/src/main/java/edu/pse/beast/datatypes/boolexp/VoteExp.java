package edu.pse.beast.datatypes.boolexp;

import toBeImplemented.InternalTypeRep;
import toBeImplemented.SymbolicVariable;

/**
 * 
 * @author Lukas
 *
 */
public class VoteExp extends TypeExpression {

    private final SymbolicVariable symbVar;
    private final int count;
    
    /**
     * 
     * @param internalTypeRep the internal type
     * @param symbVarm the described symbolic variable
     * @param count the count of this vote
     */
    public VoteExp(InternalTypeRep internalTypeRep, SymbolicVariable symbVarm, int count) {
        super(internalTypeRep);
        this.symbVar = symbVarm;
        this.count = count;
    }
    
    /**
     * 
     * @return the described symbolic variable
     */
    public SymbolicVariable getSymbolicVar() {
        return symbVar;
    }

    /**
     * 
     * @return the count of this vote
     */
    public int getCount() {
        return count;
    }
}
