package edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election;

import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;
import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.types.InOutType;

public class ElectIntersectionNode extends ElectionTypeNode {

	private List<Integer> numbers = new ArrayList<>();
	
	public ElectIntersectionNode(InOutType inOutType) {
		super(inOutType);
	}
	
	@Override
	public void getVisited(BooleanExpNodeVisitor visitor) {
		
	}

	@Override
	public String getTreeString(int depth) {
		return "Intersect Elects";
	}

	public void addElectNumber(int number) {
		numbers.add(number);		
	}

	@Override
	public void getVisited(BooleanAstVisitor visitor) {
		// TODO Auto-generated method stub
		
	}
	
}
