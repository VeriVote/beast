package edu.pse.beast.api.codegen.helperfunctions.typegenerator.vote;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.NotImplementedException;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.code_template.templates.vote.CodeTemplateVoteComparison;
import edu.pse.beast.api.codegen.code_template.templates.vote.CodeTemplateVoteSumForCandidate;
import edu.pse.beast.api.codegen.helperfunctions.CodeGenerationToolbox;
import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;
import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundType;
import edu.pse.beast.api.electiondescription.VotingInputTypes;

public class VotesumHelper {

	public static String generateCode(
			String generatedVarName,
			int voteNumber, 
			String symbolicVarCand, 
			ElectionTypeCStruct voteStruct,
			VotingInputTypes votingInputType,
			CodeGenOptions options, 
			CodeGenLoopBoundHandler loopBoundHandler) {
		
		Map<String, String> replaceMap = Map.of(
					"GENERATED_VAR", generatedVarName,
					"AMT_MEMBER", voteStruct.getAmtName(),
					"LIST_MEMBER", voteStruct.getListName(),
					"CANDIDATE_VAR", symbolicVarCand,
					"AMT_VOTERS", options.getCbmcAmountMaxVotersVarName(),
					"VOTE_VAR", "voteNUMBER".replace("NUMBER", String.valueOf(voteNumber))
				);

		String code = null;
		List<LoopBound> loopbounds = List.of();
		
		switch(votingInputType) {
			case APPROVAL : {		
				throw new NotImplementedException();
			}
			case WEIGHTED_APPROVAL : {
				throw new NotImplementedException();
			}
			case PREFERENCE : {		
				throw new NotImplementedException();
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
		
		loopBoundHandler.pushMainLoopBounds(loopbounds);
		code = CodeGenerationToolbox.replacePlaceholders(code, replaceMap);
		return code;
	}
}
