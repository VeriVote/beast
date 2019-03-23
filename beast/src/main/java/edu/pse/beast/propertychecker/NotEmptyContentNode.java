package edu.pse.beast.propertychecker;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.BooleanExpressionNode;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.NotEmptyContentContext;

public class NotEmptyContentNode extends BooleanExpressionNode {

	public final NotEmptyContentContext context;
	public final String votingOutput;
	
	public NotEmptyContentNode(NotEmptyContentContext context, String votingOutput) {
		this.context = context;
		this.votingOutput = votingOutput;
	}

	@Override
	public void getVisited(BooleanExpNodeVisitor visitor) {
		visitor.visitNotEmptyContentNode(this);
	}

	@Override
	public String getTreeString(int depth) {
		// TODO Auto-generated method stub
		return null;
	}

}
