package edu.pse.beast.api.codegen.helperfunctions.typegenerator.vote;

import java.util.List;
import java.util.Map;

import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.codegen.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.helperfunctions.CodeGenerationToolbox;
import edu.pse.beast.api.codegen.helperfunctions.templates.typeGenerator.vote.CodeTemplateVotePermutation;
import edu.pse.beast.api.codegen.helperfunctions.templates.typeGenerator.vote.CodeTemplateVoteTuple;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundHandler;
import edu.pse.beast.api.electiondescription.VotingInputTypes;

public class VotePermutationHelper {
	public static String generateCode(
			String generatedVarName,
			String varName,
			ElectionTypeCStruct voteArrStruct,
			VotingInputTypes votingInputType,
			CodeGenOptions options,
			LoopBoundHandler loopBoundHandler) {
		
		Map<String, String> replacementMap = Map.of(
					"VOTE_TYPE", voteArrStruct.getStruct().getName(),
					"AMT_MEMBER", voteArrStruct.getAmtName(),
					"LIST_MEMBER", voteArrStruct.getListName(),
					"GENERATED_VAR_NAME", generatedVarName,
					"AMT_CANDIDATES", options.getCbmcAmountCandidatesVarName(),
					"ASSUME", options.getCbmcAssumeName(),
					"NONDET_UINT", options.getCbmcNondetUintName(),
					"RHS", varName,
					"PERM", "permutationIndices",
					"AMT_VOTERS", options.getCbmcAmountVotersVarName()
				);
		
		List<String> loopbounds = List.of();
		String code = null;		
		
		switch(votingInputType) {
			case APPROVAL : {	
				code = CodeTemplateVotePermutation.templateApproval;
				loopbounds = CodeTemplateVotePermutation.loopBoundsApproval;				
				break;
			}
			case WEIGHTED_APPROVAL : {
				break;
			}
			case PREFERENCE : {		
				code = CodeTemplateVotePermutation.templatePreference;
				loopbounds = CodeTemplateVotePermutation.loopBoundsPreference;
				break;
			}
			case SINGLE_CHOICE : {
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
