package edu.pse.beast.api.testrunner.propertycheck.processes.process_handler;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.api.CBMCTestCallback;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.specificcbmcrun.SpecificCBMCRunParametersInfo;
import edu.pse.beast.api.testrunner.CBMCCodeFileData;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.electiontest.cbmb.CBMCCodeFileGenerator;
import edu.pse.beast.toolbox.FileLoader;
import edu.pse.beast.toolbox.FileSaver;
import edu.pse.beast.toolbox.SuperFolderFinder;

public class CBMCProcessHandlerWindows implements CBMCProcessHandler {
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
			String uuid, CBMCTestCallback cb, File cbmcFile,
			List<LoopBound> loopBounds, CodeGenOptions codeGenOptions)
			throws IOException {
		String cbmcPath = new File(
				SuperFolderFinder.getSuperFolder() + RELATIVE_PATH_TO_CBMC)
						.getPath();

		String Space = " ";
		String completeCommand = vsCmdPath + Space + "&" + Space + "\""
				+ cbmcPath + "\"" + Space
				+ CBMCCommandHelper.getArgumentsForCBMCJsonOutput(cbmcFile,
						codeGenOptions, loopBounds, V, C, S);

		final File batFile = new File(
				cbmcFile.getParent() + "\\" + cbmcFile.getName().replace(
						FileLoader.C_FILE_ENDING, FileLoader.BAT_FILE_ENDING));

		List<String> list = List.of(completeCommand);
		FileSaver.writeStringLinesToFile(list, batFile);
		ProcessBuilder pb = new ProcessBuilder("cmd", "/c",
				batFile.getAbsolutePath());
		return pb.start();
	}

	@Override
	public CBMCProcessStarterType getType() {
		return CBMCProcessStarterType.WINDOWS;
	}

}
