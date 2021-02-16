package edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;
import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanexpast.ComparisonSymbol;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.TypeExpression;

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
	private final ComparisonSymbol comparisonSymbol;

	/**
	 * Instantiates a new comparison node.
	 *
	 * @param lhsTypeExpr    the lhsExpression
	 * @param rhsTypeExpr    the rhsExpression
	 * @param comparisonSymb the symbol that describes this comparison (for example
	 *                       <, >, == )
	 */
	public ComparisonNode(final TypeExpression lhsTypeExpr, final TypeExpression rhsTypeExpr,
			final ComparisonSymbol comparisonSymb) {
		this.lhsTypeExp = lhsTypeExpr;
		this.rhsTypeExp = rhsTypeExpr;
		this.comparisonSymbol = comparisonSymb;
		if (comparisonSymb.getCStringRep().equals("==")) {
			this.comparisonType = ComparisonType.EQ;
		} else if (comparisonSymb.getCStringRep().equals("!=")) {
			this.comparisonType = ComparisonType.UNEQ;
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * Generates the code for the comparison of 2 types which are not integers.
	 * These types can be lists which may have different depth and might be accessed
	 * by variables.
	 */
	@Override
	public void getVisited(final BooleanExpNodeVisitor visitor) {
		visitor.visitComparisonNode(this);
	}

	@Override
	public final int hashCode() {
		int result = 1;
		result = PRIME * result + ((comparisonSymbol == null) ? 0 : comparisonSymbol.hashCode());
		result = PRIME * result + ((getLhsTypeExp() == null) ? 0 : getLhsTypeExp().hashCode());
		result = PRIME * result + ((getRhsTypeExp() == null) ? 0 : getRhsTypeExp().hashCode());
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
		final ComparisonNode that = (ComparisonNode) o;
		if (getLhsTypeExp() != null ? !getLhsTypeExp().equals(that.getLhsTypeExp()) : that.getLhsTypeExp() != null) {
			return false;
		}
		if (getRhsTypeExp() != null ? !getRhsTypeExp().equals(that.getRhsTypeExp()) : that.getRhsTypeExp() != null) {
			return false;
		}
		return comparisonSymbol != null ? comparisonSymbol.equals(that.comparisonSymbol)
				: that.comparisonSymbol == null;
	}

	private String getTreeStringExtra() {
		final String simpleClassName = this.getClass().getSimpleName();
		final String text = ": Symbol ";
		return "ComparisonNode".equals(simpleClassName) ? "" : simpleClassName + text;
	}

	@Override
	public final String getTreeString(final int depth) {
		final StringBuilder b = new StringBuilder();
		final String tabs = TABS.substring(0, depth);
		b.append(tabs + getTreeStringExtra() + comparisonSymbol.getCStringRep() + LINE_BREAK);
		b.append(tabs + TAB + LHS + getLhsTypeExp().getTreeString(depth + 1));
		b.append(tabs + TAB + RHS + getRhsTypeExp().getTreeString(depth + 1));
		return b.toString();
	}

	public ComparisonType getComparisonType() {
		return comparisonType;
	}
	
	public ComparisonSymbol getComparisonSymbol() {
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
}
