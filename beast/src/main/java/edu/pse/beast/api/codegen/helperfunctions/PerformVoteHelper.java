package edu.pse.beast.api.codegen.helperfunctions;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.cbmc.generated_code_info.CBMCGeneratedCodeInfo;
import edu.pse.beast.api.codegen.helperfunctions.init_vote.SymbVarInitVoteHelper;

public class PerformVoteHelper {

	private static String template = "ELECT_TYPE GENERATED_VAR = VOTE_FUNC_NAME(VOTE_VAR, CURRENT_AMT_VOTER, CURRENT_AMT_CAND, CURRENT_AMT_SEAT);\n";

	public static String getResultVarName(int voteNumber) {
		String resultVarName = "result" + voteNumber;
		return resultVarName;
	}

	public static String generateCode(int voteNumber,
			ElectionTypeCStruct voteArrStruct, ElectionTypeCStruct voteStruct,
			CodeGenOptions options, String votingFunctionName,
			CBMCGeneratedCodeInfo cbmcGeneratedCode) {
		String resultVarName = getResultVarName(voteNumber);
		cbmcGeneratedCode.addElectVariableName(voteNumber, resultVarName);

		String voteVarName = SymbVarInitVoteHelper.getVoteVarName(voteNumber);

		String code = template;
		code = code.replaceAll("ELECT_TYPE", voteStruct.getStruct().getName());
		code = code.replaceAll("GENERATED_VAR", resultVarName);
		code = code.replaceAll("VOTE_FUNC_NAME", votingFunctionName);
		code = code.replaceAll("VOTE_TYPE", voteStruct.getStruct().getName());
		code = code.replaceAll("VOTE_VAR", voteVarName);
		code = code.replaceAll("CURRENT_AMT_VOTER",
				SymbVarInitVoteHelper.getCurrentAmtVoter(voteNumber));
		code = code.replaceAll("CURRENT_AMT_CAND",
				SymbVarInitVoteHelper.getCurrentAmtCand(voteNumber));
		code = code.replaceAll("CURRENT_AMT_SEAT",
				SymbVarInitVoteHelper.getCurrentAmtSeat(voteNumber));

		return code;
	}
}
