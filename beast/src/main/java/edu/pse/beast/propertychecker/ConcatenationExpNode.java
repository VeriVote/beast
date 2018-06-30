package edu.pse.beast.propertychecker;

import edu.pse.beast.datatypes.booleanExpAST.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.BooleanExpressionNode;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.ConcatenationExpContext;

public class ConcatenationExpNode extends BooleanExpressionNode {

	public final ConcatenationExpContext concatenationExpContext;
	public final String voteOutput;

	public ConcatenationExpNode(ConcatenationExpContext concatenationExpContext, String voteOutput) {
		this.concatenationExpContext = concatenationExpContext;
		this.voteOutput = voteOutput;
	}

	@Override
	public void getVisited(BooleanExpNodeVisitor visitor) {
		visitor.visitConcatenationExpNode(this);
	}

	@Override
	public String getTreeString(int depth) {
		System.out.println("might add treestring");
		return "";
	}
}
