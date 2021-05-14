package edu.pse.beast.api.codegen.cbmc;

import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.api.codegen.c_code.CFunction;
import edu.pse.beast.api.specificcbmcrun.SpecificCBMCRunParametersInfo;

public class SpecificParamsMainGenerator {
	public static CFunction main(ElectionTypeCStruct voteInputStruct,
			ElectionTypeCStruct voteResultStruct,
			SpecificCBMCRunParametersInfo params, CodeGenOptions options,
			CBMCGeneratedCodeInfo cbmcGeneratedCodeInfo) {
		CFunction main = new CFunction("main",
				List.of("int argc", "char ** argv"), "int");

		List<String> code = params.getVotingParameters()
				.generateVoteStructInitCode(voteInputStruct, voteResultStruct,
						options, cbmcGeneratedCodeInfo);

		// perform voting function calls

		String votingFunctionName = params.getDescr().getVotingFunction()
				.getName();
		String votingFunctionCallTemplate = "VOTE_RES_STRUCT VOTE_RES_VAR = VOTING_FUNC(VOTING_INPUT_VAR);";

		for (int i = 0; i < params.getVotingParameters().getAmtVotes(); ++i) {
			String votingInputVarName = params.getVotingParameters()
					.getVotingVarName(i);
			String votingResStructName = voteResultStruct.getStruct().getName();
			String votingResVarName = "elect" + i;		
			String votingCallString = votingFunctionCallTemplate
					.replaceAll("VOTE_RES_STRUCT", votingResStructName)
					.replaceAll("VOTE_RES_VAR", votingResVarName)
					.replaceAll("VOTING_FUNC", votingFunctionName)
					.replaceAll("VOTING_INPUT_VAR", votingInputVarName);
			code.add(votingCallString);
		}

		code.add("assert(0);");
		code.add("return 0;");
		
		main.setCode(code);
		
		return main;
	}
}
