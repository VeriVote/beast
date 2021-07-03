package edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.others.TypeExpression;

public class BooleanExpIsEmptyNode extends BooleanExpressionNode {
	
	TypeExpression innerNode;
	
	public BooleanExpIsEmptyNode(TypeExpression innerNode) {
		super();
		this.innerNode = innerNode;
	}

	@Override
	public void getVisited(BooleanAstVisitor visitor) {
		visitor.visitEmptyNode(this);
	}

	@Override
	public String getTreeString(int depth) {
		return "EMPTY " + innerNode.getTreeString(depth + 1);
	}

	public TypeExpression getInnerNode() {
		return innerNode;
	}
}
