package edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;

/**
 * The Class EquivalencyNode.
 *
 * @author Lukas Stapelbroek
 */
public class EquivalencyNode extends BinaryRelationshipNode {

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
        String tabs = "\t\t\t\t\t\t\t\t\t\t\t\t".substring(0, depth + 1);
        return "<==>\n" + tabs + "lhs: "
                + getLHSBooleanExpNode().getTreeString(depth + 1) + tabs
                + "rhs: " + getRHSBooleanExpNode().getTreeString(depth + 1);
    }
}
