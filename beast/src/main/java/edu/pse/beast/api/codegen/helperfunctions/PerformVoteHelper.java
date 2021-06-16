package edu.pse.beast.api.codegen.helperfunctions;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.cbmc.info.CBMCGeneratedCodeInfo;

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
		cbmcGeneratedCode.addedElectVar(resultVarName);
		
		String voteVarName = InitVoteHelper.getVotesVarName(voteNumber);
		
		String code = template;
		code = code.replaceAll("ELECT_TYPE",
				voteStruct.getStruct().getName());
		code = code.replaceAll("GENERATED_VAR", resultVarName);
		code = code.replaceAll("VOTE_FUNC_NAME", votingFunctionName);
		code = code.replaceAll("VOTE_TYPE", voteStruct.getStruct().getName());
		code = code.replaceAll("VOTE_VAR", voteVarName);
		code = code.replaceAll("CURRENT_AMT_VOTER", InitVoteHelper.getCurrentAmtVoter(voteNumber));
		code = code.replaceAll("CURRENT_AMT_CAND", InitVoteHelper.getCurrentAmtCand(voteNumber));
		code = code.replaceAll("CURRENT_AMT_SEAT", InitVoteHelper.getCurrentAmtSeat(voteNumber));

		return code;
	}
}
