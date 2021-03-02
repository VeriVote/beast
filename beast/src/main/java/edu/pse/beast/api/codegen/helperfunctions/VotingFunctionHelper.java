package edu.pse.beast.api.codegen.helperfunctions;

import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.codegen.ElectionTypeCStruct;

public class VotingFunctionHelper {

	public static String generateVoteCopy(String voteArrayName,
			String votingStructVarName, ElectionTypeCStruct voteType,
			CodeGenOptions options) {
		String code = null;
		if (voteType.getVotingType().getListDimensions() == 1) {
			code = CodeTemplates.VotingFunction.template1d;
			code = code.replaceAll("VOTE_INPUT", votingStructVarName);
			code = code.replaceAll("VOTES", voteArrayName);
			code = code.replaceAll("LIST_MEMBER", voteType.getListName());
			code = code.replaceAll("AMT_VOTERS",
					options.getCbmcAmountVotersVarName());
			
			
		}else if (voteType.getVotingType().getListDimensions() == 2) {
			code = CodeTemplates.VotingFunction.template2d;

			code = code.replaceAll("VOTE_INPUT", votingStructVarName);
			code = code.replaceAll("VOTES", voteArrayName);
			code = code.replaceAll("LIST_MEMBER", voteType.getListName());
			code = code.replaceAll("OUTER_BOUND",
					options.getCbmcAmountVotersVarName());
			code = code.replaceAll("INNER_BOUND",
					options.getCbmcAmountCandidatesVarName());
		}

		return code;
	}

	public static String generateResultCopy(String resultName,
			String outputVarName, ElectionTypeCStruct output,
			CodeGenOptions options) {

		String code = null;

		if (output.getVotingType().getListDimensions() == 0) {
			code = CodeTemplates.VotingFunction.template0d;
			code = code.replaceAll("GENERATED_VAR", outputVarName);
			code = code.replaceAll("LIST_MEMBER", output.getListName());
			code = code.replaceAll("NAKED_ARR", resultName);
		}

		return code;
	}

}
