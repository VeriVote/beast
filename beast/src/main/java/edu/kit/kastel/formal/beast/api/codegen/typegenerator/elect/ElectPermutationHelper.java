package edu.kit.kastel.formal.beast.api.codegen.typegenerator.elect;

import java.util.List;
import java.util.Map;

import edu.kit.kastel.formal.beast.api.codegen.CodeGenerationToolbox;
import edu.kit.kastel.formal.beast.api.codegen.cbmc.CodeGenOptions;
import edu.kit.kastel.formal.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.kit.kastel.formal.beast.api.codegen.loopbound.CodeGenLoopBoundHandler;
import edu.kit.kastel.formal.beast.api.codegen.loopbound.LoopBound;
import edu.kit.kastel.formal.beast.api.codegen.template.elect.CodeTemplateElectPermutation;
import edu.kit.kastel.formal.beast.api.method.VotingOutputType;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class ElectPermutationHelper {
    private static final String ELECT_TYPE = "ELECT_TYPE";
    private static final String AMOUNT_MEMBER = "AMT_MEMBER";
    private static final String LIST_MEMBER = "LIST_MEMBER";
    private static final String GENERATED_VAR_NAME = "GENERATED_VAR_NAME";
    private static final String ASSUME = "ASSUME";
    private static final String NONDET_UINT = "NONDET_UINT";
    private static final String RHS = "RHS";
    private static final String PERM = "PERM";
    private static final String PERMUTATION_INDICES = "permutationIndices";
    private static final String AMOUNT_CANDIDATES = "AMT_CANDIDATES";

    public static String generateCode(final String generatedVarName,
                                      final String varName,
                                      final ElectionTypeCStruct electStruct,
                                      final VotingOutputType votingOutputType,
                                      final CodeGenOptions options,
                                      final CodeGenLoopBoundHandler loopBoundHandler) {
        final Map<String, String> replacementMap =
                Map.of(ELECT_TYPE, electStruct.getStruct().getName(),
                       AMOUNT_MEMBER, electStruct.getAmountName(),
                       LIST_MEMBER, electStruct.getListName(),
                       GENERATED_VAR_NAME, generatedVarName,
                       ASSUME, options.getCbmcAssumeName(),
                       NONDET_UINT, options.getCbmcNondetUintName(),
                       RHS, varName,
                       PERM, PERMUTATION_INDICES,
                       AMOUNT_CANDIDATES, options.getCbmcAmountMaxCandsVarName());
        final CodeTemplateElectPermutation electPermutation = new CodeTemplateElectPermutation();
        final String code = electPermutation.getTemplate(votingOutputType);
        final List<LoopBound> loopbounds =
                CodeTemplateElectPermutation.getLoopBounds(votingOutputType);

        loopBoundHandler.pushMainLoopBounds(loopbounds);
        return CodeGenerationToolbox.replacePlaceholders(code, replacementMap);
    }
}
