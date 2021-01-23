package edu.pse.beast.datatypes.booleanexpast.othervaluednodes;

import edu.pse.beast.api.electiondescription.CElectionVotingType;
import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.BooleanExpressionNode;

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
