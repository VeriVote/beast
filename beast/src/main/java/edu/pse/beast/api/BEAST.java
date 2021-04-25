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
import edu.pse.beast.api.testrunner.propertycheck.process_starter.CBMCProcessStarter;
import edu.pse.beast.api.testrunner.threadpool.ThreadPool;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.gui.testruneditor.testconfig.cbmc.CBMCPropertyTestConfiguration;

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

	public List<CBMCPropertyCheckWorkUnit> generateWorkUnits(
			File cbmcFile,
			CBMCPropertyTestConfiguration testConfig,
			CodeGenOptions codeGenOptions,
			LoopBoundHandler loopBoundHandler,
			CBMCProcessStarter processStarter) {
		List<CBMCPropertyCheckWorkUnit>  workunits = new ArrayList<>();
		String sessionUUID = UUID.randomUUID().toString();
		for(int v = testConfig.getMinVoters(); v <= testConfig.getMaxVoters(); ++v) {
			for(int c = testConfig.getMinCands(); c <= testConfig.getMaxCands(); ++c) {
				for(int s = testConfig.getMinSeats(); s <= testConfig.getMaxSeats(); ++s) {
					String uuid = UUID.randomUUID().toString();
					workunits.add(new CBMCPropertyCheckWorkUnit(
							sessionUUID, 
							testConfig.getDescr(), 
							testConfig.getPropDescr(), 
							v, c, s, 
							uuid, 
							processStarter,
							cbmcFile,
							loopBoundHandler,
							codeGenOptions));
				}
			}
		}
		return workunits;
	}
}
