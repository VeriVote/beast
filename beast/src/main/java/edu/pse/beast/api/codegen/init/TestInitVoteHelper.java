package edu.pse.beast.api.codegen.init;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.cbmc.info.GeneratedCodeInfo;
import edu.pse.beast.api.codegen.loopbound.CodeGenLoopBoundHandler;
import edu.pse.beast.api.method.VotingInputTypes;
import edu.pse.beast.api.test.VotingParameters;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class TestInitVoteHelper extends InitVoteHelper {
    private VotingParameters votingParameters;

    public TestInitVoteHelper(final VotingParameters votingParams) {
        this.votingParameters = votingParams;
    }

    @Override
    public final String generateCode(final int voteNumber,
                                     final ElectionTypeCStruct voteArrStruct,
                                     final VotingInputTypes votingInputType,
                                     final CodeGenOptions options,
                                     final CodeGenLoopBoundHandler loopBoundHandler,
                                     final GeneratedCodeInfo codeInfo) {
        final String voteVarName = getVoteVarName(options, voteNumber);
        return votingParameters.genVoteStructInitCode(voteArrStruct, options,
                                                      codeInfo, voteVarName);
    }

    @Override
    public final int getLastElectionNumber() {
        return votingParameters.getLastElectionNumber();
    }
}
