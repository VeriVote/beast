package edu.pse.beast.datatypes.booleanExpAST;

import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;

/**
 * 
 * @author Lukas
 *
 */
public class VoteSumForCandExp extends TypeExpression {

    private final SymbolicVariable symbVar; 
    
    /**
     * 
     * @param internalTypeContainer the type of this node
     * @param symbVar the symbolic variable of this node
     */
    public VoteSumForCandExp(InternalTypeContainer internalTypeContainer, SymbolicVariable symbVar) {
        super(internalTypeContainer);
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
