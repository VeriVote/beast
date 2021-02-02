package edu.pse.beast.api.codegen.helperfunctions;

import java.util.List;

import edu.pse.beast.api.codegen.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.c_code.CFunction;
import edu.pse.beast.api.codegen.c_code.CTypeNameBrackets;

public class InitializeVoteHelperFunc extends HelperFunction {
	
	//TODO acceptable ranges for vote ints
	private static final String template2D = 
			"	INPUT.OUTERAMT = nondet_uint();\n"
			+ "	assume(INPUT.OUTERAMT <= MAXVOTES);\n"
			+ "	for (int OUTERCOUNTER = 0; OUTERCOUNTER < INPUT.OUTERAMT; ++OUTERCOUNTER) {\n"
			+ "		for (int INNERCOUNTER = 0; INNERCOUNTER < INNERAMT; ++INNERCOUNTER) {\n"
			+ "			INPUT.LIST[OUTERCOUNTER][INNERCOUNTER] = nondet_uint();\n"
			+ "			assume(INPUT.LIST[OUTERCOUNTER][INNERCOUNTER] <= 1);\n"	
			+ "		}\n"
			+ "	}\n"
			+ "\n"
			+ "	return INPUT;";
	
	ElectionTypeCStruct voteStruct;
	

	public InitializeVoteHelperFunc(ElectionTypeCStruct voteStruct) {
		super(HelperFunctionTypes.INIT_VOTE);
		this.voteStruct = voteStruct;
	}

	@Override
	public String uniqueName() {
		return this.type.str();
	}
	
	private String code(String inputVarName) {
		if(voteStruct.getVotingType().getListDimensions() == 2) {
			String code = template2D;
			code = code.replaceAll("INPUT", inputVarName);
			code = code.replaceAll("OUTERCOUNTER", "i");
			code = code.replaceAll("MAXVOTES", voteStruct.getVotingType().getListSizes().get(0).toString());
			code = code.replaceAll("OUTERAMT", voteStruct.getAmtName());
			code = code.replaceAll("INNERCOUNTER", "j");
			code = code.replaceAll("INNERAMT", voteStruct.getVotingType().getListSizes().get(1).toString());
			code = code.replaceAll("LIST", voteStruct.getListName());
			return code;
		}
		return null;
	}

	@Override
	public CFunction cfunc(HelperFunctionMap funcMap) {
		// function declaration: VoteStruct initializeVote(VoteStruct vote)
		String inputVoteVarName = "vote";

		List<CTypeNameBrackets> args = List
				.of(new CTypeNameBrackets(voteStruct.getStruct().getName(), inputVoteVarName, ""));
		CFunction function = new CFunction(uniqueName(), voteStruct.getStruct().getName(), args);
		function.setCode(List.of(code(inputVoteVarName)));
		return function;
	}

	@Override
	public List<HelperFunction> dependencies() {
		return List.of();
	}

}
