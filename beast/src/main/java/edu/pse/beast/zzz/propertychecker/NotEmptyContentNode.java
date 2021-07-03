package edu.pse.beast.zzz.propertychecker;

import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.BooleanExpressionNode;
import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.NotEmptyContentContext;

/**
 * The Class NotEmptyContentNode.
 *
 * @author Lukas Stapelbroek
 */
public final class NotEmptyContentNode extends BooleanExpressionNode {

    /** The context. */
    private final NotEmptyContentContext context;

    /** The voting output. */
    private final String votingOutput;

    /**
     * Instantiates a new not empty content node.
     *
     * @param contentContext
     *            the content context
     * @param votingOutputString
     *            the voting output string
     */
    public NotEmptyContentNode(final NotEmptyContentContext contentContext,
                               final String votingOutputString) {
        this.context = contentContext;
        this.votingOutput = votingOutputString;
    }

    @Override
    public void getVisited(final BooleanExpNodeVisitor visitor) {
        visitor.visitNotEmptyContentNode(this);
    }

    @Override
    public String getTreeString(final int depth) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Gets the context.
     *
     * @return the context
     */
    public NotEmptyContentContext getContext() {
        return context;
    }

    /**
     * Gets the voting output.
     *
     * @return the voting output
     */
    public String getVotingOutput() {
        return votingOutput;
    }

}
