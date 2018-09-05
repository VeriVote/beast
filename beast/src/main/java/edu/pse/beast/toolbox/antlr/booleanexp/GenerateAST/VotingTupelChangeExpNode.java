package edu.pse.beast.toolbox.antlr.booleanexp.GenerateAST;

import edu.pse.beast.datatypes.booleanExpAST.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.BooleanExpressionNode;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.SplitExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.TupleContext;

public class VotingTupelChangeExpNode extends BooleanExpressionNode {

	public final TupleContext tuple;
	public final SplitExpContext splitExp;

	public VotingTupelChangeExpNode(TupleContext tuple, SplitExpContext splitExp) {
		this.tuple = tuple;
		this.splitExp = splitExp;
	}

	@Override
	public void getVisited(BooleanExpNodeVisitor visitor) {
		visitor.visitVotingTupleChangeNode(this);
	}

	@Override
	public String getTreeString(int depth) {
		System.out.println("might add treestring");
		return "";
	}
}
