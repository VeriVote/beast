package edu.pse.beast.api.testrunner.propertycheck.process_starter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.api.CBMCTestCallback;
import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundHandler;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.electiontest.cbmb.CBMCCodeFileGenerator;
import edu.pse.beast.toolbox.FileLoader;
import edu.pse.beast.toolbox.FileSaver;
import edu.pse.beast.toolbox.SuperFolderFinder;

public class CBMCProcessStarterWindows implements CBMCProcessStarter {
	/** The Constant CBMC_EXE. */
	private final String CBMC_EXE = "cbmc.exe";
	/** The Constant CBMC64_EXE. */
	private final String CBMC64_EXE = "cbmc64.exe";

	/** The Constant RELATIVE_PATH_TO_CBMC_64. */
	private final String RELATIVE_PATH_TO_CBMC = "/windows/cbmcWIN/" + CBMC_EXE;

	static final String CBMC_XML_OUTPUT = "--xml-ui";

	// only needed in windows
	String vsCmdPath = "\"D:\\Visual studio\\Common7\\Tools\\VsDevCmd.bat\"";
	
	public String getVsCmdPath() {
		return vsCmdPath;
	}
	
	public void setVsCmdPath(String vsCmdPath) {
		this.vsCmdPath = vsCmdPath;
	}
	
	@Override
	public ProcessBuilder startCheckForParam(String sessionUUID,
			CElectionDescription descr,
			PreAndPostConditionsDescription propertyDescr, int V, int C, int S,
			String uuid, CBMCTestCallback cb, File cbmcFile,
			LoopBoundHandler loopBoundHandler, CodeGenOptions codeGenOptions) {
		String cbmcPath = "\"" + new File(
				SuperFolderFinder.getSuperFolder() + RELATIVE_PATH_TO_CBMC)
						.getPath()
				+ "\"";
		String BLANK = " ";

		String voterArg = "V=" + V;
		String candArg = "C=" + C;
		String seatsArg = "S=" + S;

		String arguments = "-D " + voterArg + " -D " + candArg + " -D "
				+ seatsArg;

		String completeCommand = vsCmdPath + BLANK + "&" + BLANK + cbmcPath
				+ BLANK + "\"" + cbmcFile.getPath() + "\"" + BLANK
				+ CBMC_XML_OUTPUT + BLANK + arguments;

		if (!(V == C && C == S)) {
			List<LoopBound> votingLoopbounds = loopBoundHandler
					.getVotingLoopBounds(String.valueOf(V), String.valueOf(C),
							String.valueOf(S));

			List<LoopBound> mainLoopbounds = loopBoundHandler.getMainLoopBounds(
					String.valueOf(V), String.valueOf(C), String.valueOf(S));

			for (LoopBound votingLoopBound : votingLoopbounds) {
				completeCommand += " " + votingLoopBound.getCBMCString();
			}

			for (LoopBound mainLoopBound : mainLoopbounds) {
				completeCommand += " " + mainLoopBound.getCBMCString();
			}
		} else {
			completeCommand += " --unwind " + String.valueOf(V);
		}

		cb.onCompleteCommand(descr, propertyDescr, V, C, S, uuid,
				completeCommand);

		final File batFile = new File(
				cbmcFile.getParent() + "\\" + cbmcFile.getName().replace(
						FileLoader.C_FILE_ENDING, FileLoader.BAT_FILE_ENDING));
		List<String> list = new ArrayList<>();
		list.add(completeCommand);
		FileSaver.writeStringLinesToFile(list, batFile);
		ProcessBuilder pb = new ProcessBuilder("cmd", "/c",
				batFile.getAbsolutePath());
		return pb;
	}

	@Override
	public CBMCProcessStarterType getType() {
		return CBMCProcessStarterType.WINDOWS;
	}
}
