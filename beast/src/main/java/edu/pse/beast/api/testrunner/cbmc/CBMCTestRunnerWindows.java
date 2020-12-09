package edu.pse.beast.api.testrunner.cbmc;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.pse.beast.api.testrunner.BEASTResult;
import edu.pse.beast.api.testrunner.BEASTTestRunner;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.electiontest.cbmb.CBMCCodeFileGenerator;
import edu.pse.beast.toolbox.FileLoader;
import edu.pse.beast.toolbox.FileSaver;
import edu.pse.beast.toolbox.SuperFolderFinder;

public class CBMCTestRunnerWindows extends BEASTTestRunner {
	/** The Constant CBMC_EXE. */
	private String CBMC_EXE = "cbmc.exe";
	/** The Constant CBMC64_EXE. */
	private String CBMC64_EXE = "cbmc64.exe";

	/** The Constant RELATIVE_PATH_TO_CBMC_64. */
	private String RELATIVE_PATH_TO_CBMC = "/windows/cbmcWIN/" + CBMC_EXE;

	static final String CBMC_XML_OUTPUT = "--xml-ui";

	// only needed in windows
	String vsCmdPath = "\"D:\\Visual studio\\Common7\\Tools\\VsDevCmd.bat\"";

	File cbmcFile;

	public CBMCTestRunnerWindows(String vsCmdPath) {
		this.vsCmdPath = vsCmdPath;
	}

	@Override
	protected void beforeTest() {
	}

	@Override
	protected void beforeProperty(PreAndPostConditionsDescription propertyDescr) {
		this.cbmcFile = CBMCCodeFileGenerator.createCodeFileTest(description, propertyDescr);
	}

	@Override
	protected BEASTResult startTestForParam(PreAndPostConditionsDescription propertyDescr, int V, int C, int S) {
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
		Process process;
		try {
			process = pb.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			return new CBMCBEASTResult(description, propertyDescr, V, C, S, cb, process, reader);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
