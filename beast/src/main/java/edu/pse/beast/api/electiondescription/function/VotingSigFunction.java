package edu.pse.beast.api.electiondescription.function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.pse.beast.api.electiondescription.VotingInputTypes;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;

public class VotingSigFunction extends CElectionDescriptionFunction {
	private String name;
	private List<String> code = new ArrayList<>();
	VotingInputTypes inputType;
	VotingOutputTypes outputType;

	public VotingSigFunction(String name, VotingInputTypes inputType,
			VotingOutputTypes outputType) {
		super(name);
		this.name = name;
		this.inputType = inputType;
		this.outputType = outputType;
	}

	public String getName() {
		return name;
	}

	public VotingInputTypes getInputType() {
		return inputType;
	}

	public VotingOutputTypes getOutputType() {
		return outputType;
	}

	public String getVotingVarName() {
		return "votes";
	}

	public String getResultVarName() {
		return "result";
	}

}
