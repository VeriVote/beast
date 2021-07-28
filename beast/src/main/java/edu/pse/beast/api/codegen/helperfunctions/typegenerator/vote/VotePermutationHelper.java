package edu.pse.beast.api.codegen.helperfunctions.typegenerator.vote;

import java.util.List;
import java.util.Map;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.code_template.templates.vote.CodeTemplateVoteComparison;
import edu.pse.beast.api.codegen.code_template.templates.vote.CodeTemplateVotePermutation;
import edu.pse.beast.api.codegen.helperfunctions.CodeGenerationToolbox;
import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;
import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.descr.c_electiondescription.VotingInputTypes;

public class VotePermutationHelper {
    private static final String VOTE_TYPE = "VOTE_TYPE";
    private static final String AMOUNT_MEMBER = "AMT_MEMBER";
    private static final String LIST_MEMBER = "LIST_MEMBER";
    private static final String GENERATED_VAR_NAME = "GENERATED_VAR_NAME";
    private static final String AMOUNT_CANDIDATES = "AMT_CANDIDATES";
    private static final String ASSUME = "ASSUME";
    private static final String NONDET_UINT = "NONDET_UINT";
    private static final String RHS = "RHS";
    private static final String PERM = "PERM";
    private static final String PERMUTATION_INDICES = "permutationIndices";
    private static final String AMOUNT_VOTERS = "AMT_VOTERS";

    public static String generateCode(final String generatedVarName,
                                      final String varName,
                                      final ElectionTypeCStruct voteArrStruct,
                                      final VotingInputTypes votingInputType,
                                      final CodeGenOptions options,
                                      final CodeGenLoopBoundHandler loopBoundHandler,
                                      final Class<?> c) {
        final Map<String, String> replacementMap =
                Map.of(VOTE_TYPE, voteArrStruct.getStruct().getName(),
                       AMOUNT_MEMBER, voteArrStruct.getAmtName(),
                       LIST_MEMBER, voteArrStruct.getListName(),
                       GENERATED_VAR_NAME, generatedVarName,
                       AMOUNT_CANDIDATES, options.getCbmcAmountMaxCandsVarName(),
                       ASSUME, options.getCbmcAssumeName(),
                       NONDET_UINT, options.getCbmcNondetUintName(),
                       RHS, varName,
                       PERM, PERMUTATION_INDICES,
                       AMOUNT_VOTERS, options.getCbmcAmountMaxVotersVarName());

        final List<LoopBound> loopbounds =
                CodeTemplateVoteComparison.getLoopBounds(votingInputType);
        final String code = CodeTemplateVotePermutation.getTemplate(votingInputType, c);
        loopBoundHandler.pushMainLoopBounds(loopbounds);
        return CodeGenerationToolbox.replacePlaceholders(code, replacementMap);
    }
}
