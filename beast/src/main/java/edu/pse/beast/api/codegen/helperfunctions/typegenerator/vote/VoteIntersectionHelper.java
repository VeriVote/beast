package edu.pse.beast.api.codegen.helperfunctions.typegenerator.vote;

import java.util.List;
import java.util.Map;

import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.codegen.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.helperfunctions.CodeGenerationToolbox;
import edu.pse.beast.api.codegen.helperfunctions.code_template.templates.vote.CodeTemplateVoteIntersection;
import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundHandler;
import edu.pse.beast.api.electiondescription.VotingInputTypes;

public class VoteIntersectionHelper {

	public static String generateVoteIntersection(
			String generatedVarName,
			List<String> intersectedVotesVarNames, 
			ElectionTypeCStruct voteArrStruct,
			VotingInputTypes votingInputType,
			CodeGenOptions options, 
			LoopBoundHandler loopBoundHandler) {
		
		
		String comparison = "";
		
		if(voteArrStruct.getVotingType().getListDimensions() == 2) {
			int i = 0;
			for (; i < intersectedVotesVarNames.size() - 2; ++i) {
				comparison += intersectedVotesVarNames.get(i) + ".LIST_MEMBER[i][j] == "
						+ intersectedVotesVarNames.get(i + 1) + ".LIST_MEMBER[i][j] && ";
			}
			comparison += intersectedVotesVarNames.get(i) + ".LIST_MEMBER[i][j] == "
					+ intersectedVotesVarNames.get(i + 1) + ".LIST_MEMBER[i][j]";
		} else if(voteArrStruct.getVotingType().getListDimensions() == 1) {
			int i = 0;
			for (; i < intersectedVotesVarNames.size() - 2; ++i) {
				comparison += intersectedVotesVarNames.get(i) + ".LIST_MEMBER[i]] == "
						+ intersectedVotesVarNames.get(i + 1) + ".LIST_MEMBER[i] && ";
			}
			comparison += intersectedVotesVarNames.get(i) + ".LIST_MEMBER[i] == "
					+ intersectedVotesVarNames.get(i + 1) + ".LIST_MEMBER[i]";
		}		
		
		Map<String, String> replacementMap = Map.of(
				"COMPARE_VARS", comparison,
				"VOTE_TYPE", voteArrStruct.getStruct().getName(),
				"AMT_MEMBER", voteArrStruct.getAmtName(),
				"LIST_MEMBER", voteArrStruct.getListName(),
				"GENERATED_VAR_NAME", generatedVarName,
				"LHS_VAR_NAME", intersectedVotesVarNames.get(0),
				"AMT_VOTERS", options.getCbmcAmountVotersVarName(),
				"AMT_CANDIDATES", options.getCbmcAmountCandidatesVarName(),
				"ASSUME", options.getCbmcAssumeName(),
				"NONDET_UINT", options.getCbmcNondetUintName()
				);
		
		String code = null;
		List<LoopBound> loopbounds = List.of();
		
		switch(votingInputType) {
			case APPROVAL : {				
				code = CodeTemplateVoteIntersection.templateApproval;
				loopbounds = CodeTemplateVoteIntersection.loopBoundsApproval;
				break;
			}
			case WEIGHTED_APPROVAL : {
				break;
			}
			case PREFERENCE : {		
				code = CodeTemplateVoteIntersection.templatePreference;
				loopbounds = CodeTemplateVoteIntersection.loopBoundsPreference;
				break;
			}
			case SINGLE_CHOICE : {
				code = CodeTemplateVoteIntersection.templateSingleChoice;
				loopbounds = CodeTemplateVoteIntersection.loopBoundsSingleChoice;
				break;
			}
			case SINGLE_CHOICE_STACK : {
				break;
			}			
		}		

		loopBoundHandler.pushLoopBounds(options.MAIN_FUNC_NAME, loopbounds);
		code = CodeGenerationToolbox.replacePlaceholders(
				code, replacementMap);
		return code;
	}
}

