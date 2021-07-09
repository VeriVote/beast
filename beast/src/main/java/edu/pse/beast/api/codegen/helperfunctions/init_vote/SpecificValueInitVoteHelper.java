package edu.pse.beast.api.codegen.helperfunctions.init_vote;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.cbmc.generated_code_info.CBMCGeneratedCodeInfo;
import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;
import edu.pse.beast.api.electiondescription.VotingInputTypes;

public class SpecificValueInitVoteHelper extends InitVoteHelper {

	@Override
	public String generateCode(int voteNumber,
			ElectionTypeCStruct voteArrStruct, VotingInputTypes votingInputType,
			CodeGenOptions options, CodeGenLoopBoundHandler loopBoundHandler,
			CBMCGeneratedCodeInfo codeInfo) {
		// TODO Auto-generated method stub
		return null;
	}

}
