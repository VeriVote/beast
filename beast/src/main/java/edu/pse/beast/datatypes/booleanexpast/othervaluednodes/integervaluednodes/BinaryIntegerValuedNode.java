package edu.pse.beast.datatypes.booleanexpast.othervaluednodes.integervaluednodes;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;

/**
 * The Class BinaryIntegerValuedNode.
 *
 * @author Holger Klein
 */
public final class BinaryIntegerValuedNode extends IntegerValuedExpression {
    /** The lhs. */
    private final IntegerValuedExpression lhs;

    /** The rhs. */
    private final IntegerValuedExpression rhs;

    /** The relation symbol. */
    private final String relationSymbol;

    /**
     * Instantiates a new binary integer valued node.
     *
     * @param lhsExpr
     *            the lhs expr
     * @param rhsExpr
     *            the rhs expr
     * @param relationSymb
     *            the relation symb
     */
    public BinaryIntegerValuedNode(final IntegerValuedExpression lhsExpr,
                                   final IntegerValuedExpression rhsExpr,
                                   final String relationSymb) {
        this.lhs = lhsExpr;
        this.rhs = rhsExpr;
        this.relationSymbol = relationSymb;
    }

    @Override
    public void getVisited(final BooleanExpNodeVisitor visitor) {
        visitor.visitBinaryIntegerValuedNode(this);
    }

    @Override
    public String getTreeString(final int depth) {
        final StringBuilder b = new StringBuilder();
        final String tabs = TABS.substring(0, depth + 1);
        b.append("BinaryIntNode: Symbol " + relationSymbol + LINE_BREAK);
        b.append(tabs + LHS + getLhs().getTreeString(depth + 1) + LINE_BREAK);
        b.append(tabs + RHS + getRhs().getTreeString(depth + 1) + LINE_BREAK);
        return b.toString();
    }

    /**
     * Gets the relation symbol.
     *
     * @return the relation symbol
     */
    public String getRelationSymbol() {
        return relationSymbol;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = PRIME * result
                + ((getLhs() == null) ? 0 : getLhs().hashCode());
        result = PRIME * result
                + ((relationSymbol == null) ? 0 : relationSymbol.hashCode());
        result = PRIME * result
                + ((getRhs() == null) ? 0 : getRhs().hashCode());
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
        if (!super.equals(o)) {
            return false;
        }
        final BinaryIntegerValuedNode that = (BinaryIntegerValuedNode) o;
        if (getLhs() != null ? !getLhs().equals(that.getLhs())
                : that.getLhs() != null) {
            return false;
        }
        if (getRhs() != null ? !getRhs().equals(that.getRhs())
                : that.getRhs() != null) {
            return false;
        }
        if (relationSymbol != null ? !relationSymbol.equals(that.relationSymbol)
                : that.relationSymbol != null) {
            return false;
        }
        return true;
    }

    /**
     * Gets the lhs.
     *
     * @return the lhs
     */
    public IntegerValuedExpression getLhs() {
        return lhs;
    }

    /**
     * Gets the rhs.
     *
     * @return the rhs
     */
    public IntegerValuedExpression getRhs() {
        return rhs;
    }
}
