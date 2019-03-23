package edu.pse.beast.toolbox.antlr.booleanexp.generateast;

import org.antlr.v4.runtime.tree.TerminalNode;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.BooleanExpressionNode;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.VotingListChangeContentContext;

public class VotingListChangeExpNode extends BooleanExpressionNode {
    public final TerminalNode vote;
	public final VotingListChangeContentContext votingListChangeContent;

	public VotingListChangeExpNode(TerminalNode vote, VotingListChangeContentContext votingListChangeContent) {
		this.vote = vote;
		this.votingListChangeContent = votingListChangeContent;
	}

	/**
     * visits this node
     * @param visitor the visitor that visits
     */
    public void getVisited(BooleanExpNodeVisitor visitor) {
        visitor.visitVotingListChangeNode(this);
    }

    public String getTreeString(int depth) {
        System.out.println("might add treestring");
        return "";
    }
}