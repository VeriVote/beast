package edu.pse.beast.propertychecker;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.BooleanExpressionNode;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.ConcatenationExpContext;

public class ConcatenationExpNode extends BooleanExpressionNode {

    private final ConcatenationExpContext concatenationExpContext;
    private final String voteOutput;
    private final String voteOutputLength;

    public ConcatenationExpNode(final ConcatenationExpContext concatExpContext,
                                final String voteOutputString,
                                final String voteOutputLen) {
        this.concatenationExpContext = concatExpContext;
        this.voteOutput = voteOutputString;
        this.voteOutputLength = voteOutputLen;
    }

    @Override
    public void getVisited(final BooleanExpNodeVisitor visitor) {
        visitor.visitConcatenationExpNode(this);
    }

    @Override
    public String getTreeString(final int depth) {
        System.out.println("might add treestring");
        return "";
    }

    public ConcatenationExpContext getConcatenationExpContext() {
        return concatenationExpContext;
    }

    public String getVoteOutput() {
        return voteOutput;
    }

    public String getVoteOutputLength() {
        return voteOutputLength;
    }
}
