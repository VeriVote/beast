package edu.pse.beast.toolbox.antlr.booleanexp.generateast;

import org.antlr.v4.runtime.tree.TerminalNode;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.BooleanExpressionNode;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.VotingListChangeContentContext;

public class VotingListChangeExpNode extends BooleanExpressionNode {
    private final TerminalNode vote;
    private final VotingListChangeContentContext votingListChangeContent;

    public VotingListChangeExpNode(final TerminalNode voteNode,
                                   final VotingListChangeContentContext
                                       votingListChangeCont) {
        this.vote = voteNode;
        this.votingListChangeContent = votingListChangeCont;
    }

    /**
     * Visits this node.
     *
     * @param visitor the visitor that visits
     */
    public void getVisited(final BooleanExpNodeVisitor visitor) {
        visitor.visitVotingListChangeNode(this);
    }

    public String getTreeString(final int depth) {
        System.out.println("might add treestring");
        return "";
    }

    public TerminalNode getVote() {
        return vote;
    }

    public VotingListChangeContentContext getVotingListChangeContent() {
        return votingListChangeContent;
    }
}
