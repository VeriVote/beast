package edu.pse.beast.api.codegen.helperfunctions;

import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.codegen.ElectionTypeCStruct;

public class PermutationHelper {
	
	private static String template2d = 
			"    VOTE_TYPE GENERATED_VAR_NAME;\n"
			+ "\n"
			+ "    GENERATED_VAR_NAME.AMT_MEMBER = NONDET_UINT();\n"
			+ "    ASSUME(GENERATED_VAR_NAME.AMT_MEMBER == RHS.AMT_MEMBER);\n"
			+ "\n"
			+ "    unsigned int PERM[AMT_VOTES];\n"
			+ "\n"
			+ "    for (int i = 0; i < RHS.AMT_MEMBER && i < AMT_VOTES; ++i) {\n"
			+ "        PERM[i] = NONDET_UINT();\n"
			+ "        ASSUME(PERM[i] >= 0);\n"
			+ "        ASSUME(PERM[i] < RHS.AMT_MEMBER);\n"
			+ "    }\n"
			+ "    for (int i = 0; i < RHS.AMT_MEMBER - 1 && i < AMT_VOTES; ++i) {\n"
			+ "        ASSUME(PERM[i] != PERM[i + 1]);\n"
			+ "    }\n"
			+ "    for (int i = 0; i < RHS.AMT_MEMBER - 1 && i < AMT_VOTES; ++i) {\n"
			+ "        for (int j = 0; j < INNER_BOUND; ++j) {\n"
			+ "            ASSUME(GENERATED_VAR_NAME.LIST_MEMBER[i][j] == RHS.LIST_MEMBER[PERM[i]][j]);\n"
			+ "        }\n"
			+ "    }";

	public static String generateVotePermutation(String generatedVarName, String varName,
			ElectionTypeCStruct voteArrStruct, CodeGenOptions options) {
		String code = null;
		
		if(voteArrStruct.getVotingType().getListDimensions() == 2) {
			code = template2d;
			
			code = code.replaceAll("VOTE_TYPE", voteArrStruct.getStruct().getName());
			code = code.replaceAll("AMT_MEMBER", voteArrStruct.getAmtName());
			code = code.replaceAll("LIST_MEMBER", voteArrStruct.getListName());
			code = code.replaceAll("GENERATED_VAR_NAME", generatedVarName);		
			code = code.replaceAll("INNER_BOUND", options.getCbmcAmountCandidatesVarName());
			code = code.replaceAll("ASSUME", options.getCbmcAssumeName());
			code = code.replaceAll("NONDET_UINT", options.getCbmcNondetUintName());
			code = code.replaceAll("RHS", varName);
			code = code.replaceAll("PERM", "permutationIndices");			
			code = code.replaceAll("AMT_VOTES", options.getCbmcAmountVotesVarName());
		}
		
		return code;
	}
	
}
