package edu.pse.beast.api.codegen;

import java.util.List;

import edu.pse.beast.api.codegen.c_code.CFile;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;

public class CBMCCodeGeneratorNEW {
	
	private final static String STDLIB = "stdlib.h";
	private final static String STDINT = "stdint.h";
	private final static String ASSERT = "assert.h";	

	private final static String ASSUME = "assume(x)";
	private final static String CPROVER_ASSUME = "__CPROVER_assume(x)";
	
	private final static String UNSIGNED_INT = "unsigned int";
	private final static String INT = "int";
	private final static String CBMC_UINT_FUNC_NAME = "nondet_uint";
	private final static String CBMC_INT_FUNC_NAME = "nondet_int";
	
	/**
	 * generates the cbmc code for a given ElectionDescription and PreAndPostConditionsDescription.
	 * 
	 * This has several steps:
	 * 
	 * 
	 * 
	 */
	
	public static String generateCode(CElectionDescription descr, PreAndPostConditionsDescription propDescr) {
		CFile created = new CFile();
		created.include(STDLIB);
		created.include(STDINT);
		created.include(ASSERT);
		
		created.define(ASSUME, CPROVER_ASSUME);
		
		created.addFunctionDecl(UNSIGNED_INT, CBMC_UINT_FUNC_NAME, List.of());
		created.addFunctionDecl(INT, CBMC_INT_FUNC_NAME, List.of());			
		
		return created.generateCode();
	}
}
