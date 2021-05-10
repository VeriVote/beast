package edu.pse.beast.api.electiondescription;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import edu.pse.beast.api.codegen.loopbounds.FunctionAlreadyContainsLoopboundAtIndexException;
import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundHandler;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundType;
import edu.pse.beast.api.electiondescription.function.SimpleTypeFunction;
import edu.pse.beast.api.electiondescription.function.VotingSigFunction;

public class CElectionDescription {
	private List<VotingSigFunction> votingSigFunctions = new ArrayList<>();
	private List<SimpleTypeFunction> simpleTypeFunctions = new ArrayList<>();
	private Set<String> functionNames = new HashSet<>();

	private LoopBoundHandler loopBoundHandler = new LoopBoundHandler();
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
		return loopBoundHandler.getLoopBoundsForFunction(name);
	}

	public void addLoopBoundForFunction(String functionName, int loopIndex,
			LoopBoundType type, Optional<Integer> manualLoopBoundIfPresent)
			throws FunctionAlreadyContainsLoopboundAtIndexException {
		loopBoundHandler.addLoopBoundForFunction(functionName, loopIndex, type,
				manualLoopBoundIfPresent);
	}

	public void removeLoopBoundForFunction(String functionName,
			LoopBound loopBound) {
		loopBoundHandler.removeLoopBoundForFunction(functionName, loopBound);
	}
	
	public void setLoopBoundHandler(LoopBoundHandler loopBoundHandler) {
		this.loopBoundHandler = loopBoundHandler;
	}
	
	public LoopBoundHandler getLoopBoundHandler() {
		return loopBoundHandler;
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

	public String getUuid() {
		return uuid;
	}
}
