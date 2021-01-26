package edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election;

import edu.pse.beast.api.electiondescription.CElectionVotingType;
import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.TypeExpression;
import edu.pse.beast.types.InOutType;

public abstract class ElectionTypeNode extends TypeExpression {


	public ElectionTypeNode(InOutType inOutType) {
		super(inOutType);
	}

}
