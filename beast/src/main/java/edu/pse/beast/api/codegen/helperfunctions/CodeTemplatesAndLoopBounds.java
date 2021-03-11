package edu.pse.beast.api.codegen.helperfunctions;

import java.util.Arrays;
import java.util.List;

import edu.pse.beast.api.electiondescription.VotingInputTypes;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;

public abstract class CodeTemplatesAndLoopBounds {	
	
	
	
	public static class Comparison {
		public final static String template1dTopLevel = 
				  "	for(int i = 0; i < AMT; ++i) {\n"
				+ "		ASSUME_OR_ASSERT(LHS_VAR_NAME[i] COMP RHS_VAR_NAME[i]);\n" 
				+ "	}\n";

		public final static List<String> template1dloopBounds = Arrays.asList("AMT");
	}

	public static class VoteInit {
		public final static String template1d = 
				  "    VOTE_TYPE VAR_NAME;\n"
				+ "    VAR_NAME.AMT_MEMBER = NONDET_UINT();\n"
				+ "    ASSUME(VAR_NAME.amtVotes <= AMT_VOTES);\n"
				+ "    for (int i = 0; i < AMT_VOTES; ++i) {\n"
				+ "            VAR_NAME.LIST_MEMBER[i] = NONDET_UINT();\n"
				+ "            ASSUME(VAR_NAME.votes[i] >= LOWER_VOTE_BOUND);\n"
				+ "            ASSUME(VAR_NAME.votes[i] <= UPPER_VOTE_BOUND);\n"
				+ "    }\n";
		
		public final static List<String> template1dloopBounds = 
				Arrays.asList("AMT_VOTES");
		
		public final static String template2d = 
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

		public final static List<String> template2dloopBounds = Arrays.asList(
				"OUTER_BOUND", "INNER_BOUND");

		public final static String uniquenessTemplate = 
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

		public final static List<String> uniquenessTemplateloopBounds = Arrays.asList(
				"AMT_VOTES", "AMT_CANDIDATES", "AMT_CANDIDATES",
				"AMT_CANDIDATES");
	}

	public static class Intersection {
		
		public final static String template1d = 
				  "VOTE_TYPE GENERATED_VAR_NAME;\n"
				+ "{\n"
				+ "    unsigned int count = 0;\n"
				+ "    for (int i = 0; i < OUTER_BOUND; ++i) {\n"
				+ "        unsigned int eq = COMPARE_VARS;\n"
				+ "        if (eq) {\n"
				+ "            GENERATED_VAR_NAME.LIST_MEMBER[count] = NONDET_UINT();\n"
				+ "            ASSUME(GENERATED_VAR_NAME.LIST_MEMBER[count] == LHS_VAR_NAME.LIST_MEMBER[i]);\n"
				+ "            count++;\n" 
				+ "        }\n"
				+ "    }\n"
				+ "    GENERATED_VAR_NAME.AMT_MEMBER = NONDET_UINT();\n"
				+ "    ASSUME(GENERATED_VAR_NAME.AMT_MEMBER == count);\n"
				+ "}\n";
		
		
		public final static String template2d = 
				  "VOTE_TYPE GENERATED_VAR_NAME;\n"
				+ "{\n"
				+ "    unsigned int count = 0;\n"
				+ "    for (int i = 0; i < OUTER_BOUND; ++i) {\n"
				+ "        unsigned int eq = true;\n"
				+ "        for (int j = 0; j < INNER_BOUND; ++j) {\n"
				+ "            eq = eq && COMPARE_VARS;\n"
				+ "        }\n"
				+ "        if (eq) {\n"
				+ "            for (int j = 0; j < INNER_BOUND; ++j) {\n"
				+ "                GENERATED_VAR_NAME.LIST_MEMBER[count][j] = NONDET_UINT();\n"
				+ "                ASSUME(GENERATED_VAR_NAME.LIST_MEMBER[count][j] == LHS_VAR_NAME.LIST_MEMBER[i][j]);\n"
				+ "            }\n" 
				+ "        count++;\n" 
				+ "        }\n"
				+ "    }\n"
				+ "    GENERATED_VAR_NAME.AMT_MEMBER = NONDET_UINT();\n"
				+ "    ASSUME(GENERATED_VAR_NAME.AMT_MEMBER == count);"
				+ "}\n";

		public final static List<String> template2dloopBounds = Arrays.asList(
				"OUTER_BOUND", "INNER_BOUND", "INNER_BOUND");
	}
	
	public static class Permutation {
		public final static String template2d = 
				  "    VOTE_TYPE GENERATED_VAR_NAME;\n"
				+ "    GENERATED_VAR_NAME.AMT_MEMBER = NONDET_UINT();\n"
				+ "    ASSUME(GENERATED_VAR_NAME.AMT_MEMBER == RHS.AMT_MEMBER);\n"
				+ "    unsigned int PERM[AMT_VOTES];\n"
				+ "    for (int i = 0; i < RHS.AMT_MEMBER && i < AMT_VOTES; ++i) {\n"
				+ "        PERM[i] = NONDET_UINT();\n"
				+ "        ASSUME(PERM[i] >= 0);\n"
				+ "        ASSUME(PERM[i] < RHS.AMT_MEMBER);\n"
				+ "    }\n"
				+ "    for (int i = 0; i < RHS.AMT_MEMBER - 2 && i < AMT_VOTES; ++i) {\n"
				+ "        for (int j = i + 1; j < RHS.AMT_MEMBER - 1 && j < AMT_VOTES; ++j) {\n"
				+ "            ASSUME(PERM[i] != PERM[j]);\n"
				+ "        }\n"
				+ "    }\n"
				+ "    for (int i = 0; i < RHS.AMT_MEMBER - 1 && i < AMT_VOTES; ++i) {\n"
				+ "        for (int j = 0; j < INNER_BOUND; ++j) {\n"
				+ "            ASSUME(GENERATED_VAR_NAME.LIST_MEMBER[i][j] == RHS.LIST_MEMBER[PERM[i]][j]);\n"
				+ "        }\n"
				+ "    }";
		public final static List<String> template2dloopBounds = Arrays.asList(
				"AMT_VOTES", "AMT_VOTES", "AMT_VOTES", "AMT_VOTES", "INNER_BOUND");
	}
	
}
