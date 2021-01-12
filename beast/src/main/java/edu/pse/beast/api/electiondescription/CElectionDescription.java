package edu.pse.beast.api.electiondescription;

import java.util.ArrayList;
import java.util.List;

public class CElectionDescription {
	private List<VotingSigFunction> votingSigFunctions = new ArrayList<>();
	private List<SimpleTypeFunction> simpleTypeFunctions = new ArrayList<>();

	private VotingSigFunction votingFunction;

	private VotingInputTypes inputType;
	private VotingOutputTypes outputType;

	public CElectionDescription(VotingInputTypes inputType, VotingOutputTypes outputType) {
		this.inputType = inputType;
		this.outputType = outputType;
		votingFunction = new VotingSigFunction("voting", inputType, outputType);
	}

	public VotingSigFunction getVotingFunction() {
		return votingFunction;
	}	
	
	public VotingSigFunction createNewAndAdd(String name) {
		VotingSigFunction created = new VotingSigFunction(name, inputType, outputType);
		votingSigFunctions.add(created);
		return created;
	}
	

}
