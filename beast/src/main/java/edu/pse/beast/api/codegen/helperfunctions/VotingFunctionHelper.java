package edu.pse.beast.api.codegen.helperfunctions;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.helperfunctions.code_template.templates.CodeTemplateVotingFunctionResultCopy;
import edu.pse.beast.api.codegen.helperfunctions.code_template.templates.CodeTemplateVotingFunctionVoteArrayInit;
import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundType;
import edu.pse.beast.api.electiondescription.VotingInputTypes;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;

public class VotingFunctionHelper {

	public static String generateVoteResultCopy(
			String votingFunctionName,
			String resultArrayVarName,
			String resultStructVarName, 
			VotingOutputTypes votingOutputType,
			ElectionTypeCStruct outputStruct,
			CodeGenOptions options,
			CodeGenLoopBoundHandler loopBoundHandler) {

		Map<String, String> replacementMap = Map.of(
				"RESULT_TYPE", outputStruct.getStruct().getName(),
				"RESULT_VAR", resultStructVarName, 
				"AMT_MEMBER", outputStruct.getAmtName(),
				"AMT_CANDIDATES", options.getCbmcAmountCandidatesVarName(),
				"NONDET_UINT", options.getCbmcNondetUintName(),
				"ASSUME", options.getCbmcAssumeName(),
				"LIST_MEMBER", outputStruct.getListName(), 
				"RESULT_ARR", resultArrayVarName);

		String code = null;
		List<LoopBoundType> loopbounds = Arrays.asList();

		switch (votingOutputType) {
			case SINGLE_CANDIDATE : {
				code = CodeTemplateVotingFunctionResultCopy.templateSingleCandidate;
				break;
			}
			case CANDIDATE_LIST : {
				code = CodeTemplateVotingFunctionResultCopy.templateCandidateList;
				loopbounds = CodeTemplateVotingFunctionResultCopy.loopBoundsCandidateList;
				break;
			}
			case PARLIAMENT : {
				code = CodeTemplateVotingFunctionResultCopy.templateParliament;
				loopbounds = CodeTemplateVotingFunctionResultCopy.loopBoundsParliament;
				break;
			}
		}
		
		loopBoundHandler.pushVotingLoopBounds(votingFunctionName, loopbounds);
		
		code = CodeGenerationToolbox.replacePlaceholders(
				code, 
				replacementMap);

		return code;
	}	
	
	public static String generateVoteArrayCopy(
			String votingFunctionName,
			String voteArrayVarName,
			String votingStructVarName, 
			VotingInputTypes votingInputType,
			ElectionTypeCStruct inputStruct, 
			CodeGenOptions options,
			CodeGenLoopBoundHandler loopBoundHandler) {

		Map<String, String> replacementMap = Map.of(
				"VOTE_ARR", voteArrayVarName,
				"AMT_VOTERS", options.getCbmcAmountVotersVarName(), 
				"AMT_CANDIDATES", options.getCbmcAmountCandidatesVarName(),
				"VOTE_INPUT_STRUCT_VAR", votingStructVarName,
				"AMT_MEMBER", inputStruct.getAmtName(), 
				"LIST_MEMBER", inputStruct.getListName());

		String code = null;		
		List<LoopBoundType> loopbounds = Arrays.asList();

		switch (votingInputType) {
			case APPROVAL : {
				code = CodeTemplateVotingFunctionVoteArrayInit.templateApproval;
				loopbounds = CodeTemplateVotingFunctionVoteArrayInit.loopBoundsApproval;
				break;
			}
			case WEIGHTED_APPROVAL : {
				code = CodeTemplateVotingFunctionVoteArrayInit.templateApproval;
				loopbounds = CodeTemplateVotingFunctionVoteArrayInit.loopBoundsApproval;
				break;
			}
			case PREFERENCE : {
				code = CodeTemplateVotingFunctionVoteArrayInit.templatePreference;
				loopbounds = CodeTemplateVotingFunctionVoteArrayInit.loopBoundsPreference;
				break;
			}
			case SINGLE_CHOICE : {
				code = CodeTemplateVotingFunctionVoteArrayInit.templateSingleChoice;
				loopbounds = CodeTemplateVotingFunctionVoteArrayInit.loopBoundsSingleChoice;
				break;
			}
			case SINGLE_CHOICE_STACK : {
				break;
			}
		}	
		
		loopBoundHandler.addVotingInitLoopBounds(votingFunctionName, loopbounds);
		
		code = CodeGenerationToolbox.replacePlaceholders(code, replacementMap);
		
		return code;
	}

}
