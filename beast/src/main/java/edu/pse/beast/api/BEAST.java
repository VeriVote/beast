package edu.pse.beast.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.testrunner.CBMCCodeFileData;
import edu.pse.beast.api.testrunner.CBMCCodeFileGeneratorNEW;
import edu.pse.beast.api.testrunner.propertycheck.CBMCPropertyCheckWorkUnit;
import edu.pse.beast.api.testrunner.propertycheck.CBMCTestRun;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.gui.testconfigeditor.testconfig.cbmc.CBMCPropertyTestConfiguration;

public class BEAST {

	private List<Thread> createdThreads = new ArrayList<>();
	private List<CBMCPropertyCheckWorkUnit> wus = new ArrayList<>();


	public void runWorkUnit(CBMCPropertyCheckWorkUnit wu) {
		wus.add(wu);
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				wu.doWork();
			}
		});
		t.start();
		createdThreads.add(t);
	}
	
	public void stopRun(CBMCTestRun run) {
		run.getWorkUnit().interrupt();
	}



	public void shutdown() {
		for (CBMCPropertyCheckWorkUnit wu : wus) {
			wu.shutdown();
		}
	}

	public CBMCCodeFileData generateCodeFileCBMCPropertyTest(
			CElectionDescription descr,
			PreAndPostConditionsDescription propDescr,
			CodeGenOptions codeGenOptions) throws IOException {
		return CBMCCodeFileGeneratorNEW.createCodeFileTest(descr, propDescr,
				codeGenOptions);
	}

	public List<CBMCTestRun> generateTestRuns(CBMCCodeFileData cbmcCodeFile,
			CBMCPropertyTestConfiguration testConfig,
			CodeGenOptions codeGenOptions) {
		List<CBMCTestRun> runs = new ArrayList<>();
		for (int v = testConfig.getMinVoters(); v <= testConfig
				.getMaxVoters(); ++v) {
			for (int c = testConfig.getMinCands(); c <= testConfig
					.getMaxCands(); ++c) {
				for (int s = testConfig.getMinSeats(); s <= testConfig
						.getMaxSeats(); ++s) {
					String loopbounds = cbmcCodeFile.getCodeInfo()
							.getLoopBoundHandler().generateCBMCString(v, c, s);

					runs.add(new CBMCTestRun(v, s, c, codeGenOptions,
							loopbounds, cbmcCodeFile, testConfig.getDescr(),
							testConfig.getPropDescr()));
				}
			}
		}
		return runs;
	}


	
}
