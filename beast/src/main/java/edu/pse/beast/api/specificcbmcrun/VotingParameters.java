package edu.pse.beast.api.specificcbmcrun;

import java.util.List;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.cbmc.info.CBMCGeneratedCodeInfo;

public interface VotingParameters {

	List<String> generateVoteStructInitCode(ElectionTypeCStruct voteArrStruct,
			ElectionTypeCStruct voteResultStruct, CodeGenOptions options,
			CBMCGeneratedCodeInfo cbmcGeneratedCodeInfo);

	int getAmtVotes();

	String getVotingVarName(int i);
	
	int getV();
	int getC();
	int getS();

}
