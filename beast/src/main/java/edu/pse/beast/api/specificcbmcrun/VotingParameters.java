package edu.pse.beast.api.specificcbmcrun;

import java.util.List;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.cbmc.generated_code_info.CBMCGeneratedCodeInfo;

public interface VotingParameters {
	int getV();

	int getC();

	int getS();

	public String generateVoteStructInitCode(
			ElectionTypeCStruct voteInputStruct, CodeGenOptions options,
			CBMCGeneratedCodeInfo cbmcGeneratedCodeInfo,
			String generatedVarName);

	int getHighestVote();
}
