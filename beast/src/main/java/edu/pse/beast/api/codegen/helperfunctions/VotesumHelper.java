package edu.pse.beast.api.codegen.helperfunctions;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.codegen.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.helperfunctions.templates.CodeTemplateVoteComparison;
import edu.pse.beast.api.codegen.helperfunctions.templates.typeGenerator.vote.CodeTemplateVoteSumForCandidate;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundHandler;
import edu.pse.beast.api.electiondescription.VotingInputTypes;

public class VotesumHelper {

	public static String generateCode(
			String generatedVarName,
			int voteNumber, 
			String symbolicVarCand, 
			ElectionTypeCStruct voteStruct,
			VotingInputTypes votingInputType,
			CodeGenOptions options, 
			LoopBoundHandler loopBoundHandler) {
		
		Map<String, String> replaceMap = Map.of(
					"GENERATED_VAR", generatedVarName,
					"AMT_MEMBER", voteStruct.getAmtName(),
					"LIST_MEMBER", voteStruct.getListName(),
					"CANDIDATE_VAR", symbolicVarCand,
					"AMT_VOTERS", options.getCbmcAmountVotersVarName(),
					"VOTE_VAR", "voteNUMBER".replace("NUMBER", String.valueOf(voteNumber))
				);

		String code = null;
		List<String> loopbounds = List.of();
		
		switch(votingInputType) {
			case APPROVAL : {					
				break;
			}
			case WEIGHTED_APPROVAL : {
				break;
			}
			case PREFERENCE : {				
				break;
			}
			case SINGLE_CHOICE : {
				code = CodeTemplateVoteSumForCandidate.templateSingleChoice;
				break;
			}
			case SINGLE_CHOICE_STACK : {
				code = CodeTemplateVoteSumForCandidate.templateSingleChoiceStack;
				loopbounds = CodeTemplateVoteSumForCandidate.loopBoundsPreference;
				break;
			}
		}
		
		loopBoundHandler.addMainLoopBounds(loopbounds);
		code = CodeGenerationToolbox.replacePlaceholders(code, replaceMap);
		return code;
	}
}
