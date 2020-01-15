package edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;

/**
 *
 * @author Lukas Stapelbroek
 *
 */
public class NotNode extends BooleanExpressionNode {
    private BooleanExpressionNode followingNode;

    /**
     * Creates a new NotNode.
     *
     * @param followingExprNode the node that follows this node (the node that gets
     *                          negated)
     */
    public NotNode(final BooleanExpressionNode followingExprNode) {
        this.followingNode = followingExprNode;
    }

    /**
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
        String tabs = "\t\t\t\t\t\t\t\t\t\t\t\t".substring(0, depth + 1);
        return "NOT\n" + tabs + "following: " + followingNode.getTreeString(depth + 1);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((followingNode == null)
                        ? 0 : followingNode.hashCode());
        return result;
    }
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NotNode notNode = (NotNode) o;
        return followingNode != null
                ? followingNode.equals(notNode.followingNode)
                        : notNode.followingNode == null;
    }
}
