package edu.pse.beast.datatypes.booleanexpast.othervaluednodes;

import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.BooleanExpressionNode;
import edu.pse.beast.types.InOutType;
import edu.pse.beast.types.InternalTypeContainer;

/**
 * The Class TypeExpression.
 *
 * @author Holger Klein
 */
public abstract class TypeExpression extends BooleanExpressionNode {
    /** The Constant PRIME_TWO. */
    protected static final int PRIME_TWO = 1231;

    /** The Constant PRIME_THREE. */
    protected static final int PRIME_THREE = 1237;

    /** The type. */
    private final InOutType type;

    /**
     * Instantiates a new type expression.
     *
     * @param inOutType
     *            the type of this quantifier
     */
    public TypeExpression(final InOutType inOutType) {
        this.type = inOutType;
    }

    /**
     * Gets the internal type container.
     *
     * @return the type of this quantifier
     */
    public InternalTypeContainer getInternalTypeContainer() {
        return type.getInternalTypeContainer();
    }

    /**
     * {@inheritDoc}
     *
     * <p>This {@code equals} implementation for {@link TypeExpression}
     * only relies on other fields or methods that are not visible to
     * subclasses. Hence, subclasses cannot break its behavior.
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TypeExpression that = (TypeExpression) o;
        return type != null ? type.equals(that.type) : that.type == null;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Use this implementation by calling "super()" in inheriting
     * implementations of this method.
     */
    @Override
    public int hashCode() {
        return PRIME + (type != null ? type.hashCode() : 0);
    }
}
