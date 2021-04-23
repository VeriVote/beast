package edu.pse.beast.api.electiondescription;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import edu.pse.beast.api.codegen.loopbounds.LoopBound;

public class CElectionDescription {
	private List<VotingSigFunction> votingSigFunctions = new ArrayList<>();
	private List<SimpleTypeFunction> simpleTypeFunctions = new ArrayList<>();
	private Set<String> functionNames = new HashSet<>();

	private Map<String, List<LoopBound>> loopBounds = new HashMap();
	private String name;
	private String uuid;

	private VotingSigFunction votingFunction;

	private VotingInputTypes inputType;
	private VotingOutputTypes outputType;

	public CElectionDescription(VotingInputTypes inputType,
			VotingOutputTypes outputType, String name) {
		this.inputType = inputType;
		this.outputType = outputType;
		this.name = name;
		votingFunction = new VotingSigFunction("voting", inputType, outputType);
		this.uuid = UUID.randomUUID().toString();
	}

	public CElectionDescription(String uuid, String name,
			VotingInputTypes inputType, VotingOutputTypes outputType) {
		this.inputType = inputType;
		this.outputType = outputType;
		this.name = name;
		votingFunction = new VotingSigFunction("voting", inputType, outputType);
		this.uuid = uuid;
	}

	public VotingSigFunction getVotingFunction() {
		return votingFunction;
	}

	public void setVotingFunction(VotingSigFunction votingFunction) {
		this.votingFunction = votingFunction;
		functionNames.add(votingFunction.getName());
	}

	public VotingSigFunction createNewVotingSigFunctionAndAdd(String name) {
		if (functionNames.contains(name)) {
			throw new IllegalArgumentException(
					"function with this name already exists");
		}
		VotingSigFunction created = new VotingSigFunction(name, inputType,
				outputType);
		votingSigFunctions.add(created);
		functionNames.add(name);
		return created;
	}

	@Override
	public String toString() {
		return name;
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
		if (!loopBounds.containsKey(name)) {
			return List.of();
		}
		return loopBounds.get(name);
	}

	private void sortLoopBoundListByIndex(List<LoopBound> list) {
		list.sort((LoopBound lhs, LoopBound rhs) -> {
			return Integer.compare(lhs.getIndex(), rhs.getIndex());
		});
	}

	public void addLoopBoundForFunction(String functionName, int loopIndex,
			String bound) {
		if (!loopBounds.containsKey(functionName)) {
			loopBounds.put(functionName, new ArrayList<>());
		}
		List<LoopBound> list = loopBounds.get(functionName);
		list.add(new LoopBound(functionName, loopIndex, bound));
		sortLoopBoundListByIndex(list);
	}

	public void removeLoopBoundForFunction(String functionName,
			String loopBoundString) {
		if (!loopBounds.containsKey(functionName)) {
			return;
		}
		List<LoopBound> list = loopBounds.get(functionName);
		list.removeIf((LoopBound b) -> {
			boolean rm = b.toString().equals(loopBoundString);
			return rm;
		});
		loopBounds.put(functionName, list);
	}

	public String getName() {
		return name;
	}

	public void removeFunction(String functionName) {
		if (votingFunction.getName().equals(functionName))
			return;
		functionNames.remove(functionName);
		votingSigFunctions.removeIf(f -> f.getName().equals(functionName));
		simpleTypeFunctions.removeIf(f -> f.getName().equals(functionName));
	}

	public List<LoopBound> getLoopBounds() {
		List<LoopBound> created = new ArrayList<>();
		for (String k : loopBounds.keySet()) {
			created.addAll(loopBounds.get(k));
		}
		return created;
	}

	public void setLoopBounds(List<LoopBound> loopBoundsFromJsonArray) {
		for (LoopBound b : loopBoundsFromJsonArray) {
			String functionName = b.getFunctionName();
			if (loopBounds.containsKey(functionName)) {
				loopBounds.put(functionName, Arrays.asList());
			}
			loopBounds.get(functionName).add(b);
		}
	}

	public String getUuid() {
		return uuid;
	}
}
