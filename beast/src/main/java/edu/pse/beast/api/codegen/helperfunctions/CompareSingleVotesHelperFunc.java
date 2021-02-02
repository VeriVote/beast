package edu.pse.beast.api.codegen.helperfunctions;

import java.util.List;

import edu.pse.beast.api.codegen.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.TypeManager;
import edu.pse.beast.api.codegen.c_code.CFunction;
import edu.pse.beast.api.codegen.c_code.CTypeNameBrackets;
import edu.pse.beast.api.electiondescription.CElectionSimpleTypes;

public class CompareSingleVotesHelperFunc extends HelperFunction {

	private final static String template2D = 
				"	bool RESULT = true;\n"
			+ 	"	for (int COUNTER = 0; COUNTER < AMT && RESULT; ++COUNTER) {\n"
			+ 	"			RESULT = LHS.LIST[POS][COUNTER] == RHS.LIST[POS][COUNTER];\n"  
			+	"	}\n" +
				"	return RESULT;";

	private ElectionTypeCStruct voteStruct;

	public CompareSingleVotesHelperFunc(ElectionTypeCStruct voteStruct) {
		super(HelperFunctionTypes.COMPARE_SINGLE_VOTES_EQUAL);
		this.voteStruct = voteStruct;
	}

	@Override
	public String uniqueName() {
		return this.type.str();
	}

	private String funcCode(String posVarName, String lhsVarName, String rhsVarName) {
		if (voteStruct.getVotingType().getListDimensions() == 2) {
			String code = template2D;
			code = code.replaceAll("RESULT", "result");
			code = code.replaceAll("COUNTER", "i");
			code = code.replaceAll("LIST", voteStruct.getListName());
			code = code.replaceAll("AMT", voteStruct.getVotingType().getListSizes().get(1).toString());
			code = code.replaceAll("POS", posVarName);
			code = code.replaceAll("LHS", lhsVarName);
			code = code.replaceAll("RHS", rhsVarName);
			return code;
		}
		return null;
	}

	@Override
	public CFunction cfunc(HelperFunctionMap funcMap) {
		//function declaration: bool compareVotes(VoteStruct lhs, VoteStruct rhs, int pos)
		String lhsVarName = "lhs";
		String rhsVarName = "rhs";
		String posVarName = "pos";
		List<CTypeNameBrackets> args = List.of(
				new CTypeNameBrackets(voteStruct.getStruct().getName(), lhsVarName, ""),
				new CTypeNameBrackets(voteStruct.getStruct().getName(), rhsVarName, ""), 
				new CTypeNameBrackets(TypeManager.SimpleTypeToCType(CElectionSimpleTypes.INT), posVarName, ""));
		CFunction function = new CFunction(uniqueName(), TypeManager.SimpleTypeToCType(CElectionSimpleTypes.BOOL),
				args);
		function.setCode(List.of(funcCode(posVarName, lhsVarName, rhsVarName)));
		return function;
	}

	@Override
	public List<HelperFunction> dependencies() {
		return List.of();
	}

}
