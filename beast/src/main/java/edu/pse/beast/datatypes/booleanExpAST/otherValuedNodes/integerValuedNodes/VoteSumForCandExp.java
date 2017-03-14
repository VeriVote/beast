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

    @Override
    public String getTreeString(int depth) {
        return "Votesum " + voteArrNum + "\n" +
                "\t\t\t\t\t\t\t\t\t\t".substring(0,depth + 1 ) +
                "var " + acessingVar.getTreeString(depth + 1);
    }

    /**
     * 
     * @return the number of the vote array
     */
    public int getVoteNumber() {
        return voteArrNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        VoteSumForCandExp that = (VoteSumForCandExp) o;

        if (voteArrNum != that.voteArrNum) return false;
        return acessingVar != null ? acessingVar.equals(that.acessingVar) : that.acessingVar == null;
    }

    @Override
    public int hashCode() {
        int result = acessingVar != null ? acessingVar.hashCode() : 0;
        result = 31 * result + voteArrNum;
        return result;
    }
}
