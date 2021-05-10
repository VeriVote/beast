package edu.pse.beast.api.codegen.cbmc;

import java.util.HashMap;
import java.util.Map;

import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar.CBMCVarType;

public class Scope {
	private Map<String, SymbolicCBMCVar.CBMCVarType> containedVars = new HashMap<>();

	public void add(SymbolicCBMCVar var) {
		containedVars.put(var.getName(), var.getVarType());
	}

	public CBMCVarType getType(String name) {
		if(containedVars.containsKey(name)) {
			return containedVars.get(name);
		}
		return null;
	}
	
}
