package edu.pse.beast.api.codegen.helperfunctions;

import java.util.List;
import java.util.Map;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.helperfunctions.code_template.templates.CodeTemplateVotingFunctionVoteArrayInit;
import edu.pse.beast.api.codegen.helperfunctions.code_template.templates.vote.CodeTemplateVoteComparison;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundHandler;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundType;
import edu.pse.beast.api.electiondescription.VotingInputTypes;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.ComparisonNode;

public class VoteComparisonHelper {
	
	private static List<LoopBoundType> getLoopBounds(
			VotingInputTypes votingInputType) {
		switch(votingInputType) {
			case APPROVAL : {		
				return CodeTemplateVoteComparison.loopBoundsApproval;
			}
			case WEIGHTED_APPROVAL : {
				break;
			}
			case PREFERENCE : {
				return CodeTemplateVoteComparison.loopBoundsPreference;
			}
			case SINGLE_CHOICE : {
				return CodeTemplateVoteComparison.loopBoundsSingleChoice;
			}
			case SINGLE_CHOICE_STACK : {
				break;
			}
		}
		return List.of();
	}
	
	public static String generateCode(
			String generatedVarName,
			String lhsVarName,
			String rhsVarName, 
			ElectionTypeCStruct comparedType,
			VotingInputTypes votingInputType,
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
			switch(votingInputType) {
				case APPROVAL : {			
					code = CodeTemplateVoteComparison.templateApprovalUneq;
					break;
				}
				case WEIGHTED_APPROVAL : {
					break;
				}
				case PREFERENCE : {
					code = CodeTemplateVoteComparison.templatePreferenceUneq;
					break;
				}
				case SINGLE_CHOICE : {
					code = CodeTemplateVoteComparison.templateSingleChoiceUnEq;
					break;
				}
				case SINGLE_CHOICE_STACK : {
					break;
				}
			}
		} else {
			switch(votingInputType) {
				case APPROVAL : {				
					code = CodeTemplateVoteComparison.templateApproval;
					break;
				}
				case WEIGHTED_APPROVAL : {
					break;
				}
				case PREFERENCE : {				
					code = CodeTemplateVoteComparison.templatePreference;
					break;
				}
				case SINGLE_CHOICE : {
					code = CodeTemplateVoteComparison.templateSingleChoiceEq;
					break;
				}
				case SINGLE_CHOICE_STACK : {
					break;
				}
			}
		}
		
		List<LoopBoundType> loopbounds = getLoopBounds(votingInputType);
		loopBoundHandler.pushMainLoopBounds(loopbounds);
		
		code = CodeGenerationToolbox.replacePlaceholders(code, replacementMap);		
		return code;
	}	

}
