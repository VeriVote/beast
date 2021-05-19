package edu.pse.beast.api;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundHandler;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.specificcbmcrun.SpecificCBMCRunParametersInfo;
import edu.pse.beast.api.testrunner.CBMCCodeFileData;
import edu.pse.beast.api.testrunner.CBMCCodeFileGeneratorNEW;
import edu.pse.beast.api.testrunner.propertycheck.CBMCPropertyCheckWorkUnit;
import edu.pse.beast.api.testrunner.propertycheck.CBMCTestRun;
import edu.pse.beast.api.testrunner.propertycheck.processes.process_handler.CBMCProcessHandler;
import edu.pse.beast.api.testrunner.run_with_specific_params.SpecificParamsTestRun;
import edu.pse.beast.api.testrunner.threadpool.ThreadPool;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.gui.testconfigeditor.testconfig.cbmc.CBMCPropertyTestConfiguration;
import javafx.application.Platform;

public class BEAST {
	

	public void addCBMCWorkItemToQueue(CBMCPropertyCheckWorkUnit wu) {
		
	}

	public CBMCCodeFileData generateCodeFileCBMCPropertyTest(
			CElectionDescription descr,
			PreAndPostConditionsDescription propDescr,
			CodeGenOptions codeGenOptions, LoopBoundHandler loopBoundHandler)
			throws IOException {
		return CBMCCodeFileGeneratorNEW.createCodeFileTest(descr, propDescr,
				codeGenOptions, loopBoundHandler);
	}

	public CBMCCodeFileData generateCodeFileSpecificParamsTestRun(
			SpecificCBMCRunParametersInfo info, CodeGenOptions codeGenOptions)
			throws IOException {
		return CBMCCodeFileGeneratorNEW.createCodeFileSpecificParameters(info,
				codeGenOptions);
	}

	public SpecificParamsTestRun generateSpecificParamsTestRun(
			CBMCCodeFileData codeFileData, SpecificCBMCRunParametersInfo info,
			CodeGenOptions codeGenOptions) {
		return new SpecificParamsTestRun(info, codeFileData, codeGenOptions);
	}

	public List<CBMCTestRun> generateTestRuns(CBMCCodeFileData cbmcCodeFile,
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
							loopBoundHandler.getLoopBoundsAsList(),
							cbmcCodeFile, testConfig.getDescr(),
							testConfig.getPropDescr()));
				}
			}
		}
		return runs;
	}
}
