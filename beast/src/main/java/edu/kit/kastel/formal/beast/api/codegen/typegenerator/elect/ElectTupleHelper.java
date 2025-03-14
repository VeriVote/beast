package edu.kit.kastel.formal.beast.api.codegen.typegenerator.elect;

import java.util.List;
import java.util.Map;

import edu.kit.kastel.formal.beast.api.codegen.CodeGenerationToolbox;
import edu.kit.kastel.formal.beast.api.codegen.cbmc.CodeGenOptions;
import edu.kit.kastel.formal.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.kit.kastel.formal.beast.api.codegen.loopbound.CodeGenLoopBoundHandler;
import edu.kit.kastel.formal.beast.api.codegen.loopbound.LoopBound;
import edu.kit.kastel.formal.beast.api.codegen.template.elect.CodeTemplateElectTuple;
import edu.kit.kastel.formal.beast.api.method.VotingOutputType;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class ElectTupleHelper {
    public static final String VAR_SETUP_FILE_KEY = "VAR_SETUP";
    public static final String AMOUNT_FILE_KEY = "AMOUNT";

    private static final String PLUS = " + ";
    private static final String ELECT_AMT_SUM = "ELECT_AMT_SUM";
    private static final String AMT_MEMBER = "AMT_MEMBER";
    private static final String LIST_MEMBER = "LIST_MEMBER";
    private static final String NONDET_UINT = "NONDET_UINT";
    private static final String AMT_CANDIDATES = "AMT_CANDIDATES";
    private static final String VOTE_TYPE = "VOTE_TYPE";
    private static final String VAR_NAME = "VAR_NAME";
    private static final String ASSUME = "ASSUME";
    private static final String CURRENT_ELECT = "CURRENT_ELECT";

    public static String generateCode(final String generatedVarName,
                                      final List<String> electNames,
                                      final ElectionTypeCStruct electStruct,
                                      final VotingOutputType votingOutputType,
                                      final CodeGenOptions options,
                                      final CodeGenLoopBoundHandler loopBoundHandler) {
        final CodeTemplateElectTuple electTuple = new CodeTemplateElectTuple();
        final StringBuilder electAmtSum = new StringBuilder();
        for (int i = 0; i < electNames.size() - 1; ++i) {
            electAmtSum.append(
                    electTuple.getTemplate(AMOUNT_FILE_KEY)
                            .replaceAll(CURRENT_ELECT, electNames.get(i))
                           + PLUS);
        }
        electAmtSum.append(electTuple.getTemplate(AMOUNT_FILE_KEY)
                        .replaceAll(CURRENT_ELECT, electNames.get(electNames.size() - 1)));

        final Map<String, String> replacementMap =
                Map.of(ELECT_AMT_SUM, electAmtSum.toString(),
                       AMT_MEMBER, electStruct.getAmountName(),
                       LIST_MEMBER, electStruct.getListName(),
                       NONDET_UINT, options.getCbmcNondetUintName(),
                       AMT_CANDIDATES, options.getCbmcAmountMaxCandsVarName(),
                       VOTE_TYPE, electStruct.getStruct().getName(),
                       VAR_NAME, generatedVarName,
                       ASSUME, options.getCbmcAssumeName());
        final StringBuilder code = new StringBuilder(electTuple.getTemplate(VAR_SETUP_FILE_KEY));

        for (final String electVarName : electNames) {
            code.append(electTuple.getTemplate(votingOutputType)
                        .replaceAll(CURRENT_ELECT, electVarName));
        }
        final List<LoopBound> loopbounds = CodeTemplateElectTuple.getLoopBounds(votingOutputType);

        loopBoundHandler.pushMainLoopBounds(loopbounds);
        return CodeGenerationToolbox.replacePlaceholders(code.toString(), replacementMap);
    }
}
