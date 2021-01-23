package edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election;

import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.types.InOutType;

public class VoteIntersectionNode extends ElectionTypeNode {
	private List<Integer> numbers = new ArrayList<>();

	public VoteIntersectionNode(InOutType inOutType) {
		super(inOutType);
	}

	@Override
	public void getVisited(BooleanExpNodeVisitor visitor) {
	}

	@Override
	public String getTreeString(int depth) {
		return "Intersect Votes";
	}

	public void addVoteNumber(int number) {
		numbers.add(number);		
	}

}
