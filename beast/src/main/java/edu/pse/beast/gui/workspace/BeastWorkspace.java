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

import edu.pse.beast.api.BEAST;
import edu.pse.beast.api.c_parser.AntlrCLoopParser;
import edu.pse.beast.api.c_parser.ExtractedCLoop;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar;
import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.electiondescription.VotingInputTypes;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;
import edu.pse.beast.api.electiondescription.function.CElectionDescriptionFunction;
import edu.pse.beast.api.electiondescription.function.SimpleTypeFunction;
import edu.pse.beast.api.electiondescription.function.VotingSigFunction;
import edu.pse.beast.api.savingloading.SavingLoadingInterface;
import edu.pse.beast.api.testrunner.CBMCCodeFileData;
import edu.pse.beast.api.testrunner.propertycheck.CBMCPropertyCheckWorkUnit;
import edu.pse.beast.api.testrunner.propertycheck.CBMCTestRun;
import edu.pse.beast.api.testrunner.propertycheck.processes.process_handler.CBMCProcessHandler;
import edu.pse.beast.datatypes.propertydescription.FormalPropertiesDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.gui.ErrorHandler;
import edu.pse.beast.gui.FileDialogHelper;
import edu.pse.beast.gui.paths.PathHandler;
import edu.pse.beast.gui.processHandler.CBMCProcessHandlerCreator;
import edu.pse.beast.gui.testconfigeditor.testconfig.TestConfiguration;
import edu.pse.beast.gui.testconfigeditor.testconfig.TestConfigurationList;
import edu.pse.beast.gui.testconfigeditor.testconfig.cbmc.CBMCTestConfiguration;
import edu.pse.beast.toolbox.Tuple;

public class BeastWorkspace {

	private CBMCProcessHandlerCreator cbmcProcessHandlerCreator;

	private BEAST beast = new BEAST();
	private List<WorkspaceUpdateListener> updateListener = new ArrayList<>();
	private ErrorHandler errorHandler;

	private List<CElectionDescription> loadedDescrs = new ArrayList<>();
	private Map<CElectionDescription, File> filesPerDescr = new HashMap<>();
	private Set<CElectionDescription> descrWithUnsavedChanges = new HashSet();

	private List<PreAndPostConditionsDescription> loadedPropDescrs = new ArrayList<>();
	private Map<PreAndPostConditionsDescription, File> filesPerPropDescr = new HashMap<>();
	private Set<PreAndPostConditionsDescription> propDescrWithUnsavedChanges = new HashSet();

	private CodeGenOptions codeGenOptions;
	private TestConfigurationList testConfigList = new TestConfigurationList();

	private String name = "test";
	private File workspaceFile;

	private PathHandler pathHandler;

	public static BeastWorkspace getStandardWorkspace(
			CBMCProcessHandlerCreator cbmcProcessHandlerCreator) {
		BeastWorkspace beastWorkspace = new BeastWorkspace();

		beastWorkspace.cbmcProcessHandlerCreator = cbmcProcessHandlerCreator;

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

		beastWorkspace.setTestConfigList(new TestConfigurationList());

		return beastWorkspace;
	}

