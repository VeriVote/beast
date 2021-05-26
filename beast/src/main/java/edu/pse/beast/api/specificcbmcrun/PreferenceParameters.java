package edu.pse.beast.api.specificcbmcrun;

import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.cbmc.info.CBMCGeneratedCodeInfo;

public class PreferenceParameters implements VotingParameters {

	private int V = 0;
	private int C;
	private int S = 0;

	private List<List<Integer>> votesPerVoter = new ArrayList<>();

	public PreferenceParameters(int c) {
		this.C = c;
	}

	public void addVoter(List<Integer> preferenceVotes) {
		votesPerVoter.add(preferenceVotes);
		this.V++;
	}

	private final String votingDeclTemplate = "VOTE_STRUCT_TYPE VOTE_VAR_NAME;";
	private final String amtVotesTemplate = "VOTE_VAR_NAME.AMT_MEMBER = AMT_VOTES;";
	private final String votesPerVoterTemplate = "VOTE_VAR_NAME.LIST_MEMBER[LIST_INDEX][CAND_INDEX] = VOTE_GIVEN;";

	@Override
	public List<String> generateVoteStructInitCode(
			ElectionTypeCStruct voteInputStruct,
			ElectionTypeCStruct voteResultStruct, CodeGenOptions options,
			CBMCGeneratedCodeInfo cbmcGeneratedCodeInfo) {
		List<String> code = new ArrayList<>();

		String votingVarName = "votes";
		String votingStructName = voteInputStruct.getStruct().getName();
		String votingStructDeclString = votingDeclTemplate
				.replaceAll("VOTE_STRUCT_TYPE", votingStructName)
				.replaceAll("VOTE_VAR_NAME", votingVarName);

		code.add(votingStructDeclString);

		String amtDeclString = amtVotesTemplate
				.replaceAll("VOTE_VAR_NAME", votingVarName)
				.replaceAll("AMT_MEMBER", voteInputStruct.getAmtName())
				.replaceAll("AMT_VOTES", String.valueOf(V));

		code.add(amtDeclString);

		for (int i = 0; i < votesPerVoter.size(); ++i) {
			for (int j = 0; j < C; ++j) {
				String voteString = votesPerVoterTemplate
						.replaceAll("VOTE_VAR_NAME", votingVarName)
						.replaceAll("LIST_MEMBER",
								voteInputStruct.getListName())
						.replaceAll("LIST_INDEX", String.valueOf(i))
						.replaceAll("CAND_INDEX", String.valueOf(j))
						.replaceAll("VOTE_GIVEN",
								String.valueOf(votesPerVoter.get(i).get(j)));

				code.add(voteString);
			}
		}

		return code;
	}

	@Override
	public int getAmtVotes() {
		return 1;
	}

	@Override
	public String getVotingVarName(int i) {
		return "votes";
	}
	
	@Override
	public int getV() {
		return V;
	}
	
	@Override
	public int getC() {
		return C;
	}
	
	@Override
	public int getS() {
		return S;
	}

}
