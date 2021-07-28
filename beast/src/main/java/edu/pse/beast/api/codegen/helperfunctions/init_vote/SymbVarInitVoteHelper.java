package edu.pse.beast.api.codegen.helperfunctions.init_vote;

import java.util.List;
import java.util.Map;

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
    private static final String MAX_AMOUNT_CAND = "MAX_AMT_CAND";
    private static final String MAX_AMOUNT_VOTER = "MAX_AMT_VOTER";
    private static final String MAX_AMOUNT_SEAT = "MAX_AMT_SEAT";
    private static final String CURRENT_AMOUNT_VOTER = "CURRENT_AMT_VOTER";
    private static final String CURRENT_AMOUNT_CAND = "CURRENT_AMT_CAND";
    private static final String CURRENT_AMOUNT_SEAT = "CURRENT_AMT_SEAT";
    private static final String VOTE_TYPE = "VOTE_TYPE";
    private static final String AMOUNT_MEMBER = "AMT_MEMBER";
    private static final String VOTE_NUMBER = "VOTE_NUMBER";
    private static final String LIST_MEMBER = "LIST_MEMBER";
    private static final String VAR_NAME = "VAR_NAME";
    private static final String ASSUME = "ASSUME";
    private static final String NONDET_UINT = "NONDET_UINT";

    public final String generateCode(final int voteNumber,
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
                        MAX_AMOUNT_CAND, options.getCbmcAmountMaxCandsVarName(),
                        MAX_AMOUNT_VOTER, options.getCbmcAmountMaxVotersVarName(),
                        MAX_AMOUNT_SEAT, options.getCbmcAmountMaxSeatsVarName(),
                        CURRENT_AMOUNT_VOTER, currentAmtVoterVarName,
                        CURRENT_AMOUNT_CAND, currentAmtCandVarName,
                        CURRENT_AMOUNT_SEAT, currentAmtSeatVarName,
                        VOTE_TYPE, voteArrStruct.getStruct().getName(),
                        AMOUNT_MEMBER, voteArrStruct.getAmtName(),
                        VOTE_NUMBER, String.valueOf(voteNumber),
                        LIST_MEMBER, voteArrStruct.getListName(),
                        VAR_NAME, varName,
                        ASSUME, options.getCbmcAssumeName(),
                        NONDET_UINT, options.getCbmcNondetUintName());
        final String code = CodeTemplateInitVote.getTemplate(votingInputType, this.getClass());
        final List<LoopBound> loopbounds = CodeTemplateInitVote.getLoopBounds(votingInputType);

        loopBoundHandler.pushMainLoopBounds(loopbounds);
        return CodeGenerationToolbox.replacePlaceholders(code, replacementMap);
    }

    @Override
    public final int getHighestVote() {
        return 0;
    }
}
