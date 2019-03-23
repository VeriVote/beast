package edu.pse.beast.propertychecker;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.BooleanExpressionNode;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.NotEmptyExpContext;

public class NotEmptyExpressionNode extends BooleanExpressionNode {

    public final NotEmptyExpContext context;
    public final boolean isTop;

    public NotEmptyExpressionNode(NotEmptyExpContext context, boolean isTop) {
        this.context = context;
        this.isTop = isTop;
    }

    @Override
    public void getVisited(BooleanExpNodeVisitor visitor) {
        visitor.visitNotEmptyExpNode(this);
    }

    @Override
    public String getTreeString(int depth) {
        // TODO Auto-generated method stub
        return null;
    }
}
