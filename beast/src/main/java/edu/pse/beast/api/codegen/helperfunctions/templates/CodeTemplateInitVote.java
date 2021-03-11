package edu.pse.beast.api.codegen.helperfunctions.templates;

import java.util.Arrays;
import java.util.List;

public class CodeTemplateInitVote {
	public final static String templateSingleChoice = 
			  "    VOTE_TYPE VAR_NAME;\n"
			+ "    VAR_NAME.AMT_MEMBER = NONDET_UINT();\n"
			+ "    ASSUME(VAR_NAME.amtVotes <= AMT_VOTES);\n"
			+ "    for (int i = 0; i < AMT_VOTES; ++i) {\n"
			+ "            VAR_NAME.LIST_MEMBER[i] = NONDET_UINT();\n"
			+ "            ASSUME(VAR_NAME.votes[i] >= LOWER_VOTE_BOUND);\n"
			+ "            ASSUME(VAR_NAME.votes[i] <= UPPER_VOTE_BOUND);\n"
			+ "    }\n";
	
	public final static List<String> loopBoundsSingleCandidate = 
			Arrays.asList("AMT_VOTES");
	
	public final static String templatePreference = 
			  "    VOTE_TYPE VAR_NAME;\n"
			+ "    VAR_NAME.AMT_MEMBER = NONDET_UINT();\n"
			+ "    ASSUME(VAR_NAME.amtVotes <= AMT_VOTES);\n"
			+ "    for (int i = 0; i < OUTER_BOUND; ++i) {\n"
			+ "        for (int j = 0; j < INNER_BOUND; ++j) {\n"
			+ "            VAR_NAME.LIST_MEMBER[i][j] = NONDET_UINT();\n"
			+ "            ASSUME(VAR_NAME.LIST_MEMBER[i][j] >= LOWER_VOTE_BOUND);\n"
			+ "            ASSUME(VAR_NAME.LIST_MEMBER[i][j] <= UPPER_VOTE_BOUND);\n"
			+ "        }\n" 
			+ "    }\n"
			+ "    for (int i = 0; i < AMT_VOTES; ++i) {\n"
			+ "        unsigned int tmp[AMT_CANDIDATES];\n"
			+ "        for (int k = 0; k < AMT_CANDIDATES; ++k) {\n"
			+ "            tmp[k] = 0;\n" 
			+ "        }\n"
			+ "        for (int j = 0; j < AMT_CANDIDATES; ++j) {\n"
			+ "            for (int k = 0; k < AMT_CANDIDATES; ++k) {\n"
			+ "                if (VAR_NAME.LIST_MEMBER[i][j] == k) {\n"
			+ "                    ASSUME(tmp[k] == 0);\n"
			+ "                    tmp[k] = 1;\n" 
			+ "                }\n"
			+ "            }\n"
			+ "        }\n" 
			+ "    }\n";
	

	public final static List<String> loopBoundsPreference = Arrays.asList(
			"OUTER_BOUND", "INNER_BOUND", 
			"AMT_VOTES", "AMT_CANDIDATES", 
			"AMT_CANDIDATES", "AMT_CANDIDATES");
}
