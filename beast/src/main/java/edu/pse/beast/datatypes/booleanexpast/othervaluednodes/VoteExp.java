package edu.pse.beast.datatypes.booleanexpast.othervaluednodes;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.types.InOutType;

/**
 *
 * @author Lukas Stapelbroek
 *
 */
public class VoteExp extends AccessValueNode {
    /**
     *
     * @param type  the internal type
     * @param accessingVars accessing variables
     * @param count the count of this vote
     * @return the count of this vote
     */
    public VoteExp(InOutType type, TypeExpression[] accessingVars, int count) {
        super(type, accessingVars, count);
    }

    @Override
    public void getVisited(BooleanExpNodeVisitor visitor) {
        visitor.visitVoteExp(this);
    }

    @Override
    public String getTreeString(int depth) {
        return null;
    }
}