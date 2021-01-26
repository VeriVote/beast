package edu.pse.beast.api.codegen.helperfunctions;

import java.util.List;

import edu.pse.beast.api.codegen.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.c_code.CFunction;
import edu.pse.beast.api.codegen.c_code.CTypeAndName;

public class IntersectHelperFunction implements HelperFunction {

	ElectionTypeCStruct electionTypeCStruct;

	public IntersectHelperFunction(ElectionTypeCStruct electionTypeCStruct) {
		this.electionTypeCStruct = electionTypeCStruct;
	}

	@Override
	public CFunction generateCFunc() {
		String lhsArgName = "lhs";
		String rhsArgName = "rhs";

		CFunction func = new CFunction(getUUID(), electionTypeCStruct.getStruct().getName(),
				List.of(new CTypeAndName(electionTypeCStruct.getStruct().getName(), lhsArgName),
						new CTypeAndName(electionTypeCStruct.getStruct().getName(), rhsArgName)));

		String generatedVarName = "created";
		
		

		return func;
	}

	@Override
	public String getUUID() {
		return "intersect" + electionTypeCStruct.getStruct().getName();
	}

}
