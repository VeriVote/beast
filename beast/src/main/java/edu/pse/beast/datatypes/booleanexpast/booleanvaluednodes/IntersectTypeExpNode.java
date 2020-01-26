package edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.TypeExpression;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.IntersectExpContext;
import edu.pse.beast.types.InOutType;

/**
 * The Class IntersectTypeExpNode.
 *
 * @author Lukas Stapelbroek
 */
public final class IntersectTypeExpNode extends TypeExpression {
    /** The context. */
    private final IntersectExpContext context;

    /**
     * Instantiates a new intersect type exp node.
     *
     * @param type
     *            the type
     * @param exprContext
     *            the expr context
     */
    public IntersectTypeExpNode(final InOutType type,
                                final IntersectExpContext exprContext) {
        super(type);
        this.context = exprContext;
    }

    @Override
    public void getVisited(final BooleanExpNodeVisitor visitor) {
        visitor.visitIntersectTypeExpNode(this);
    }

    @Override
    public String getTreeString(final int depth) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Gets the context.
     *
     * @return the context
     */
    public IntersectExpContext getContext() {
        return context;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = PRIME * result + ((context == null) ? 0 : context.hashCode());
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
        final IntersectTypeExpNode that = (IntersectTypeExpNode) o;
        return context != null ? context.equals(that.context)
                : that.context == null;
    }
}
