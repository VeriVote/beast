package edu.kit.kastel.formal.beast.api.codegen.ast.expression.type.election;

import edu.kit.kastel.formal.beast.api.codegen.ast.BooleanAstVisitor;

//TODO fix code generation bug with comparisons

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class VotePermutationNode extends ElectionTypeNode {
    private static final String PERMUTATION_VOTE_TAG = "Permutation Vote ";

    private int voteNumber;

    @Override
    public final String getTreeString(final int depth) {
        return PERMUTATION_VOTE_TAG + voteNumber;
    }

    public final void setVoteNumber(final int voteNum) {
        this.voteNumber = voteNum;
    }

    public final int getVoteNumber() {
        return voteNumber;
    }

    @Override
    public final void getVisited(final BooleanAstVisitor visitor) {
        visitor.visitVotePermutation(this);
    }
}
