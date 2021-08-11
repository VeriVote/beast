package edu.pse.beast.api.codegen;

import java.util.List;
import java.util.Map;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.loopbound.CodeGenLoopBoundHandler;
import edu.pse.beast.api.codegen.loopbound.LoopBound;
import edu.pse.beast.api.codegen.template.CodeTemplateVotingFunctionResultCopy;
import edu.pse.beast.api.codegen.template.CodeTemplateVotingFunctionVoteArrayInit;
import edu.pse.beast.api.method.VotingInputTypes;
import edu.pse.beast.api.method.VotingOutputTypes;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
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

    public static String generateVoteResultCopy(final CNames names,
                                                final VotingOutputTypes votingOutputType,
                                                final ElectionTypeCStruct outputStruct,
                                                final CodeGenOptions options,
                                                final CodeGenLoopBoundHandler loopBoundHandler) {
        final Map<String, String> replacementMap =
                Map.of(RESULT_TYPE, outputStruct.getStruct().getName(),
                       RESULT_VAR, names.structVar,
                       AMOUNT_MEMBER, outputStruct.getAmountName(),
                       MAX_AMOUNT_CANDIDATES, options.getCbmcAmountMaxCandsVarName(),
                       CURRENT_AMOUNT_CAND, options.getCurrentAmountCandsVarName(),
                       NONDET_UINT, options.getCbmcNondetUintName(),
                       ASSUME, options.getCbmcAssumeName(),
                       LIST_MEMBER, outputStruct.getListName(),
                       RESULT_ARR, names.arrayVar);
        final CodeTemplateVotingFunctionResultCopy resultCopy =
                new CodeTemplateVotingFunctionResultCopy();
        final String code = resultCopy.getTemplate(votingOutputType);
        final List<LoopBound> loopbounds =
                CodeTemplateVotingFunctionResultCopy.getLoopBounds(votingOutputType);

        loopBoundHandler.pushVotingLoopBounds(names.votingFunction, loopbounds);
        return CodeGenerationToolbox.replacePlaceholders(code, replacementMap);
    }

    public static String generateVoteArrayCopy(final CNames names,
                                               final VotingInputTypes votingInputType,
                                               final ElectionTypeCStruct inputStruct,
                                               final CodeGenOptions options,
                                               final CodeGenLoopBoundHandler loopBoundHandler) {
        final Map<String, String> replacementMap =
                Map.of(VOTE_ARR, names.arrayVar,
                       AMOUNT_VOTERS, options.getCbmcAmountMaxVotersVarName(),
                       AMOUNT_CANDIDATES, options.getCbmcAmountMaxCandsVarName(),
                       // FIXME: Add votes array name
                       CURRENT_AMOUNT_VOTER, options.getCurrentAmountVotersVarName(),
                       CURRENT_AMOUNT_CAND, options.getCurrentAmountCandsVarName(),
                       VOTE_INPUT_STRUCT_VAR, names.structVar,
                       AMOUNT_MEMBER, inputStruct.getAmountName(),
                       LIST_MEMBER, inputStruct.getListName());
        final List<LoopBound> loopbounds =
                CodeTemplateVotingFunctionVoteArrayInit.getLoopBounds(votingInputType);
        final CodeTemplateVotingFunctionVoteArrayInit voteArrayInit =
                new CodeTemplateVotingFunctionVoteArrayInit();
        final String code = voteArrayInit.getTemplate(votingInputType);
        loopBoundHandler.addVotingInitLoopBounds(names.votingFunction, loopbounds);
        return CodeGenerationToolbox.replacePlaceholders(code, replacementMap);
    }

    /**
     * TODO: Write documentation.
     *
     * @author Holger Klein
     *
     */
    public static final class CNames {
        final String votingFunction;
        final String arrayVar;
        final String structVar;

        public CNames(final String functionName,
                      final String arrayVarName,
                      final String structVarName) {
            this.votingFunction = functionName;
            this.arrayVar = arrayVarName;
            this.structVar = structVarName;
        }
    }
}
