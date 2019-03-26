package edu.pse.beast.datatypes.booleanexpast.othervaluednodes.integervaluednodes;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.TypeExpression;

/**
 *
 * @author Lukas Stapelbroek
 *
 */
public class VoteSumForCandExp extends IntegerValuedExpression {
    private final TypeExpression accessingVar;
    private final int voteArrNum;
    private final boolean unique;

    /**
     * @param voteArrNum the number of the vote array
     * @param accessingVar the accessing variable
     * @param unique whether the variable is unique
     *
     */
    public VoteSumForCandExp(int voteArrNum,
                             TypeExpression accessingVar,
                             boolean unique) {
        this.accessingVar = accessingVar;
        this.voteArrNum = voteArrNum;
        this.unique = unique;
    }

    /**
     *
     * @return the symbolic variable of this node
     */
    public TypeExpression getAccessingVariable() {
        return accessingVar;
    }

    @Override
    public void getVisited(BooleanExpNodeVisitor visitor) {
        visitor.visitVoteSumExp(this, unique);
    }

    @Override
    public String getTreeString(int depth) {
        return "Votesum" + (unique ? "Unique" : "") + " " + voteArrNum + "\n"
                + "\t\t\t\t\t\t\t\t\t\t".substring(0, depth + 1) + "var "
                + accessingVar.getTreeString(depth + 1);
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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        VoteSumForCandExp that = (VoteSumForCandExp) o;

        if (unique != that.unique) {
            return false;
        }
        if (voteArrNum != that.voteArrNum) {
            return false;
        }
        return accessingVar != null
                ? accessingVar.equals(that.accessingVar)
                        : that.accessingVar == null;
    }

    @Override
    public int hashCode() {
        int result = 31 + (accessingVar != null ? accessingVar.hashCode() : 0);
        result = 31 * result + (unique ? 1231 : 1237);
        result = 31 * result + voteArrNum;
        return result;
    }
}