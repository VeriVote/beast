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
			+ "            ASSUME(VAR_NAME.votes[i][j] >= LOWER_VOTE_BOUND);\n"
			+ "            ASSUME(VAR_NAME.votes[i][j] <= UPPER_VOTE_BOUND);\n"
			+ "        }\n"
			+ "    }\n";
	
	private final static String uniquenessTemplate = 
			  "    for (int i = 0; i < AMT_VOTES; ++i) {\n"
			+ "        unsigned int tmp[AMT_CANDIDATES];\n"
			+ "        for (int k = 0; k < AMT_CANDIDATES; ++k) {\n"
			+ "            tmp[k] = 0;\n"
			+ "        }\n"
			+ "        for (int j = 0; j < AMT_CANDIDATES; ++j) {\n"
			+ "            for (int k = 0; k < AMT_CANDIDATES; ++k) {\n"
			+ "                if (vote1.votes[i][j] == k) {\n"
			+ "                    assume(tmp[k] == 0);\n"
			+ "                    tmp[k] = 1;\n"
			+ "                }\n"
			+ "            }\n"
			+ "        }\n"
			+ "    }\n";
	
	public static String generateCode(String varName, ElectionTypeCStruct voteArrStruct, CodeGenOptions options) {
		String code = null;
		
		if(voteArrStruct.getVotingType().getListDimensions() == 2) {
			code = template2d;
			
			if(voteArrStruct.getVotingType().isUniqueVotes()) {
				code += uniquenessTemplate;
				code = code.replaceAll("AMT_CANDIDATES", options.getCbmcAmountCandidatesVarName());
			}
			
			code = code.replaceAll("VOTE_TYPE", voteArrStruct.getStruct().getName());
			code = code.replaceAll("AMT_MEMBER", voteArrStruct.getAmtName());
			code = code.replaceAll("LIST_MEMBER", voteArrStruct.getListName());
			
			code = code.replaceAll("AMT_VOTES", options.getCbmcAmountVotersVarName());
			
			code = code.replaceAll("VAR_NAME", varName);
			
			code = code.replaceAll("OUTER_BOUND", options.getCbmcAmountVotersVarName());
			code = code.replaceAll("INNER_BOUND", options.getCbmcAmountCandidatesVarName());
			
			code = code.replaceAll("ASSUME", options.getCbmcAssumeName());
			code = code.replaceAll("NONDET_UINT", options.getCbmcNondetUintName());
			code = code.replaceAll("LOWER_VOTE_BOUND", options.getVotesLowerBoundVarName());
			code = code.replaceAll("UPPER_VOTE_BOUND", options.getVotesUpperBoundVarName());			
		}
		
		return code;
	}
}
