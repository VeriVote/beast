package edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;
import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.BooleanExpressionNode;

public class FalseNode extends BooleanExpressionNode {
	@Override
	public void getVisited(BooleanAstVisitor visitor) {
		visitor.visitBooleanExpFalseNode(this);
	}

	@Override
	public void getVisited(BooleanExpNodeVisitor visitor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTreeString(int depth) {
		// TODO Auto-generated method stub
		return "False";
	}
}
