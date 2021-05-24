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

	public void addCBMCWorkItemToQueue(CBMCPropertyCheckWorkUnit wu) {

	}

	public CBMCCodeFileData generateCodeFileCBMCPropertyTest(
			CElectionDescription descr,
			PreAndPostConditionsDescription propDescr,
			CodeGenOptions codeGenOptions,
			CodeGenLoopBoundHandler loopBoundHandler) throws IOException {
		return CBMCCodeFileGeneratorNEW.createCodeFileTest(descr, propDescr,
				codeGenOptions, loopBoundHandler);
	}

	public List<CBMCTestRun> generateTestRuns(CBMCCodeFileData cbmcCodeFile,
			CBMCPropertyTestConfiguration testConfig,
			CodeGenOptions codeGenOptions,
			CodeGenLoopBoundHandler loopBoundHandler) {
		List<CBMCTestRun> runs = new ArrayList<>();
		for (int v = testConfig.getMinVoters(); v <= testConfig
				.getMaxVoters(); ++v) {
			for (int c = testConfig.getMinCands(); c <= testConfig
					.getMaxCands(); ++c) {
				for (int s = testConfig.getMinSeats(); s <= testConfig
						.getMaxSeats(); ++s) {
					runs.add(new CBMCTestRun(v, s, c, codeGenOptions,
							loopBoundHandler.getLoopBoundsAsList(),
							cbmcCodeFile, testConfig.getDescr(),
							testConfig.getPropDescr()));
				}
			}
		}
		return runs;
	}
}
