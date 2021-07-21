package edu.pse.beast.api.codegen.helperfunctions;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.NotImplementedException;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.code_template.templates.vote.CodeTemplateVoteComparison;
import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;
import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.descr.c_electiondescription.VotingInputTypes;

public class VoteComparisonHelper {
    private static final String GENERATED_VAR_NAME = "GENERATED_VAR";
    private static final String LHS_VAR_NAME = "LHS_VAR";
    private static final String RHS_VAR_NAME = "RHS_VAR";
    private static final String AMOUNT_NAME = "AMT_MEMBER";
    private static final String AMOUNT_MAX_CAND_VAR_NAME = "AMT_CANDIDATES";
    private static final String COMPARE_SYMBOL = "COMP";
    private static final String LIST_NAME = "LIST_MEMBER";
    private static final String NOT_EQUAL = "!=";

    private static List<LoopBound> getLoopBounds(final VotingInputTypes votingInputType) {
        final List<LoopBound> bounds;
        switch (votingInputType) {
        case APPROVAL:
            bounds = CodeTemplateVoteComparison.LOOP_BOUNDS_APPROVAL;
            break;
        case WEIGHTED_APPROVAL:
            bounds = List.of();
            throw new NotImplementedException();
        case PREFERENCE:
            bounds = CodeTemplateVoteComparison.LOOP_BOUNDS_PREFERENCE;
            break;
        case SINGLE_CHOICE:
            bounds = CodeTemplateVoteComparison.LOOP_BOUNDS_SINGLE_CHOICE;
            break;
        case SINGLE_CHOICE_STACK:
            bounds = List.of();
            throw new NotImplementedException();
        default:
            bounds = List.of();
        }
        return bounds;
    }

    public static String generateCode(final String generatedVarName,
                                      final Comparison comparison,
                                      final ElectionTypeCStruct comparedType,
                                      final VotingInputTypes votingInputType,
                                      final CodeGenOptions options,
                                      final CodeGenLoopBoundHandler loopBoundHandler) {
        final Map<String, String> replacementMap =
                Map.of(GENERATED_VAR_NAME, generatedVarName,
                       LHS_VAR_NAME, comparison.lhsVarName,
                       RHS_VAR_NAME, comparison.rhsVarName,
                       AMOUNT_NAME, comparedType.getAmtName(),
                       AMOUNT_MAX_CAND_VAR_NAME, options.getCbmcAmountMaxCandsVarName(),
                       COMPARE_SYMBOL, comparison.symbol,
                       LIST_NAME, comparedType.getListName());

        String code = null;
        if (NOT_EQUAL.equals(comparison.symbol)) {
            switch (votingInputType) {
            case APPROVAL:
                code = CodeTemplateVoteComparison.getTemplateApprovalUneq();
                break;
            case WEIGHTED_APPROVAL:
                break;
            case PREFERENCE:
                code = CodeTemplateVoteComparison.getTemplatePreferenceUneq();
                break;
            case SINGLE_CHOICE:
                code = CodeTemplateVoteComparison.getTemplateSingleChoiceUnEq();
                break;
            case SINGLE_CHOICE_STACK:
                break;
            default:
            }
        } else {
            switch (votingInputType) {
            case APPROVAL:
                code = CodeTemplateVoteComparison.getTemplateApproval();
                break;
            case WEIGHTED_APPROVAL:
                break;
            case PREFERENCE:
                code = CodeTemplateVoteComparison.getTemplatePreference();
                break;
            case SINGLE_CHOICE:
                code = CodeTemplateVoteComparison.getTemplateSingleChoiceEq();
                break;
            case SINGLE_CHOICE_STACK:
                break;
            default:
            }
        }

        final List<LoopBound> loopbounds = getLoopBounds(votingInputType);
        loopBoundHandler.pushMainLoopBounds(loopbounds);
        return CodeGenerationToolbox.replacePlaceholders(code, replacementMap);
    }

    public static class Comparison {
        public final String symbol;
        public final String lhsVarName;
        public final String rhsVarName;

        public Comparison(final String compSymb,
                          final String lhsVar,
                          final String rhsVar) {
            this.symbol = compSymb;
            this.lhsVarName = lhsVar;
            this.rhsVarName = rhsVar;
        }
    }
}
