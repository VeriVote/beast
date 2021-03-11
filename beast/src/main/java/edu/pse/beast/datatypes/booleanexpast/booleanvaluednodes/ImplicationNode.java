package edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes;

import edu.pse.beast.api.codegen.booleanExpAst.BinaryCombinationSymbols;
import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;
import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;

/**
 * The Class ImplicationNode.
 *
 * @author Lukas Stapelbroek
 */
public final class ImplicationNode extends BinaryRelationshipNode {
    /**
     * Instantiates a new implication node.
     *
     * @param lhsExpNode
     *            the lhs node
     * @param rhsExpNode
     *            the rhs node
     */
    public ImplicationNode(final BooleanExpressionNode lhsExpNode,
                           final BooleanExpressionNode rhsExpNode) {
        super(lhsExpNode, rhsExpNode);
    }

    @Override
    public void getVisited(final BooleanExpNodeVisitor visitor) {
        visitor.visitImplicationNode(this);
    }

    @Override
    public String getTreeString(final int depth) {
        final String tabs = TABS.substring(0, depth + 1);
        return "==>" + LINE_BREAK + tabs + LHS
                + getLHSBooleanExpNode().getTreeString(depth + 1) + tabs
                + RHS + getRHSBooleanExpNode().getTreeString(depth + 1);
    }

	@Override
	public void getVisited(BooleanAstVisitor visitor) {
		visitor.visitBinaryRelationNode(this, BinaryCombinationSymbols.IMPL);
	}
}
