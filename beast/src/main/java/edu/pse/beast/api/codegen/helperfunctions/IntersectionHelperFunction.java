package edu.pse.beast.api.codegen.helperfunctions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.pse.beast.api.codegen.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.c_code.CFunction;

public class IntersectionHelperFunction extends HelperFunction {
	ElectionTypeCStruct typeToCreate;

	public IntersectionHelperFunction(ElectionTypeCStruct typeToCreated) {
		super(HelperFunctionTypes.INTERSECT);
		this.typeToCreate = typeToCreated;
	}

	@Override
	public String uniqueName() {
		return "intersect" + typeToCreate.getStruct().getName();
	}

	@Override
	public CFunction cfunc(HelperFunctionMap funcMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HelperFunction> dependencies() {
		return List.of(new ComparisonHelperFunction(typeToCreate.getVotingType().getTypeOneDimLess()));
	}

}
