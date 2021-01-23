package edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.types.InOutType;

public class VotePermutationNode extends ElectionTypeNode {
	
	private int voteNumber;

	public VotePermutationNode(InOutType inOutType) {
		super(inOutType);
	}

	@Override
	public void getVisited(BooleanExpNodeVisitor visitor) {
		
	}

	@Override
	public String getTreeString(int depth) {
		return "Permutation Vote " + voteNumber;
	}
	
	public void setVoteNumber(int voteNumber) {
		this.voteNumber = voteNumber;
	}

}
