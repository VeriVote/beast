package edu.pse.beast.api.codegen.loopbounds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.electiondescription.function.CElectionDescriptionFunction;

public class LoopBoundHandler {

	private Map<String, List<LoopBound>> functionNamesToLoopbounds = new HashMap<>();

	public void addLoopBoundToFunction(LoopBound b, String funcName) {
		if (!functionNamesToLoopbounds.containsKey(funcName)) {
			functionNamesToLoopbounds.put(funcName, new ArrayList<>());
		}
		functionNamesToLoopbounds.get(funcName).add(b);
	}

}
