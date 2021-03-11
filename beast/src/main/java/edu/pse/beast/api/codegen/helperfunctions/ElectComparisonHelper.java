package edu.pse.beast.api.codegen.helperfunctions;

import java.util.Map;

import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.codegen.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.helperfunctions.templates.CodeTemplateElectComparison;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundHandler;
import edu.pse.beast.api.electiondescription.VotingInputTypes;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;

public class ElectComparisonHelper {
	public static String generateCode(
			String generatedVarName,
			String lhsVarName,
			String rhsVarName, 
			ElectionTypeCStruct comparedType,
			VotingOutputTypes votingOutputType,
			CodeGenOptions options, 
			String compareSymbol,
			LoopBoundHandler loopBoundHandler) {
		
		Map<String, String> replacementMap = Map.of(
				"GENERATED_VAR", generatedVarName,
				"LHS_VAR", lhsVarName,
				"RHS_VAR", rhsVarName,
				"AMT_MEMBER", comparedType.getAmtName(),
				"AMT_CANDIDATES", options.getCbmcAmountCandidatesVarName(),
				"COMP", compareSymbol,
				"LIST_MEMBER", comparedType.getListName()
			);
		
		String code = null;
		
		if(compareSymbol.equals("!=")) {
			switch(votingOutputType) {
				case CANDIDATE_LIST : {
					code = CodeTemplateElectComparison.templateCandidateListUneq;
					break;
				}
				case PARLIAMENT : {
					break;
				}
				case PARLIAMENT_STACK : {
					break;
				}
				case SINGLE_CANDIDATE : {
					code = CodeTemplateElectComparison.templateSingleCandidate;
					break;
				}
			}
		} else {
			switch(votingOutputType) {
				case CANDIDATE_LIST : {
					code = CodeTemplateElectComparison.templateCandidateList;
					break;
				}
				case PARLIAMENT : {
					break;
				}
				case PARLIAMENT_STACK : {
					break;
				}
				case SINGLE_CANDIDATE : {
					code = CodeTemplateElectComparison.templateSingleCandidate;
					break;
				}
			}
		}
		
		code = CodeGenerationToolbox.replacePlaceholders(code, replacementMap);		
		return code;
	}
}
