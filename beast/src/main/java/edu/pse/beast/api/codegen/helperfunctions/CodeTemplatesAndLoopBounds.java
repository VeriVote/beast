package edu.pse.beast.api.codegen.helperfunctions;

import java.util.Arrays;
import java.util.List;

import edu.pse.beast.api.electiondescription.VotingInputTypes;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;

public abstract class CodeTemplatesAndLoopBounds {	
	
	
	
	public static class Comparison {
		public final static String template1dTopLevel = 
				  "	for(int i = 0; i < AMT; ++i) {\n"
				+ "		ASSUME_OR_ASSERT(LHS_VAR_NAME[i] COMP RHS_VAR_NAME[i]);\n" 
				+ "	}\n";

		public final static List<String> template1dloopBounds = Arrays.asList("AMT");
	}

}
