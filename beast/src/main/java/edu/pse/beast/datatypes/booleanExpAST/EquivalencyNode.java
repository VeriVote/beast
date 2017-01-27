package edu.pse.beast.datatypes.booleanExpAST;

/**
 * 
 * @author Lukas
 *
 */
public class EquivalencyNode extends BinaryRelationshipNode {

    /**
     * 
     * @param lhsExpNode the lhs node
     * @param rhsExpNode the rhs node
     */
    public EquivalencyNode(BooleanExpressionNode lhsExpNode, BooleanExpressionNode rhsExpNode) {
        super(lhsExpNode, rhsExpNode);
    }

    @Override
    public void getVisited(BooleanExpNodeVisitor visitor) {
        visitor.visitAquivalencyNode(this);
    }
}
