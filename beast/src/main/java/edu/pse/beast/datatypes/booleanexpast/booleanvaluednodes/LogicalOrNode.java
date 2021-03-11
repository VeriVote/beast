package edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes;

import edu.pse.beast.api.codegen.booleanExpAst.BinaryCombinationSymbols;
import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;
import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;

/**
 * The Class LogicalOrNode.
 *
 * @author Lukas Stapelbroek
 */
public final class LogicalOrNode extends BinaryRelationshipNode {
    /**
     * Instantiates a new logical or node.
     *
     * @param lhsExpNode
     *            the lhs node
     * @param rhsExpNode
     *            the rhs node
     */
    public LogicalOrNode(final BooleanExpressionNode lhsExpNode,
                         final BooleanExpressionNode rhsExpNode) {
        super(lhsExpNode, rhsExpNode);
    }

    @Override
    public void getVisited(final BooleanExpNodeVisitor visitor) {
        visitor.visitOrNode(this);
    }

    @Override
    public String getTreeString(final int depth) {
        final String tabs = TABS.substring(0, depth + 1);
        return "||" + LINE_BREAK + tabs + LHS
                + getLHSBooleanExpNode().getTreeString(depth + 1) + tabs
                + RHS + getRHSBooleanExpNode().getTreeString(depth + 1);
    }

	@Override
	public void getVisited(BooleanAstVisitor visitor) {
		visitor.visitBinaryRelationNode(this, BinaryCombinationSymbols.OR);
	}
}
