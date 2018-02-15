package edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes;

import edu.pse.beast.datatypes.booleanExpAST.BooleanExpNodeVisitor;

/**
 * 
 * @author Lukas
 *
 */
public class NotNode extends BooleanExpressionNode {
    private BooleanExpressionNode followingNode;

    /**

     * creates a new NotNode
     * @param followingNode the node that follows this node (the node that gets negated)
     */
    public NotNode(BooleanExpressionNode followingNode) {
        this.followingNode = followingNode;
    }

    /**
     * 
     * @return the negated expression node
     */
    public BooleanExpressionNode getNegatedExpNode() {
        return followingNode;
    }

    @Override
    public void getVisited(BooleanExpNodeVisitor visitor) {
        visitor.visitNotNode(this);
    }

    @Override
    public String getTreeString(int depth) {
        String tabs = "\t\t\t\t\t\t\t\t\t\t\t\t".substring(0, depth + 1);
        return "NOT\n" +
                tabs + "following: " + followingNode.getTreeString(depth + 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NotNode notNode = (NotNode) o;

        return followingNode != null ? followingNode.equals(notNode.followingNode) : notNode.followingNode == null;
    }
}
