package edu.pse.beast.datatypes.booleanExpAST;

/**
 * 
 * @author Lukas
 *
 */
public class LogicalAndNode extends BinaryRelationshipNode {

    /**
     * 
     * @param lhsExpNode the lhs node
     * @param rhsExpNode the rhs node
     */
    public LogicalAndNode(BooleanExpressionNode lhsExpNode, BooleanExpressionNode rhsExpNode) {
        super(lhsExpNode, rhsExpNode);
    }

    @Override
    public void getVisited(BooleanExpNodeVisitor visitor) {
        visitor.visitAndNode(this);
    }
    
}
