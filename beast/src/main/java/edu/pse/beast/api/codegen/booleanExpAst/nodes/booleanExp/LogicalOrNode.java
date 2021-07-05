package edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;
import edu.pse.beast.api.codegen.booleanExpAst.data.BinaryCombinationSymbols;

/**
 * The Class LogicalOrNode.
 *
 * @author Lukas Stapelbroek
 */
public final class LogicalOrNode extends BinaryRelationshipNode {
	/**
	 * Instantiates a new logical or node.
	 *
	 * @param lhsExpNode the lhs node
	 * @param rhsExpNode the rhs node
	 */
	public LogicalOrNode(final BooleanExpressionNode lhsExpNode,
			final BooleanExpressionNode rhsExpNode) {
		super(lhsExpNode, rhsExpNode);
	}

	@Override
	public String getTreeString(final int depth) {
		return null;
	}

	@Override
	public void getVisited(BooleanAstVisitor visitor) {
		visitor.visitBinaryRelationNode(this, BinaryCombinationSymbols.OR);
	}
}
