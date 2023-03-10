package edu.kit.kastel.formal.beast.api.codegen.init;

import edu.kit.kastel.formal.beast.api.codegen.cbmc.CodeGenOptions;
import edu.kit.kastel.formal.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.kit.kastel.formal.beast.api.codegen.cbmc.info.GeneratedCodeInfo;
import edu.kit.kastel.formal.beast.api.codegen.loopbound.CodeGenLoopBoundHandler;
import edu.kit.kastel.formal.beast.api.method.VotingInputType;
import edu.kit.kastel.formal.beast.api.test.VotingParameters;

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
                                     final VotingInputType votingInputType,
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
