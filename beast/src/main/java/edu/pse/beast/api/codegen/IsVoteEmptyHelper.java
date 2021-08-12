package edu.pse.beast.api.codegen;

import java.util.Map;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.loopbound.CodeGenLoopBoundHandler;
import edu.pse.beast.api.codegen.template.vote.CodeTemplateVoteEmpty;
import edu.pse.beast.api.method.VotingInputType;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class IsVoteEmptyHelper {
    private static final String GENERATED_VAR = "GENERATED_VAR";
    private static final String TESTED_VAR = "TESTED_VAR";
    private static final String AMOUNT_MEMBER = "AMT_MEMBER";

    public static String generateCode(final String generatedVarName,
                                      final String testedVarName,
                                      final ElectionTypeCStruct votingStruct,
                                      final VotingInputType votingInputType,
                                      final CodeGenOptions options,
                                      final CodeGenLoopBoundHandler loopBoundHandler) {
        final Map<String, String> replacementMap =
                Map.of(GENERATED_VAR, generatedVarName,
                       TESTED_VAR, testedVarName,
                       AMOUNT_MEMBER, votingStruct.getAmountName());
        final CodeTemplateVoteEmpty voteEmpty = new CodeTemplateVoteEmpty();
        final String code = voteEmpty.getTemplate();
        return CodeGenerationToolbox.replacePlaceholders(code, replacementMap);
    }
}
