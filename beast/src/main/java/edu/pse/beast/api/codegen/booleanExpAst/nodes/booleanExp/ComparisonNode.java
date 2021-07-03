package edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.TypeExpression;

/**
 * The Class ComparisonNode.
 *
 * @author Lukas Stapelbroek
 */
public class ComparisonNode extends BooleanExpressionNode {

	public enum ComparisonType {
		EQ, UNEQ, L, G, LEQ, GEQ
	}

	private ComparisonType comparisonType;

	/** The lhs type exp. */
	private final TypeExpression lhsTypeExp;

	/** The rhs type exp. */
	private final TypeExpression rhsTypeExp;

	/** The comparison symbol. */
	private final String comparisonSymbol;

	/**
	 * Instantiates a new comparison node.
	 *
	 * @param lhsTypeExpr    the lhsExpression
	 * @param rhsTypeExpr    the rhsExpression
	 * @param comparisonSymb the symbol that describes this comparison (for
	 *                       example <, >, == )
	 */
	public ComparisonNode(final TypeExpression lhsTypeExpr,
			final TypeExpression rhsTypeExpr, final String comparisonSymb) {
		this.lhsTypeExp = lhsTypeExpr;
		this.rhsTypeExp = rhsTypeExpr;
		this.comparisonSymbol = comparisonSymb;
		if (comparisonSymb.equals("==")) {
			this.comparisonType = ComparisonType.EQ;
		} else if (comparisonSymb.equals("!=")) {
			this.comparisonType = ComparisonType.UNEQ;
		}
	}

	private String getTreeStringExtra() {
		final String simpleClassName = this.getClass().getSimpleName();
		final String text = ": Symbol ";
		return "ComparisonNode".equals(simpleClassName) ? ""
				: simpleClassName + text;
	}

	public ComparisonType getComparisonType() {
		return comparisonType;
	}

	public String getComparisonSymbol() {
		return comparisonSymbol;
	}

	/**
	 * Gets the lhs type exp.
	 *
	 * @return the lhs type exp
	 */
	public TypeExpression getLhsTypeExp() {
		return lhsTypeExp;
	}

	/**
	 * Gets the rhs type exp.
	 *
	 * @return the rhs type exp
	 */
	public TypeExpression getRhsTypeExp() {
		return rhsTypeExp;
	}

	@Override
	public void getVisited(BooleanAstVisitor visitor) {
		visitor.visitComparisonNode(this);
	}

	@Override
	public String getTreeString(int depth) {
		// TODO Auto-generated method stub
		return null;
	}
}
