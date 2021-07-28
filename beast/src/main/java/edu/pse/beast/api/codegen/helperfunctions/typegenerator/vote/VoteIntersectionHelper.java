package edu.pse.beast.api.codegen.helperfunctions.typegenerator.vote;

import java.util.List;
import java.util.Map;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.code_template.templates.vote.CodeTemplateVoteIntersection;
import edu.pse.beast.api.codegen.helperfunctions.CodeGenerationToolbox;
import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;
import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.descr.c_electiondescription.VotingInputTypes;

public class VoteIntersectionHelper {
    private static final String DOT = ".";
    private static final String ARR_CLOSE = "]";
    private static final String EQ = " == ";
    private static final String AND = " && ";
    private static final String IDX_I = "[i]";
    private static final String IDX_J = "[j]";
    private static final String COMPARE_VARS = "COMPARE_VARS";
    private static final String VOTE_TYPE = "VOTE_TYPE";
    private static final String AMOUNT_MEMBER = "AMT_MEMBER";
    private static final String LIST_MEMBER = "LIST_MEMBER";
    private static final String GENERATED_VAR_NAME = "GENERATED_VAR_NAME";
    private static final String LHS_VAR_NAME = "LHS_VAR_NAME";
    private static final String AMOUNT_VOTERS = "AMT_VOTERS";
    private static final String AMOUNT_CANDIDATES = "AMT_CANDIDATES";
    private static final String ASSUME = "ASSUME";
    private static final String NONDET_UINT = "NONDET_UINT";

    private static String generateComparison(final List<String> intersectedVotesVarNames,
                                             final ElectionTypeCStruct voteArrStruct) {
        String comparison = "";
        if (voteArrStruct.getVotingType().getListDimensions() == 2) {
            int i = 0;
            for (i = 0; i < intersectedVotesVarNames.size() - 2; ++i) {
                comparison += intersectedVotesVarNames.get(i)
                        + DOT + LIST_MEMBER + IDX_I + IDX_J + EQ
                        + intersectedVotesVarNames.get(i + 1)
                        + DOT + LIST_MEMBER + IDX_I + IDX_J + AND;
            }
            comparison += intersectedVotesVarNames.get(i)
                    + DOT + LIST_MEMBER + IDX_I + IDX_J + EQ
                    + intersectedVotesVarNames.get(i + 1)
                    + DOT + LIST_MEMBER + IDX_I + IDX_J;
        } else if (voteArrStruct.getVotingType().getListDimensions() == 1) {
            int i = 0;
            for (i = 0; i < intersectedVotesVarNames.size() - 2; ++i) {
                comparison += intersectedVotesVarNames.get(i)
                        + DOT + LIST_MEMBER + IDX_I + ARR_CLOSE + EQ
                        + intersectedVotesVarNames.get(i + 1)
                        + DOT + LIST_MEMBER + IDX_I + AND;
            }
            comparison += intersectedVotesVarNames.get(i)
                    + DOT + LIST_MEMBER + IDX_I + AND
                    + intersectedVotesVarNames.get(i + 1)
                    + DOT + LIST_MEMBER + IDX_I;
        }
        return comparison;
    }

    //TODO: This code was written when the intersection method was still thought
    //to be a lot simpler. Thus, this code could generate intersection for an
    //arbitrary amount of vote structs. This is no longer the case,
    //and intersection should only generate code for pairwise intersections.
    //
    //This has been fixed for the generation of intersection code of
    //election result structs. See the class
    //edu.pse.beast.api.codegen.helperfunctions.typegenerator.elect.ElectIntersectionHelper

    public static String generateVoteIntersection(final String generatedVarName,
                                                  final List<String> intersectedVotesVarNames,
                                                  final ElectionTypeCStruct voteArrStruct,
                                                  final VotingInputTypes votingInputType,
                                                  final CodeGenOptions options,
                                                  final CodeGenLoopBoundHandler loopBoundHandler,
                                                  final Class<?> c) {
        final String comparison = generateComparison(intersectedVotesVarNames, voteArrStruct);
        final Map<String, String> replacementMap =
                Map.of(COMPARE_VARS, comparison,
                       VOTE_TYPE, voteArrStruct.getStruct().getName(),
                       AMOUNT_MEMBER, voteArrStruct.getAmtName(),
                       LIST_MEMBER, voteArrStruct.getListName(),
                       GENERATED_VAR_NAME, generatedVarName,
                       LHS_VAR_NAME, intersectedVotesVarNames.get(0),
                       AMOUNT_VOTERS, options.getCbmcAmountMaxVotersVarName(),
                       AMOUNT_CANDIDATES, options.getCbmcAmountMaxCandsVarName(),
                       ASSUME, options.getCbmcAssumeName(),
                       NONDET_UINT, options.getCbmcNondetUintName());
        final String code = CodeTemplateVoteIntersection.getTemplate(votingInputType, c);
        final List<LoopBound> loopbounds =
                CodeTemplateVoteIntersection.getLoopBounds(votingInputType);

        loopBoundHandler.pushMainLoopBounds(loopbounds);
        return CodeGenerationToolbox.replacePlaceholders(code, replacementMap);
    }
}
