package edu.pse.beast.api;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundHandler;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.testrunner.propertycheck.CBMCCodeFileGeneratorNEW;
import edu.pse.beast.api.testrunner.propertycheck.CBMCPropertyCheckWorkUnit;
import edu.pse.beast.api.testrunner.propertycheck.CBMCTestRun;
import edu.pse.beast.api.testrunner.propertycheck.processes.process_handler.CBMCProcessHandler;
import edu.pse.beast.api.testrunner.threadpool.ThreadPool;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.gui.testconfigeditor.testconfig.cbmc.CBMCPropertyTestConfiguration;

public class BEAST {

	private ThreadPool tp;

	public BEAST() {
		this.tp = new ThreadPool(4);
	}

	public void shutdown() throws InterruptedException {
		tp.shutdown();
	}

	public void addCBMCWorkItemToQueue(CBMCPropertyCheckWorkUnit wu) {
		tp.addWork(wu);
	}

	public File generateCodeFile(CElectionDescription descr,
			PreAndPostConditionsDescription propDescr,
			CodeGenOptions codeGenOptions, LoopBoundHandler loopBoundHandler)
			throws IOException {
		return CBMCCodeFileGeneratorNEW.createCodeFileTest(descr, propDescr,
				codeGenOptions, loopBoundHandler);
	}

	public List<CBMCTestRun> generateTestRuns(File cbmcCodeFile,
			CBMCPropertyTestConfiguration testConfig,
			CodeGenOptions codeGenOptions, LoopBoundHandler loopBoundHandler) {
		List<CBMCTestRun> runs = new ArrayList<>();
		for (int v = testConfig.getMinVoters(); v <= testConfig
				.getMaxVoters(); ++v) {
			for (int c = testConfig.getMinCands(); c <= testConfig
					.getMaxCands(); ++c) {
				for (int s = testConfig.getMinSeats(); s <= testConfig
						.getMaxSeats(); ++s) {
					runs.add(new CBMCTestRun(v, s, c, codeGenOptions,
							loopBoundHandler, cbmcCodeFile,
							testConfig.getDescr(), testConfig.getPropDescr()));
				}
			}
		}
		return runs;
	}	
}
