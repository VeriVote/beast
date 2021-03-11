package edu.pse.beast.api.codegen.helperfunctions.templates.typeGenerator.vote;

import java.util.Arrays;
import java.util.List;

public class CodeTemplateVotePermutation {
	public final static String templatePreference = 
			  "    VOTE_TYPE GENERATED_VAR_NAME;\n"
			+ "    GENERATED_VAR_NAME.AMT_MEMBER = NONDET_UINT();\n"
			+ "    ASSUME(GENERATED_VAR_NAME.AMT_MEMBER == RHS.AMT_MEMBER);\n"
			+ "    unsigned int PERM[AMT_VOTERS];\n"
			+ "    for (int i = 0; i < RHS.AMT_MEMBER && i < AMT_VOTERS; ++i) {\n"
			+ "        PERM[i] = NONDET_UINT();\n"
			+ "        ASSUME(PERM[i] >= 0);\n"
			+ "        ASSUME(PERM[i] < RHS.AMT_MEMBER);\n"
			+ "    }\n"
			+ "    for (int i = 0; i < RHS.AMT_MEMBER - 1 && i < AMT_VOTERS; ++i) {\n"
			+ "        for (int j = i + 1; j < RHS.AMT_MEMBER && j < AMT_VOTERS; ++j) {\n"
			+ "            ASSUME(PERM[i] != PERM[j]);\n"
			+ "        }\n"
			+ "    }\n"
			+ "    for (int i = 0; i < RHS.AMT_MEMBER - 1 && i < AMT_VOTERS; ++i) {\n"
			+ "        for (int j = 0; j < AMT_CANDIDATES; ++j) {\n"
			+ "            ASSUME(GENERATED_VAR_NAME.LIST_MEMBER[i][j] == RHS.LIST_MEMBER[PERM[i]][j]);\n"
			+ "        }\n"
			+ "    }";
	
	public final static List<String> loopBoundsPreference = Arrays.asList(
			"AMT_VOTERS", "AMT_VOTERS", "AMT_VOTERS", "AMT_VOTERS", "AMT_CANDIDATES");
	
	public final static String templateApproval = templatePreference;
	public final static List<String> loopBoundsApproval = loopBoundsPreference;
}
