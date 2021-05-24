package edu.pse.beast.api.codegen.loopbounds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.electiondescription.function.CElectionDescriptionFunction;

public class CodeGenLoopBoundHandler {

	private Map<String, List<LoopBound>> functionNamesToLoopbounds = new HashMap<>();
	private List<LoopBoundType> mainLoopbounds = new ArrayList<>();
	private Map<String, List<LoopBoundType>> votingInitLoopbounds = new HashMap<>();
	private Map<String, List<LoopBoundType>> votingBackLoopbounds = new HashMap<>();

	public void addLoopBound(LoopBound b) {
		String funcName = b.getFunctionName();
		if (!functionNamesToLoopbounds.containsKey(funcName)) {
			functionNamesToLoopbounds.put(funcName, new ArrayList<>());
		}
		functionNamesToLoopbounds.get(funcName).add(b);
	}

	public List<LoopBound> getLoopBoundsAsList() {
		List<LoopBound> loopbounds = new ArrayList<>();
		for (List<LoopBound> lbl : functionNamesToLoopbounds.values()) {
			loopbounds.addAll(lbl);
		}
		return loopbounds;
	}

	public void pushMainLoopBounds(List<LoopBoundType> loopbounds) {
		mainLoopbounds.addAll(loopbounds);
	}

	public void addVotingInitLoopBounds(String votingFunctionName,
			List<LoopBoundType> loopbounds) {
		votingInitLoopbounds.put(votingFunctionName, loopbounds);
	}

	public void pushVotingLoopBounds(String votingFunctionName,
			List<LoopBoundType> loopbounds) {
		votingBackLoopbounds.put(votingFunctionName, loopbounds);
	}

}
