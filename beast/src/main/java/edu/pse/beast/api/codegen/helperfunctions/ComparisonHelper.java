package edu.pse.beast.api.codegen.helperfunctions;

import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.codegen.ElectionTypeCStruct;

public class ComparisonHelper {

	private static String topLevelTemplate2d = 
			"ASSUME_OR_ASSERT(LHS_VAR.AMT_MEMBER == RHS_VAR.AMT_MEMBER);\n"
			+ "    for (int i = 0; i < OUTER_BOUND; ++i) {\n"
			+ "        for (int j = 0; j < INNER_BOUND; ++j) {\n"
			+ "            ASSUME_OR_ASSERT(LHS_VAR.LIST_MEMBER[i][j] COMP RHS_VAR.LIST_MEMBER[i][j]);\n"
			+ "        } \n"
			+ "    }";
	
	private static String template0d = "unsigned int GENERATED_VAR = LHS.LIST_MEMBER COMP RHS.LIST_MEMBER;";
	
	private static String uneqTemplate0d = "unsigned int GENERATED_VAR = LHS.LIST_MEMBER != RHS.LIST_MEMBER;";
	
	public static String generateTopLevelComparisonCode(String lhsVarName, String rhsVarName, ElectionTypeCStruct comparedType,
			CodeGenOptions options, String assumeAssert, String compareSymbol) {
		String code = null;
		
		if(comparedType.getVotingType().getListDimensions() == 2) {
			code = topLevelTemplate2d;
			
			code = code.replaceAll("LHS_VAR", lhsVarName);
			code = code.replaceAll("RHS_VAR", rhsVarName);
			
			code = code.replaceAll("AMT_MEMBER", comparedType.getAmtName());
			code = code.replaceAll("LIST_MEMBER", comparedType.getListName());
			
			code = code.replaceAll("OUTER_BOUND", options.getCbmcAmountVotesVarName());
			code = code.replaceAll("INNER_BOUND", options.getCbmcAmountCandidatesVarName());	
			
			code = code.replaceAll("ASSUME_OR_ASSERT", assumeAssert);	;
			code = code.replaceAll("COMP", compareSymbol);			
		}
		
		return code;
	}

	public static String generateComparisonCode(String generatedBoolName, String lhsVarName, String rhsVarName, 
			ElectionTypeCStruct comparedType,
			CodeGenOptions options, String compareSymbol) {
		
		String code = null;
		
		if(comparedType.getVotingType().getListDimensions() == 0) {
			code = template0d;
			code = code.replaceAll("GENERATED_VAR", generatedBoolName);
			code = code.replaceAll("LHS", lhsVarName);
			code = code.replaceAll("RHS", rhsVarName);
			code = code.replaceAll("LIST_MEMBER", comparedType.getListName());
			code = code.replaceAll("COMP", compareSymbol);
		}
		
		return code;
	}

	public static String generateUneqCode(String generatedBoolName, String lhsVarName, String rhsVarName, 
			ElectionTypeCStruct comparedType,
			CodeGenOptions options) {
		String code = null;
		
		if(comparedType.getVotingType().getListDimensions() == 0) {
			code = uneqTemplate0d;
			code = code.replaceAll("GENERATED_VAR", generatedBoolName);
			code = code.replaceAll("LHS", lhsVarName);
			code = code.replaceAll("RHS", rhsVarName);
			code = code.replaceAll("LIST_MEMBER", comparedType.getListName());
		}
		
		return code;
	}


	
}
