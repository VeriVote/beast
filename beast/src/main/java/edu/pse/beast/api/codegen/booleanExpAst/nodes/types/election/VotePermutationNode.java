package edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;

//TODO fix code generation bug with comparisons

public class VotePermutationNode extends ElectionTypeNode {
	
	private int voteNumber;

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
