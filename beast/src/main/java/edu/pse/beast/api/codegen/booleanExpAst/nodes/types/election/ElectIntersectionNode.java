package edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election;

import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;

public class ElectIntersectionNode extends ElectionTypeNode {

	private List<Integer> numbers = new ArrayList<>();	
	

	public List<Integer> getNumbers() {
		return numbers;
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
		visitor.visitElectIntersectionNode(this);
	}
	
}
