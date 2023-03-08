package edu.kit.kastel.formal.beast.api.codegen.ast.expression.type.election;

import java.util.ArrayList;
import java.util.List;

import edu.kit.kastel.formal.beast.api.codegen.ast.BooleanAstVisitor;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class VoteIntersectionNode extends ElectionTypeNode {
    private static final String INTERSECT_VOTES_TAG = "Intersect Votes";

    private List<Integer> numbers = new ArrayList<Integer>();

    @Override
    public final String getTreeString(final int depth) {
        return INTERSECT_VOTES_TAG;
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
