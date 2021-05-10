package edu.pse.beast.api.codegen.loopbounds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
		list.add(new LoopBound(loopBoundType, list.size() - 1, funcname));
	}

	public void pushLoopBounds(String funcname,
			List<LoopBoundType> loopboundTypes) {
		if (!funcNameToLoopbounds.containsKey(funcname)) {
			funcNameToLoopbounds.put(funcname, new ArrayList<>());
		}
		for (LoopBoundType t : loopboundTypes) {
			pushLoopBound(funcname, t);
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

	public void addLoopBoundForFunction(String functionName, int loopIndex,
			LoopBoundType loopBoundType,
			Optional<Integer> manualLoopBoundIfPresent)
			throws FunctionAlreadyContainsLoopboundAtIndexException {
		if (!funcNameToLoopbounds.containsKey(functionName)) {
			funcNameToLoopbounds.put(functionName, new ArrayList<>());
		}
		List<LoopBound> list = funcNameToLoopbounds.get(functionName);
		for (LoopBound lb : list) {
			if (lb.getIndex() == loopIndex) {
				throw new FunctionAlreadyContainsLoopboundAtIndexException(lb);
			}
		}
		list.add(new LoopBound(loopBoundType, loopIndex, functionName,
				manualLoopBoundIfPresent));
		sortLoopBoundListByIndex(list);
	}

	public void removeLoopBoundForFunction(String functionName,
			LoopBound loopBound) {
		if (!funcNameToLoopbounds.containsKey(functionName)) {
			return;
		}
		List<LoopBound> list = funcNameToLoopbounds.get(functionName);
		list.removeIf(lb -> lb.getIndex() == loopBound.getIndex());
	}
	
	public List<LoopBound> getLoopBoundsAsList() {
		List<LoopBound> list = new ArrayList();
		
		for(List<LoopBound> functionLBs : funcNameToLoopbounds.values()) {
			list.addAll(functionLBs);
		}
		
		return list;
	}

}
