package edu.pse.beast.api.codegen.helperfunctions;

import java.util.Map;

import org.apache.commons.lang3.NotImplementedException;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.code_template.templates.elect.CodeTemplateElectEmpty;
import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;
import edu.pse.beast.api.descr.c_electiondescription.VotingOutputTypes;

public class IsElectEmptyHelper {
    public static String generateCode(final String generatedVarName,
                                      final String testedVarName,
                                      final ElectionTypeCStruct electStruct,
                                      final VotingOutputTypes votingOutputType,
                                      final CodeGenOptions options,
                                      final CodeGenLoopBoundHandler loopBoundHandler) {
        final Map<String, String> replacementMap =
                Map.of("GENERATED_VAR", generatedVarName,
                       "TESTED_VAR", testedVarName,
                       "AMT_MEMBER", electStruct.getAmtName());

        String code = null;
        switch (votingOutputType) {
        case CANDIDATE_LIST:
            code = CodeTemplateElectEmpty.getTemplateCandidateList();
            break;
        case PARLIAMENT:
            code = CodeTemplateElectEmpty.getTemplateParliament();
            break;
        case PARLIAMENT_STACK:
            throw new NotImplementedException();
        case SINGLE_CANDIDATE:
            throw new NotImplementedException();
        default:
        }

        return CodeGenerationToolbox.replacePlaceholders(code, replacementMap);
    }
}
