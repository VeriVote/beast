package edu.pse.beast.api.codegen.helperfunctions;

import java.util.Map;

import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.codegen.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.helperfunctions.templates.CodeTemplateVotePermutation;
import edu.pse.beast.api.codegen.helperfunctions.templates.CodeTemplateVoteTuple;
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
					"AMT_VOTES", options.getCbmcAmountVotersVarName()
				);
		
		String code = null;		
		
		switch(votingInputType) {
			case APPROVAL : {					
				break;
			}
			case WEIGHTED_APPROVAL : {
				break;
			}
			case PREFERENCE : {		
				code = CodeTemplateVotePermutation.templatePreference;
				break;
			}
			case SINGLE_CHOICE : {
				break;
			}
			case SINGLE_CHOICE_STACK : {
				break;
			}			
		}				
		
		code = CodeGenerationToolbox.replacePlaceholders(code, replacementMap);
		return code;
	}
}
