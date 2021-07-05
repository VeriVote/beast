package edu.pse.beast.api.testrunner.propertycheck.processes.process_handler;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.NotImplementedException;

import edu.pse.beast.api.CBMCTestCallback;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.savingloading.SavingLoadingInterface;
import edu.pse.beast.gui.paths.PathHandler;

public class CBMCProcessHandlerWindows implements CBMCProcessHandler {
	private static final String BLANK = " ";
	private static final long WAITING_TIME_FOR_TERMINATION = 8000;
	private static final double A_VERY_LONG_TIME = 1000d;
	/** The Constant CBMC_EXE. */
	private String CBMC_EXE = "cbmc.exe";
	/** The Constant CBMC64_EXE. */
	private String CBMC64_EXE = "cbmc64.exe";

	/** The Constant RELATIVE_PATH_TO_CBMC_64. */
	private String RELATIVE_PATH_TO_CBMC = "/windows/cbmcWIN/" + CBMC_EXE;

	// only needed in windows
	private String vsCmdPath; // =
	// "\"D:\\Visual studio\\Common7\\Tools\\VsDevCmd.bat\"";

	public CBMCProcessHandlerWindows(String vsCmdPath) {
		this.vsCmdPath = "\"" + vsCmdPath + "\"";
	}

	public String getVsCmdPath() {
		return vsCmdPath;
	}

	public void setVsCmdPath(String vsCmdPath) {
		this.vsCmdPath = vsCmdPath;
	}

	@Override
	public Process startCheckForParam(String sessionUUID, int V, int C, int S,
			String uuid, CBMCTestCallback cb, File cbmcFile, String loopBounds,
			CodeGenOptions codeGenOptions, PathHandler pathHandler)
			throws IOException {
		String cbmcPath = new File(pathHandler.getBaseDir().getAbsolutePath()
				+ RELATIVE_PATH_TO_CBMC).getPath();

		String Space = " ";
		String completeCommand = vsCmdPath + Space + "&" + Space + "\""
				+ cbmcPath + "\"" + Space + cbmcFile.getAbsolutePath() + Space
				+ CBMCArgumentHelper.getConstCommands(codeGenOptions, V, C, S)
				+ Space + "--json-ui " + loopBounds;

		final File batFile = new File(cbmcFile.getParent() + "\\"
				+ cbmcFile.getName().replace(".c", ".bat"));

		SavingLoadingInterface.writeStringToFile(batFile, completeCommand);
		ProcessBuilder pb = new ProcessBuilder("cmd", "/c",
				batFile.getAbsolutePath());
		return pb.start();
	}

	@Override
	public CBMCProcessStarterType getType() {
		return CBMCProcessStarterType.WINDOWS;
	}

	@Override
	public void endProcess(Process p) {
		throw new NotImplementedException();
	}



}
