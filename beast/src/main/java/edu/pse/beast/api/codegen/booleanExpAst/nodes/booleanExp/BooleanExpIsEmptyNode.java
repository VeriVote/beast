package edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;
import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.BooleanExpressionNode;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.TypeExpression;

public class BooleanExpIsEmptyNode extends BooleanExpressionNode {
	
	TypeExpression innerNode;

	
	
	public BooleanExpIsEmptyNode(TypeExpression innerNode) {
		super();
		this.innerNode = innerNode;
	}

	@Override
	public void getVisited(BooleanExpNodeVisitor visitor) {
		
	}

	@Override
	public void getVisited(BooleanAstVisitor visitor) {
	}

	@Override
	public String getTreeString(int depth) {
		return "EMPTY " + innerNode.getTreeString(depth + 1);
	}

}
