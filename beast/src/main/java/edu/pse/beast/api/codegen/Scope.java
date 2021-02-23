package edu.pse.beast.api.codegen;

import java.util.HashMap;
import java.util.Map;

import edu.pse.beast.api.codegen.CBMCVar.CBMCVarType;

public class Scope {
	private Map<String, CBMCVar.CBMCVarType> containedVars = new HashMap<>();

	public void add(CBMCVar var) {
		containedVars.put(var.getName(), var.getVarType());
	}

	public CBMCVarType getType(String name) {
		if(containedVars.containsKey(name)) {
			return containedVars.get(name);
		}
		return null;
	}
	
}
