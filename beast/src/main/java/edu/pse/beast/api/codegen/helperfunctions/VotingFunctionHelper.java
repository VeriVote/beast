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
    private static final String RESULT_TYPE = "RESULT_TYPE";
    private static final String RESULT_VAR = "RESULT_VAR";
    private static final String MAX_AMOUNT_CANDIDATES = "MAX_AMT_CANDIDATES";
    private static final String NONDET_UINT = "NONDET_UINT";
    private static final String ASSUME = "ASSUME";
    private static final String RESULT_ARR = "RESULT_ARR";
    private static final String VOTE_ARR = "VOTE_ARR";
    private static final String AMOUNT_VOTERS = "AMT_VOTERS";
    private static final String AMOUNT_CANDIDATES = "AMT_CANDIDATES";
    private static final String CURRENT_AMOUNT_VOTER = "CURRENT_AMT_VOTER";
    private static final String CURRENT_AMOUNT_CAND = "CURRENT_AMT_CAND";
    private static final String VOTE_INPUT_STRUCT_VAR = "VOTE_INPUT_STRUCT_VAR";
    private static final String AMOUNT_MEMBER = "AMT_MEMBER";
    private static final String LIST_MEMBER = "LIST_MEMBER";

    public static String generateVoteResultCopy(final String votingFunctionName,
                                                final String resultArrayVarName,
                                                final String resultStructVarName,
                                                final VotingOutputTypes votingOutputType,
                                                final ElectionTypeCStruct outputStruct,
                                                final CodeGenOptions options,
                                                final CodeGenLoopBoundHandler loopBoundHandler) {
        final Map<String, String> replacementMap =
                Map.of(RESULT_TYPE, outputStruct.getStruct().getName(),
                       RESULT_VAR, resultStructVarName,
                       AMOUNT_MEMBER, outputStruct.getAmtName(),
                       MAX_AMOUNT_CANDIDATES, options.getCbmcAmountMaxCandsVarName(),
                       CURRENT_AMOUNT_CAND, options.getCurrentAmountCandsVarName(),
                       NONDET_UINT, options.getCbmcNondetUintName(),
                       ASSUME, options.getCbmcAssumeName(),
                       LIST_MEMBER, outputStruct.getListName(),
                       RESULT_ARR, resultArrayVarName);
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
                Map.of(VOTE_ARR, voteArrayVarName,
                       AMOUNT_VOTERS, options.getCbmcAmountMaxVotersVarName(),
                       AMOUNT_CANDIDATES, options.getCbmcAmountMaxCandsVarName(),
                       CURRENT_AMOUNT_VOTER, options.getCurrentAmountVotersVarName(),
                       CURRENT_AMOUNT_CAND, options.getCurrentAmountCandsVarName(),
                       VOTE_INPUT_STRUCT_VAR, votingStructVarName,
                       AMOUNT_MEMBER, inputStruct.getAmtName(),
                       LIST_MEMBER, inputStruct.getListName());
        String code = null;
        List<LoopBound> loopbounds = Arrays.asList();

        switch (votingInputType) {
        case APPROVAL:
            code = CodeTemplateVotingFunctionVoteArrayInit.TEMPLATE_APPROVAL;
            loopbounds = CodeTemplateVotingFunctionVoteArrayInit.LOOP_BOUNDS_APPROVAL;
            break;
        case WEIGHTED_APPROVAL:
            code = CodeTemplateVotingFunctionVoteArrayInit.TEMPLATE_APPROVAL;
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
