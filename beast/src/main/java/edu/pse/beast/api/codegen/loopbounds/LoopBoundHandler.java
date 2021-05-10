package edu.pse.beast.api.codegen.loopbounds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.electiondescription.function.CElectionDescriptionFunction;

public class LoopBoundHandler {

	private Map<String, List<LoopBound>> funcNameToLoopbounds = new HashMap<>();

	public Map<String, List<LoopBound>> getFuncNameToLoopbounds() {
		return funcNameToLoopbounds;
	}

	public void setFuncNameToLoopbounds(
			Map<String, List<LoopBound>> funcNameToLoopbounds) {
		this.funcNameToLoopbounds = funcNameToLoopbounds;
	}

	public void pushLoopBound(String funcname, LoopBoundType loopBoundType) {
		if (!funcNameToLoopbounds.containsKey(funcname)) {
			funcNameToLoopbounds.put(funcname, new ArrayList<>());
		}
		List<LoopBound> list = funcNameToLoopbounds.get(funcname);
		list.add(new LoopBound(loopBoundType, list.size(), funcname));
	}

	public void pushMainLoopBounds(List<LoopBoundType> loopboundTypes) {
		String funcName = CodeGenOptions.MAIN_FUNC_NAME;

		if (!funcNameToLoopbounds.containsKey(funcName)) {
			funcNameToLoopbounds.put(funcName, new ArrayList<>());
		}
		for (LoopBoundType t : loopboundTypes) {
			pushLoopBound(funcName, t);
		}
	}

	private void sortLoopBoundListByIndex(List<LoopBound> list) {
		list.sort((LoopBound lhs, LoopBound rhs) -> {
			return Integer.compare(lhs.getIndex(), rhs.getIndex());
		});
	}

	public List<LoopBound> getLoopBoundsForFunction(String functionName) {
		if (funcNameToLoopbounds.containsKey(functionName)) {
			return funcNameToLoopbounds.get(functionName);
		} else {
			return List.of();
		}
	}

	public LoopBound addLoopBoundForFunction(String functionName, int loopIndex,
			LoopBoundType loopBoundType,
			Optional<Integer> manualLoopBoundIfPresent){
		if (!funcNameToLoopbounds.containsKey(functionName)) {
			funcNameToLoopbounds.put(functionName, new ArrayList<>());
		}
		List<LoopBound> list = funcNameToLoopbounds.get(functionName);
		for (LoopBound lb : list) {
			if (lb.getIndex() >= loopIndex) {
				lb.incrementIndex();
			}
		}
		LoopBound lb = new LoopBound(loopBoundType, loopIndex, functionName,
				manualLoopBoundIfPresent);
		list.add(lb);
		sortLoopBoundListByIndex(list);
		return lb;
	}

	public void removeLoopBoundForFunction(
			CElectionDescriptionFunction func,
			LoopBound loopBound) {
		if (!funcNameToLoopbounds.containsKey(func.getName())) {
			return;
		}
		List<LoopBound> list = funcNameToLoopbounds.get(func.getName());		
		list.removeIf(lb -> lb.getIndex() == loopBound.getIndex());
		for (LoopBound lb : list) {
			if (lb.getIndex() >= loopBound.getIndex()) {
				lb.decrementIndex();
			}
		}
	}

	public List<LoopBound> getLoopBoundsAsList() {
		List<LoopBound> list = new ArrayList();

		for (List<LoopBound> functionLBs : funcNameToLoopbounds.values()) {
			list.addAll(functionLBs);
		}

		return list;
	}

	public void pushVotingLoopBounds(String votingFunctionName,
			List<LoopBoundType> loopbounds) {
		if (!funcNameToLoopbounds.containsKey(votingFunctionName)) {
			funcNameToLoopbounds.put(votingFunctionName, new ArrayList<>());
		}
		List<LoopBound> list = funcNameToLoopbounds.get(votingFunctionName);
		for(LoopBoundType type : loopbounds) {
			list.add(new LoopBound(type, list.size(), votingFunctionName));
		}
	}

	public void addVotingInitLoopBounds(String votingFunctionName,
			List<LoopBoundType> loopbounds) {
		if (!funcNameToLoopbounds.containsKey(votingFunctionName)) {
			funcNameToLoopbounds.put(votingFunctionName, new ArrayList<>());
		}
		List<LoopBound> list = funcNameToLoopbounds.get(votingFunctionName);
		int idx = 0;
		for(LoopBoundType type : loopbounds) {
			addLoopBoundForFunction(
					votingFunctionName, 
					idx, 
					type,
					Optional.empty());
			idx++;
		}
	}

}
