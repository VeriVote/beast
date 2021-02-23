package edu.pse.beast.api.codegen.helperfunctions;

import java.util.List;

import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.codegen.ElectionTypeCStruct;

public class IntersectionHelper {

	private static String template2d = 
			"    VOTE_TYPE GENERATED_VAR_NAME;\n"
			+ "\n"
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
			+ "\n"
			+ "    GENERATED_VAR_NAME.AMT_MEMBER = NONDET_UINT();\n"
			+ "    ASSUME(GENERATED_VAR_NAME.AMT_MEMBER == count);"
			+ "}\n";
	

	public static String generateVoteIntersection(String generatedVarName, List<String> varNames,
			ElectionTypeCStruct voteArrStruct, CodeGenOptions options) {
		String code = null;

		if (voteArrStruct.getVotingType().getListDimensions() == 2) {
			code = template2d;
			
			String comparison = "";
			int i = 0;
			for (; i < varNames.size() - 2; ++i) {
				comparison += varNames.get(i) + ".LIST_MEMBER[i][j] == " + varNames.get(i + 1) + ".LIST_MEMBER[i][j] && ";
			}
			comparison += varNames.get(i) + ".LIST_MEMBER[i][j] == " + varNames.get(i + 1) + ".LIST_MEMBER[i][j]";
			
			code = code.replaceAll("COMPARE_VARS", comparison);
		
			code = code.replaceAll("VOTE_TYPE", voteArrStruct.getStruct().getName());
			code = code.replaceAll("AMT_MEMBER", voteArrStruct.getAmtName());
			code = code.replaceAll("LIST_MEMBER", voteArrStruct.getListName());
			
			
			code = code.replaceAll("GENERATED_VAR_NAME", generatedVarName);		
			
			code = code.replaceAll("LHS_VAR_NAME", varNames.get(0));			

			code = code.replaceAll("OUTER_BOUND", options.getCbmcAmountVotersVarName());
			code = code.replaceAll("INNER_BOUND", options.getCbmcAmountCandidatesVarName());
			
			code = code.replaceAll("ASSUME", options.getCbmcAssumeName());
			code = code.replaceAll("NONDET_UINT", options.getCbmcNondetUintName());
		}

		return code;
	}


}
