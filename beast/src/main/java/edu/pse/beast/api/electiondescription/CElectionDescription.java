package edu.pse.beast.api.electiondescription;

import java.util.ArrayList;
import java.util.List;

public class CElectionDescription {
	private List<VotingSigFunction> votingSigFunctions = new ArrayList<>();
	private List<SimpleTypeFunction> simpleTypeFunctions = new ArrayList<>();

	private VotingSigFunction votingFunction;

	CElectionVotingType inputType;
	CElectionVotingType outputType;

	public CElectionDescription(CElectionVotingType inputType, CElectionVotingType outputType,
			String votingFuncName) {
		this.inputType = inputType;
		this.outputType = outputType;
	}

}
