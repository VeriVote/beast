package edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes;

import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.BooleanExpressionNode;
import edu.pse.beast.types.InternalTypeContainer;

/**
 *
 * @author Holger Klein
 *
 */
public abstract class TypeExpression extends BooleanExpressionNode {

    private final InternalTypeContainer container;

    /**
     *
     * @param container the type of this quantifier
     */
    public TypeExpression(InternalTypeContainer container) {
        this.container = container;
    }

    /**
     *
     * @return the type of this quantifier
     */
    public InternalTypeContainer getInternalTypeContainer() {
        return container;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TypeExpression that = (TypeExpression) o;

        return container != null ? container.equals(that.container) : that.container == null;
    }

    @Override
    public int hashCode() {
        return 31 + (container != null ? container.hashCode() : 0);
    }
}
