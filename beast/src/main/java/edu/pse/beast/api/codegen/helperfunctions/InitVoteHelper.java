package edu.pse.beast.api.codegen.helperfunctions;

import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.codegen.ElectionTypeCStruct;

public abstract class InitVoteHelper {

	private final static String template2d = 
			  "    VOTE_TYPE VAR_NAME;\n"
			+ "    VAR_NAME.AMT_MEMBER = NONDET_UINT();\n"
			+ "    ASSUME(VAR_NAME.amtVotes <= AMT_VOTES);\n"
			+ "    for (int i = 0; i < OUTER_BOUND; ++i) {\n"
			+ "        for (int j = 0; j < INNER_BOUND; ++j) {\n"
			+ "            VAR_NAME.LIST_MEMBER[i][j] = NONDET_UINT();\n"
			+ "            ASSUME(vote1.votes[i][j] >= LOWER_VOTE_BOUND);\n"
			+ "            ASSUME(vote1.votes[i][j] <= UPPER_VOTE_BOUND);\n"
			+ "        }\n"
			+ "    }";
	
	public static String generateCode(String varName, ElectionTypeCStruct voteArrStruct, CodeGenOptions options) {
		String code = null;
		
		if(voteArrStruct.getVotingType().getListDimensions() == 2) {
			code = template2d;
			code = code.replaceAll("VOTE_TYPE", voteArrStruct.getStruct().getName());
			code = code.replaceAll("AMT_MEMBER", voteArrStruct.getAmtName());
			code = code.replaceAll("LIST_MEMBER", voteArrStruct.getListName());
			
			code = code.replaceAll("AMT_VOTES", options.getCbmcAmountVotesVarName());
			
			code = code.replaceAll("VAR_NAME", varName);
			
			code = code.replaceAll("OUTER_BOUND", options.getCbmcAmountVotesVarName());
			code = code.replaceAll("INNER_BOUND", options.getCbmcAmountCandidatesVarName());
			
			code = code.replaceAll("ASSUME", options.getCbmcAssumeName());
			code = code.replaceAll("NONDET_UINT", options.getCbmcNondetUintName());
			code = code.replaceAll("LOWER_VOTE_BOUND", options.getVotesLowerBoundVarName());
			code = code.replaceAll("UPPER_VOTE_BOUND", options.getVotesUpperBoundVarName());			
		}
		
		return code;
	}
}
