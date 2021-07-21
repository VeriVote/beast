package edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election;

import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;

public class VoteTupleNode extends ElectionTypeNode {
    private List<Integer> voteNumbers = new ArrayList<>();

    @Override
    public final String getTreeString(final int depth) {
        return "Vote Tuple";
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
