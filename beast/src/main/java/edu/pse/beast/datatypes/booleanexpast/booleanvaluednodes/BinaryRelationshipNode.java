package edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes;

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
     * @param lhsExprNode
     *            the lhs node
     * @param rhsExprNode
     *            the rhs node
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

    @Override
    public final int hashCode() {
        int result = 1;
        result = PRIME * result
                + ((lhsExpNode == null) ? 0 : lhsExpNode.hashCode());
        result = PRIME * result
                + ((rhsExpNode == null) ? 0 : rhsExpNode.hashCode());
        return result;
    }

    @Override
    public final boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BinaryRelationshipNode that = (BinaryRelationshipNode) o;
        if (lhsExpNode != null ? !lhsExpNode.equals(that.lhsExpNode)
                : that.lhsExpNode != null) {
            return false;
        }
        return rhsExpNode != null ? rhsExpNode.equals(that.rhsExpNode)
                : that.rhsExpNode == null;
    }
}
