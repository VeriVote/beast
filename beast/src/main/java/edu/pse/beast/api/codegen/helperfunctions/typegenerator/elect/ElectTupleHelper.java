package edu.pse.beast.api.codegen.helperfunctions.typegenerator.elect;

import java.util.List;
import java.util.Map;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.code_template.templates.elect.CodeTemplateElectTuple;
import edu.pse.beast.api.codegen.helperfunctions.CodeGenerationToolbox;
import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;
import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.descr.c_electiondescription.VotingOutputTypes;

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
                                      final VotingOutputTypes votingOutputType,
                                      final CodeGenOptions options,
                                      final CodeGenLoopBoundHandler loopBoundHandler,
                                      final Class<?> c) {
        String electAmtSum = "";
        for (int i = 0; i < electNames.size() - 1; ++i) {
            electAmtSum += CodeTemplateElectTuple.getTemplate(AMOUNT_FILE_KEY, c)
                           .replaceAll(CURRENT_ELECT, electNames.get(i))
                           + PLUS;
        }
        electAmtSum += CodeTemplateElectTuple.getTemplate(AMOUNT_FILE_KEY, c)
                        .replaceAll(CURRENT_ELECT, electNames.get(electNames.size() - 1));

        final Map<String, String> replacementMap =
                Map.of(ELECT_AMT_SUM, electAmtSum,
                       AMT_MEMBER, electStruct.getAmtName(),
                       LIST_MEMBER, electStruct.getListName(),
                       NONDET_UINT, options.getCbmcNondetUintName(),
                       AMT_CANDIDATES, options.getCbmcAmountMaxCandsVarName(),
                       VOTE_TYPE, electStruct.getStruct().getName(),
                       VAR_NAME, generatedVarName,
                       ASSUME, options.getCbmcAssumeName());
        String code = CodeTemplateElectTuple.getTemplate(VAR_SETUP_FILE_KEY, c);

        for (final String electVarName : electNames) {
            code += CodeTemplateElectTuple.getTemplate(votingOutputType, c)
                    .replaceAll(CURRENT_ELECT, electVarName);
        }
        final List<LoopBound> loopbounds = CodeTemplateElectTuple.getLoopBounds(votingOutputType);

        loopBoundHandler.pushMainLoopBounds(loopbounds);
        return CodeGenerationToolbox.replacePlaceholders(code, replacementMap);
    }
}
