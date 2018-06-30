package edu.pse.beast.toolbox.antlr.booleanexp.GenerateAST;

import org.antlr.v4.runtime.tree.TerminalNode;

import edu.pse.beast.datatypes.booleanExpAST.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.BooleanExpressionNode;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.IntersectExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.SplitExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.TupleContext;

public class CandidateListChangeExpNode extends BooleanExpressionNode {

	public final TerminalNode elect;
	public final IntersectExpContext intersectExp;
	
	public CandidateListChangeExpNode(TerminalNode elect, IntersectExpContext intersectExp) {
		this.elect = elect;
		this.intersectExp = intersectExp;
	}

	@Override
	public void getVisited(BooleanExpNodeVisitor visitor) {
		visitor.visitCandidateListChangeExpNode(this);
	}

	@Override
	public String getTreeString(int depth) {
		System.out.println("might add treestring");
		return "";
	}

}
