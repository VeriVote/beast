package edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.types.InOutType;

public class EmptyListNode extends ElectionTypeNode {

	public EmptyListNode(InOutType inOutType) {
		super(inOutType);
	}

	@Override
	public void getVisited(BooleanExpNodeVisitor visitor) {		
	}

	@Override
	public String getTreeString(int depth) {
		return "EmptyList";
	}

}
