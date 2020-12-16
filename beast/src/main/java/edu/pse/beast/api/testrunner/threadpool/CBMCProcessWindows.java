package edu.pse.beast.api.testrunner.threadpool;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.api.BEASTCallback;
import edu.pse.beast.api.testrunner.BEASTResult;
import edu.pse.beast.api.testrunner.cbmc.CBMCBEASTResult;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.electiontest.cbmb.CBMCCodeFileGenerator;
import edu.pse.beast.toolbox.FileLoader;
import edu.pse.beast.toolbox.FileSaver;
import edu.pse.beast.toolbox.SuperFolderFinder;

public class CBMCProcessWindows {
	/** The Constant CBMC_EXE. */
	private String CBMC_EXE = "cbmc.exe";
	/** The Constant CBMC64_EXE. */
	private String CBMC64_EXE = "cbmc64.exe";

	/** The Constant RELATIVE_PATH_TO_CBMC_64. */
	private String RELATIVE_PATH_TO_CBMC = "/windows/cbmcWIN/" + CBMC_EXE;

	static final String CBMC_XML_OUTPUT = "--xml-ui";

	// only needed in windows
	String vsCmdPath = "\"D:\\Visual studio\\Common7\\Tools\\VsDevCmd.bat\"";

	public ProcessBuilder startTestForParam(ElectionDescription description, PreAndPostConditionsDescription propertyDescr,
			int V, int C, int S, BEASTCallback cb) {

		File cbmcFile = CBMCCodeFileGenerator.createCodeFileTest(description, propertyDescr);

		String cbmcPath = new File(SuperFolderFinder.getSuperFolder() + RELATIVE_PATH_TO_CBMC).getPath();
		String BLANK = " ";

		String voterArg = "V=" + V;
		String candArg = "C=" + C;
		String seatsArg = "S=" + S;

		String arguments = "-D " + voterArg + " -D " + candArg + " -D " + seatsArg;

		String completeCommand = vsCmdPath + BLANK + "&" + BLANK + cbmcPath + BLANK + cbmcFile.getPath() + BLANK
				+ CBMC_XML_OUTPUT + BLANK + arguments;

		final File batFile = new File(cbmcFile.getParent() + "\\"
				+ cbmcFile.getName().replace(FileLoader.C_FILE_ENDING, FileLoader.BAT_FILE_ENDING));
		List<String> list = new ArrayList<>();
		list.add(completeCommand);
		FileSaver.writeStringLinesToFile(list, batFile);
		ProcessBuilder pb = new ProcessBuilder("cmd", "/c", batFile.getAbsolutePath());
		return pb;
	}
}
