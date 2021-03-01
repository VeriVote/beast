package edu.pse.beast.api.codegen.helperfunctions;

import java.util.List;

import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.codegen.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundHandler;

public class VoteComparisonHelper {

	
	public static String generateTopLevelVoteComparisonCode(String lhsVarName, String rhsVarName, ElectionTypeCStruct comparedType,
			CodeGenOptions options, String assumeAssert, String compareSymbol, LoopBoundHandler loopBoundHandler) {
		String code = null;
		
		if(comparedType.getVotingType().getListDimensions() == 2) {
			code = CodeTemplates.VoteComparison.topLevelTemplate2d;
			
			List<String> loopbounds = CodeTemplates.VoteComparison.topLevelTemplate2dLoopBounds;
			CodeTemplates.replaceAll(loopbounds, "OUTER_BOUND", options.getCbmcAmountVotersVarName());
			CodeTemplates.replaceAll(loopbounds, "INNER_BOUND", options.getCbmcAmountCandidatesVarName());
			loopBoundHandler.addMainLoopBounds(loopbounds);
			
			code = code.replaceAll("LHS_VAR", lhsVarName);
			code = code.replaceAll("RHS_VAR", rhsVarName);
			
			code = code.replaceAll("AMT_MEMBER", comparedType.getAmtName());
			code = code.replaceAll("LIST_MEMBER", comparedType.getListName());
			
			code = code.replaceAll("OUTER_BOUND", options.getCbmcAmountVotersVarName());
			code = code.replaceAll("INNER_BOUND", options.getCbmcAmountCandidatesVarName());	
			
			code = code.replaceAll("ASSUME_OR_ASSERT", assumeAssert);	;
			code = code.replaceAll("COMP", compareSymbol);			
		}
		
		return code;
	}

	public static String generateVoteComparisonCode(String generatedBoolName, String lhsVarName, String rhsVarName, 
			ElectionTypeCStruct comparedType,
			CodeGenOptions options, String compareSymbol) {
		
		String code = null;
		
		if(comparedType.getVotingType().getListDimensions() == 0) {
			code = CodeTemplates.VoteComparison.template0d;
			code = code.replaceAll("GENERATED_VAR", generatedBoolName);
			code = code.replaceAll("LHS", lhsVarName);
			code = code.replaceAll("RHS", rhsVarName);
			code = code.replaceAll("LIST_MEMBER", comparedType.getListName());
			code = code.replaceAll("COMP", compareSymbol);
		}
		
		return code;
	}

	public static String generateVoteUneqCode(String generatedBoolName, String lhsVarName, String rhsVarName, 
			ElectionTypeCStruct comparedType,
			CodeGenOptions options) {
		String code = null;
		
		if(comparedType.getVotingType().getListDimensions() == 0) {
			code = CodeTemplates.VoteComparison.uneqTemplate0d;
			code = code.replaceAll("GENERATED_VAR", generatedBoolName);
			code = code.replaceAll("LHS", lhsVarName);
			code = code.replaceAll("RHS", rhsVarName);
			code = code.replaceAll("LIST_MEMBER", comparedType.getListName());
		}
		
		return code;
	}


	
}
