package edu.pse.beast.api.codegen.helperfunctions.typegenerator.vote;

import java.util.List;
import java.util.Map;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.helperfunctions.CodeGenerationToolbox;
import edu.pse.beast.api.codegen.helperfunctions.code_template.templates.vote.CodeTemplateVotePermutation;
import edu.pse.beast.api.codegen.helperfunctions.code_template.templates.vote.CodeTemplateVoteTuple;
import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundType;
import edu.pse.beast.api.electiondescription.VotingInputTypes;

public class VotePermutationHelper {
	public static String generateCode(
			String generatedVarName,
			String varName,
			ElectionTypeCStruct voteArrStruct,
			VotingInputTypes votingInputType,
			CodeGenOptions options,
			CodeGenLoopBoundHandler loopBoundHandler) {
		
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
		
		List<LoopBoundType> loopbounds = List.of();
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
		
		loopBoundHandler.pushMainLoopBounds(loopbounds);
		code = CodeGenerationToolbox.replacePlaceholders(code, replacementMap);
		return code;
	}
}
