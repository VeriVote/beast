package edu.pse.beast.toolbox.antlr.booleanexp.generateast;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.BooleanExpressionNode;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.VoteEquivalentsContext;

/**
 * The Class VoteEquivalentsNode.
 */
public class VoteEquivalentsNode extends BooleanExpressionNode {

    /** The vote equivalents context. */
    private final VoteEquivalentsContext voteEquivalentsContext;

    /** The to output. */
    private final String toOutput;

    /** The vote output length. */
    private final String voteOutputLength;

    /**
     * The constructor.
     *
     * @param voteEquivContext
     *            the vote equiv context
     * @param toOutputString
     *            the to output string
     * @param voteOutputLen
     *            the vote output len
     */
    public VoteEquivalentsNode(final VoteEquivalentsContext voteEquivContext,
                               final String toOutputString,
                               final String voteOutputLen) {
        this.voteEquivalentsContext = voteEquivContext;
        this.toOutput = toOutputString;
        this.voteOutputLength = voteOutputLen;
    }

    @Override
    public void getVisited(final BooleanExpNodeVisitor visitor) {
        visitor.visitVoteEquivalentsNode(this);
    }

    @Override
    public String getTreeString(final int depth) {
        System.out.println("might add treestring");
        return "";
    }

    /**
     * Gets the vote equivalents context.
     *
     * @return the vote equivalents context
     */
    public VoteEquivalentsContext getVoteEquivalentsContext() {
        return voteEquivalentsContext;
    }

    /**
     * Gets the to output.
     *
     * @return the to output
     */
    public String getToOutput() {
        return toOutput;
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
