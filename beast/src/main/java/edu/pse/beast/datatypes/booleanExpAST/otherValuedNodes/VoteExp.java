package edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes;

import edu.pse.beast.datatypes.booleanExpAST.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;

/**
 *
 * @author Lukas
 *
 */
public class VoteExp extends AccessValueNode {

    /**
     *
     * @param internalTypeContainer the internal type
     *
     * @param count the count of this vote
     */
    public VoteExp(InternalTypeContainer internalTypeContainer, TypeExpression[] accessingVars, int count) {
        super(internalTypeContainer, accessingVars, count);
    }

    /**
     *
     * @return the count of this vote
     */

    @Override
    public void getVisited(BooleanExpNodeVisitor visitor) {
        visitor.visitVoteExp(this);
    }
}
