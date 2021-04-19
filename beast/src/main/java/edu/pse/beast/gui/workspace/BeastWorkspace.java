package edu.pse.beast.gui.workspace;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.pse.beast.api.BEAST;
import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundHandler;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.testrunner.propertycheck.CBMCProcessStarter;
import edu.pse.beast.api.testrunner.propertycheck.CBMCPropertyCheckWorkUnit;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.gui.testruneditor.testconfig.TestConfiguration;
import edu.pse.beast.gui.testruneditor.testconfig.TestConfigurationList;
import edu.pse.beast.gui.testruneditor.testconfig.cbmc.CBMCPropertyTestConfiguration;
import edu.pse.beast.gui.testruneditor.testconfig.cbmc.runs.CBMCTestRun;
import edu.pse.beast.gui.workspace.events.WorkspaceErrorEvent;
import edu.pse.beast.gui.workspace.events.WorkspaceErrorEventType;
import edu.pse.beast.gui.workspace.events.WorkspaceUpdateEvent;
import edu.pse.beast.gui.workspace.events.WorkspaceUpdateEventType;

public class BeastWorkspace {
	private List<CElectionDescription> loadedDescrs = new ArrayList<>();
	private List<PreAndPostConditionsDescription> loadedPropDescrs = new ArrayList<>();
	private CodeGenOptions codeGenOptions;
	private TestConfigurationList testConfigList = new TestConfigurationList();
	private File baseDir;
	private CBMCProcessStarter cbmcProcessStarter;

	private List<WorkspaceUpdateListener> updateListener = new ArrayList<>();

	private BEAST beast = new BEAST();

	public void setCodeGenOptions(CodeGenOptions codeGenOptions) {
		this.codeGenOptions = codeGenOptions;
	}
	
	public void setCbmcProcessStarter(CBMCProcessStarter cbmcProcessStarter) {
		this.cbmcProcessStarter = cbmcProcessStarter;
	}

	public void registerUpdateListener(WorkspaceUpdateListener l) {
		updateListener.add(l);
	}

	public List<CElectionDescription> getLoadedDescrs() {
		return loadedDescrs;
	}

	public List<PreAndPostConditionsDescription> getLoadedPropDescrs() {
		return loadedPropDescrs;
	}

	public void addTestConfiguration(TestConfiguration testConfig) {
		testConfigList.add(testConfig);
	}

	public CElectionDescription getDescrByName(String name) {
		for (CElectionDescription descr : loadedDescrs) {
			if (descr.getName().equals(name))
				return descr;
		}
		return null;
	}

	public PreAndPostConditionsDescription getPropDescrByName(String name) {
		for (PreAndPostConditionsDescription propDescr : loadedPropDescrs) {
			if (propDescr.getName().equals(name))
				return propDescr;
		}
		return null;
	}

	private void messageUpdateListener() {
		for (WorkspaceUpdateListener l : updateListener)
			l.handleWorkspaceUpdateGeneric();
	}

	public void addElectionDescription(CElectionDescription descr) {
		if (getDescrByName(descr.getName()) != null)
			return;
		loadedDescrs.add(descr);
		messageUpdateListener();
	}

	public void addPropertyDescription(
			PreAndPostConditionsDescription propDescr) {
		loadedPropDescrs.add(propDescr);
	}

	public CodeGenOptions getCodeGenOptions() {
		return codeGenOptions;
	}

	public void setBaseDir(File baseDir) {
		this.baseDir = baseDir;
	}

	public File getBaseDir() {
		return baseDir;
	}

	public Map<String, List<TestConfiguration>> getConfigsByElectionDescription() {
		return testConfigList.getConfigsByElectionDescription();
	}

	public Map<String, List<TestConfiguration>> getConfigsByPropertyDescription() {
		return testConfigList.getConfigsByPropertyDescription();
	}

	public void createCBMCTestRuns(
			CBMCPropertyTestConfiguration currentConfig) {
		try {
			if (cbmcProcessStarter == null) {
				for (WorkspaceUpdateListener l : updateListener) {
					l.handleWorkspaceErrorNoCBMCProcessStarter();
				}
				return;
			}

			LoopBoundHandler loopBoundHandler = new LoopBoundHandler();
			File cbmcFile = beast.generateCodeFile(currentConfig.getDescr(),
					currentConfig.getPropDescr(), codeGenOptions,
					loopBoundHandler);
			List<CBMCPropertyCheckWorkUnit> wus = beast.generateWorkUnits(cbmcFile,
					currentConfig, codeGenOptions, loopBoundHandler,
					cbmcProcessStarter);
			for(CBMCPropertyCheckWorkUnit wu : wus) {
				CBMCTestRun run = new CBMCTestRun(wu, cbmcFile);
				currentConfig.addRun(run);
				if(currentConfig.getStartRunsOnCreation()) {
					beast.addCBMCWorkItemToQueue(wu);
				}
			}			
			messageUpdateListener();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void shutdown() throws InterruptedException {
		beast.shutdown();
	}

}
