package edu.pse.beast.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.datatypes.electioncheckparameter.ElectionCheckParameter;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.electiontest.cbmb.CBMCCodeFileGenerator;
import edu.pse.beast.toolbox.FileLoader;
import edu.pse.beast.toolbox.FileSaver;
import edu.pse.beast.toolbox.SuperFolderFinder;

public class BEASTTestRunner implements Runnable {
	/** The Constant CBMC_EXE. */
	private static final String CBMC_EXE = "cbmc.exe";
	/** The Constant CBMC64_EXE. */
	private static final String CBMC64_EXE = "cbmc64.exe";

	/** The Constant RELATIVE_PATH_TO_CBMC_64. */
	private static final String RELATIVE_PATH_TO_CBMC = "/windows/cbmcWIN/" + CBMC_EXE;

	static final String CBMC_XML_OUTPUT = "--xml-ui";

	private BEASTCallback cb;
	private ElectionDescription description;
	private List<PreAndPostConditionsDescription> propertiesToTest;
	private ElectionCheckParameter param;

	// TODO pass user wishes about which lvl of concurrency somewhere
	public BEASTTestRunner(BEASTCallback cb, ElectionDescription description,
			List<PreAndPostConditionsDescription> propertiesToTest, ElectionCheckParameter param) {
		this.cb = cb;
		this.description = description;
		this.propertiesToTest = propertiesToTest;
		this.param = param;
	}

	public void stop() {
		cb.onTestStopped();
	}

	@Override
	public void run() {
		cb.onTestStarted();
		// TODO pass which type of checker we want to use
		// for now only use cbmc

		// first generate the c file for cbmc. This one will be shard between the test
		// processes
		// with different numbers of candidates etc.

		// TODO pass user wishes about which lvl of concurrency somewhere.
		// For now we go property by property and just start processes for each
		// combination of values

		// run with vsDevCmd
		// TODO add cygwin support why not
		// TODO NEEDS path to vsDevCmd and CBMC.exe. We ship CBMC - need a way
		// to pass vsDevCmd from outside tho. As a class somehow
		// TODO should abstract both os and how to run it - but
		// less convoluted than we do rn
		// for now just do the easiest thing to see what we need
		String cbmcPath = new File(SuperFolderFinder.getSuperFolder() + RELATIVE_PATH_TO_CBMC).getPath();
		String vsCmdPath = "D:\\Visual studio\\Common7\\Tools\\VsDevCmd.bat";

		for (PreAndPostConditionsDescription propertyDescr : propertiesToTest) {
			File cbmcFile = CBMCCodeFileGenerator.createCodeFileTest(description, propertyDescr);

			// TODO pass the constant names for voters etc. I dont like using some
			// static class somewhere

			for (int V : param.getRangeOfVoters()) {
				for (int C : param.getRangeofCandidates()) {
					for (int S : param.getRangeOfSeats()) {
						String BLANK = " ";

						String voterArg = "V=" + V;
						String candArg = "C=" + C;
						String seatsArg = "S=" + S;

						String arguments = "-D " + voterArg + " -D " + candArg + " -D " + seatsArg;

						String completeCommand = vsCmdPath + BLANK + "&" + BLANK + cbmcPath + BLANK + cbmcFile.getPath()
								+ BLANK + CBMC_XML_OUTPUT + BLANK + arguments;

						final File batFile = new File(cbmcFile.getParent() + "\\"
								+ cbmcFile.getName().replace(FileLoader.C_FILE_ENDING, FileLoader.BAT_FILE_ENDING));
						List<String> list = new ArrayList<>();
						list.add(completeCommand);
						FileSaver.writeStringLinesToFile(list, batFile);
						ProcessBuilder pb = new ProcessBuilder("cmd", "/c", batFile.getAbsolutePath());
						try {
							Process process = pb.start();
							BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
							String line;
							while ((line = reader.readLine()) != null) {
								System.out.println(line);
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}

		}
	}
}
