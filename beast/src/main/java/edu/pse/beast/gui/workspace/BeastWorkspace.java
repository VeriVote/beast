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
import edu.pse.beast.api.codegen.SymbolicCBMCVar;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundHandler;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.electiondescription.function.CElectionDescriptionFunction;
import edu.pse.beast.api.testrunner.propertycheck.CBMCPropertyCheckWorkUnit;
import edu.pse.beast.api.testrunner.propertycheck.process_starter.CBMCProcessStarter;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.gui.ErrorDialogHelper;
import edu.pse.beast.gui.ErrorHandler;
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

	private ErrorHandler errorHandler;

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

	public PreAndPostConditionsDescription getPropDescrByUUID(String uuid) {
		for (PreAndPostConditionsDescription propDescr : loadedPropDescrs) {
			if (propDescr.getUuid().equals(uuid))
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

	public void updateFilesForRuns(CBMCPropertyTestConfiguration currentConfig)
			throws IOException {
		if (cbmcProcessStarter == null) {

			return;
		}
		LoopBoundHandler loopBoundHandler = new LoopBoundHandler();
		File cbmcFile = beast.generateCodeFile(currentConfig.getDescr(),
				currentConfig.getPropDescr(), codeGenOptions, loopBoundHandler);

		for (CBMCTestRun cbmcTr : currentConfig.getRuns()) {
			cbmcTr.updateDataForCheck(cbmcFile, loopBoundHandler);
		}
	}

	public void addCBCMVarToPropDescr(
			PreAndPostConditionsDescription currentPropDescr,
			SymbolicCBMCVar var) {
		try {
			currentPropDescr.addCBMCVar(var);
		} catch (Exception e) {
			errorHandler.logAndDisplayError("invalid name",
					"name already exists in Property Description");
			return;
		}

		propDescrWithUnsavedChanges.add(currentPropDescr);
		for (TestConfiguration tc : testConfigList
				.getConfigsByPropertyDescription()
				.get(currentPropDescr.getUuid())) {
			tc.handlePropDescrChanged();
		}

		for (WorkspaceUpdateListener l : updateListener) {
			l.handleWorkspaceUpdateAddedVarToPropDescr(currentPropDescr, var);
		}
	}

	public void updateCodeForDescrFunction(CElectionDescription currentDescr,
			CElectionDescriptionFunction function, String code) {
		function.setCode(code);
		descrWithUnsavedChanges.add(currentDescr);

		for (TestConfiguration tc : testConfigList
				.getConfigsByElectionDescription()
				.get(currentDescr.getUuid())) {
			tc.handleDescrCodeChange();
		}

		for (WorkspaceUpdateListener l : updateListener) {
			l.handleCodeChangeInDescr(currentDescr, function, code);
		}
	}

	public void setErrorHandler(ErrorHandler errorHandler) {
		this.errorHandler = errorHandler;
	}
}
