package edu.pse.beast.datatypes.boolexp;

import toBeImplemented.InternalTypeRep;
import toBeImplemented.SymbolicVariable;

/**
 * 
 * @author Lukas
 *
 */
public class VoteSumForCandExp extends TypeExpression {

    private final SymbolicVariable symbVar; 
    
    /**
     * 
     * @param internalTypeRep the type of this node
     * @param symbVar the symbolic variable of this node
     */
    public VoteSumForCandExp(InternalTypeRep internalTypeRep, SymbolicVariable symbVar) {
        super(internalTypeRep);
        this.symbVar = symbVar;
    }

    /**
     * 
     * @return the symbolic variable of this node
     */
    public SymbolicVariable getSymbolicVariable() {
        return symbVar;
    }

    @Override
    public void getVisited(BooleanExpNodeVisitor visitor) {
        visitor.visitVoteSumExp(this);
    }
    
}
