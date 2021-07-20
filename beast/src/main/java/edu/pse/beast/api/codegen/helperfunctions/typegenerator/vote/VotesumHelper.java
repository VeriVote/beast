package edu.pse.beast.api.codegen.helperfunctions.typegenerator.vote;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.NotImplementedException;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.code_template.templates.vote.CodeTemplateVoteSumForCandidate;
import edu.pse.beast.api.codegen.helperfunctions.CodeGenerationToolbox;
import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;
import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.descr.c_electiondescription.VotingInputTypes;

public class VotesumHelper {

    public static String generateCode(final String generatedVarName,
                                      final int voteNumber,
                                      final String symbolicVarCand,
                                      final ElectionTypeCStruct voteStruct,
                                      final VotingInputTypes votingInputType,
                                      final CodeGenOptions options,
                                      final CodeGenLoopBoundHandler loopBoundHandler) {
        final Map<String, String> replaceMap =
                Map.of("GENERATED_VAR", generatedVarName,
                       "AMT_MEMBER", voteStruct.getAmtName(),
                       "LIST_MEMBER", voteStruct.getListName(),
                       "CANDIDATE_VAR", symbolicVarCand,
                       "AMT_VOTERS", options.getCbmcAmountMaxVotersVarName(),
                       "VOTE_VAR", "voteNUMBER".replace("NUMBER", String.valueOf(voteNumber)));
        String code = null;
        List<LoopBound> loopbounds = List.of();

        switch (votingInputType) {
        case APPROVAL:
            throw new NotImplementedException();
        case WEIGHTED_APPROVAL:
            throw new NotImplementedException();
        case PREFERENCE:
            throw new NotImplementedException();
        case SINGLE_CHOICE:
            code = CodeTemplateVoteSumForCandidate.getTemplateSingleChoice();
            break;
        case SINGLE_CHOICE_STACK:
            code = CodeTemplateVoteSumForCandidate.getTemplateSingleChoiceStack();
            loopbounds = CodeTemplateVoteSumForCandidate.LOOP_BOUNDS_PREFERENCE;
            break;
        default:
        }

        loopBoundHandler.pushMainLoopBounds(loopbounds);
        return CodeGenerationToolbox.replacePlaceholders(code, replaceMap);
    }
}
