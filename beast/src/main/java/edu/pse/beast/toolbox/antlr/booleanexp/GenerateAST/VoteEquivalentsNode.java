package edu.pse.beast.toolbox.antlr.booleanexp.GenerateAST;

import edu.pse.beast.datatypes.booleanExpAST.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.BooleanExpressionNode;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.VoteEquivalentsContext;

public class VoteEquivalentsNode extends BooleanExpressionNode {
	
	public final VoteEquivalentsContext voteEquivalentsContext;
	public final String toOutput;

	public VoteEquivalentsNode(VoteEquivalentsContext voteEquivalentsContext, String toOutput) {
		this.voteEquivalentsContext = voteEquivalentsContext;
		this.toOutput = toOutput;
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
