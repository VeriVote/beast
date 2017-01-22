package edu.pse.beast.datatypes.boolexp;

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
    public ImplicationNode(BinaryRelationshipNode lhsExpNode, BinaryRelationshipNode rhsExpNode) {
        super(lhsExpNode, rhsExpNode);
    }

    @Override
    public void getVisited(BooleanExpNodeVisitor visitor) {
        visitor.visitImplicationNode(this);
    }
    
}
