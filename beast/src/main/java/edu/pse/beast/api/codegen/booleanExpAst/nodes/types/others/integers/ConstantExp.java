package edu.pse.beast.api.codegen.booleanExpAst.nodes.types.others.integers;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;

/**
 * The Class ConstantExp.
 *
 * @author Holger Klein
 */
public final class ConstantExp extends IntegerValuedExpression {
	/** The constant. */
	private final String constant;

	/**
	 * Instantiates a new constant exp.
	 *
	 * @param constantStr the constant saved in this node
	 */
	public ConstantExp(final String constantStr) {
		this.constant = constantStr;
	}

	/**
	 * Gets the constant.
	 *
	 * @return the constant saved in this node
	 */
	public String getConstant() {
		return constant;
	}

	@Override
	public String getTreeString(final int depth) {
		return "const " + constant;
	}

	@Override
	public void getVisited(BooleanAstVisitor visitor) {
		visitor.visitConstantExp(this);
	}
}
