package edu.pse.beast.propertychecker;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.BooleanExpressionNode;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.PermutationExpContext;

public class PermutationExpNode extends BooleanExpressionNode {
    public final PermutationExpContext permutationExpContext;
    public final String voteOutput;
    public final String voteOutoutLength;

    public PermutationExpNode(PermutationExpContext permutationExpContext,
                              String voteOutput,
                              String voteOutputLength) {
        this.permutationExpContext = permutationExpContext;
        this.voteOutput = voteOutput;
        this.voteOutoutLength = voteOutputLength;
    }

    @Override
    public void getVisited(BooleanExpNodeVisitor visitor) {
        visitor.visitPermutationExpNode(this);
    }

    @Override
    public String getTreeString(int depth) {
        System.out.println("might add treestring");
        return "";
    }
}
