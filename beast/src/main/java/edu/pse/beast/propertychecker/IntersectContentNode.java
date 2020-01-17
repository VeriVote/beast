package edu.pse.beast.propertychecker;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.BooleanExpressionNode;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.IntersectContentContext;

/**
 * The Class IntersectContentNode.
 *
 * @author Lukas Stapelbroek
 */
public class IntersectContentNode extends BooleanExpressionNode {

    /** The intersect content context. */
    private final IntersectContentContext intersectContentContext;

    /** The vote output. */
    private final String voteOutput;

    /**
     * Instantiates a new intersect content node.
     *
     * @param intersectContContext
     *            the intersect cont context
     * @param voteOutputString
     *            the vote output string
     */
    public IntersectContentNode(final IntersectContentContext intersectContContext,
                                final String voteOutputString) {
        this.intersectContentContext = intersectContContext;
        this.voteOutput = voteOutputString;
    }

    @Override
    public void getVisited(final BooleanExpNodeVisitor visitor) {
        visitor.visitIntersectContentNode(this);
    }

    @Override
    public String getTreeString(final int depth) {
        System.out.println("might add treestring");
        return "";
    }

    /**
     * Gets the intersect content context.
     *
     * @return the intersect content context
     */
    public IntersectContentContext getIntersectContentContext() {
        return intersectContentContext;
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
