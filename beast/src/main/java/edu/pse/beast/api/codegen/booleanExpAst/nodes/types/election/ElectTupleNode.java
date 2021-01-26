package edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election;

import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;
import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.types.InOutType;

public class ElectTupleNode extends ElectionTypeNode {
	private List<Integer> electNumbers = new ArrayList<>();

	public ElectTupleNode(InOutType inOutType) {
		super(inOutType);
	}

	@Override
	public void getVisited(BooleanExpNodeVisitor visitor) {
	}

	@Override
	public String getTreeString(int depth) {
		return "Elect Tuple";
	}

	public void addElectNumber(int number) {
		electNumbers.add(number);
	}

	@Override
	public void getVisited(BooleanAstVisitor visitor) {
		// TODO Auto-generated method stub
		
	}

}
