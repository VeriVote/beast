package edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.integerValuedNodes;

import edu.pse.beast.datatypes.booleanExpAST.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;

/**
 *
 * @author Lukas
 *
 */
public class VoteSumForCandExp extends IntegerValuedExpression {

    private final SymbolicVariable symbVar;
    private final int voteArrNum;

    /**
     *
     * @param voteArrNum the number of the vote array
     * @param symbVar the symbolic variable of this node
     */
    public VoteSumForCandExp(int voteArrNum, SymbolicVariable symbVar) {
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

    /**
     * 
     * @return the number of the vote array
     */
    public int getVoteNumber() {
        return voteArrNum;
    }

}
