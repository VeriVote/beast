package edu.pse.beast.datatypes.booleanexpast.othervaluednodes;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.integervaluednodes.IntegerValuedExpression;
import edu.pse.beast.types.InternalTypeContainer;

/**
 * The Class AtPosExp.
 *
 * @author Holger Klein
 */
public final class AtPosExp extends TypeExpression {
    /** The integer valued expression. */
    private final IntegerValuedExpression integerValuedExpression;

    /**
     * Instantiates a new at pos exp.
     *
     * @param container
     *            the container
     * @param integerValuedExpr
     *            the integer valued expr
     */
    public AtPosExp(final InternalTypeContainer container,
                    final IntegerValuedExpression integerValuedExpr) {
        super(container);
        this.integerValuedExpression = integerValuedExpr;
    }

    @Override
    public void getVisited(final BooleanExpNodeVisitor visitor) {
        visitor.visitAtPosNode(this);
    }

    @Override
    public String getTreeString(final int depth) {
        return "atpos" + LINE_BREAK + TABS.substring(0, depth + 1)
                + "int " + integerValuedExpression.getTreeString(depth + 1);
    }

    /**
     * Gets the integer valued expression.
     *
     * @return the integer valued expression
     */
    public IntegerValuedExpression getIntegerValuedExpression() {
        return integerValuedExpression;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = PRIME * result + ((integerValuedExpression == null) ? 0
                : integerValuedExpression.hashCode());
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
        final AtPosExp atPosExp = (AtPosExp) o;
        return integerValuedExpression != null
                ? integerValuedExpression
                        .equals(atPosExp.integerValuedExpression)
                : atPosExp.integerValuedExpression == null;
    }
}
