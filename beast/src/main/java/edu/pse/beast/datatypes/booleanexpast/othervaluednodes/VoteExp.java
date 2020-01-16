package edu.pse.beast.datatypes.booleanexpast.othervaluednodes;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.types.InOutType;

/**
 * The Class VoteExp.
 *
 * @author Lukas Stapelbroek
 */
public class VoteExp extends AccessValueNode {

    /**
     * Instantiates a new vote exp.
     *
     * @param type  the internal type
     * @param accessingVars accessing variables
     * @param count the count of this vote
     */
    public VoteExp(final InOutType type,
                   final TypeExpression[] accessingVars,
                   final int count) {
        super(type, accessingVars, count);
    }

    @Override
    public void getVisited(final BooleanExpNodeVisitor visitor) {
        visitor.visitVoteExp(this);
    }

    @Override
    public String getTreeString(final int depth) {
        return null;
    }
}
