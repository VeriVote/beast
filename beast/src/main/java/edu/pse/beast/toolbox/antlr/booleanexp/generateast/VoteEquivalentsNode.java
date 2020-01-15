package edu.pse.beast.toolbox.antlr.booleanexp.generateast;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.BooleanExpressionNode;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.VoteEquivalentsContext;

public class VoteEquivalentsNode extends BooleanExpressionNode {
    private final VoteEquivalentsContext voteEquivalentsContext;
    private final String toOutput;
    private final String voteOutputLength;

    public VoteEquivalentsNode(final VoteEquivalentsContext voteEquivContext,
                               final String toOutputString,
                               final String voteOutputLen) {
        this.voteEquivalentsContext = voteEquivContext;
        this.toOutput = toOutputString;
        this.voteOutputLength = voteOutputLen;
    }

    @Override
    public void getVisited(final BooleanExpNodeVisitor visitor) {
        visitor.visitVoteEquivalentsNode(this);
    }

    @Override
    public String getTreeString(final int depth) {
        System.out.println("might add treestring");
        return "";
    }

    public VoteEquivalentsContext getVoteEquivalentsContext() {
        return voteEquivalentsContext;
    }

    public String getToOutput() {
        return toOutput;
    }

    public String getVoteOutputLength() {
        return voteOutputLength;
    }
}
