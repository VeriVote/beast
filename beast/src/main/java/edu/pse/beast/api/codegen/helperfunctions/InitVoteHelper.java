package edu.pse.beast.api.codegen.helperfunctions;

import java.util.List;
import java.util.Map;

import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.codegen.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.helperfunctions.code_template.templates.CodeTemplateInitVote;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundHandler;
import edu.pse.beast.api.electiondescription.VotingInputTypes;

public abstract class InitVoteHelper {

	public static String generateCode(
			String varName,
			ElectionTypeCStruct voteArrStruct,
			VotingInputTypes votingInputType,
			CodeGenOptions options,
			LoopBoundHandler loopBoundHandler) {
		
		Map<String, String> replacementMap = Map.of(
					"AMT_CANDIDATES",
					options.getCbmcAmountCandidatesVarName(),
					"AMT_VOTERS",
					options.getCbmcAmountVotersVarName(),
					"VOTE_TYPE",
					voteArrStruct.getStruct().getName(),
					"AMT_MEMBER", voteArrStruct.getAmtName(),
					"LIST_MEMBER", voteArrStruct.getListName(),
					"VAR_NAME", varName,
					"ASSUME", options.getCbmcAssumeName(),
					"NONDET_UINT",
					options.getCbmcNondetUintName(),
					"LOWER_VOTE_BOUND",
					options.getVotesLowerBoundVarName(),
					"UPPER_VOTE_BOUND",
					options.getVotesUpperBoundVarName()
				);
		
		
		String code = null;
		List<String> loopbounds = List.of();
		
		switch(votingInputType) {
			case APPROVAL : {		
				code = CodeTemplateInitVote.templateApproval;
				loopbounds = CodeTemplateInitVote.loopBoundsApproval;
				break;
			}
			case WEIGHTED_APPROVAL : {
				break;
			}
			case PREFERENCE : {			
				code = CodeTemplateInitVote.templatePreference;
				loopbounds = CodeTemplateInitVote.loopBoundsPreference;
				break;
			}
			case SINGLE_CHOICE : {				
				code = CodeTemplateInitVote.templateSingleChoice;
				loopbounds = CodeTemplateInitVote.loopBoundsSingleChoice;
				break;
			}
			case SINGLE_CHOICE_STACK : {
				break;
			}
		}		
		
		loopBoundHandler.addMainLoopBounds(loopbounds);
		code = CodeGenerationToolbox.replacePlaceholders(code, replacementMap);
		return code;
	}
}
