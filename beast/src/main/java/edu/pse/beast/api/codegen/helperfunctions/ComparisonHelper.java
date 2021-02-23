package edu.pse.beast.api.codegen.helperfunctions;

import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.electiondescription.CBMCVars;
import edu.pse.beast.api.electiondescription.CElectionVotingType;

public class ComparisonHelper {
	
	private final static String template1dTopLevel = 
			  "for(int i = 0; i < AMT; ++i) {\n"
			+ "    ASSUME_OR_ASSERT(LHS_VAR_NAME[i] COMP RHS_VAR_NAME[i]);\n"
			+ "}\n";
	
	public static String generateTopLevelCompCode(String comparison, String lhsVarName, 
			String rhsVarName, CElectionVotingType type, CodeGenOptions options, String assumeOrAssert) {
		String code = null;
		if(type.getListDimensions() == 1) {
			code = template1dTopLevel;
			CBMCVars listSize = type.getListSizes().get(0);
			String amtString = null;
			switch (listSize) {
			case AMT_CANDIDATES:
				amtString = options.getCbmcAmountCandidatesVarName();
				break;
			case AMT_VOTERS:
				amtString = options.getCbmcAmountVotersVarName();
				break;
			default:
				break;
			}			
			
			code = code.replaceAll("AMT", amtString);
			code = code.replaceAll("ASSUME_OR_ASSERT", assumeOrAssert);
			code = code.replaceAll("LHS_VAR_NAME", lhsVarName);
			code = code.replaceAll("RHS_VAR_NAME", rhsVarName);
			code = code.replaceAll("COMP", comparison);			
		}
		return code;
	}

	public static String generateCompCode(String genVarName, String comparison, String lhsVarName, String rhsVarName,
			CElectionVotingType cElectionVotingType, CodeGenOptions options, String assumeAssert) {
		String code = null;
		
		if(cElectionVotingType.getListDimensions() == 0) {
			code = "unsigned int GENERATED_VAR_NAME = LHS_VAR_NAME COMP RHS_VAR_NAME;";
			code = code.replaceAll("GENERATED_VAR_NAME", genVarName);
			code = code.replaceAll("LHS_VAR_NAME", lhsVarName);
			code = code.replaceAll("RHS_VAR_NAME", rhsVarName);
			code = code.replaceAll("COMP", comparison);			
		}
		
		return code;
	}
}
