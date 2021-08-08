package edu.pse.beast.api.codegen.helperfunctions.init_vote;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.cbmc.generated_code_info.CBMCGeneratedCodeInfo;
import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;
import edu.pse.beast.api.descr.c_electiondescription.VotingInputTypes;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public abstract class InitVoteHelper {

    public static String getVoteVarName(final CodeGenOptions options, final int voteNumber) {
        return options.getCurrentVotesVarName() + voteNumber;
    }

    public static String getCurrentAmtVoter(final CodeGenOptions options, final int voteNumber) {
        return options.getCurrentAmountVotersVarName() + voteNumber;
    }

    public static String getCurrentAmtCand(final CodeGenOptions options, final int voteNumber) {
        return options.getCurrentAmountCandsVarName() + voteNumber;
    }

    public static String getCurrentAmtSeat(final CodeGenOptions options, final int voteNumber) {
        return options.getCurrentAmountSeatsVarName() + voteNumber;
    }

    public abstract String generateCode(int voteNumber,
                                        ElectionTypeCStruct voteArrStruct,
                                        VotingInputTypes votingInputType,
                                        CodeGenOptions options,
                                        CodeGenLoopBoundHandler loopBoundHandler,
                                        CBMCGeneratedCodeInfo codeInfo);

    public abstract int getHighestVote();
}
