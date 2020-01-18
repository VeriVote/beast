package edu.pse.beast.datatypes.booleanexpast.othervaluednodes.integervaluednodes;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;

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
     * @param constantStr
     *            the constant saved in this node
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
    public void getVisited(final BooleanExpNodeVisitor visitor) {
        visitor.visitConstExp(this);
    }

    @Override
    public String getTreeString(final int depth) {
        return "const " + constant + LINE_BREAK;
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
        ConstantExp that = (ConstantExp) o;
        return constant != null
                ? constant.equals(that.constant) : that.constant == null;
    }

    @Override
    public int hashCode() {
        return PRIME + (constant != null ? constant.hashCode() : 0);
    }
}
