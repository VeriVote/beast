package edu.pse.beast.api.specificcbmcrun;

import java.util.List;

import edu.pse.beast.api.codegen.cbmc.CBMCGeneratedCodeInfo;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.ElectionTypeCStruct;

public interface VotingParameters {

	List<String> generateVoteStructInitCode(ElectionTypeCStruct voteArrStruct,
			ElectionTypeCStruct voteResultStruct, CodeGenOptions options,
			CBMCGeneratedCodeInfo cbmcGeneratedCodeInfo);

	int getAmtVotes();

	String getVotingVarName(int i);

}
