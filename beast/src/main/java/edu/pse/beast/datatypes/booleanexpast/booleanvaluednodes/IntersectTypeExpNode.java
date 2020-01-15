package edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.TypeExpression;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.IntersectExpContext;
import edu.pse.beast.types.InOutType;

public class IntersectTypeExpNode extends TypeExpression {
    private final IntersectExpContext context;

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

    public IntersectExpContext getContext() {
        return context;
    }
}
