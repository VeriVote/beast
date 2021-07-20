package edu.pse.beast.api.codegen.helperfunctions.typegenerator.elect;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.NotImplementedException;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.cbmc.StringReplacementMap;
import edu.pse.beast.api.codegen.code_template.templates.elect.CodeTemplateElectIntersection;
import edu.pse.beast.api.codegen.helperfunctions.CodeGenerationToolbox;
import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;
import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.descr.c_electiondescription.VotingOutputTypes;

public class ElectIntersectionHelper {

    public static String generateCode(final String generatedVarName,
                                      final List<String> intersectedElectNames,
                                      final ElectionTypeCStruct electStruct,
                                      final VotingOutputTypes votingOutputType,
                                      final CodeGenOptions options,
                                      final CodeGenLoopBoundHandler loopBoundHandler) {
        final Map<String, String> replacementMap =
                StringReplacementMap.genMap("ELECT_TYPE", electStruct.getStruct().getName(),
                                            "AMT_MEMBER", electStruct.getAmtName(),
                                            "LIST_MEMBER", electStruct.getListName(),
                                            "GENERATED_VAR_NAME", generatedVarName,
                                            "LHS", intersectedElectNames.get(0),
                                            "RHS", intersectedElectNames.get(1),
                                            "MAX_AMT_CANDIDATES",
                                            options.getCbmcAmountMaxCandsVarName(),
                                            "ASSUME", options.getCbmcAssumeName(),
                                            "NONDET_UINT", options.getCbmcNondetUintName());
        List<LoopBound> loopbounds = List.of();
        String code = "";

        switch (votingOutputType) {
        case CANDIDATE_LIST:
            code = CodeTemplateElectIntersection.TEMPLATE_CANDIDATE_LIST;
            loopbounds = CodeTemplateElectIntersection.LOOP_BOUNDS_CANDIDATE_LIST;
            break;
        case PARLIAMENT:
            code = CodeTemplateElectIntersection.TEMPLATE_PARLIAMENT;
            loopbounds = CodeTemplateElectIntersection.LOOP_BOUNDS_PARLIAMENT;
            break;
        case PARLIAMENT_STACK:
            throw new NotImplementedException();
        case SINGLE_CANDIDATE:
            throw new NotImplementedException();
        default:
        }

        loopBoundHandler.pushMainLoopBounds(loopbounds);
        return CodeGenerationToolbox.replacePlaceholders(code, replacementMap);
    }
}
