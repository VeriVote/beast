package edu.kit.kastel.formal.beast.api.codegen.ast.expression.bool;

/**
 * The Class BinaryRelationshipNode.
 *
 * @author Lukas Stapelbroek
 */
public abstract class BinaryRelationshipNode extends BooleanExpressionNode {
    /** The lhs exp node. */
    private final BooleanExpressionNode lhsExpNode;

    /** The rhs exp node. */
    private final BooleanExpressionNode rhsExpNode;

    /**
     * Instantiates a new binary relationship node.
     *
     * @param lhsExprNode the lhs node
     * @param rhsExprNode the rhs node
     */
    public BinaryRelationshipNode(final BooleanExpressionNode lhsExprNode,
            final BooleanExpressionNode rhsExprNode) {
        this.lhsExpNode = lhsExprNode;
        this.rhsExpNode = rhsExprNode;
    }

    /**
     * Gets the LHS boolean exp node.
     *
     * @return the LHSNode
     */
    public BooleanExpressionNode getLHSBooleanExpNode() {
        return lhsExpNode;
    }

    /**
     * Gets the RHS boolean exp node.
     *
     * @return the RHSNode
     */
    public BooleanExpressionNode getRHSBooleanExpNode() {
        return rhsExpNode;
    }

}
