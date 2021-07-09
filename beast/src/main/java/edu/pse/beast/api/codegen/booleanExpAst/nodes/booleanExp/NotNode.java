package edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;

/**
 * The Class NotNode.
 *
 * @author Lukas Stapelbroek
 */
public final class NotNode extends BooleanExpressionNode {
    /** The following node. */
    private BooleanExpressionNode followingNode;

    /**
     * Creates a new NotNode.
     *
     * @param followingExprNode the node that follows this node (the node that
     *                          gets negated)
     */
    public NotNode(final BooleanExpressionNode followingExprNode) {
        this.followingNode = followingExprNode;
    }

    /**
     * Gets the negated exp node.
     *
     * @return the negated expression node
     */
    public BooleanExpressionNode getNegatedExpNode() {
        return followingNode;
    }

    @Override
    public String getTreeString(final int depth) {
        return null;
    }

    @Override
    public void getVisited(BooleanAstVisitor visitor) {
        visitor.visitNotNode(this);
    }
}
