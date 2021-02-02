package edu.pse.beast.api.codegen.helperfunctions;

import java.util.List;

import edu.pse.beast.api.codegen.c_code.CFunction;

public abstract class HelperFunction {
	protected HelperFunctionTypes type;

	public HelperFunction(HelperFunctionTypes type) {
		this.type = type;
	}

	public abstract String uniqueName();

	public abstract CFunction cfunc(HelperFunctionMap funcMap);

	public abstract List<HelperFunction> dependencies();
	
	public HelperFunctionTypes getType() {
		return type;
	}
}
