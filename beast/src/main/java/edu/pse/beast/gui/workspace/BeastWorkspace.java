package edu.pse.beast.gui.workspace;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
import edu.pse.beast.api.savingloading.SavingLoadingInterface;
import edu.pse.beast.api.testrunner.propertycheck.CBMCPropertyCheckWorkUnit;
import edu.pse.beast.api.testrunner.propertycheck.process_starter.CBMCProcessStarter;
import edu.pse.beast.datatypes.propertydescription.FormalPropertiesDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.gui.ErrorDialogHelper;
import edu.pse.beast.gui.ErrorHandler;
import edu.pse.beast.gui.FileDialogHelper;
import edu.pse.beast.gui.testruneditor.testconfig.TestConfiguration;
import edu.pse.beast.gui.testruneditor.testconfig.TestConfigurationList;
import edu.pse.beast.gui.testruneditor.testconfig.cbmc.CBMCPropertyTestConfiguration;
import edu.pse.beast.gui.testruneditor.testconfig.cbmc.runs.CBMCTestRun;
import javafx.stage.FileChooser;

public class BeastWorkspace {
	private List<CElectionDescription> loadedDescrs = new ArrayList<>();
	private Map<CElectionDescription, File> filesPerDescr = new HashMap<>();
	private Set<CElectionDescription> descrWithUnsavedChanges = new HashSet();

	private List<PreAndPostConditionsDescription> loadedPropDescrs = new ArrayList<>();
	private Map<PreAndPostConditionsDescription, File> filesPerPropDescr = new HashMap<>();
	private Set<PreAndPostConditionsDescription> propDescrWithUnsavedChanges = new HashSet();

	private CodeGenOptions codeGenOptions;
	private TestConfigurationList testConfigList = new TestConfigurationList();
	private File baseDir;
	private CBMCProcessStarter cbmcProcessStarter;

	private List<WorkspaceUpdateListener> updateListener = new ArrayList<>();

	private BEAST beast = new BEAST();

	private ErrorHandler errorHandler;

	private String name = "test";
	private File workspaceFile;

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

	public Map<CElectionDescription, File> getFilesPerDescr() {
		return filesPerDescr;
	}

	public Map<PreAndPostConditionsDescription, File> getFilesPerPropDescr() {
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

	public Map<CElectionDescription, List<TestConfiguration>> getConfigsByElectionDescription() {
		return testConfigList.getTestConfigsByDescr();
	}

	public Map<PreAndPostConditionsDescription, List<TestConfiguration>> getConfigsByPropertyDescription() {
		return testConfigList.getTestConfigsByPropDescr();
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
		filesPerDescr.put(loadedDescr, descrFile);
	}

	public void addFileForPropDescr(
			PreAndPostConditionsDescription loadedPropDescr,
			File propDescrFile) {
		filesPerPropDescr.put(loadedPropDescr, propDescrFile);
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
		for (TestConfiguration tc : testConfigList.getTestConfigsByPropDescr()
				.get(currentPropDescr)) {
			tc.handlePropDescrChanged();
		}

		for (WorkspaceUpdateListener l : updateListener) {
			l.handleWorkspaceUpdateAddedVarToPropDescr(currentPropDescr, var);
		}
	}

	public void updateCodeForPropDescr(String code,
			FormalPropertiesDescription conditionDescription,
			PreAndPostConditionsDescription propDescr) {
		propDescrWithUnsavedChanges.add(propDescr);
		for (TestConfiguration tc : testConfigList.getTestConfigsByPropDescr()
				.get(propDescr)) {
			tc.handlePropDescrChanged();
		}
	}

	public void updateCodeForDescrFunction(CElectionDescription currentDescr,
			CElectionDescriptionFunction function, String code) {
		function.setCode(code);
		descrWithUnsavedChanges.add(currentDescr);

		for (TestConfiguration tc : testConfigList.getTestConfigsByDescr()
				.get(currentDescr)) {
			tc.handleDescrCodeChange();
		}

		for (WorkspaceUpdateListener l : updateListener) {
			l.handleCodeChangeInDescr(currentDescr, function, code);
		}
	}

	public void setErrorHandler(ErrorHandler errorHandler) {
		this.errorHandler = errorHandler;
	}

	public void saveDescr(CElectionDescription descr) {
		File f = null;
		if (filesPerDescr.containsKey(descr)) {
			f = filesPerDescr.get(descr);
		} else {
			f = FileDialogHelper.letUserSaveFile(baseDir, "choose save File",
					descr.getName() + ".belec");
			if (f == null) {
				return;
			}
			filesPerDescr.put(descr, f);
		}
		try {
			SavingLoadingInterface.storeCElection(descr, f);
			descrWithUnsavedChanges.remove(descr);
		} catch (IOException e) {
			errorHandler.logAndDisplayError("save error",
					e.getLocalizedMessage());
		}
	}

	public void savePropDescr(PreAndPostConditionsDescription propDescr) {
		File f = null;
		if (filesPerPropDescr.containsKey(propDescr)) {
			f = filesPerPropDescr.get(propDescr);
		} else {
			f = FileDialogHelper.letUserSaveFile(baseDir, "choose save File",
					propDescr.getName() + ".bprp");
			if (f == null) {
				return;
			}
			filesPerPropDescr.put(propDescr, f);
		}
		try {
			SavingLoadingInterface
					.storePreAndPostConditionDescription(propDescr, f);
			propDescrWithUnsavedChanges.remove(propDescr);
		} catch (IOException e) {
			errorHandler.logAndDisplayError("save error",
					e.getLocalizedMessage());
		}
	}

	public void saveAll() {
		List<CElectionDescription> descrListCopy = new ArrayList<>(
				descrWithUnsavedChanges);
		for (CElectionDescription descr : descrListCopy) {
			saveDescr(descr);
		}
		List<PreAndPostConditionsDescription> propDescrListCopy = new ArrayList<>(
				propDescrWithUnsavedChanges);
		for (PreAndPostConditionsDescription propDescr : propDescrListCopy) {
			savePropDescr(propDescr);
		}
	}

	public void setWorkspaceFile(File workspaceFile) {
		this.workspaceFile = workspaceFile;
	}

	public void saveWorkspace() {
		if (workspaceFile == null) {
			workspaceFile = FileDialogHelper.letUserSaveFile(baseDir,
					"file for workspace", name + ".beastws");
		}
		if (workspaceFile == null)
			return;
		try {
			SavingLoadingInterface.storeBeastWorkspace(this, workspaceFile);
		} catch (IOException e) {
			errorHandler.logAndDisplayError("save error",
					e.getLocalizedMessage());
		}
	}

	public Map<String, List<TestConfiguration>> getConfigsByElectionDescriptionName() {
		Map<String, List<TestConfiguration>> map = new HashMap<>();
		for (CElectionDescription descr : getConfigsByElectionDescription()
				.keySet()) {
			map.put(descr.getName(),
					getConfigsByElectionDescription().get(descr));
		}
		return map;
	}

	public Map<String, List<TestConfiguration>> getConfigsByPropertyDescriptionName() {
		Map<String, List<TestConfiguration>> map = new HashMap<>();
		for (PreAndPostConditionsDescription propDescr : getConfigsByPropertyDescription()
				.keySet()) {
			map.put(propDescr.getName(),
					getConfigsByPropertyDescription().get(propDescr));
		}
		return map;
	}

}
