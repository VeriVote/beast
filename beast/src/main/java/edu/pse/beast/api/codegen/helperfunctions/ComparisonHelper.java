package edu.pse.beast.api.codegen.helperfunctions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.code_template.templates.CodeTemplateComparison;
import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;
import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundType;
import edu.pse.beast.api.descr.c_electiondescription.CBMCVars;
import edu.pse.beast.api.descr.c_electiondescription.CElectionVotingType;

public class ComparisonHelper {

    public static String generateCode(final String generatedVar,
                                      final String comparison,
                                      final String lhsVarName,
                                      final String rhsVarName,
                                      final CElectionVotingType type,
                                      final CodeGenOptions options,
                                      final String assumeOrAssert,
                                      final CodeGenLoopBoundHandler loopBoundHandler) {
        Map<String, String> replacementMap = null;
        String code = null;
        if (type.getListDimensions() == 0) {
            replacementMap =
                    Map.of("ASSUME_OR_ASSERT", assumeOrAssert,
                           "LHS_VAR_NAME", lhsVarName,
                           "RHS_VAR_NAME", rhsVarName,
                           "COMP", comparison,
                           "GENERATED_VAR", generatedVar);
            code = CodeTemplateComparison.TEMPLATE_0D;
        } else if (type.getListDimensions() == 1) {
            final CBMCVars listSize = type.getListSizes().get(0);
            String amtString = null;
            final List<LoopBound> loopbounds = new ArrayList<>();

            switch (listSize) {
            case AMT_CANDIDATES:
                loopbounds.add(
                        LoopBound.codeGenLoopbound(LoopBoundType.LOOP_BOUND_AMT_CANDS));
                amtString = options.getCbmcAmountMaxCandsVarName();
                break;
            case AMT_VOTERS:
                loopbounds.add(
                        LoopBound.codeGenLoopbound(LoopBoundType.LOOP_BOUND_AMT_VOTERS));
                amtString = options.getCbmcAmountMaxVotersVarName();
                break;
            default:
                break;
            }

            loopBoundHandler.pushMainLoopBounds(loopbounds);
            replacementMap =
                    Map.of("AMT", amtString,
                           "ASSUME_OR_ASSERT", assumeOrAssert,
                           "LHS_VAR_NAME", lhsVarName,
                           "RHS_VAR_NAME", rhsVarName,
                           "COMP", comparison,
                           "GENERATED_VAR", generatedVar);
            if ("!=".equals(comparison)) {
                code = CodeTemplateComparison.TEMPLATE_1D_UNEQ;
            } else {
                code = CodeTemplateComparison.TEMPLATE_1D;
            }
        }
        return CodeGenerationToolbox.replacePlaceholders(code, replacementMap);
    }
}
