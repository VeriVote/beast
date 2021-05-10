package edu.pse.beast.api.electiondescription;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import edu.pse.beast.api.codegen.c_code.CFunction;
import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundHandler;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundType;
import edu.pse.beast.api.electiondescription.function.CElectionDescriptionFunction;
import edu.pse.beast.api.electiondescription.function.VotingSigFunction;

public class CElectionDescription {
	private List<CElectionDescriptionFunction> functions = new ArrayList<>();
	private Set<String> functionNames = new HashSet<>();
	private VotingSigFunction votingFunction;

	private LoopBoundHandler loopBoundHandler = new LoopBoundHandler();
	private String name;
	private String uuid;

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

	public boolean hasFunctionName(String name) {
		return functionNames.contains(name);
	}

	public VotingSigFunction getVotingFunction() {
		return votingFunction;
	}

	public void setVotingFunction(VotingSigFunction votingFunction) {
		this.votingFunction = votingFunction;
		functionNames.add(votingFunction.getName());
	}

	public VotingSigFunction createNewVotingSigFunctionAndAdd(String name) {
		VotingSigFunction created = new VotingSigFunction(name, inputType,
				outputType);
		functions.add(created);
		functionNames.add(name);
		return created;
	}

	public void removeFunction(CElectionDescriptionFunction func) {
		functionNames.remove(func.getName());
		functions.remove(func);
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

	public List<LoopBound> getLoopBoundsForFunction(CElectionDescriptionFunction func) {
		return loopBoundHandler.getLoopBoundsForFunction(func.getName());
	}

	public void addLoopBoundForFunction(String functionName, int loopIndex,
			LoopBoundType type, Optional<Integer> manualLoopBoundIfPresent) {
		loopBoundHandler.addLoopBoundForFunction(functionName, loopIndex, type,
				manualLoopBoundIfPresent);
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

	public List<CElectionDescriptionFunction> getFunctions() {
		return functions;
	}

	public String getUuid() {
		return uuid;
	}
}
