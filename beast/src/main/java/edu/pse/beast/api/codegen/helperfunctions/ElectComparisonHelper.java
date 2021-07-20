package edu.pse.beast.api.codegen.helperfunctions;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.NotImplementedException;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.code_template.templates.elect.CodeTemplateElectComparison;
import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;
import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.descr.c_electiondescription.VotingOutputTypes;

public class ElectComparisonHelper {

    private static List<LoopBound> getLoopBounds(final VotingOutputTypes votingOutputType) {
        switch (votingOutputType) {
        case CANDIDATE_LIST:
            return CodeTemplateElectComparison.LOOP_BOUNDS_CANDIDATE_LIST;
        case PARLIAMENT:
            return CodeTemplateElectComparison.LOOP_BOUNDS_PARLIAMENT;
        case PARLIAMENT_STACK:
            throw new NotImplementedException();
        case SINGLE_CANDIDATE:
            throw new NotImplementedException();
        default:
            return List.of();
        }
    }

    public static String generateCode(final String generatedVarName,
                                      final String lhsVarName,
                                      final String rhsVarName,
                                      final ElectionTypeCStruct comparedType,
                                      final VotingOutputTypes votingOutputType,
                                      final CodeGenOptions options,
                                      final String compareSymbol,
                                      final CodeGenLoopBoundHandler loopBoundHandler) {
        final Map<String, String> replacementMap =
                Map.of("GENERATED_VAR", generatedVarName,
                       "LHS_VAR", lhsVarName,
                       "RHS_VAR", rhsVarName,
                       "AMT_MEMBER", comparedType.getAmtName(),
                       "AMT_CANDIDATES", options.getCbmcAmountMaxCandsVarName(),
                       "COMP", compareSymbol,
                       "LIST_MEMBER", comparedType.getListName());

        String code = null;
        if ("!=".equals(compareSymbol)) {
            switch (votingOutputType) {
            case CANDIDATE_LIST:
                code = CodeTemplateElectComparison.getTemplateCandidateListUneq();
                break;
            case PARLIAMENT:
                code = CodeTemplateElectComparison.getTemplateParliamentUneq();
                break;
            case PARLIAMENT_STACK:
                break;
            case SINGLE_CANDIDATE:
                code = CodeTemplateElectComparison.getTemplateSingleCandidate();
                break;
            default:
            }
        } else {
            switch (votingOutputType) {
            case CANDIDATE_LIST:
                code = CodeTemplateElectComparison.getTemplateCandidateList();
                break;
            case PARLIAMENT:
                code = CodeTemplateElectComparison.getTemplateParliament();
                break;
            case PARLIAMENT_STACK:
                break;
            case SINGLE_CANDIDATE:
                code = CodeTemplateElectComparison.getTemplateSingleCandidate();
                break;
            default:
            }
        }

        final List<LoopBound> loopbounds = getLoopBounds(votingOutputType);
        loopBoundHandler.pushMainLoopBounds(loopbounds);
        return CodeGenerationToolbox.replacePlaceholders(code, replacementMap);
    }
}
