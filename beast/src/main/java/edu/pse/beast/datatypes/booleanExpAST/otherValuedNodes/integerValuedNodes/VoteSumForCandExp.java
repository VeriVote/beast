package edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.integerValuedNodes;

import edu.pse.beast.datatypes.booleanExpAST.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.TypeExpression;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;

/**
 *
 * @author Lukas
 *
 */
public class VoteSumForCandExp extends IntegerValuedExpression {

    private final TypeExpression acessingVar;
    private final int voteArrNum;

    /**
     *  @param voteArrNum the number of the vote array
     *
     */
    public VoteSumForCandExp(int voteArrNum, TypeExpression acessingVar) {
        this.acessingVar = acessingVar;
        this.voteArrNum = voteArrNum;
    }

    /**
     *
     * @return the symbolic variable of this node
     */
    public TypeExpression getAccesingVariable() {
        return acessingVar;
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
