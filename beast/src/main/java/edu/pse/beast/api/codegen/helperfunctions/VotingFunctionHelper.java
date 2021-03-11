package edu.pse.beast.api.codegen.helperfunctions;

import java.util.List;
import java.util.Map;

import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.codegen.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.helperfunctions.templates.CodeTemplateVotingFunctionResultCopy;
import edu.pse.beast.api.codegen.helperfunctions.templates.CodeTemplateVotingFunctionVoteArrayInit;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundHandler;
import edu.pse.beast.api.electiondescription.VotingInputTypes;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;

public class VotingFunctionHelper {

	public static String generateVoteResultInit(
			String resultArrayVarName,
			VotingOutputTypes votingOutputType, 
			CodeGenOptions options) {

		Map<String, String> replacementMap = Map.of(
				"VAR_NAME",	resultArrayVarName, 
				"AMT_CANDIDATES", options.getCbmcAmountCandidatesVarName());

		String code = null;

		switch (votingOutputType) {
			case CANDIDATE_LIST : {
				code = "unsigned int VAR_NAME[AMT_CANDIDATES];\n";
				break;
			}
			case PARLIAMENT : {
				code = "unsigned int VAR_NAME[AMT_CANDIDATES];\n";
				break;
			}
			case PARLIAMENT_STACK : {
				break;
			}
			case SINGLE_CANDIDATE : {
				code = "unsigned int VAR_NAME;\n";
				break;
			}

		}

		code = CodeGenerationToolbox.replacePlaceholders(code, replacementMap);
		return code;
	}

	public static String generateVoteResultCopy(
			String resultArrayVarName,
			String resultStructVarName, 
			VotingOutputTypes votingOutputType,
			ElectionTypeCStruct outputStruct,
			CodeGenOptions options,
			LoopBoundHandler loopBoundHandler) {

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

		switch (votingOutputType) {
			case SINGLE_CANDIDATE : {
				code = CodeTemplateVotingFunctionResultCopy.templateSingleCandidate;
				break;
			}
			case CANDIDATE_LIST : {
				code = CodeTemplateVotingFunctionResultCopy.templateCandidateList;
				break;
			}
			case PARLIAMENT : {
				code = CodeTemplateVotingFunctionResultCopy.templateParliament;
				break;
			}
		}
		code = CodeGenerationToolbox.replacePlaceholders(
				code, 
				replacementMap);

		return code;
	}

	public static String generateVoteArrayInitAndCopy(
			String voteArrayVarName,
			String votingStructVarName, 
			VotingInputTypes votingInputType,
			ElectionTypeCStruct inputStruct, 
			CodeGenOptions options,
			LoopBoundHandler loopBoundHandler) {

		Map<String, String> replacementMap = Map.of(
				"VOTE_ARR", voteArrayVarName,
				"AMT_VOTERS", options.getCbmcAmountVotersVarName(), 
				"AMT_CANDIDATES", options.getCbmcAmountCandidatesVarName(),
				"VOTE_INPUT_STRUCT_VAR", votingStructVarName,
				"AMT_MEMBER", inputStruct.getAmtName(), 
				"LIST_MEMBER", inputStruct.getListName());

		String code = null;

		switch (votingInputType) {
			case APPROVAL : {
				code = CodeTemplateVotingFunctionVoteArrayInit.templateApproval;
				break;
			}
			case WEIGHTED_APPROVAL : {
				break;
			}
			case PREFERENCE : {
				code = CodeTemplateVotingFunctionVoteArrayInit.templatePreference;
				break;
			}
			case SINGLE_CHOICE : {
				code = CodeTemplateVotingFunctionVoteArrayInit.templateSingleChoice;
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
