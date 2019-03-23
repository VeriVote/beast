package edu.pse.beast.propertychecker;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.BooleanExpressionNode;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.IntersectExpContext;

public class IntersectExpNode extends BooleanExpressionNode {

    public final IntersectExpContext intersectExpContext;
    public final String voteOutput;

    public IntersectExpNode(IntersectExpContext intersectExpContext, String voteOutput) {
        this.intersectExpContext = intersectExpContext;
        this.voteOutput = voteOutput;
    }

    @Override
    public void getVisited(BooleanExpNodeVisitor visitor) {
        visitor.visitIntersectExpNode(this);
    }

    @Override
    public String getTreeString(int depth) {
        System.out.println("might add treestring");
        return "";
    }
}