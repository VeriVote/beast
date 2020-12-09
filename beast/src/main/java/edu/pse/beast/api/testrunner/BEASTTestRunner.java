package edu.pse.beast.api.testrunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.pse.beast.api.BEASTCallback;
import edu.pse.beast.datatypes.electioncheckparameter.ElectionCheckParameter;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.electiontest.cbmb.CBMCCodeFileGenerator;
import edu.pse.beast.toolbox.FileLoader;
import edu.pse.beast.toolbox.FileSaver;
import edu.pse.beast.toolbox.SuperFolderFinder;

public abstract class BEASTTestRunner implements Runnable {

	private Thread t;

	private List<PreAndPostConditionsDescription> propertiesToTest;
	private ElectionCheckParameter param;

	protected ElectionDescription description;
	protected BEASTCallback cb;

	private BEASTAfterTestOp afterOp;

	private boolean running;

	// TODO pass user wishes about which lvl of concurrency somewhere
	public BEASTTestRunner() {
	}

	public void setupTestRun(BEASTCallback cb, ElectionDescription description,
			List<PreAndPostConditionsDescription> propertiesToTest, ElectionCheckParameter param) {
		this.cb = cb;
		this.description = description;
		this.propertiesToTest = propertiesToTest;
		this.param = param;
	}

	public void stop() {
		cb.onTestStopped();
	}

	public void start() {
		this.t = new Thread(this);
		t.start();
	}

	protected abstract void beforeTest();

	protected abstract void beforeProperty(PreAndPostConditionsDescription propertyDescr);

	protected abstract BEASTResult startTestForParam(PreAndPostConditionsDescription propertyDescr, int V, int C,
			int S);
	
	private void waitForAllSync(List<BEASTResult> results) {
		boolean wait = true;
		while(wait) {
			wait = false;
			for(BEASTResult result : results) {
				if(!result.verificationFinished())
					wait = true;
			}
		} 
	}
	
	private int getNumberWorkingResults(List<BEASTResult> results) {
		int num = 0;
		for(BEASTResult result : results) {
			if(!result.verificationFinished())
				num++;
		}
		return num;
	}

	@Override
	public void run() {
		running = true;
		cb.onTestStarted();
		beforeTest();
		List<BEASTResult> results = new ArrayList<>();

		for (PreAndPostConditionsDescription propertyDescr : propertiesToTest) {
			beforeProperty(propertyDescr);

			for (int V : param.getRangeOfVoters()) {
				for (int C : param.getRangeofCandidates()) {
					for (int S : param.getRangeOfSeats()) {

						//TODO concurrency here
						int amtWorkingRes = getNumberWorkingResults(results);
						
						BEASTResult res = startTestForParam(propertyDescr, V, C, S);
						
						if (res != null)
							results.add(res);

					}
				}
			}
		}

		waitForAllSync(results);
		running = false;
		if (afterOp != null)
			afterOp.op();
	}

	public void doAfter(BEASTAfterTestOp op) {
		this.afterOp = op;
	}

	public boolean isRunning() {
		return running;
	}

}
