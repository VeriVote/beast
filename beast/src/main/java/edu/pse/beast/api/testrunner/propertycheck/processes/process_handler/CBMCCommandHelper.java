package edu.pse.beast.api.testrunner.propertycheck.processes.process_handler;

import java.io.File;
import java.util.List;

import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.codegen.loopbounds.LoopBound;

public class CBMCCommandHelper {

	private final static String DEFINE_CONST_FLAG = " -D ";
	private final static String LOOP_BOUND_TEMPLATE = " --unwindset FUNC_NAME:IDX BOUND ";
	private final static String OUTPUT_FORMAT_JSON_STRING = " --json-ui ";

	public static String getAmountConstantString(CodeGenOptions codeGenOptions,
			int V, int C, int S) {
		String voterString = codeGenOptions.getCbmcAmountVotersVarName() + "="
				+ V;
		String candString = codeGenOptions.getCbmcAmountCandidatesVarName()
				+ "=" + C;
		String seatString = codeGenOptions.getCbmcAmountSeatsVarName() + "="
				+ S;

		return DEFINE_CONST_FLAG + voterString + DEFINE_CONST_FLAG + candString
				+ DEFINE_CONST_FLAG + seatString;
	}

	public static String getOutputAsJsonArgument() {
		return OUTPUT_FORMAT_JSON_STRING;
	}

	// String of the form --unwindset main:0 5
	public static String getUnwindArguments(List<LoopBound> loopBounds, int V,
			int C, int S) {

		StringBuilder unwindArgsStringBuilder = new StringBuilder();

		for (LoopBound loopBound : loopBounds) {
			String funcName = loopBound.getFunctionName();
			String loopIdx = String.valueOf(loopBound.getIndex());
			String bound = null;

			switch (loopBound.getLoopBoundType()) {
				case LOOP_BOUND_AMT_VOTERS :
					bound = String.valueOf(V);
					break;
				case LOOP_BOUND_AMT_CANDS :
					bound = String.valueOf(C);
					break;
				case LOOP_BOUND_AMT_SEATS :
					bound = String.valueOf(S);
					break;
			}

			String currentUnwindArgument = LOOP_BOUND_TEMPLATE
					.replaceAll("FUNC_NAME", funcName)
					.replaceAll("IDX", loopIdx).replaceAll("BOUND", bound);

			unwindArgsStringBuilder.append(currentUnwindArgument);
		}

		return unwindArgsStringBuilder.toString();
	}

	public static String getArgumentsForCBMCJsonOutput(File codeFileToTest,
			CodeGenOptions codeGenOptions, List<LoopBound> loopBounds, int V,
			int C, int S) {
		return codeFileToTest.getAbsolutePath() + " "
				+ getAmountConstantString(codeGenOptions, V, C, S)
				+ getOutputAsJsonArgument()
				+ getUnwindArguments(loopBounds, V, C, S);
	}

}
