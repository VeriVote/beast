package edu.pse.beast.propertychecker;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.BooleanExpressionNode;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.NotEmptyExpContext;

/**
 * The Class NotEmptyExpressionNode.
 */
public class NotEmptyExpressionNode extends BooleanExpressionNode {

    /** The context. */
    private final NotEmptyExpContext context;

    /** The is top. */
    private final boolean isTop;

    /**
     * The constructor.
     *
     * @param notEmptyExpContext
     *            the not empty exp context
     * @param top
     *            the top
     */
    public NotEmptyExpressionNode(final NotEmptyExpContext notEmptyExpContext,
                                  final boolean top) {
        this.context = notEmptyExpContext;
        this.isTop = top;
    }

    @Override
    public void getVisited(final BooleanExpNodeVisitor visitor) {
        visitor.visitNotEmptyExpNode(this);
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
    public NotEmptyExpContext getContext() {
        return context;
    }

    /**
     * Checks if is top.
     *
     * @return true, if is top
     */
    public boolean isTop() {
        return isTop;
    }
}
