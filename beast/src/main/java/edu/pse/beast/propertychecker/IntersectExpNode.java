package edu.pse.beast.propertychecker;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.BooleanExpressionNode;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.IntersectExpContext;

public class IntersectExpNode extends BooleanExpressionNode {

    private final IntersectExpContext intersectExpContext;
    private final String voteOutput;

    public IntersectExpNode(final IntersectExpContext intersectExpressionContext,
                            final String voteOutString) {
        this.intersectExpContext = intersectExpressionContext;
        this.voteOutput = voteOutString;
    }

    @Override
    public void getVisited(final BooleanExpNodeVisitor visitor) {
        visitor.visitIntersectExpNode(this);
    }

    @Override
    public String getTreeString(final int depth) {
        System.out.println("might add treestring");
        return "";
    }

    public IntersectExpContext getIntersectExpContext() {
        return intersectExpContext;
    }

    public String getVoteOutput() {
        return voteOutput;
    }
}
