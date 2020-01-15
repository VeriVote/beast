package edu.pse.beast.propertychecker;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.BooleanExpressionNode;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.IntersectContentContext;

public class IntersectContentNode extends BooleanExpressionNode {

    private final IntersectContentContext intersectContentContext;
    private final String voteOutput;

    public IntersectContentNode(final IntersectContentContext intersectContContext,
                                final String voteOutputString) {
        this.intersectContentContext = intersectContContext;
        this.voteOutput = voteOutputString;
    }

    @Override
    public void getVisited(final BooleanExpNodeVisitor visitor) {
        visitor.visitIntersectContentNode(this);
    }

    @Override
    public String getTreeString(final int depth) {
        System.out.println("might add treestring");
        return "";
    }

    public IntersectContentContext getIntersectContentContext() {
        return intersectContentContext;
    }

    public String getVoteOutput() {
        return voteOutput;
    }
}
