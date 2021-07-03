package edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;
import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar;

/**
 * The Class ThereExistsNode.
 *
 * @author Holger Klein
 */
public final class ThereExistsNode extends QuantifierNode {
	/**
	 * Instantiates a new there exists node.
	 *
	 * @param declSymbVar   the symbolic variable of this quantifier
	 * @param followingNode the following node of this quantifier
	 */
	private SymbolicCBMCVar var;

	public ThereExistsNode(final SymbolicCBMCVar declSymbVar,
			final BooleanExpressionNode followingNode) {
		super(declSymbVar, followingNode);
	}

	@Override
	public String getTreeString(final int depth) {
		return null;
	}

	@Override
	public void getVisited(BooleanAstVisitor visitor) {
		visitor.visitExistsCandidateNode(this);
	}
}
