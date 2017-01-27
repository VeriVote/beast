package edu.pse.beast.datatypes.booleanExpAST;

/**
 * 
 * @author Lukas
 *
 */
public class ImplicationNode extends BinaryRelationshipNode {

    /**
     * 
     * @param lhsExpNode the lhs node
     * @param rhsExpNode the rhs node
     */
    public ImplicationNode(BooleanExpressionNode lhsExpNode, BooleanExpressionNode rhsExpNode) {
        super(lhsExpNode, rhsExpNode);
    }

    @Override
    public void getVisited(BooleanExpNodeVisitor visitor) {
        visitor.visitImplicationNode(this);
    }
    
}
