package edu.pse.beast.datatypes.booleanexpast.othervaluednodes;

import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.BooleanExpressionNode;
import edu.pse.beast.types.InOutType;
import edu.pse.beast.types.InternalTypeContainer;

/**
 *
 * @author Holger Klein
 *
 */
public abstract class TypeExpression extends BooleanExpressionNode {
    protected static final int PRIME_ONE = 31;
    protected static final int PRIME_TWO = 1231;
    protected static final int PRIME_THREE = 1237;
    private final InOutType type;

    /**
     *
     * @param inOutType the type of this quantifier
     */
    public TypeExpression(final InOutType inOutType) {
        this.type = inOutType;
    }

    /**
     *
     * @return the type of this quantifier
     */
    public InternalTypeContainer getInternalTypeContainer() {
        return type.getInternalTypeContainer();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TypeExpression that = (TypeExpression) o;
        return type != null
                ? type.equals(that.type)
                        : that.type == null;
    }

    @Override
    public int hashCode() {
        return PRIME_ONE + (type != null ? type.hashCode() : 0);
    }
}
