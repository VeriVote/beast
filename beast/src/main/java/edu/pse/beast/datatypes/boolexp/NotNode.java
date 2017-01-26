package edu.pse.beast.datatypes.boolexp;

/**
 * 
 * @author Lukas
 *
 */
public class NotNode extends BooleanExpressionNode {
    private BooleanExpressionNode followingNode;
    
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
