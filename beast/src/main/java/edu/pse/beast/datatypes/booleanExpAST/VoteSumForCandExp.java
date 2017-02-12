package edu.pse.beast.datatypes.booleanExpAST;

import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeRep;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;

/**
 * 
 * @author Lukas
 *
 */
public class VoteSumForCandExp extends TypeExpression {

    private final SymbolicVariable symbVar; 
    private final int voteArrNum;
    /**
     * 
     * @param voteArrNum
     * @param symbVar the symbolic variable of this node
     */
    public VoteSumForCandExp(int voteArrNum, SymbolicVariable symbVar) {
        super(new InternalTypeContainer(InternalTypeRep.INTEGER));
        this.symbVar = symbVar;
        this.voteArrNum = voteArrNum;
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
