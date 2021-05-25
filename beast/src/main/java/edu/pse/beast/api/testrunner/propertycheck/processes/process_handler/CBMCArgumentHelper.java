package edu.pse.beast.api.testrunner.propertycheck.processes.process_handler;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;

public class CBMCArgumentHelper {
	public static String getConstCommands(CodeGenOptions codeGenOptions, int V, int C,
			int S) {
		return 	" -D " + codeGenOptions.getCbmcAmountVotersVarName() + "=" + V +
				" -D " + codeGenOptions.getCbmcAmountCandidatesVarName() + "=" + C +
				" -D " + codeGenOptions.getCbmcAmountSeatsVarName() + "=" + S;
	}
}