	public void setCodeGenOptions(CodeGenOptions codeGenOptions) {
		this.codeGenOptions = codeGenOptions;
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

	public void setTestConfigList(TestConfigurationList testConfigList) {
		//remove the old list so it doesnt receive updates
		updateListener.remove(this.testConfigList);
		this.testConfigList = testConfigList;
		for (CBMCTestRun run : testConfigList.getCBMCTestRuns()) {
			CBMCPropertyCheckWorkUnit wu = new CBMCPropertyCheckWorkUnit(
					cbmcProcessHandlerCreator, UUID.randomUUID().toString());
			run.setAndInitializeWorkUnit(wu);
		}
		registerUpdateListener(testConfigList);
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
			Tuple<PreAndPostConditionsDescription, File> t = FileDialogHelper
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
			CBMCTestConfiguration config) {
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

			String sessionUUID = UUID.randomUUID().toString();
			for (CBMCTestRun run : createdTestRuns) {
				CBMCPropertyCheckWorkUnit workUnit = new CBMCPropertyCheckWorkUnit(
						cbmcProcessHandlerCreator, sessionUUID);
				run.setAndInitializeWorkUnit(workUnit);
			}

			config.addRuns(createdTestRuns);

			for (WorkspaceUpdateListener l : updateListener) {
				l.handleWorkspaceUpdateAddedCBMCRuns(config, createdTestRuns);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addRunToQueue(CBMCTestRun run) {
		if (!cbmcProcessHandlerCreator.hasProcessHandler()) {
			cbmcProcessHandlerCreator.askUserForCBMCProcessHandler();
		}
		if (cbmcProcessHandlerCreator.hasProcessHandler())
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

	public void updateFilesForRuns(CBMCTestConfiguration currentConfig)
			throws IOException {

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

		for (WorkspaceUpdateListener l : updateListener) {
			l.handleWorkspaceUpdateAddedVarToPropDescr(currentPropDescr, var);
		}
	}

	public void updateCodeForPropDescr(String code,
			FormalPropertiesDescription conditionDescription,
			PreAndPostConditionsDescription propDescr) {
		conditionDescription.setCode(code);
		propDescrWithUnsavedChanges.add(propDescr);
		for (WorkspaceUpdateListener l : updateListener) {
			l.handlePropDescrChangedCode(propDescr);
		}
	}

	public void setErrorHandler(ErrorHandler errorHandler) {
		this.errorHandler = errorHandler;
	}

	public boolean saveDescr(CElectionDescription descr) {
		File f = null;
		if (filesPerDescr.containsKey(descr)) {
			f = filesPerDescr.get(descr);
		} else {
			f = FileDialogHelper.letUserSaveFile(
					pathHandler.getElectionDescrDir(), "choose save File",
					descr.getName() + ".belec");
			if (f == null) {
				return false;
			}
			filesPerDescr.put(descr, f);
		}
		try {
			SavingLoadingInterface.storeCElection(descr, f);
			descrWithUnsavedChanges.remove(descr);
		} catch (IOException e) {
			errorHandler.logAndDisplayError("save error",
					e.getLocalizedMessage());
			return false;
		}
		return true;
	}

	public boolean savePropDescr(PreAndPostConditionsDescription propDescr) {
		File f = null;
		if (filesPerPropDescr.containsKey(propDescr)) {
			f = filesPerPropDescr.get(propDescr);
		} else {
			f = FileDialogHelper.letUserSaveFile(pathHandler.getPropDescrDir(),
					"choose save File", propDescr.getName() + ".bprp");
			if (f == null) {
				return false;
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
			return false;
		}
		return true;
	}

	private boolean saveAll() {
		List<CElectionDescription> descrListCopy = new ArrayList<>(
				descrWithUnsavedChanges);
		for (CElectionDescription descr : descrListCopy) {
			if (!saveDescr(descr))
				return false;
		}
		List<PreAndPostConditionsDescription> propDescrListCopy = new ArrayList<>(
				propDescrWithUnsavedChanges);
		for (PreAndPostConditionsDescription propDescr : propDescrListCopy) {
			if (!savePropDescr(propDescr))
				return false;
		}
		return true;
	}

	public void setWorkspaceFile(File workspaceFile) {
		this.workspaceFile = workspaceFile;
	}

	private void loadWorkspace(BeastWorkspace ws) {
		loadedDescrs = ws.loadedDescrs;
		filesPerDescr = ws.filesPerDescr;
		descrWithUnsavedChanges = ws.descrWithUnsavedChanges;

		loadedPropDescrs = ws.loadedPropDescrs;
		filesPerPropDescr = ws.filesPerPropDescr;
		propDescrWithUnsavedChanges = ws.propDescrWithUnsavedChanges;

		codeGenOptions = ws.codeGenOptions;
		setTestConfigList(ws.testConfigList);

		name = ws.name;
		workspaceFile = ws.workspaceFile;

		pathHandler = ws.pathHandler;

		messageUpdateListener();
	}

	public void letUserLoadWorkSpace() {
		File f = FileDialogHelper.letUserOpenFile("Workspace", ".beastws",
				"open the workspace file", pathHandler.getWorkspaceDir(), null);
		if (f != null) {
			try {
				BeastWorkspace ws = SavingLoadingInterface
						.loadBeastWorkspace(f);
				loadWorkspace(ws);
			} catch (Exception e) {
				errorHandler.logAndDisplayError("save error",
						e.getLocalizedMessage());
			}
		}
	}

	public void saveWorkspace() {
		boolean allSaved = saveAll();
		if (!allSaved) {
			errorHandler.logAndDisplayError("Saving Workspace",
					"There was an error when trying to save all descr and prop descr, wont save workspace.");
		}

		if (workspaceFile == null) {
			workspaceFile = FileDialogHelper.letUserSaveFile(
					pathHandler.getWorkspaceDir(), "file for workspace",
					name + ".beastws");
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

	public void createTestConfig(String name, CElectionDescription descr,
			PreAndPostConditionsDescription propDescr) {
		TestConfiguration tc = new TestConfiguration(descr, propDescr, name);
		CBMCTestConfiguration configuration = new CBMCTestConfiguration();
		configuration.setDescr(descr);
		configuration.setPropDescr(propDescr);
		configuration.setName(name);
		tc.addCBMCTestConfiguration(configuration);
		testConfigList.add(tc);
		for (WorkspaceUpdateListener l : updateListener) {
			l.handleAddedTestConfig(tc);
		}
	}

	public void stopRun(CBMCTestRun run) {
		beast.stopRun(run);
	}

	public void removeSymbolicVar(PreAndPostConditionsDescription propDescr,
			SymbolicCBMCVar selectedVar) {
		propDescr.getCbmcVariables().remove(selectedVar);
		propDescrWithUnsavedChanges.add(propDescr);
		for (WorkspaceUpdateListener l : updateListener) {
			l.handlePropDescrRemovedVar(propDescr, selectedVar);
		}
	}

	public void addSimpleFunctionToDescr(CElectionDescription descr,
			SimpleTypeFunction f) {
		descr.addSimpleFunction(f);
		handleDescrChange(descr);
		for (WorkspaceUpdateListener l : updateListener) {
			l.handleDescrChangeAddedSimpleFunction(descr, f);
		}
	}

	public void editDescr(CElectionDescription descr, String name,
			VotingInputTypes inType, VotingOutputTypes outType) {
		descr.setName(name);
		descr.setInputType(inType);
		descr.setOutputType(outType);
		handleDescrChange(descr);
		for (WorkspaceUpdateListener l : updateListener) {
			l.handleDescrChangeInOutName(descr);
		}
	}

	public void deleteCBMCRun(CBMCTestRun run) {
		testConfigList.deleteCBMCRun(run);
	}

}
