package edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;

public class ElectPermutationNode extends ElectionTypeNode {
	
	private int electNumber;


	@Override
	public String getTreeString(int depth) {
		return "Permutation Elect " + electNumber;
	}
	
	public void setElectNumber(int electionNumber) {
		this.electNumber = electionNumber;
	}
	
	public int getElectNumber() {
		return electNumber;
	}

	@Override
	public void getVisited(BooleanAstVisitor visitor) {
		visitor.visitElectPermutation(this);
	}

}
