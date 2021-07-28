package edu.pse.beast.api.codegen.helperfunctions;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.code_template.templates.CodeTemplateComparison;
import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;
import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundType;
import edu.pse.beast.api.descr.c_electiondescription.CElectionVotingType;

public class ComparisonHelper {
    private static final String AMOUNT = "AMT";
    private static final String ASSUME_OR_ASSERT = "ASSUME_OR_ASSERT";
    private static final String LHS_VAR_NAME = "LHS_VAR_NAME";
    private static final String RHS_VAR_NAME = "RHS_VAR_NAME";
    private static final String COMPARISON = "COMP";
    private static final String GENERATED_VAR = "GENERATED_VAR";
    private static final String UN_EQ = "!=";

    public static String generateCode(final String generatedVar,
                                      final Comparison comparison,
                                      final CElectionVotingType type,
                                      final CodeGenOptions options,
                                      final String assumeOrAssert,
                                      final CodeGenLoopBoundHandler loopBoundHandler,
                                      final Class<?> c) {
        final int dimension = type.getListDimensions();
        final String code =
                CodeTemplateComparison.getTemplate(dimension,
                                                   UN_EQ.equals(comparison.symbol),
                                                   c);
        final Map<String, String> replacementMap = new LinkedHashMap<String, String>();
        if (dimension == 1) {
            final LoopBoundType loopBoundType;
            switch (type.getListSizes().get(0)) {
            case AMOUNT_CANDIDATES:
                loopBoundType = LoopBoundType.LOOP_BOUND_AMT_CANDS;
                replacementMap.put(AMOUNT, options.getCbmcAmountMaxCandsVarName());
                break;
            case AMOUNT_VOTERS:
                loopBoundType = LoopBoundType.LOOP_BOUND_AMT_VOTERS;
                replacementMap.put(AMOUNT, options.getCbmcAmountMaxVotersVarName());
                break;
            case AMOUNT_SEATS:
            default:
                loopBoundType = null;
            }
            final List<LoopBound> loopbounds = new ArrayList<>();
            loopbounds.add(LoopBound.codeGenLoopbound(loopBoundType));
            loopBoundHandler.pushMainLoopBounds(loopbounds);
        }
        replacementMap.putAll(Map.of(ASSUME_OR_ASSERT, assumeOrAssert,
                                     LHS_VAR_NAME, comparison.lhsVarName,
                                     RHS_VAR_NAME, comparison.rhsVarName,
                                     COMPARISON, comparison.symbol,
                                     GENERATED_VAR, generatedVar));
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
