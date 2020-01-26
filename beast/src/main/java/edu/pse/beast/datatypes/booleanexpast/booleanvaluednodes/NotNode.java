package edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;

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
     * @param followingExprNode
     *            the node that follows this node (the node that gets negated)
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
    public void getVisited(final BooleanExpNodeVisitor visitor) {
        visitor.visitNotNode(this);
    }

    @Override
    public String getTreeString(final int depth) {
        final String tabs = TABS.substring(0, depth + 1);
        return "NOT" + LINE_BREAK + tabs + "following: "
                + followingNode.getTreeString(depth + 1);
    }

    @Override
    public int hashCode() {
        return PRIME
                + ((followingNode == null)
                        ? 0 : followingNode.hashCode());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final NotNode notNode = (NotNode) o;
        return followingNode != null
                ? followingNode.equals(notNode.followingNode)
                : notNode.followingNode == null;
    }
}
