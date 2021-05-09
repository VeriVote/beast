package edu.pse.beast.api.codegen.loopbounds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoopBoundHandler {

	private Map<String, List<LoopBound>> funcNameToLoopbounds = new HashMap<>();

	public void pushLoopBound(String funcname, int index,
			LoopBoundType loopBoundType) {
		if (!funcNameToLoopbounds.containsKey(funcname)) {
			funcNameToLoopbounds.put(funcname, new ArrayList<>());
		}
		funcNameToLoopbounds.get(funcname)
				.add(new LoopBound(loopBoundType, index, funcname));

	}

	public void pushLoopBounds(String funcname, List<LoopBound> loopbounds) {
		if (!funcNameToLoopbounds.containsKey(funcname)) {
			funcNameToLoopbounds.put(funcname, new ArrayList<>());
		}
		funcNameToLoopbounds.get(funcname).addAll(loopbounds);
		
	}

}
