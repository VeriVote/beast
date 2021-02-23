package edu.pse.beast.api.codegen.helperfunctions;

import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.codegen.ElectionTypeCStruct;

public class VotingFunctionHelper {

	private static String template2d = "for (int i = 0; i < OUTER_BOUND; ++i) {\n"
			+ "        for (int j = 0; j < INNER_BOUND; ++j) {\n"
			+ "            VOTES[i][j] = VOTE_INPUT.LIST_MEMBER[i][j];\n" + "        }\n" + "    }";
	
	private static String template0d = "GENERATED_VAR.LIST_MEMBER = NAKED_ARR;";

	public static String generateVoteCopy(String voteArrayName, String votingStructVarName,
			ElectionTypeCStruct voteType, CodeGenOptions options) {
		String code = null;

		if (voteType.getVotingType().getListDimensions() == 2) {
			code = template2d;

			code = code.replaceAll("VOTE_INPUT", votingStructVarName);
			code = code.replaceAll("VOTES", voteArrayName);
			code = code.replaceAll("LIST_MEMBER", voteType.getListName());
			code = code.replaceAll("OUTER_BOUND", options.getCbmcAmountVotersVarName());
			code = code.replaceAll("INNER_BOUND", options.getCbmcAmountCandidatesVarName());

		}

		return code;
	}

	public static String generateResultCopy(String resultName, String outputVarName, ElectionTypeCStruct output,
			CodeGenOptions options) {
		
		String code = null;
		
		if(output.getVotingType().getListDimensions() == 0) {
			code = template0d;
			code = code.replaceAll("GENERATED_VAR", outputVarName);
			code = code.replaceAll("LIST_MEMBER", output.getListName());
			code = code.replaceAll("NAKED_ARR", resultName);
		}
		
		return code;
	}

}
