package edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;
import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.types.InOutType;

//TODO fix code generation bug with comparisons

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

	public int getVoteNumber() {
		return voteNumber;
	}
	
	@Override
	public void getVisited(BooleanAstVisitor visitor) {
		visitor.visitVotePermutation(this);		
	}

}
