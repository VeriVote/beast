package edu.pse.beast.api.codegen.helperfunctions;

import java.util.List;

import edu.pse.beast.api.codegen.c_code.CFunction;
import edu.pse.beast.api.electiondescription.CElectionVotingType;

public class ComparisonHelperFunction extends HelperFunction {
	private CElectionVotingType typeToCompare;
	
	public ComparisonHelperFunction(CElectionVotingType typeToCompare) {
		super(HelperFunctionTypes.COMPARE_EQUAL);
		this.typeToCompare = typeToCompare;
	}

	@Override
	public String uniqueName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CFunction cfunc(HelperFunctionMap funcMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HelperFunction> dependencies() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
