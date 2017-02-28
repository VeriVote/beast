package edu.pse.beast.datatypes.booleanExpAST;

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
}
