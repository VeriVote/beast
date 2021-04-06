package edu.pse.beast.api.electiondescription;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.pse.beast.api.codegen.loopbounds.LoopBound;

public class CElectionDescription {
	private List<VotingSigFunction> votingSigFunctions = new ArrayList<>();
	private List<SimpleTypeFunction> simpleTypeFunctions = new ArrayList<>();
	private Map<String, List<LoopBound>> loopBounds = new HashMap();
	private String name;

	private VotingSigFunction votingFunction;

	private VotingInputTypes inputType;
	private VotingOutputTypes outputType;

	public CElectionDescription(VotingInputTypes inputType,
			VotingOutputTypes outputType, String name) {
		this.inputType = inputType;
		this.outputType = outputType;
		this.name = name;
		votingFunction = new VotingSigFunction("voting", inputType, outputType);
	}

	public VotingSigFunction getVotingFunction() {
		return votingFunction;
	}

	public void setVotingFunction(VotingSigFunction votingFunction) {
		this.votingFunction = votingFunction;
	}

	public VotingSigFunction createNewVotingSigFunctionAndAdd(String name) {
		VotingSigFunction created = new VotingSigFunction(name, inputType,
				outputType);
		votingSigFunctions.add(created);
		return created;
	}

	public VotingInputTypes getInputType() {
		return inputType;
	}

	public VotingOutputTypes getOutputType() {
		return outputType;
	}

	public List<VotingSigFunction> getVotingSigFunctions() {
		return votingSigFunctions;
	}

	public List<LoopBound> getLoopBoundsForFunction(String name) {
		return loopBounds.get(name);
	}

	public void addLoopBoundForFunction(String functionName, String loopIndex,
			String bound) {
		if (!loopBounds.containsKey(functionName)) {
			loopBounds.put(functionName, new ArrayList<>());
		}
		List<LoopBound> list = loopBounds.get(functionName);
		list.add(new LoopBound(functionName, loopIndex, bound));
	}

	public String getName() {
		return name;
	}

}
