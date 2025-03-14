package edu.kit.kastel.formal.beast.api.codegen.typegenerator.elect;

import java.util.List;
import java.util.Map;

import edu.kit.kastel.formal.beast.api.codegen.CodeGenerationToolbox;
import edu.kit.kastel.formal.beast.api.codegen.cbmc.CodeGenOptions;
import edu.kit.kastel.formal.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.kit.kastel.formal.beast.api.codegen.cbmc.StringReplacementMap;
import edu.kit.kastel.formal.beast.api.codegen.loopbound.CodeGenLoopBoundHandler;
import edu.kit.kastel.formal.beast.api.codegen.loopbound.LoopBound;
import edu.kit.kastel.formal.beast.api.codegen.template.elect.CodeTemplateElectIntersection;
import edu.kit.kastel.formal.beast.api.method.VotingOutputType;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class ElectIntersectionHelper {
    private static final String ELECT_TYPE = "ELECT_TYPE";
    private static final String AMOUNT_MEMBER = "AMT_MEMBER";
    private static final String LIST_MEMBER = "LIST_MEMBER";
    private static final String GENERATED_VAR_NAME = "GENERATED_VAR_NAME";
    private static final String LHS = "LHS";
    private static final String RHS = "RHS";
    private static final String MAX_AMOUNT_CANDIDATES = "MAX_AMT_CANDIDATES";
    private static final String ASSUME = "ASSUME";
    private static final String NONDET_UINT = "NONDET_UINT";

    public static String generateCode(final String generatedVarName,
                                      final List<String> intersectedElectNames,
                                      final ElectionTypeCStruct electStruct,
                                      final VotingOutputType votingOutputType,
                                      final CodeGenOptions options,
                                      final CodeGenLoopBoundHandler loopBoundHandler) {
        final Map<String, String> replacementMap =
                StringReplacementMap.genMap(ELECT_TYPE, electStruct.getStruct().getName(),
                                            AMOUNT_MEMBER, electStruct.getAmountName(),
                                            LIST_MEMBER, electStruct.getListName(),
                                            GENERATED_VAR_NAME, generatedVarName,
                                            LHS, intersectedElectNames.get(0),
                                            RHS, intersectedElectNames.get(1),
                                            MAX_AMOUNT_CANDIDATES,
                                            options.getCbmcAmountMaxCandsVarName(),
                                            ASSUME, options.getCbmcAssumeName(),
                                            NONDET_UINT, options.getCbmcNondetUintName());
        final List<LoopBound> loopbounds =
                CodeTemplateElectIntersection.getLoopBounds(votingOutputType);
        final CodeTemplateElectIntersection electIntersection = new CodeTemplateElectIntersection();
        final String code = electIntersection.getTemplate(votingOutputType);

        loopBoundHandler.pushMainLoopBounds(loopbounds);
        return CodeGenerationToolbox.replacePlaceholders(code, replacementMap);
    }
}
