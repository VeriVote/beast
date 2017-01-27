package edu.pse.beast.datatypes.booleanExpAST;

/**
 * 
 * @author Lukas
 *
 */
public abstract class BinaryRelationshipNode extends BooleanExpressionNode {

    private final BooleanExpressionNode lhsExpNode;
    private final BooleanExpressionNode rhsExpNode;
    
    /**
     * 
     * @param lhsExpNode the lhs node
     * @param rhsExpNode the rhs node
     */
    public BinaryRelationshipNode(BooleanExpressionNode lhsExpNode, BooleanExpressionNode rhsExpNode) {
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
}
