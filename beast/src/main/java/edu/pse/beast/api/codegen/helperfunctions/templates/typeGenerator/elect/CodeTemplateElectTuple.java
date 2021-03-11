package edu.pse.beast.api.codegen.helperfunctions.templates.typeGenerator.elect;

public class CodeTemplateElectTuple {
	public final static String templateVarSetup = 
			  "        ELECT_TYPE VAR_NAME;\n"
			+ "        VAR_NAME.AMT_MEMBER = NONDET_UINT();\n"
			+ "        ASSUME(VAR_NAME.AMT_MEMBER == VOTE_ELECT_SUM);\n"
			+ "        unsigned int pos = 0;\n";
	
	
	public static String templateCandidateList = 
			  "        for (unsigned int i = 0; i < CURRENT_ELECT.AMT_MEMBER && i < AMT_CANDIDATES; ++pos) {\n"
		    + "            VAR_NAME.LIST_MEMBER[pos] = NONDET_UINT();\n"
		    + "            ASSUME(VAR_NAME.LIST_MEMBER[pos] == CURRENT_VOTE.LIST_MEMBER[i]);\n"
			+ "            pos++;\n"
			+ "        }\n";
}
