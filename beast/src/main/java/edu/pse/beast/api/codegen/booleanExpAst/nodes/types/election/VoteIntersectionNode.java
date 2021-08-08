package edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election;

import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class VoteIntersectionNode extends ElectionTypeNode {
    private List<Integer> numbers = new ArrayList<Integer>();

    @Override
    public final String getTreeString(final int depth) {
        return "Intersect Votes";
    }

    public final void addVoteNumber(final int number) {
        numbers.add(number);
    }

    @Override
    public final void getVisited(final BooleanAstVisitor visitor) {
        visitor.visitVoteIntersectionNode(this);
    }

    public final List<Integer> getNumbers() {
        return numbers;
    }
}
