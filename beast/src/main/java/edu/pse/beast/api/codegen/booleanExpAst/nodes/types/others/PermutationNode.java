package edu.pse.beast.api.codegen.booleanExpAst.nodes.types.others;

import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.BooleanExpressionNode;
import edu.pse.beast.api.electiondescription.CElectionVotingType;
import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;

public class PermutationNode extends BooleanExpressionNode {

	private CElectionVotingType permutatedType;
	private AccessValueNode permutatedExp;	

	public PermutationNode(CElectionVotingType permutatedType, AccessValueNode permutatedExp) {
		super();
		this.permutatedType = permutatedType;
		this.permutatedExp = permutatedExp;
	}

	@Override
	public void getVisited(BooleanExpNodeVisitor visitor) {
	}

	@Override
	public String getTreeString(int depth) {
		return "PERM(" + permutatedExp.getTreeString(depth) + ")";
	}

}
