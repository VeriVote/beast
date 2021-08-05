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
    private static final String VOTES = "votes";

    private static final String VOTER_SYMBOL = "V";
    private static final String CAND_SYMBOL = "C";
    private static final String SEAT_SYMBOL = "S";

    public static String getVoteVarName(final int voteNumber) {
        return VOTES + voteNumber;
    }

    public static String getCurrentAmtVoter(final int voteNumber) {
        return VOTER_SYMBOL + voteNumber;
    }

    public static String getCurrentAmtCand(final int voteNumber) {
        return CAND_SYMBOL + voteNumber;
    }

    public static String getCurrentAmtSeat(final int voteNumber) {
        return SEAT_SYMBOL + voteNumber;
    }

    public abstract String generateCode(int voteNumber,
                                        ElectionTypeCStruct voteArrStruct,
                                        VotingInputTypes votingInputType,
                                        CodeGenOptions options,
                                        CodeGenLoopBoundHandler loopBoundHandler,
                                        CBMCGeneratedCodeInfo codeInfo);

    public abstract int getHighestVote();
}
