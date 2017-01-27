package edu.pse.beast.datatypes.booleanExpAST;

/**
 * 
 * @author Lukas
 *
 */
public class LogicalOrNode extends BinaryRelationshipNode {

    /**
     * 
     * @param lhsExpNode the lhs node
     * @param rhsExpNode the rhs node
     */
    public LogicalOrNode(BooleanExpressionNode lhsExpNode, BooleanExpressionNode rhsExpNode) {
        super(lhsExpNode, rhsExpNode);
    }

    @Override
    public void getVisited(BooleanExpNodeVisitor visitor) {
        visitor.visitOrNode(this);
    }

}
