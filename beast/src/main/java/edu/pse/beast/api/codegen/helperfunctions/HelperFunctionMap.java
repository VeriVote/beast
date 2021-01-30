package edu.pse.beast.api.codegen.helperfunctions;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class HelperFunctionMap {
	private Map<String, HelperFunction> generated = new HashMap<>();

	public void add(HelperFunction func) {
		generated.put(func.uniqueName(), func);
	}

	public Collection<HelperFunction> values() {
		return generated.values();
	}
}
