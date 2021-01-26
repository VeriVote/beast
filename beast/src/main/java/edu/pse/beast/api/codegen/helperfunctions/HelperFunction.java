package edu.pse.beast.api.codegen.helperfunctions;

import edu.pse.beast.api.codegen.c_code.CFunction;

public interface HelperFunction {
	public CFunction generateCFunc();

	public String getUUID();
}
