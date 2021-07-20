package edu.pse.beast.api.codegen.helperfunctions;

import java.util.Map;

import org.apache.commons.lang3.NotImplementedException;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.code_template.templates.vote.CodeTemplateVoteEmpty;
import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;
import edu.pse.beast.api.descr.c_electiondescription.VotingInputTypes;

public class IsVoteEmptyHelper {
    public static String generateCode(final String generatedVarName,
                                      final String testedVarName,
                                      final ElectionTypeCStruct votingStruct,
                                      final VotingInputTypes votingInputType,
                                      final CodeGenOptions options,
                                      final CodeGenLoopBoundHandler loopBoundHandler) {
        final Map<String, String> replacementMap =
                Map.of("GENERATED_VAR", generatedVarName,
                       "TESTED_VAR", testedVarName,
                       "AMT_MEMBER", votingStruct.getAmtName());

        String code = null;
        switch (votingInputType) {
        case APPROVAL:
            throw new NotImplementedException();
        case WEIGHTED_APPROVAL:
            throw new NotImplementedException();
        case PREFERENCE:
            code = CodeTemplateVoteEmpty.getTemplatePreference();
            break;
        case SINGLE_CHOICE:
            throw new NotImplementedException();
        case SINGLE_CHOICE_STACK:
            throw new NotImplementedException();
        default:
        }
        return CodeGenerationToolbox.replacePlaceholders(code, replacementMap);
    }
}
