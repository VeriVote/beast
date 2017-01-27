package edu.pse.beast.datatypes.boolexp;

import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;


/**
 * 
 * @author Lukas
 *
 */
public class VoteExp extends TypeExpression {

    private final SymbolicVariable[] accesVars;
    private final int count;
    
    /**
     * 
     * @param internalTypeRep the internal type
     * @param symbVarm the described symbolic variable
     * @param count the count of this vote
     */
    public VoteExp(InternalTypeContainer internalTypeContainer, SymbolicVariable[] symbVarm, int count) {
        super(internalTypeContainer);
        this.accesVars = symbVarm;
        this.count = count;
    }
    
    /**
     * 
     * @return the described symbolic variable
     */
    public SymbolicVariable[] getAccessVar() {
        return accesVars;
    }

    /**
     * 
     * @return the count of this vote
     */
    public int getCount() {
        return count;
    }

    @Override
    public void getVisited(BooleanExpNodeVisitor visitor) {
        visitor.visitVoteExp(this);
    }
}
