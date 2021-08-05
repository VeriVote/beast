package edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;

//TODO fix code generation bug with comparisons

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class VotePermutationNode extends ElectionTypeNode {
    private int voteNumber;

    @Override
    public final String getTreeString(final int depth) {
        return "Permutation Vote " + voteNumber;
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
