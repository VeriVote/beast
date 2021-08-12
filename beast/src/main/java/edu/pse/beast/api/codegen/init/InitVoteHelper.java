package edu.pse.beast.api.codegen.init;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.cbmc.info.GeneratedCodeInfo;
import edu.pse.beast.api.codegen.loopbound.CodeGenLoopBoundHandler;
import edu.pse.beast.api.method.VotingInputTypes;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public abstract class InitVoteHelper {
    private static final int INDEX_OF_FIRST_ELECTION = 1;

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
                                        GeneratedCodeInfo codeInfo);

    public static int getFirstElectionNumber() {
        return INDEX_OF_FIRST_ELECTION;
    }

    public abstract int getLastElectionNumber();
}