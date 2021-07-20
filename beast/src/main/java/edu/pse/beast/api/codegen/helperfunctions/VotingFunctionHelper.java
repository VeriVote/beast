package edu.pse.beast.api.codegen.helperfunctions;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.code_template.templates.CodeTemplateVotingFunctionResultCopy;
import edu.pse.beast.api.codegen.code_template.templates.CodeTemplateVotingFunctionVoteArrayInit;
import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;
import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.descr.c_electiondescription.VotingInputTypes;
import edu.pse.beast.api.descr.c_electiondescription.VotingOutputTypes;

public class VotingFunctionHelper {

    public static String generateVoteResultCopy(final String votingFunctionName,
                                                final String resultArrayVarName,
                                                final String resultStructVarName,
                                                final VotingOutputTypes votingOutputType,
                                                final ElectionTypeCStruct outputStruct,
                                                final CodeGenOptions options,
                                                final CodeGenLoopBoundHandler loopBoundHandler) {
        final Map<String, String> replacementMap =
                Map.of("RESULT_TYPE", outputStruct.getStruct().getName(),
                       "RESULT_VAR", resultStructVarName,
                       "AMT_MEMBER", outputStruct.getAmtName(),
                       "MAX_AMT_CANDIDATES", options.getCbmcAmountMaxCandsVarName(),
                       "CURRENT_AMT_CAND", options.getCurrentAmountCandsVarName(),
                       "NONDET_UINT", options.getCbmcNondetUintName(),
                       "ASSUME", options.getCbmcAssumeName(),
                       "LIST_MEMBER", outputStruct.getListName(),
                       "RESULT_ARR", resultArrayVarName);
        String code = null;
        List<LoopBound> loopbounds = Arrays.asList();

        switch (votingOutputType) {
        case SINGLE_CANDIDATE:
            code = CodeTemplateVotingFunctionResultCopy.TEMPLATE_SINGLE_CANDIDATE;
            break;
        case CANDIDATE_LIST:
            code = CodeTemplateVotingFunctionResultCopy.TEMPLATE_CANDIDATE_LIST;
            loopbounds = CodeTemplateVotingFunctionResultCopy.LOOP_BOUNDS_CANDIDATE_LIST;
            break;
        case PARLIAMENT:
            code = CodeTemplateVotingFunctionResultCopy.TEMPLATE_PARLIAMENT;
            loopbounds = CodeTemplateVotingFunctionResultCopy.LOOP_BOUNDS_PARLIAMENT;
            break;
        default:
            break;
        }
        loopBoundHandler.pushVotingLoopBounds(votingFunctionName, loopbounds);
        return CodeGenerationToolbox.replacePlaceholders(code, replacementMap);
    }

    public static String generateVoteArrayCopy(final String votingFunctionName,
                                               final String voteArrayVarName,
                                               final String votingStructVarName,
                                               final VotingInputTypes votingInputType,
                                               final ElectionTypeCStruct inputStruct,
                                               final CodeGenOptions options,
                                               final CodeGenLoopBoundHandler loopBoundHandler) {
        final Map<String, String> replacementMap =
                Map.of("VOTE_ARR", voteArrayVarName,
                       "AMT_VOTERS", options.getCbmcAmountMaxVotersVarName(),
                       "AMT_CANDIDATES", options.getCbmcAmountMaxCandsVarName(),
                       "CURRENT_AMT_VOTER", options.getCurrentAmountVotersVarName(),
                       "CURRENT_AMT_CAND", options.getCurrentAmountCandsVarName(),
                       "VOTE_INPUT_STRUCT_VAR", votingStructVarName,
                       "AMT_MEMBER", inputStruct.getAmtName(),
                       "LIST_MEMBER", inputStruct.getListName());
        String code = null;
        List<LoopBound> loopbounds = Arrays.asList();

        switch (votingInputType) {
        case APPROVAL:
            code = CodeTemplateVotingFunctionVoteArrayInit.TEMPLATW_APPROVAL;
            loopbounds = CodeTemplateVotingFunctionVoteArrayInit.LOOP_BOUNDS_APPROVAL;
            break;
        case WEIGHTED_APPROVAL:
            code = CodeTemplateVotingFunctionVoteArrayInit.TEMPLATW_APPROVAL;
            loopbounds = CodeTemplateVotingFunctionVoteArrayInit.LOOP_BOUNDS_APPROVAL;
            break;
        case PREFERENCE:
            code = CodeTemplateVotingFunctionVoteArrayInit.TEMPLATE_PREFERENCE;
            loopbounds = CodeTemplateVotingFunctionVoteArrayInit.LOOP_BOUNDS_PREFERENCE;
            break;
        case SINGLE_CHOICE:
            code = CodeTemplateVotingFunctionVoteArrayInit.TEMPLATE_SINGLE_CHOICE;
            loopbounds = CodeTemplateVotingFunctionVoteArrayInit.LOOP_BOUNDS_SINGLE_CHOICE;
            break;
        case SINGLE_CHOICE_STACK:
            break;
        default:
            break;
        }
        loopBoundHandler.addVotingInitLoopBounds(votingFunctionName, loopbounds);
        return CodeGenerationToolbox.replacePlaceholders(code, replacementMap);
    }
}
