package edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;
import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.BooleanExpressionNode;

public class BooleanExpListElementNode extends BooleanExpressionNode {

	private BooleanExpressionNode firstChild;	
	private String completeCode;
	
	public BooleanExpListElementNode(String completeCode, BooleanExpressionNode firstChild) {
		this.firstChild = firstChild;
		this.completeCode = completeCode;
	}

	@Override
	public void getVisited(BooleanExpNodeVisitor visitor) {		
	}

	@Override
	public void getVisited(BooleanAstVisitor visitor) {
		visitor.visitBooleanExpListElementNode(this);
	}

	@Override
	public String getTreeString(int depth) {
		return firstChild.getTreeString(depth);
	}
	
	public String getCompleteCode() {
		return completeCode;
	}
	
	public BooleanExpressionNode getFirstChild() {
		return firstChild;
	}

}
