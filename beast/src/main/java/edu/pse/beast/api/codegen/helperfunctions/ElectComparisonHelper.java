package edu.pse.beast.api.codegen.helperfunctions;

import java.util.List;
import java.util.Map;

import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.codegen.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.helperfunctions.templates.CodeTemplateElectComparison;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundHandler;
import edu.pse.beast.api.electiondescription.VotingInputTypes;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;

public class ElectComparisonHelper {
	
	private static List<String> getLoopBund(
			VotingOutputTypes votingOutputType) {
		switch(votingOutputType) {
			case CANDIDATE_LIST : {
				return CodeTemplateElectComparison.loopBoundsCandidateList;
			}
			case PARLIAMENT : {
				return CodeTemplateElectComparison.loopBoundsParliament;
			}
			case PARLIAMENT_STACK : {
				break;
			}
			case SINGLE_CANDIDATE : {
				break;
			}
		}
		return null;
	}
	
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
					code = CodeTemplateElectComparison.templateParliamentUneq;
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
					code = CodeTemplateElectComparison.templateParliament;
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
		
		List<String> loopbounds = CodeGenerationToolbox.replaceLoopBounds(
				getLoopBund(votingOutputType),
				replacementMap);
		loopBoundHandler.addMainLoopBounds(loopbounds);
		
		code = CodeGenerationToolbox.replacePlaceholders(code, replacementMap);		
		return code;
	}
}
