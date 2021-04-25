package edu.pse.beast.gui.workspace;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.pse.beast.api.BEAST;
import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundHandler;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.testrunner.propertycheck.CBMCPropertyCheckWorkUnit;
import edu.pse.beast.api.testrunner.propertycheck.process_starter.CBMCProcessStarter;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.gui.testruneditor.testconfig.TestConfiguration;
import edu.pse.beast.gui.testruneditor.testconfig.TestConfigurationList;
import edu.pse.beast.gui.testruneditor.testconfig.cbmc.CBMCPropertyTestConfiguration;
import edu.pse.beast.gui.testruneditor.testconfig.cbmc.runs.CBMCTestRun;

public class BeastWorkspace {
	private List<CElectionDescription> loadedDescrs = new ArrayList<>();
	private Map<String, File> filesPerDescr = new HashMap<>();
	private Set<CElectionDescription> descrWithUnsavedChanges = new HashSet();

	private List<PreAndPostConditionsDescription> loadedPropDescrs = new ArrayList<>();
	private Map<String, File> filesPerPropDescr = new HashMap<>();
	private Set<PreAndPostConditionsDescription> propDescrWithUnsavedChanges = new HashSet();

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

	public CBMCProcessStarter getCbmcProcessStarter() {
		return cbmcProcessStarter;
	}

	public List<PreAndPostConditionsDescription> getLoadedPropDescrs() {
		return loadedPropDescrs;
	}

	public void addTestConfiguration(TestConfiguration testConfig) {
		testConfigList.add(testConfig);
	}

	public void setTestConfigList(TestConfigurationList testConfigList) {
		this.testConfigList = testConfigList;
	}

	public TestConfigurationList getTestConfigList() {
		return testConfigList;
	}

	public Map<String, File> getFilesPerDescr() {
		return filesPerDescr;
	}

	public Map<String, File> getFilesPerPropDescr() {
		return filesPerPropDescr;
	}

	public CElectionDescription getDescrByUUID(String uuid) {
		for (CElectionDescription descr : loadedDescrs) {
			if (descr.getUuid().equals(uuid))
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
		if (getDescrByUUID(descr.getUuid()) != null)
			return;
		loadedDescrs.add(descr);
		descrWithUnsavedChanges.add(descr);
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

	public void createCBMCTestRuns(CBMCPropertyTestConfiguration config) {
		try {
			if (cbmcProcessStarter == null) {
				
				return;
			}

			LoopBoundHandler loopBoundHandler = new LoopBoundHandler();
			File cbmcFile = beast.generateCodeFile(config.getDescr(),
					config.getPropDescr(), codeGenOptions, loopBoundHandler);
			List<CBMCPropertyCheckWorkUnit> wus = beast.generateWorkUnits(
					cbmcFile, config, codeGenOptions, loopBoundHandler,
					cbmcProcessStarter);

			List<CBMCTestRun> createdTestRuns = new ArrayList<>();

			for (CBMCPropertyCheckWorkUnit wu : wus) {
				CBMCTestRun run = new CBMCTestRun(wu);
				createdTestRuns.add(run);
				config.addRun(run);
				if (config.getStartRunsOnCreation()) {
					beast.addCBMCWorkItemToQueue(wu);
				}
			}
			for (WorkspaceUpdateListener l : updateListener) {
				l.handleWorkspaceUpdateAddedCBMCRuns(config, createdTestRuns);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void shutdown() throws InterruptedException {
		beast.shutdown();
	}

	public void addRunToQueue(CBMCTestRun run) {
		beast.addCBMCWorkItemToQueue(run.getWorkUnit());
	}

	public void addFileForDescr(CElectionDescription loadedDescr,
			File descrFile) {
		filesPerDescr.put(loadedDescr.getUuid(), descrFile);
	}

	public void addFileForPropDescr(
			PreAndPostConditionsDescription loadedPropDescr,
			File propDescrFile) {
		filesPerPropDescr.put(loadedPropDescr.getUuid(), propDescrFile);
	}

	public void handleDescrChange(CElectionDescription descr) {
		descrWithUnsavedChanges.add(descr);
		testConfigList.handleDescrChange(descr);
	}

	public void updateFilesForRuns(
			CBMCPropertyTestConfiguration currentConfig) throws IOException {
		if (cbmcProcessStarter == null) {
			
			return;
		}
		LoopBoundHandler loopBoundHandler = new LoopBoundHandler();
		File cbmcFile = beast.generateCodeFile(currentConfig.getDescr(),
				currentConfig.getPropDescr(), codeGenOptions, loopBoundHandler);		
		
		for(CBMCTestRun cbmcTr : currentConfig.getRuns()) {
			cbmcTr.updateDataForCheck(cbmcFile, loopBoundHandler);
		}
	}

}
