package edu.pse.beast.api.codegen.helperfunctions.code_template.templates.vote;

import java.util.Arrays;
import java.util.List;

public class CodeTemplateVoteIntersection {
	public final static String templateSingleChoice = 
			  "VOTE_TYPE GENERATED_VAR_NAME;\n"
			+ "{\n"
			+ "    unsigned int count = 0;\n"
			+ "    for (int i = 0; i < AMT_VOTERS; ++i) {\n"
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
	
	public final static List<String> loopBoundsSingleChoice = 
			Arrays.asList("AMT_VOTERS");
	
	public final static String templatePreference = 
			  "VOTE_TYPE GENERATED_VAR_NAME;\n"
			+ "{\n"
			+ "    unsigned int count = 0;\n"
			+ "    for (int i = 0; i < LHS_VAR_NAME.AMT_MEMBER; ++i) {\n"
			+ "        unsigned int eq = true;\n"
			+ "        for (int j = 0; j < AMT_CANDIDATES; ++j) {\n"
			+ "            eq = eq && COMPARE_VARS;\n"
			+ "        }\n"
			+ "        if (eq) {\n"
			+ "            for (int j = 0; j < AMT_CANDIDATES; ++j) {\n"
			+ "                GENERATED_VAR_NAME.LIST_MEMBER[count][j] = NONDET_UINT();\n"
			+ "                ASSUME(GENERATED_VAR_NAME.LIST_MEMBER[count][j] == LHS_VAR_NAME.LIST_MEMBER[i][j]);\n"
			+ "            }\n" 
			+ "        count++;\n" 
			+ "        }\n"
			+ "    }\n"
			+ "    GENERATED_VAR_NAME.AMT_MEMBER = NONDET_UINT();\n"
			+ "    ASSUME(GENERATED_VAR_NAME.AMT_MEMBER == count);"
			+ "}\n";

	public final static List<String> loopBoundsPreference = Arrays.asList(
			"AMT_VOTERS", "AMT_CANDIDATES", "AMT_CANDIDATES");
	
	public final static String templateApproval = templatePreference; 
	public final static List<String> loopBoundsApproval = loopBoundsPreference;
}
