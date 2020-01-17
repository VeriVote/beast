package edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanexpast.ComparisonSymbol;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.TypeExpression;

/**
 * The Class ComparisonNode.
 *
 * @author Lukas Stapelbroek
 */
public class ComparisonNode extends BooleanExpressionNode {
    /** The Constant PRIME. */
    private static final int PRIME = 31;

    /** The lhs type exp. */
    private final TypeExpression lhsTypeExp;

    /** The rhs type exp. */
    private final TypeExpression rhsTypeExp;

    /** The comparison symbol. */
    private final ComparisonSymbol comparisonSymbol;

    /**
     * Instantiates a new comparison node.
     *
     * @param lhsTypeExpr
     *            the lhsExpression
     * @param rhsTypeExpr
     *            the rhsExpression
     * @param comparisonSymb
     *            the symbol that describes this comparision (for example <, >,
     *            == )
     */
    public ComparisonNode(final TypeExpression lhsTypeExpr,
                          final TypeExpression rhsTypeExpr,
                          final ComparisonSymbol comparisonSymb) {
        this.lhsTypeExp = lhsTypeExpr;
        this.rhsTypeExp = rhsTypeExpr;
        this.comparisonSymbol = comparisonSymb;
    }

    /**
     * Gets the LHS boolean exp node.
     *
     * @return the LHSNode
     */
    public TypeExpression getLHSBooleanExpNode() {
        return getLhsTypeExp();
    }

    /**
     * Gets the RHS boolean exp node.
     *
     * @return the RHSNode
     */
    public TypeExpression getRHSBooleanExpNode() {
        return getRhsTypeExp();
    }

    @Override
    public void getVisited(final BooleanExpNodeVisitor visitor) {
        visitor.visitComparisonNode(this);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = PRIME * result + ((comparisonSymbol == null) ? 0
                : comparisonSymbol.hashCode());
        result = PRIME * result
                + ((getLhsTypeExp() == null) ? 0 : getLhsTypeExp().hashCode());
        result = PRIME * result
                + ((getRhsTypeExp() == null) ? 0 : getRhsTypeExp().hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ComparisonNode that = (ComparisonNode) o;
        if (getLhsTypeExp() != null
                ? !getLhsTypeExp().equals(that.getLhsTypeExp())
                : that.getLhsTypeExp() != null) {
            return false;
        }
        if (getRhsTypeExp() != null
                ? !getRhsTypeExp().equals(that.getRhsTypeExp())
                : that.getRhsTypeExp() != null) {
            return false;
        }
        return comparisonSymbol != null
                ? comparisonSymbol.equals(that.comparisonSymbol)
                : that.comparisonSymbol == null;
    }

    @Override
    public String getTreeString(final int depth) {
        StringBuilder b = new StringBuilder();
        String tabs = "\t\t\t\t\t\t\t\t\t\t\t".substring(0, depth);
        b.append(tabs + comparisonSymbol.getCStringRep() + "\n");
        b.append(tabs + "\t" + "lhs: "
                + getLhsTypeExp().getTreeString(depth + 1));
        b.append(tabs + "\t" + "rhs: "
                + getRhsTypeExp().getTreeString(depth + 1));
        return b.toString();
    }

    /**
     * Gets the comparison symbol.
     *
     * @return the symbol that describes this comparison
     */
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
}
