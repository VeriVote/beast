package edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp;

import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar;

/**
 * The Class QuantifierNode.
 *
 * @author Holger Klein, Lukas Stapelbroek
 */
public abstract class QuantifierNode extends BooleanExpressionNode {
	/** The decl symb var. */
	private final SymbolicCBMCVar declSymbVar;

	/** The following node. */
	private final BooleanExpressionNode followingNode;

	/**
	 * Instantiates a new quantifier node.
	 *
	 * @param declSymbVariable  the symbolic variable of this quantifier
	 * @param followingExprNode the following node of this quantifier
	 */
	public QuantifierNode(final SymbolicCBMCVar declSymbVariable,
			final BooleanExpressionNode followingExprNode) {
		this.declSymbVar = declSymbVariable;
		this.followingNode = followingExprNode;
	}

	/**
	 * Gets the declared symbolic var.
	 *
	 * @return the symbolic variable of this expression
	 */
	public SymbolicCBMCVar getDeclaredSymbolicVar() {
		return declSymbVar;
	}

	/**
	 * Gets the following exp node.
	 *
	 * @return the following node
	 */
	public BooleanExpressionNode getFollowingExpNode() {
		return followingNode;
	}
}
