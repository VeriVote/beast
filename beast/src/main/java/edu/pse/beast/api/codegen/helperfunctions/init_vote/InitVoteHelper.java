package edu.pse.beast.api.codegen.helperfunctions.init_vote;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.cbmc.generated_code_info.CBMCGeneratedCodeInfo;
import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;
import edu.pse.beast.api.descr.c_electiondescription.VotingInputTypes;

public abstract class InitVoteHelper {
    public static String getVoteVarName(final int voteNumber) {
        return "votes" + voteNumber;
    }

    public static String getCurrentAmtVoter(final int voteNumber) {
        return "V" + voteNumber;
    }

    public static String getCurrentAmtCand(final int voteNumber) {
        return "C" + voteNumber;
    }

    public static String getCurrentAmtSeat(final int voteNumber) {
        return "S" + voteNumber;
    }

    public abstract String generateCode(int voteNumber,
                                        ElectionTypeCStruct voteArrStruct,
                                        VotingInputTypes votingInputType,
                                        CodeGenOptions options,
                                        CodeGenLoopBoundHandler loopBoundHandler,
                                        CBMCGeneratedCodeInfo codeInfo);

    public abstract int getHighestVote();
}
