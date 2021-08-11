package edu.pse.beast.api.codegen.ast.expression.type.election;

import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.api.codegen.ast.BooleanAstVisitor;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class VoteTupleNode extends ElectionTypeNode {
    private static final String VOTE_TUPLE_TAG = "Vote Tuple";

    private List<Integer> voteNumbers = new ArrayList<Integer>();

    @Override
    public final String getTreeString(final int depth) {
        return VOTE_TUPLE_TAG;
    }

    public final List<Integer> getNumbers() {
        return voteNumbers;
    }

    public final void addVoteNumber(final int number) {
        voteNumbers.add(number);
    }

    @Override
    public final void getVisited(final BooleanAstVisitor visitor) {
        visitor.visitVoteTuple(this);
    }
}
