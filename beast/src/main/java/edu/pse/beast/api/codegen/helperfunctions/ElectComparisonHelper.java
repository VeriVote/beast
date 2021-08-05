package edu.pse.beast.api.codegen.helperfunctions;

import java.util.List;
import java.util.Map;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.code_template.templates.elect.CodeTemplateElectComparison;
import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;
import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.descr.c_electiondescription.VotingOutputTypes;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class ElectComparisonHelper {
    private static final String NOT_EQUAL = "!=";
    private static final String GENERATED_VAR = "GENERATED_VAR";
    private static final String LHS_VAR = "LHS_VAR";
    private static final String RHS_VAR = "RHS_VAR";
    private static final String AMOUNT_MEMBER = "AMT_MEMBER";
    private static final String AMOUNT_CANDIDATES = "AMT_CANDIDATES";
    private static final String COMP = "COMP";
    private static final String LIST_MEMBER = "LIST_MEMBER";

    public static String generateCode(final String generatedVarName,
                                      final Comparison comparison,
                                      final ElectionTypeCStruct comparedType,
                                      final VotingOutputTypes votingOutputType,
                                      final CodeGenOptions options,
                                      final CodeGenLoopBoundHandler loopBoundHandler,
                                      final Class<?> c) {
        final Map<String, String> replacementMap =
                Map.of(GENERATED_VAR, generatedVarName,
                       LHS_VAR, comparison.lhsVarName,
                       RHS_VAR, comparison.rhsVarName,
                       AMOUNT_MEMBER, comparedType.getAmtName(),
                       AMOUNT_CANDIDATES, options.getCbmcAmountMaxCandsVarName(),
                       COMP, comparison.symbol,
                       LIST_MEMBER, comparedType.getListName());

        final boolean uneq = NOT_EQUAL.equals(comparison.symbol);
        final String code = CodeTemplateElectComparison.getTemplate(votingOutputType, uneq, c);
        final List<LoopBound> loopbounds =
                CodeTemplateElectComparison.getLoopBounds(votingOutputType);
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
