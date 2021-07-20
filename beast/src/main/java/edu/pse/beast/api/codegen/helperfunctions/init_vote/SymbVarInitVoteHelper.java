package edu.pse.beast.api.codegen.helperfunctions.init_vote;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.NotImplementedException;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.cbmc.StringReplacementMap;
import edu.pse.beast.api.codegen.cbmc.generated_code_info.CBMCGeneratedCodeInfo;
import edu.pse.beast.api.codegen.code_template.templates.CodeTemplateInitVote;
import edu.pse.beast.api.codegen.helperfunctions.CodeGenerationToolbox;
import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;
import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.descr.c_electiondescription.VotingInputTypes;

public class SymbVarInitVoteHelper extends InitVoteHelper {

    public String generateCode(final int voteNumber,
                               final ElectionTypeCStruct voteArrStruct,
                               final VotingInputTypes votingInputType,
                               final CodeGenOptions options,
                               final CodeGenLoopBoundHandler loopBoundHandler,
                               final CBMCGeneratedCodeInfo codeInfo) {
        final String varName = getVoteVarName(voteNumber);
        codeInfo.addVotingVariableName(voteNumber, varName);

        final String currentAmtVoterVarName = getCurrentAmtVoter(voteNumber);
        final String currentAmtCandVarName = getCurrentAmtCand(voteNumber);
        final String currentAmtSeatVarName = getCurrentAmtSeat(voteNumber);

        final Map<String, String> replacementMap =
                StringReplacementMap.genMap(
                        "MAX_AMT_CAND", options.getCbmcAmountMaxCandsVarName(),
                        "MAX_AMT_VOTER", options.getCbmcAmountMaxVotersVarName(),
                        "MAX_AMT_SEAT", options.getCbmcAmountMaxSeatsVarName(),
                        "CURRENT_AMT_VOTER", currentAmtVoterVarName,
                        "CURRENT_AMT_CAND", currentAmtCandVarName,
                        "CURRENT_AMT_SEAT", currentAmtSeatVarName,
                        "VOTE_TYPE", voteArrStruct.getStruct().getName(),
                        "AMT_MEMBER", voteArrStruct.getAmtName(),
                        "VOTE_NUMBER", String.valueOf(voteNumber),
                        "LIST_MEMBER", voteArrStruct.getListName(),
                        "VAR_NAME", varName,
                        "ASSUME", options.getCbmcAssumeName(),
                        "NONDET_UINT", options.getCbmcNondetUintName());
        String code = null;
        List<LoopBound> loopbounds = List.of();

        switch (votingInputType) {
        case APPROVAL:
            code = CodeTemplateInitVote.TEMPLATE_APPROVAL;
            loopbounds = CodeTemplateInitVote.LOOP_BOUNDS_APPROVAL;
            break;
        case WEIGHTED_APPROVAL:
            throw new NotImplementedException();
        case PREFERENCE:
            code = CodeTemplateInitVote.TEMPLATE_PREFERENCE;
            loopbounds = CodeTemplateInitVote.LOOP_BOUNDS_PREFERENCE;
            break;
        case SINGLE_CHOICE:
            code = CodeTemplateInitVote.TEMPLATE_SINGLE_CHOICE;
            loopbounds = CodeTemplateInitVote.LOOP_BOUNDS_SINGLE_CHOICE;
            break;
        case SINGLE_CHOICE_STACK:
            throw new NotImplementedException();
        default:
        }

        loopBoundHandler.pushMainLoopBounds(loopbounds);
        return CodeGenerationToolbox.replacePlaceholders(code, replacementMap);
    }

    @Override
    public int getHighestVote() {
        return 0;
    }
}
