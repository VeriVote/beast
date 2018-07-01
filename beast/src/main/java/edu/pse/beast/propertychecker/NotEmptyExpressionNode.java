package edu.pse.beast.propertychecker;

import edu.pse.beast.datatypes.booleanExpAST.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.BooleanExpressionNode;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.NotEmptyExpContext;

public class NotEmptyExpressionNode extends BooleanExpressionNode {

	public final NotEmptyExpContext context;
	
	public NotEmptyExpressionNode(NotEmptyExpContext context) {
		this.context = context;
	}
	
	@Override
	public void getVisited(BooleanExpNodeVisitor visitor) {
		visitor.visitNotEmptyExpNode(this);
	}

	@Override
	public String getTreeString(int depth) {
		// TODO Auto-generated method stub
		return null;
	}
}
