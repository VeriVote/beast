package edu.pse.beast.api.codegen.helperfunctions;

import java.util.List;

import edu.pse.beast.api.codegen.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.c_code.CFunction;
import edu.pse.beast.api.codegen.c_code.CTypeNameBrackets;

public class CompareVotesHelperFunction extends HelperFunction {

	private ElectionTypeCStruct voteStruct;

	private final static String template2d = 
					"	bool RESULT = LHS.AMT == RHS.AMT;\n"
					+ "	\n"
					+ "	for (int COUNTER = 0; COUNTER < LHS.AMT && result; ++COUNTER) {\n"
					+ "		RESULT = COMPARESINGLEVOTES(LHS, RHS, COUNTER);\n"
					+ "	}\n"
					+ "\n"
					+ "	return RESULT;";
	
	
	public CompareVotesHelperFunction(ElectionTypeCStruct voteStruct) {
		super(HelperFunctionTypes.COMPARE_VOTE_EQUAL);
		this.voteStruct = voteStruct;
	}

	@Override
	public String uniqueName() {
		return this.type.str();
	}

	private String code(String lhsVarName, String rhsVarName, HelperFunctionMap funcMap) {
		if(voteStruct.getVotingType().getListDimensions() == 2) {
			String code = template2d;
			code = code.replaceAll("RESULT", "result");
			code = code.replaceAll("AMT", voteStruct.getAmtName());
			code = code.replaceAll("COUNTER", "i");
			code = code.replaceAll("LHS", lhsVarName);
			code = code.replaceAll("RHS", rhsVarName);
			code = code.replaceAll("COMPARESINGLEVOTES", funcMap.getCompareSingleVotesFunction().uniqueName());
			return code;
		}
		return null;
	}

	@Override
	public CFunction cfunc(HelperFunctionMap funcMap) {
		// function declaration: bool compareVotesEqual(VoteStruct lhs, VoteStruct rhs)
		String lhsVarName = "lhs";
		String rhsVarName = "rhs";

		List<CTypeNameBrackets> args = List.of(
				new CTypeNameBrackets(voteStruct.getStruct().getName(), lhsVarName, ""), 
				new CTypeNameBrackets(voteStruct.getStruct().getName(), rhsVarName, ""));

		CFunction function = new CFunction(uniqueName(), "bool", args);

		function.setCode(List.of(code(lhsVarName, rhsVarName, funcMap)));		
		
		return function;
	}

	@Override
	public List<HelperFunction> dependencies() {
		return List.of(new CompareSingleVotesHelperFunc(voteStruct));
	}

}
