package edu.pse.beast.propertychecker;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.BooleanExpressionNode;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.PermutationExpContext;

/**
 * The Class PermutationExpNode.
 *
 * @author Lukas Stapelbroek
 */
public final class PermutationExpNode extends BooleanExpressionNode {

    /** The permutation exp context. */
    private final PermutationExpContext permutationExpContext;

    /** The vote output. */
    private final String voteOutput;

    /** The vote output length. */
    private final String voteOutputLength;

    /**
     * Instantiates a new permutation exp node.
     *
     * @param permExpContext
     *            the perm exp context
     * @param voteOutputString
     *            the vote output string
     * @param voteOutputLen
     *            the vote output len
     */
    public PermutationExpNode(final PermutationExpContext permExpContext,
                              final String voteOutputString,
                              final String voteOutputLen) {
        this.permutationExpContext = permExpContext;
        this.voteOutput = voteOutputString;
        this.voteOutputLength = voteOutputLen;
    }

    @Override
    public void getVisited(final BooleanExpNodeVisitor visitor) {
        visitor.visitPermutationExpNode(this);
    }

    @Override
    public String getTreeString(final int depth) {
        System.out.println("might add treestring");
        return "";
    }

    /**
     * Gets the permutation exp context.
     *
     * @return the permutation exp context
     */
    public PermutationExpContext getPermutationExpContext() {
        return permutationExpContext;
    }

    /**
     * Gets the vote output.
     *
     * @return the vote output
     */
    public String getVoteOutput() {
        return voteOutput;
    }

    /**
     * Gets the vote output length.
     *
     * @return the vote output length
     */
    public String getVoteOutputLength() {
        return voteOutputLength;
    }
}
