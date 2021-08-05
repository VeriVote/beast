package edu.pse.beast.api.codegen.helperfunctions.init_vote;

import edu.pse.beast.api.cbmc_run_with_specific_values.VotingParameters;
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
public class SpecificValueInitVoteHelper extends InitVoteHelper {
    private VotingParameters votingParameters;

    public SpecificValueInitVoteHelper(final VotingParameters votingParams) {
        this.votingParameters = votingParams;
    }

    @Override
    public final String generateCode(final int voteNumber,
                                     final ElectionTypeCStruct voteArrStruct,
                                     final VotingInputTypes votingInputType,
                                     final CodeGenOptions options,
                                     final CodeGenLoopBoundHandler loopBoundHandler,
                                     final CBMCGeneratedCodeInfo codeInfo) {
        final String voteVarName = getVoteVarName(voteNumber);
        return votingParameters.genVoteStructInitCode(voteArrStruct,
                                                           options, codeInfo,
                                                           voteVarName);
    }

    @Override
    public final int getHighestVote() {
        return votingParameters.getHighestVote();
    }
}
