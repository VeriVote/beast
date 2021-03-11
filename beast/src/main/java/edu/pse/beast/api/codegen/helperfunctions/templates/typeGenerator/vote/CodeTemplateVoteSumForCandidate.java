package edu.pse.beast.api.codegen.helperfunctions.templates.typeGenerator.vote;

public class CodeTemplateVoteSumForCandidate {
	public static String templateSingleChoice = 
			  "    unsigned int GENERATED_VAR = 0;\n"
			+ "    for (int i = 0; i < VOTE_VAR.AMT_MEMBER; ++i) {\n"
			+ "        if (VOTE_VAR.LIST_MEMBER[i] == CANDIDATE_VAR) {\n"
			+ "            GENERATED_VAR++;\n"
			+ "        }\n"
			+ "    }";
	
	public static String templateSingleChoiceStack =
			"unsigned int GENERATED_VAR = VOTE_VAR.LIST_MEMBER[CANDIDATE_VAR];\n";
}
