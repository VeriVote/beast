package edu.pse.beast.propertychecker;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.BooleanExpressionNode;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.PermutationExpContext;

public class PermutationExpNode extends BooleanExpressionNode {
    private final PermutationExpContext permutationExpContext;
    private final String voteOutput;
    private final String voteOutputLength;

    public PermutationExpNode(final PermutationExpContext permExpContext,
                              final String voteOutputString,
                              final String voteOutputLen) {
        this.permutationExpContext = permExpContext;
        this.voteOutput = voteOutputString;
        this.voteOutputLength = voteOutputLen;
    }

    @Override
    public void getVisited(final BooleanExpNodeVisitor visitor) {
        visitor.visitPermutationExpNode(this);
    }

    @Override
    public String getTreeString(final int depth) {
        System.out.println("might add treestring");
        return "";
    }

    public PermutationExpContext getPermutationExpContext() {
        return permutationExpContext;
    }

    public String getVoteOutput() {
        return voteOutput;
    }

    public String getVoteOutputLength() {
        return voteOutputLength;
    }
}
