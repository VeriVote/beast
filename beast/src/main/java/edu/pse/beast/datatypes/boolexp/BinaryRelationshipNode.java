package edu.pse.beast.datatypes.boolexp;

/**
 * 
 * @author Lukas
 *
 */
public class BinaryRelationshipNode extends BooleanExpressionNode {

    private final BinaryRelationshipNode lhsExpNode;
    private final BinaryRelationshipNode rhsExpNode;
    
    /**
     * 
     * @param lhsExpNode the lhs node
     * @param rhsExpNode the rhs node
     */
    public BinaryRelationshipNode(BinaryRelationshipNode lhsExpNode, BinaryRelationshipNode rhsExpNode) {
        this.lhsExpNode = lhsExpNode;
        this.rhsExpNode = rhsExpNode;
    }
    
    /**
     * 
     * @return the LHSNode
     */
    public BooleanExpressionNode getLHSBooleanExpNode() {
        return lhsExpNode;
    }

    /**
     * 
     * @return the RHSNode
     */
    public BooleanExpressionNode getRHSBooleanExpNode() {
        return rhsExpNode;
    }

    @Override
    public void getVisited(BooleanExpNodeVisitor visitor) {
        // TODO Auto-generated method stub

    }

}
