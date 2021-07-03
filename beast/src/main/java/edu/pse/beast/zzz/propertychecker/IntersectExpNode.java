package edu.pse.beast.zzz.propertychecker;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.BooleanExpressionNode;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.IntersectExpContext;

/**
 * The Class IntersectExpNode.
 *
 * @author Lukas Stapelbroek
 */
public final class IntersectExpNode extends BooleanExpressionNode {

    /** The intersect exp context. */
    private final IntersectExpContext intersectExpContext;

    /** The vote output. */
    private final String voteOutput;

    /**
     * Instantiates a new intersect exp node.
     *
     * @param intersectExpressionContext
     *            the intersect expression context
     * @param voteOutString
     *            the vote out string
     */
    public IntersectExpNode(final IntersectExpContext intersectExpressionContext,
                            final String voteOutString) {
        this.intersectExpContext = intersectExpressionContext;
        this.voteOutput = voteOutString;
    }

    @Override
    public void getVisited(final BooleanExpNodeVisitor visitor) {
        visitor.visitIntersectExpNode(this);
    }

    @Override
    public String getTreeString(final int depth) {
        System.out.println("might add treestring");
        return "";
    }

    /**
     * Gets the intersect exp context.
     *
     * @return the intersect exp context
     */
    public IntersectExpContext getIntersectExpContext() {
        return intersectExpContext;
    }

    /**
     * Gets the vote output.
     *
     * @return the vote output
     */
    public String getVoteOutput() {
        return voteOutput;
    }
}
