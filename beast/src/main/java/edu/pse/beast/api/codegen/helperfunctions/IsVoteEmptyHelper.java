package edu.pse.beast.api.codegen.helperfunctions;

import java.util.Map;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.code_template.templates.vote.CodeTemplateVoteEmpty;
import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;
import edu.pse.beast.api.descr.c_electiondescription.VotingInputTypes;

public class IsVoteEmptyHelper {
    private static final String GENERATED_VAR = "GENERATED_VAR";
    private static final String TESTED_VAR = "TESTED_VAR";
    private static final String AMOUNT_MEMBER = "AMT_MEMBER";

    public static String generateCode(final String generatedVarName,
                                      final String testedVarName,
                                      final ElectionTypeCStruct votingStruct,
                                      final VotingInputTypes votingInputType,
                                      final CodeGenOptions options,
                                      final CodeGenLoopBoundHandler loopBoundHandler,
                                      final Class<?> c) {
        final Map<String, String> replacementMap =
                Map.of(GENERATED_VAR, generatedVarName,
                       TESTED_VAR, testedVarName,
                       AMOUNT_MEMBER, votingStruct.getAmtName());
        final String code = CodeTemplateVoteEmpty.getTemplate(c);
        return CodeGenerationToolbox.replacePlaceholders(code, replacementMap);
    }
}
