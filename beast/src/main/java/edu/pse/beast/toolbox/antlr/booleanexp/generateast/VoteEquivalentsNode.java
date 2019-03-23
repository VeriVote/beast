package edu.pse.beast.toolbox.antlr.booleanexp.generateast;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.BooleanExpressionNode;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.VoteEquivalentsContext;

public class VoteEquivalentsNode extends BooleanExpressionNode {
    public final VoteEquivalentsContext voteEquivalentsContext;
    public final String toOutput;
    public final String voteOutputLength;

    public VoteEquivalentsNode(VoteEquivalentsContext voteEquivalentsContext, String toOutput,
            String voteOutputLength) {
        this.voteEquivalentsContext = voteEquivalentsContext;
        this.toOutput = toOutput;
        this.voteOutputLength = voteOutputLength;
    }

    @Override
    public void getVisited(BooleanExpNodeVisitor visitor) {
        visitor.visitVoteEquivalentsNode(this);
    }

    @Override
    public String getTreeString(int depth) {
        System.out.println("might add treestring");
        return "";
    }
}