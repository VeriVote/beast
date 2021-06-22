package edu.pse.beast.gui.workspace;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.reactfx.util.Tuple2;

import edu.pse.beast.api.BEAST;
import edu.pse.beast.api.c_parser.AntlrCLoopParser;
import edu.pse.beast.api.c_parser.ExtractedCLoop;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar;
import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.electiondescription.function.CElectionDescriptionFunction;
import edu.pse.beast.api.electiondescription.function.VotingSigFunction;
import edu.pse.beast.api.savingloading.SavingLoadingInterface;
import edu.pse.beast.api.testrunner.CBMCCodeFileData;
import edu.pse.beast.api.testrunner.propertycheck.CBMCPropertyCheckWorkUnit;
import edu.pse.beast.api.testrunner.propertycheck.CBMCTestRun;
import edu.pse.beast.api.testrunner.propertycheck.processes.process_handler.CBMCProcessHandler;
import edu.pse.beast.datatypes.propertydescription.FormalPropertiesDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.gui.CBMCProcessHandlerGetter;
import edu.pse.beast.gui.ErrorHandler;
import edu.pse.beast.gui.FileDialogHelper;
import edu.pse.beast.gui.paths.PathHandler;
import edu.pse.beast.gui.testconfigeditor.testconfig.TestConfiguration;
import edu.pse.beast.gui.testconfigeditor.testconfig.TestConfigurationList;
import edu.pse.beast.gui.testconfigeditor.testconfig.cbmc.CBMCPropertyTestConfiguration;
import edu.pse.beast.toolbox.Tuple;

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
	private CBMCProcessHandler cbmcProcessStarter;

	private List<WorkspaceUpdateListener> updateListener = new ArrayList<>();

	private BEAST beast = new BEAST();

	private ErrorHandler errorHandler;

	private String name = "test";
	private File workspaceFile;

	private PathHandler pathHandler;

	public static BeastWorkspace getStandardWorkspace() {
		BeastWorkspace beastWorkspace = new BeastWorkspace();

		CodeGenOptions codeGenOptions = new CodeGenOptions();

		codeGenOptions.setCbmcAmountMaxCandidatesVarName("MAX_CANDIDATES");
		codeGenOptions.setCbmcAmountMaxVotersVarName("MAX_VOTERS");
		codeGenOptions.setCbmcAmountMaxSeatsVarName("MAX_SEATS");

		codeGenOptions.setCurrentAmountCandsVarName("C");
		codeGenOptions.setCurrentAmountVotersVarName("V");
		codeGenOptions.setCurrentAmountSeatsVarName("S");

		codeGenOptions.setCbmcAssertName("assert");
		codeGenOptions.setCbmcAssumeName("assume");

		beastWorkspace.setCodeGenOptions(codeGenOptions);

		beastWorkspace.setPathHandler(new PathHandler());

		return beastWorkspace;
	}

	public void setCodeGenOptions(CodeGenOptions codeGenOptions) {
		this.codeGenOptions = codeGenOptions;
	}

	public void setCbmcProcessStarter(CBMCProcessHandler cbmcProcessStarter) {
		this.cbmcProcessStarter = cbmcProcessStarter;
	}

	public void registerUpdateListener(WorkspaceUpdateListener l) {
		updateListener.add(l);
	}

	public List<CElectionDescription> getLoadedDescrs() {
		return loadedDescrs;
	}

	public CBMCProcessHandler getCbmcProcessStarter() {
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

	public void letUserLoadPropDescr() {
		try {
			Tuple<PreAndPostConditionsDescription, File> t= FileDialogHelper
					.letUserLoadPropDescr(pathHandler.getPropDescrDir(), null);
			if (t.first() != null) {
				addPropertyDescription(t.first());
				addFileForPropDescr(t.first(), t.second());
			}
		} catch (IOException e) {
			errorHandler.logAndDisplayError("Loading prop descr",
					"There was an error trying to load the prop election descr");
		}
	}

	public void addPropertyDescription(
			PreAndPostConditionsDescription propDescr) {
		loadedPropDescrs.add(propDescr);
		for (WorkspaceUpdateListener l : updateListener)
			l.handleAddedPropDescr(propDescr);
	}

	public CodeGenOptions getCodeGenOptions() {
		return codeGenOptions;
	}

	public void setPathHandler(PathHandler pathHandler) {
		this.pathHandler = pathHandler;
	}

	public PathHandler getPathHandler() {
		return pathHandler;
	}

	public void setBaseDir(File baseDir) {
		this.baseDir = baseDir;
	}

	public File getBaseDir() {
		return baseDir;
	}

	public ErrorHandler getErrorHandler() {
		return errorHandler;
	}

	public Map<CElectionDescription, List<TestConfiguration>> getConfigsByElectionDescription() {
		return testConfigList.getTestConfigsByDescr();
	}

	public Map<PreAndPostConditionsDescription, List<TestConfiguration>> getConfigsByPropertyDescription() {
		return testConfigList.getTestConfigsByPropDescr();
	}

	public void createCBMCTestRunsAndAddToConfig(
			CBMCPropertyTestConfiguration config) {
		try {

			CElectionDescription descr = config.getDescr();

			for (CElectionDescriptionFunction f : descr.getFunctions()) {
				if (!f.allLoopsDescribed()) {
					errorHandler.logAndDisplayError("missing loop bounds",
							"The function " + f.getName()
									+ " has loopbounds which are not described.");
					return;
				}
			}

			CodeGenLoopBoundHandler loopBoundHandler = descr
					.generateLoopBoundHandler();

			CBMCCodeFileData cbmcCodeFile = beast
					.generateCodeFileCBMCPropertyTest(config.getDescr(),
							config.getPropDescr(), codeGenOptions);

			List<CBMCTestRun> createdTestRuns = beast
					.generateTestRuns(cbmcCodeFile, config, codeGenOptions);

			if (!hasProcessStarter()) {
				askUserForProcessStarter();
			}

			if (hasProcessStarter()) {
				String sessionUUID = UUID.randomUUID().toString();
				for (CBMCTestRun run : createdTestRuns) {
					CBMCPropertyCheckWorkUnit workUnit = new CBMCPropertyCheckWorkUnit(
							cbmcProcessStarter, sessionUUID);
					run.setAndInitializeWorkUnit(workUnit);
				}
			}

			config.addRuns(createdTestRuns);

			for (WorkspaceUpdateListener l : updateListener) {
				l.handleWorkspaceUpdateAddedCBMCRuns(config, createdTestRuns);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// TODO check if the process starter works?
	private boolean hasProcessStarter() {
		return cbmcProcessStarter != null;
	}

	private void askUserForProcessStarter() {
		CBMCProcessHandler processHandler = CBMCProcessHandlerGetter
				.askUserForCBMCProcessHandler();
	}

	public void addRunToQueue(CBMCTestRun run) {
		beast.runWorkUnit(run.getWorkUnit());
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
		if (!hasProcessStarter()) {
			return;
		}

		for (CElectionDescriptionFunction f : currentConfig.getDescr()
				.getFunctions()) {
			if (!f.allLoopsDescribed()) {
				errorHandler.logAndDisplayError("missing loop bounds",
						"The function " + f.getName()
								+ " has loopbounds which are not described.");
				return;
			}
		}

		CBMCCodeFileData cbmcFileData = beast.generateCodeFileCBMCPropertyTest(
				currentConfig.getDescr(), currentConfig.getPropDescr(),
				codeGenOptions);

		for (CBMCTestRun cbmcTr : currentConfig.getRuns()) {
			cbmcTr.updateDataForCheck(cbmcFileData, cbmcTr.getCodeGenOptions());
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
				.getTestConfigsByPropDescr(currentPropDescr)) {
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

	/* ==============CelectionDescription changes============== */

	// TODO(Holger), rn, we do all our sanity checks (dont remove the voting
	// function, etc)
	// in here. should we move that into CelectionDescription?
	// I dont think so, i like it to remain just a data class, however
	// reasonable ppl can disagree on that
	private void handleDescrChange(CElectionDescription descr) {
		descrWithUnsavedChanges.add(descr);

		testConfigList.handleDescrChange(descr);

	}

	public void updateCodeForDescrFunction(CElectionDescription descr,
			CElectionDescriptionFunction function, String code) {
		function.setCode(code);
		handleDescrChange(descr);
		function.getExtractedLoops().clear();

		for (WorkspaceUpdateListener l : updateListener) {
			l.handleDescrChangeUpdatedFunctionCode(descr, function, code);
		}
	}

	public void addVotingSigFunctionToDescr(CElectionDescription descr,
			String name) {
		if (descr.hasFunctionName(name)) {
			errorHandler.logAndDisplayError("Duplicate Function name",
					"A function with this name already exists. "
							+ "Can't have 2 functions with the same name in a C program.");
		} else {
			VotingSigFunction func = descr
					.createNewVotingSigFunctionAndAdd(name);
			handleDescrChange(descr);
			for (WorkspaceUpdateListener l : updateListener) {
				l.handleDescrChangeAddedVotingSigFunction(descr, func);
			}
		}

	}

	public void removeFunctionFromDescr(CElectionDescription descr,
			CElectionDescriptionFunction func) {
		if (func == descr.getVotingFunction()) {
			errorHandler.logAndDisplayError("Removing Voting Function",
					"You tried to remove the voting function, you cant do this my guy");
		} else {
			descr.removeFunction(func);
			handleDescrChange(descr);
			for (WorkspaceUpdateListener l : updateListener) {
				l.handleDescrChangeRemovedFunction(descr, func);
			}
		}
	}

	public void findLoopBounds(CElectionDescription descr,
			CElectionDescriptionFunction func) {
		String code = func.getCode();
		List<ExtractedCLoop> loops = AntlrCLoopParser.findLoops(func.getName(),
				code, codeGenOptions);
		func.setExtractedLoops(loops);

		for (WorkspaceUpdateListener l : updateListener) {
			l.handleExtractedFunctionLoops(descr, func);
		}
	}

	public void shutdown() {
		beast.shutdown();
	}

	public void letUserLoadDescr() {
		try {
			Tuple<CElectionDescription, File> t = FileDialogHelper
					.letUserLoadElectionDescription(
							pathHandler.getElectionDescrDir(), null);
			if (t.first() != null) {
				addElectionDescription(t.first());
				addFileForDescr(t.first(), t.second());
			}

		} catch (IOException exception) {
			errorHandler.logAndDisplayError("Loading descr",
					"There was an error trying to load the election descr");
		}
	}

}
