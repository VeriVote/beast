package edu.pse.beast.propertychecker;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.BooleanExpressionNode;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.ConcatenationExpContext;

/**
 * The Class ConcatenationExpNode.
 *
 * @author Lukas Stapelbroek
 */
public class ConcatenationExpNode extends BooleanExpressionNode {

    /** The concatenation exp context. */
    private final ConcatenationExpContext concatenationExpContext;

    /** The vote output. */
    private final String voteOutput;

    /** The vote output length. */
    private final String voteOutputLength;

    /**
     * Instantiates a new concatenation exp node.
     *
     * @param concatExpContext
     *            the concat exp context
     * @param voteOutputString
     *            the vote output string
     * @param voteOutputLen
     *            the vote output len
     */
    public ConcatenationExpNode(final ConcatenationExpContext concatExpContext,
                                final String voteOutputString,
                                final String voteOutputLen) {
        this.concatenationExpContext = concatExpContext;
        this.voteOutput = voteOutputString;
        this.voteOutputLength = voteOutputLen;
    }

    @Override
    public void getVisited(final BooleanExpNodeVisitor visitor) {
        visitor.visitConcatenationExpNode(this);
    }

    @Override
    public String getTreeString(final int depth) {
        System.out.println("might add treestring");
        return "";
    }

    /**
     * Gets the concatenation exp context.
     *
     * @return the concatenation exp context
     */
    public ConcatenationExpContext getConcatenationExpContext() {
        return concatenationExpContext;
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
