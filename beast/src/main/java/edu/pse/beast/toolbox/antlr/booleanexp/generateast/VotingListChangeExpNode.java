package edu.pse.beast.toolbox.antlr.booleanexp.generateast;

import org.antlr.v4.runtime.tree.TerminalNode;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.BooleanExpressionNode;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.VotingListChangeContentContext;

/**
 * The Class VotingListChangeExpNode.
 *
 * @author Lukas Stapelbroek
 */
public class VotingListChangeExpNode extends BooleanExpressionNode {

    /** The vote. */
    private final TerminalNode vote;

    /** The voting list change content. */
    private final VotingListChangeContentContext votingListChangeContent;

    /**
     * The constructor.
     *
     * @param voteNode
     *            the vote node
     * @param votingListChangeCont
     *            the voting list change cont
     */
    public VotingListChangeExpNode(final TerminalNode voteNode,
                                   final VotingListChangeContentContext
                                           votingListChangeCont) {
        this.vote = voteNode;
        this.votingListChangeContent = votingListChangeCont;
    }

    @Override
    public void getVisited(final BooleanExpNodeVisitor visitor) {
        visitor.visitVotingListChangeNode(this);
    }

    @Override
    public String getTreeString(final int depth) {
        System.out.println("might add treestring");
        return "";
    }

    /**
     * Gets the vote.
     *
     * @return the vote
     */
    public TerminalNode getVote() {
        return vote;
    }

    /**
     * Gets the voting list change content.
     *
     * @return the voting list change content
     */
    public VotingListChangeContentContext getVotingListChangeContent() {
        return votingListChangeContent;
    }
}
