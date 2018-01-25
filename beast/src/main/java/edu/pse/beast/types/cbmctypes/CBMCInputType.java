package edu.pse.beast.types.cbmctypes;

import java.util.List;

import edu.pse.beast.types.InputType;

public abstract class CBMCInputType extends InputType {

	@Override
	protected void getHelper() {
		super.helper = new CbmcHelpMethods();
	}
	
	@Override
	public List<String> addCheckerSpecificHeaders(List<String> code) {
		// add the headers CBMC needs;
		code.add("#include <stdlib.h>");
		code.add("#include <stdint.h>");
		code.add("#include <assert.h>");
		code.add("");
		code.add("unsigned int nondet_uint();");
		code.add("int nondet_int();");
		code.add("");
		code.add("#define assert2(x, y) __CPROVER_assert(x, y)");
		code.add("#define assume(x) __CPROVER_assume(x)");
		code.add("");
		return code;
	}
}
