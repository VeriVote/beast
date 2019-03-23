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
    private final InOutType type;

    /**
     *
     * @param type the type of this quantifier
     */
    public TypeExpression(InOutType type) {
        this.type = type;
    }

    /**
     *
     * @return the type of this quantifier
     */
    public InternalTypeContainer getInternalTypeContainer() {
        return type.getInternalTypeContainer();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TypeExpression that = (TypeExpression) o;

        return type != null ? type.equals(that.type) : that.type == null;
    }

    @Override
    public int hashCode() {
        return 31 + (type != null ? type.hashCode() : 0);
    }
}