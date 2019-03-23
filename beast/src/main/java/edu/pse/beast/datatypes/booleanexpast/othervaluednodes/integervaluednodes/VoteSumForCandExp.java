package edu.pse.beast.datatypes.booleanexpast.othervaluednodes.integervaluednodes;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.TypeExpression;

/**
 *
 * @author Lukas
 *
 */
public class VoteSumForCandExp extends IntegerValuedExpression {
    private final TypeExpression acessingVar;
    private final int voteArrNum;
    private final boolean unique;

    /**
     * @param voteArrNum the number of the vote array
     *
     */
    public VoteSumForCandExp(int voteArrNum, TypeExpression acessingVar, boolean unique) {
        this.acessingVar = acessingVar;
        this.voteArrNum = voteArrNum;
        this.unique = unique;
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
        visitor.visitVoteSumExp(this, unique);
    }

    @Override
    public String getTreeString(int depth) {
        return "Votesum" + (unique ? "Unique" : "") + " " + voteArrNum + "\n"
                + "\t\t\t\t\t\t\t\t\t\t".substring(0, depth + 1) + "var " + acessingVar.getTreeString(depth + 1);
    }

    /**
     *
     * @return the number of the vote array
     */
    public int getVoteNumber() {
        return voteArrNum;
    }

    /**
     *
     * @return whether only unique candidate sums are taken into account
     */
    public boolean isUnique() {
        return unique;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        VoteSumForCandExp that = (VoteSumForCandExp) o;

        if (unique != that.unique)
            return false;
        if (voteArrNum != that.voteArrNum)
            return false;
        return acessingVar != null ? acessingVar.equals(that.acessingVar) : that.acessingVar == null;
    }

    @Override
    public int hashCode() {
        int result = 31 + (acessingVar != null ? acessingVar.hashCode() : 0);
        result = 31 * result + (unique ? 1231 : 1237);
        result = 31 * result + voteArrNum;
        return result;
    }
}