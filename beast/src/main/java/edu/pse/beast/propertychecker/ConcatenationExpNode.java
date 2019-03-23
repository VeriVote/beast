package edu.pse.beast.propertychecker;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.BooleanExpressionNode;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.ConcatenationExpContext;

public class ConcatenationExpNode extends BooleanExpressionNode {

    public final ConcatenationExpContext concatenationExpContext;
    public final String voteOutput;
    public final String voteOutoutLength;

    public ConcatenationExpNode(ConcatenationExpContext concatenationExpContext, String voteOutput,
            String voteOutoutLength) {
        this.concatenationExpContext = concatenationExpContext;
        this.voteOutput = voteOutput;
        this.voteOutoutLength = voteOutoutLength;
    }

    @Override
    public void getVisited(BooleanExpNodeVisitor visitor) {
        visitor.visitConcatenationExpNode(this);
    }

    @Override
    public String getTreeString(int depth) {
        System.out.println("might add treestring");
        return "";
    }
}
