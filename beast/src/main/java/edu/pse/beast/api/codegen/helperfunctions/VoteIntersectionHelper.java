package edu.pse.beast.api.codegen.helperfunctions;

import java.util.List;
import java.util.Map;

import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.codegen.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.helperfunctions.templates.typeGenerator.vote.CodeTemplateVoteIntersection;
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
		
		Map<String, String> replacementMap = Map.of(
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
		
		switch(votingInputType) {
			case APPROVAL : {					
				break;
			}
			case WEIGHTED_APPROVAL : {
				break;
			}
			case PREFERENCE : {		
				code = CodeTemplateVoteIntersection.templatePreference;
				break;
			}
			case SINGLE_CHOICE : {
				code = CodeTemplateVoteIntersection.templateSingleChoice;
				break;
			}
			case SINGLE_CHOICE_STACK : {
				break;
			}			
		}		

		return code;
	}
}

