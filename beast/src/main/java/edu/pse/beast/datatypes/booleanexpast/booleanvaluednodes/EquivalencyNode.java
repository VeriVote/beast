package edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;

/**
 * The Class EquivalencyNode.
 *
 * @author Lukas Stapelbroek
 */
public final class EquivalencyNode extends BinaryRelationshipNode {
    /**
     * Instantiates a new equivalency node.
     *
     * @param lhsExpNode
     *            the lhs node
     * @param rhsExpNode
     *            the rhs node
     */
    public EquivalencyNode(final BooleanExpressionNode lhsExpNode,
                           final BooleanExpressionNode rhsExpNode) {
        super(lhsExpNode, rhsExpNode);
    }

    @Override
    public void getVisited(final BooleanExpNodeVisitor visitor) {
        visitor.visitEquivalencyNode(this);
    }

    @Override
    public String getTreeString(final int depth) {
        String tabs = TABS.substring(0, depth + 1);
        return "<==>" + LINE_BREAK + tabs + LHS
                + getLHSBooleanExpNode().getTreeString(depth + 1) + tabs
                + RHS + getRHSBooleanExpNode().getTreeString(depth + 1);
    }
}
