package edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election;

import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;
import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.types.InOutType;

public class VoteTupleNode extends ElectionTypeNode {
	
	private List<Integer> voteNumbers = new ArrayList<>();

	public VoteTupleNode(InOutType inOutType) {
		super(inOutType);
	}

	@Override
	public void getVisited(BooleanExpNodeVisitor visitor) {
	}

	@Override
	public String getTreeString(int depth) {
		return "Vote Tuple";
	}
	
	public List<Integer> getNumbers() {
		return voteNumbers;
	}

	public void addVoteNumber(int number) {
		voteNumbers.add(number);
	}

	@Override
	public void getVisited(BooleanAstVisitor visitor) {
		visitor.visitVoteTuple(this);		
	}

}
