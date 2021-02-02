package edu.pse.beast.api.codegen.helperfunctions;

import java.util.List;

import edu.pse.beast.api.codegen.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.c_code.CFunction;
import edu.pse.beast.api.codegen.c_code.CTypeNameBrackets;

public class IntersectVotesHelperFunc extends HelperFunction {
	
	private static final String template2D = 
			"	TYPE CREATED;\n"
			+ "	int IDX = 0;\n"
			+ "	for (int COUNTER = 0; COUNTER < LHS.AMT; ++COUNTER) {\n"
			+ "		if (COMPAREVOTES(LHS, RHS, COUNTER)) {\n"
			+ "			COPYVOTES(CREATED, IDX, LHS, COUNTER);\n"
			+ "			IDX++;\n"
			+ "		}\n"
			+ "	}\n"
			+ "	return CREATED;";
	

	ElectionTypeCStruct generatedStruct;

	public IntersectVotesHelperFunc(ElectionTypeCStruct voteStruct) {
		super(HelperFunctionTypes.INTERSECT_VOTES);
		generatedStruct = voteStruct;
	}

	@Override
	public String uniqueName() {
		return this.type.str();
	}
	
	
	
	String code(HelperFunctionMap funcMap, String lhsVarName, String rhsVarName) {
		if(generatedStruct.getVotingType().getListDimensions() == 2) {
			String createdPosVarName = "createdPos";
			String code = template2D;				

			code = code.replaceAll("TYPE", generatedStruct.getStruct().getName());
			code = code.replaceAll("CREATED", "created");
			code = code.replaceAll("IDX", "posInCreated");
			code = code.replaceAll("COUNTER", "i");
			code = code.replaceAll("AMT", generatedStruct.getAmtName());
			code = code.replaceAll("LHS", lhsVarName);
			code = code.replaceAll("RHS", rhsVarName);
			
			code = code.replaceAll("COMPAREVOTES", funcMap.getCompareSingleVotesFunction().uniqueName());
			code = code.replaceAll("COPYVOTES", funcMap.getCopyVotesFunction().uniqueName());
			
			return code;
		}
		
		return null;
	}



	@Override
	public CFunction cfunc(HelperFunctionMap funcMap) {
		//function declaration: VoteStruct intersectVotes(VoteStruct lhs, VoteStruct rhs) {
		String voteStructType = generatedStruct.getStruct().getName();
		String lhsVarName = "lhs";
		String rhsVarName = "rhs";
		List<CTypeNameBrackets> args = List.of(
					new CTypeNameBrackets(voteStructType, lhsVarName, ""),
					new CTypeNameBrackets(voteStructType, rhsVarName, "")
				);
		CFunction function = new CFunction(uniqueName(), voteStructType, args);
		function.setCode(List.of(code(funcMap, lhsVarName, rhsVarName)));
		return function;
	}

	@Override
	public List<HelperFunction> dependencies() {
		return List.of(new CompareSingleVotesHelperFunc(generatedStruct), new CopyVotesHelperFunc(generatedStruct));
	}

}
