package edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes;

import edu.pse.beast.datatypes.booleanExpAST.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.TypeExpression;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.IntersectExpContext;
import edu.pse.beast.types.InOutType;

public class IntersectTypeExpNode extends TypeExpression {

	public final IntersectExpContext context;
	
	public IntersectTypeExpNode(InOutType type, IntersectExpContext context) {
		super(type);
		this.context = context;
	}

	@Override
	public void getVisited(BooleanExpNodeVisitor visitor) {
		visitor.visitIntersectTypeExpNode(this);
	}

	@Override
	public String getTreeString(int depth) {
		// TODO Auto-generated method stub
		return null;
	}

}
