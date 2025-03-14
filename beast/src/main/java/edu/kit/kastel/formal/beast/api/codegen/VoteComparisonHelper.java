package edu.kit.kastel.formal.beast.api.codegen;

import java.util.List;
import java.util.Map;

import edu.kit.kastel.formal.beast.api.codegen.cbmc.CodeGenOptions;
import edu.kit.kastel.formal.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.kit.kastel.formal.beast.api.codegen.loopbound.CodeGenLoopBoundHandler;
import edu.kit.kastel.formal.beast.api.codegen.loopbound.LoopBound;
import edu.kit.kastel.formal.beast.api.codegen.template.vote.CodeTemplateVoteComparison;
import edu.kit.kastel.formal.beast.api.method.VotingInputType;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class VoteComparisonHelper {
    private static final String GENERATED_VAR_NAME = "GENERATED_VAR";
    private static final String LHS_VAR_NAME = "LHS_VAR";
    private static final String RHS_VAR_NAME = "RHS_VAR";
    private static final String AMOUNT_NAME = "AMT_MEMBER";
    private static final String AMOUNT_MAX_CAND_VAR_NAME = "AMT_CANDIDATES";
    private static final String COMPARE_SYMBOL = "COMP";
    private static final String LIST_NAME = "LIST_MEMBER";
    private static final String NOT_EQUAL = "!=";

    public static String generateCode(final String generatedVarName,
                                      final Comparison comparison,
                                      final ElectionTypeCStruct comparedType,
                                      final VotingInputType votingInputType,
                                      final CodeGenOptions options,
                                      final CodeGenLoopBoundHandler loopBoundHandler) {
        final Map<String, String> replacementMap =
                Map.of(GENERATED_VAR_NAME, generatedVarName,
                       LHS_VAR_NAME, comparison.lhsVarName,
                       RHS_VAR_NAME, comparison.rhsVarName,
                       AMOUNT_NAME, comparedType.getAmountName(),
                       AMOUNT_MAX_CAND_VAR_NAME, options.getCbmcAmountMaxCandsVarName(),
                       COMPARE_SYMBOL, comparison.symbol,
                       LIST_NAME, comparedType.getListName());
        final CodeTemplateVoteComparison voteComparison = new CodeTemplateVoteComparison();
        final String code =
                voteComparison.getTemplate(votingInputType, NOT_EQUAL.equals(comparison.symbol));
        final List<LoopBound> loopbounds =
                CodeTemplateVoteComparison.getLoopBounds(votingInputType);
        loopBoundHandler.pushMainLoopBounds(loopbounds);
        return CodeGenerationToolbox.replacePlaceholders(code, replacementMap);
    }

    /**
     * TODO: Write documentation.
     *
     * @author Holger Klein
     *
     */
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
