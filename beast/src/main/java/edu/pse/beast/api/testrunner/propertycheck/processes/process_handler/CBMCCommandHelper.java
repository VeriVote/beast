package edu.pse.beast.api.testrunner.propertycheck.processes.process_handler;

import java.util.List;

import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundHandler;

public class CBMCCommandHelper {

	public static String cbmcXMLOutput() {
		return " --xml-ui ";
	}

	public static String getVoterArguments(int V, int C, int S) {
		String voterArg = "V=" + V;
		String candArg = "C=" + C;
		String seatsArg = "S=" + S;

		String arguments = "-D " + voterArg + " -D " + candArg + " -D "
				+ seatsArg;

		return arguments;
	}

	public static String getUnwindArgument(LoopBoundHandler loopBoundHandler,
			int V, int C, int S) {
		String argument = "";
		if (!(V == C && C == S)) {
			List<LoopBound> votingLoopbounds = loopBoundHandler
					.getVotingLoopBounds(String.valueOf(V), String.valueOf(C),
							String.valueOf(S));

			List<LoopBound> mainLoopbounds = loopBoundHandler.getMainLoopBounds(
					String.valueOf(V), String.valueOf(C), String.valueOf(S));

			for (LoopBound votingLoopBound : votingLoopbounds) {
				argument += " " + votingLoopBound.getCBMCString();
			}

			for (LoopBound mainLoopBound : mainLoopbounds) {
				argument += " " + mainLoopBound.getCBMCString();
			}
		} else {
			argument = " --unwind " + String.valueOf(V);
		}
		return argument;
	}
}
