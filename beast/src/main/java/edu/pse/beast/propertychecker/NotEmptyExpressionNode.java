package edu.pse.beast.propertychecker;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.BooleanExpressionNode;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.NotEmptyExpContext;

public class NotEmptyExpressionNode extends BooleanExpressionNode {

    private final NotEmptyExpContext context;
    private final boolean isTop;

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

    public NotEmptyExpContext getContext() {
        return context;
    }

    public boolean isTop() {
        return isTop;
    }
}
