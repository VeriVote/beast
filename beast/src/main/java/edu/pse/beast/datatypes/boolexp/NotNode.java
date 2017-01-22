package edu.pse.beast.datatypes.boolexp;

/**
 * 
 * @author Lukas
 *
 */
public class NotNode extends BooleanExpressionNode {

    /**
     * 
     * @return the negated expression node
     */
    public BooleanExpressionNode getNegatedExpNode() {
        // TODO
        return null;
    }

    @Override
    public void getVisited(BooleanExpNodeVisitor visitor) {
        visitor.visitNotNode(this);
    }
}
