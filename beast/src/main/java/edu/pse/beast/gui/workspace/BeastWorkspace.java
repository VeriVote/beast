package edu.pse.beast.gui.workspace;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import edu.pse.beast.api.BEAST;
import edu.pse.beast.api.c_parser.AntlrCLoopParser;
import edu.pse.beast.api.c_parser.ExtractedCLoop;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar;
import edu.pse.beast.api.descr.c_electiondescription.CElectionDescription;
import edu.pse.beast.api.descr.c_electiondescription.VotingInputTypes;
import edu.pse.beast.api.descr.c_electiondescription.VotingOutputTypes;
import edu.pse.beast.api.descr.c_electiondescription.function.CElectionDescriptionFunction;
import edu.pse.beast.api.descr.c_electiondescription.function.SimpleTypeFunction;
import edu.pse.beast.api.descr.c_electiondescription.function.VotingSigFunction;
import edu.pse.beast.api.descr.property_description.FormalPropertiesDescription;
import edu.pse.beast.api.descr.property_description.PreAndPostConditionsDescription;
import edu.pse.beast.api.paths.PathHandler;
import edu.pse.beast.api.savingloading.SavingLoadingInterface;
import edu.pse.beast.api.testrunner.code_files.CBMCCodeFileData;
import edu.pse.beast.api.testrunner.propertycheck.CBMCPropertyCheckWorkUnit;
import edu.pse.beast.api.testrunner.propertycheck.symbolic_vars.CBMCTestRunWithSymbolicVars;
import edu.pse.beast.api.toolbox.Tuple;
import edu.pse.beast.gui.FileDialogHelper;
import edu.pse.beast.gui.errors.BeastError;
import edu.pse.beast.gui.errors.BeastErrorTypes;
import edu.pse.beast.gui.errors.ErrorHandler;
import edu.pse.beast.gui.processHandler.CBMCProcessHandlerCreator;
import edu.pse.beast.gui.testconfigeditor.testconfig.TestConfiguration;
import edu.pse.beast.gui.testconfigeditor.testconfig.TestConfigurationList;
import edu.pse.beast.gui.testconfigeditor.testconfig.cbmc.CBMCTestConfiguration;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class BeastWorkspace {
    private static final String ASSUME = "assume";
    private static final String ASSERT = "assert";

    private static final String BEASTWS_FILE_ENDING = ".beastws";
    private static final String BELEC_FILE_ENDING = ".belec";
    private static final String BPRP_FILE_ENDING = ".bprp";

    private static final String CANDIDATE_AMOUNT_VAR = "C";
    private static final String VOTER_AMOUNT_VAR = "V";
    private static final String SEAT_AMOUNT_VAR = "S";

    private static final String MAX_CANDIDATES = "MAX_CANDIDATES";
    private static final String MAX_VOTERS = "MAX_VOTERS";
    private static final String MAX_SEATS = "MAX_SEATS";

    private static final String THE_FUNCTION = "The function ";
    private static final String LOOPBOUNDS_NOT_DESCRIBED =
            " has loopbounds which are not described.";
    private static final String CHOOSE_SAVE_FILE = "choose save file";

    private CBMCProcessHandlerCreator cbmcProcessHandlerCreator;

    private BEAST beast = new BEAST();
    private List<WorkspaceUpdateListener> updateListener = new ArrayList<>();
    private ErrorHandler errorHandler;

    private List<CElectionDescription> loadedDescrs = new ArrayList<>();
    private Map<CElectionDescription, File> filesPerDescr = new HashMap<>();
    private Set<CElectionDescription> descrWithUnsavedChanges =
            new HashSet<CElectionDescription>();

    private List<PreAndPostConditionsDescription> loadedPropDescrs = new ArrayList<>();
    private Map<PreAndPostConditionsDescription, File> filesPerPropDescr = new HashMap<>();
    private Set<PreAndPostConditionsDescription> propDescrWithUnsavedChanges =
            new HashSet<PreAndPostConditionsDescription>();

    private CodeGenOptions codeGenerationOptions;
    private TestConfigurationList testConfigList = new TestConfigurationList();

    private String name = "test";
    private File workspaceFile;

    private PathHandler pathHandler;

    public static final BeastWorkspace
            getStandardWorkspace(final CBMCProcessHandlerCreator cbmcProcessHandlerCreator) {
        final BeastWorkspace beastWorkspace = new BeastWorkspace();
        beastWorkspace.cbmcProcessHandlerCreator = cbmcProcessHandlerCreator;

        final CodeGenOptions codeGenOptions = new CodeGenOptions();
        codeGenOptions.setCbmcAmountMaxCandidatesVarName(MAX_CANDIDATES);
        codeGenOptions.setCbmcAmountMaxVotersVarName(MAX_VOTERS);
        codeGenOptions.setCbmcAmountMaxSeatsVarName(MAX_SEATS);
        codeGenOptions.setCurrentAmountCandsVarName(CANDIDATE_AMOUNT_VAR);
        codeGenOptions.setCurrentAmountVotersVarName(VOTER_AMOUNT_VAR);
        codeGenOptions.setCurrentAmountSeatsVarName(SEAT_AMOUNT_VAR);
        codeGenOptions.setCbmcAssertName(ASSERT);
        codeGenOptions.setCbmcAssumeName(ASSUME);

        beastWorkspace.setCodeGenOptions(codeGenOptions);
        beastWorkspace.setPathHandler(new PathHandler());
        beastWorkspace.setTestConfigList(new TestConfigurationList());
        return beastWorkspace;
    }

    public final void setCodeGenOptions(final CodeGenOptions codeGenOptions) {
        this.codeGenerationOptions = codeGenOptions;
    }

    public final void registerUpdateListener(final WorkspaceUpdateListener l) {
        updateListener.add(l);
    }

    public final List<CElectionDescription> getLoadedDescrs() {
        return loadedDescrs;
    }

    public final List<PreAndPostConditionsDescription> getLoadedPropDescrs() {
        return loadedPropDescrs;
    }

    public final void addTestConfiguration(final TestConfiguration testConfig) {
        testConfigList.add(testConfig);
    }

    public final void setTestConfigList(final TestConfigurationList configurationList) {
        // remove the old list so it does not receive updates
        updateListener.remove(this.testConfigList);
        this.testConfigList = configurationList;
        for (final CBMCTestRunWithSymbolicVars run
                : configurationList.getCBMCTestRuns()) {
            final CBMCPropertyCheckWorkUnit wu =
                    new CBMCPropertyCheckWorkUnit(cbmcProcessHandlerCreator,
                                                  UUID.randomUUID().toString());
            run.setAndInitializeWorkUnit(wu, pathHandler);
        }
        registerUpdateListener(configurationList);
    }

    public final TestConfigurationList getTestConfigList() {
        return testConfigList;
    }

    public final Map<CElectionDescription, File> getFilesPerDescr() {
        return filesPerDescr;
    }

    public final Map<PreAndPostConditionsDescription, File> getFilesPerPropDescr() {
        return filesPerPropDescr;
    }

    public final CElectionDescription getDescrByUUID(final String uuid) {
        for (final CElectionDescription descr : loadedDescrs) {
            if (descr.getUuid().equals(uuid)) {
                return descr;
            }
        }
        return null;
    }

    public final PreAndPostConditionsDescription getPropDescrByUUID(final String uuid) {
        for (final PreAndPostConditionsDescription propDescr : loadedPropDescrs) {
            if (propDescr.getUuid().equals(uuid)) {
                return propDescr;
            }
        }
        return null;
    }

    private void messageUpdateListener() {
        for (final WorkspaceUpdateListener l : updateListener) {
            l.handleWorkspaceUpdateGeneric();
        }
    }

    public final void addElectionDescription(final CElectionDescription descr) {
        if (getDescrByUUID(descr.getUuid()) != null) {
            return;
        }
        loadedDescrs.add(descr);
        descrWithUnsavedChanges.add(descr);
        messageUpdateListener();
    }

    public final void letUserLoadPropDescr() {
        try {
            final Tuple<PreAndPostConditionsDescription, File> t =
                    FileDialogHelper.letUserLoadPropDescr(pathHandler.getPropDescrDir(), null);
            if (t.first() != null) {
                addPropertyDescription(t.first());
                addFileForPropDescr(t.first(), t.second());
            }
        } catch (IOException e) {
            errorHandler.logAndDisplayError(
                    new BeastError(BeastErrorTypes.IO_ERROR,
                                   "trying to load property description",
                                   e));
        }
    }

    public final void addPropertyDescription(final PreAndPostConditionsDescription propDescr) {
        loadedPropDescrs.add(propDescr);
        for (final WorkspaceUpdateListener l : updateListener) {
            l.handleAddedPropDescr(propDescr);
        }
    }

    public final CodeGenOptions getCodeGenOptions() {
        return codeGenerationOptions;
    }

    public final void setPathHandler(final PathHandler handler) {
        this.pathHandler = handler;
    }

    public final PathHandler getPathHandler() {
        return pathHandler;
    }

    public final ErrorHandler getErrorHandler() {
        return errorHandler;
    }

    public final Map<CElectionDescription, List<TestConfiguration>>
                    getConfigsByElectionDescription() {
        return testConfigList.getTestConfigsByDescr();
    }

    public final Map<PreAndPostConditionsDescription, List<TestConfiguration>>
                    getConfigsByPropertyDescription() {
        return testConfigList.getTestConfigsByPropDescr();
    }

    public final void createCBMCTestRunsAndAddToConfig(final CBMCTestConfiguration config) {
        try {
            final CElectionDescription descr = config.getDescr();
            for (final CElectionDescriptionFunction f : descr.getFunctions()) {
                if (!f.allLoopsDescribed()) {
                    final String more =
                            THE_FUNCTION + f.getName() + LOOPBOUNDS_NOT_DESCRIBED;
                    errorHandler.logAndDisplayError(
                            new BeastError(BeastErrorTypes.NOT_ALL_LOOPS_DESCRIBED, more));
                    return;
                }
            }
            descr.generateLoopBoundHandler();
            final CBMCCodeFileData cbmcCodeFile =
                    beast.generateCodeFileCBMCPropertyTest(config.getDescr(),
                                                           config.getPropDescr(),
                                                           codeGenerationOptions,
                                                           pathHandler);
            final List<CBMCTestRunWithSymbolicVars> createdTestRuns =
                    beast.generateTestRuns(cbmcCodeFile, config, codeGenerationOptions);
            final String sessionUUID = UUID.randomUUID().toString();
            for (final CBMCTestRunWithSymbolicVars run : createdTestRuns) {
                final CBMCPropertyCheckWorkUnit workUnit =
                        new CBMCPropertyCheckWorkUnit(cbmcProcessHandlerCreator, sessionUUID);
                run.setAndInitializeWorkUnit(workUnit, pathHandler);
            }
            config.addRuns(createdTestRuns);
            for (final WorkspaceUpdateListener l : updateListener) {
                l.handleWorkspaceUpdateAddedCBMCRuns(config, createdTestRuns);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public final void addRunToQueue(final CBMCTestRunWithSymbolicVars run) {
        if (!cbmcProcessHandlerCreator.hasProcessHandler()) {
            cbmcProcessHandlerCreator.askUserForCBMCProcessHandler();
        }
        if (cbmcProcessHandlerCreator.hasProcessHandler()) {
            beast.runWorkUnit(run.getWorkUnit());
        }
    }

    public final void addFileForDescr(final CElectionDescription loadedDescr,
                                      final File descrFile) {
        filesPerDescr.put(loadedDescr, descrFile);
    }

    public final void addFileForPropDescr(final PreAndPostConditionsDescription loadedPropDescr,
                                          final File propDescrFile) {
        filesPerPropDescr.put(loadedPropDescr, propDescrFile);
    }

    public final void updateFilesForRuns(final CBMCTestConfiguration currentConfig)
            throws IOException {
        for (final CElectionDescriptionFunction f : currentConfig.getDescr().getFunctions()) {
            if (!f.allLoopsDescribed()) {
                final String more =
                        THE_FUNCTION + f.getName() + LOOPBOUNDS_NOT_DESCRIBED;
                errorHandler.logAndDisplayError(
                        new BeastError(BeastErrorTypes.NOT_ALL_LOOPS_DESCRIBED, more));
                return;
            }
        }

        final CBMCCodeFileData cbmcFileData =
                beast.generateCodeFileCBMCPropertyTest(currentConfig.getDescr(),
                                                       currentConfig.getPropDescr(),
                                                       codeGenerationOptions, pathHandler);

        for (final CBMCTestRunWithSymbolicVars cbmcTr : currentConfig.getRuns()) {
            cbmcTr.updateDataForCheck(cbmcFileData, cbmcTr.getCodeGenerationOptions());
        }
        for (final WorkspaceUpdateListener l : updateListener) {
            l.handleCBMConfigUpdatedFiles(currentConfig);
        }
    }

    public final void addCBCMVarToPropDescr(final PreAndPostConditionsDescription currentPropDescr,
                                            final SymbolicCBMCVar var) {
        try {
            currentPropDescr.addCBMCVar(var);
        } catch (IllegalArgumentException e) {
            errorHandler.logAndDisplayError(
                    new BeastError(BeastErrorTypes.CBMC_VAR_NAME_ALREADY_EXISTS,
                                   var.getName(), e));
            return;
        }
        propDescrWithUnsavedChanges.add(currentPropDescr);
        for (final WorkspaceUpdateListener l : updateListener) {
            l.handleWorkspaceUpdateAddedVarToPropDescr(currentPropDescr, var);
        }
    }

    public final void updateCodeForPropDescr(final String code,
                                             final FormalPropertiesDescription conditionDescription,
                                             final PreAndPostConditionsDescription propDescr) {
        conditionDescription.setCode(code);
        propDescrWithUnsavedChanges.add(propDescr);
        for (final WorkspaceUpdateListener l : updateListener) {
            l.handlePropDescrChangedCode(propDescr);
        }
    }

    public final void setErrorHandler(final ErrorHandler errHandler) {
        this.errorHandler = errHandler;
    }

    public final boolean saveDescr(final CElectionDescription descr) {
        File f = null;
        if (filesPerDescr.containsKey(descr)) {
            f = filesPerDescr.get(descr);
        } else {
            f = FileDialogHelper
                    .letUserSaveFile(pathHandler.getElectionDescrDir(),
                                     CHOOSE_SAVE_FILE,
                                     descr.getName() + BELEC_FILE_ENDING);
            if (f == null) {
                return false;
            }
            filesPerDescr.put(descr, f);
        }
        try {
            SavingLoadingInterface.storeCElection(descr, f);
            descrWithUnsavedChanges.remove(descr);
        } catch (IOException e) {
            errorHandler.logAndDisplayError(
                    new BeastError(BeastErrorTypes.IO_ERROR, descr.getName(), e));
            return false;
        }
        return true;
    }

    public final boolean savePropDescr(final PreAndPostConditionsDescription propDescr) {
        File f = null;
        if (filesPerPropDescr.containsKey(propDescr)) {
            f = filesPerPropDescr.get(propDescr);
        } else {
            f = FileDialogHelper
                    .letUserSaveFile(pathHandler.getPropDescrDir(),
                                     "choose save File",
                                     propDescr.getName() + BPRP_FILE_ENDING);
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
            errorHandler.logAndDisplayError(
                    new BeastError(BeastErrorTypes.IO_ERROR, propDescr.getName(), e));
            return false;
        }
        return true;
    }

    private boolean saveAll() {
        final List<CElectionDescription> descrListCopy =
                new ArrayList<>(descrWithUnsavedChanges);
        for (final CElectionDescription descr : descrListCopy) {
            if (!saveDescr(descr)) {
                return false;
            }
        }
        final List<PreAndPostConditionsDescription> propDescrListCopy =
                new ArrayList<>(propDescrWithUnsavedChanges);
        for (final PreAndPostConditionsDescription propDescr : propDescrListCopy) {
            if (!savePropDescr(propDescr)) {
                return false;
            }
        }
        return true;
    }

    public final void setWorkspaceFile(final File file) {
        this.workspaceFile = file;
    }

    private void loadWorkspace(final BeastWorkspace ws) {
        loadedDescrs = ws.loadedDescrs;
        filesPerDescr = ws.filesPerDescr;
        descrWithUnsavedChanges = ws.descrWithUnsavedChanges;
        loadedPropDescrs = ws.loadedPropDescrs;
        filesPerPropDescr = ws.filesPerPropDescr;
        propDescrWithUnsavedChanges = ws.propDescrWithUnsavedChanges;
        codeGenerationOptions = ws.codeGenerationOptions;
        setTestConfigList(ws.testConfigList);
        name = ws.name;
        workspaceFile = ws.workspaceFile;
        pathHandler = ws.pathHandler;
        messageUpdateListener();
    }

    public final void letUserLoadWorkSpace() {
        final File f =
                FileDialogHelper
                .letUserOpenFile("Workspace", BEASTWS_FILE_ENDING,
                                 "open the workspace file",
                                 pathHandler.getWorkspaceDir(),
                                 null);
        if (f != null) {
            try {
                final BeastWorkspace ws =
                        SavingLoadingInterface.loadBeastWorkspace(f, pathHandler);
                loadWorkspace(ws);
            } catch (IOException e) {
                e.printStackTrace();
                errorHandler.logAndDisplayError(
                        new BeastError(BeastErrorTypes.IO_ERROR, "workspace", e));

            }
        }
    }

    public final void saveWorkspace() {
        final boolean allSaved = saveAll();
        if (!allSaved) {
            errorHandler.logAndDisplayError(
                    new BeastError(BeastErrorTypes.ERROR_WHEN_SAVING_ALL, ""));
        }

        if (workspaceFile == null) {
            workspaceFile =
                    FileDialogHelper.letUserSaveFile(
                            pathHandler.getWorkspaceDir(), "file for workspace",
                            name + BEASTWS_FILE_ENDING);
        }
        if (workspaceFile == null) {
            return;
        }

        try {
            SavingLoadingInterface.storeBeastWorkspace(this, workspaceFile,
                    pathHandler);
        } catch (IOException e) {
            errorHandler.logAndDisplayError(
                    new BeastError(BeastErrorTypes.IO_ERROR, "", e));
        }
    }

    public final Map<String, List<TestConfiguration>> getConfigsByElectionDescriptionName() {
        final Map<String, List<TestConfiguration>> map = new HashMap<>();
        for (final CElectionDescription descr
                : getConfigsByElectionDescription().keySet()) {
            map.put(descr.getName(),
                    getConfigsByElectionDescription().get(descr));
        }
        return map;
    }

    public final Map<String, List<TestConfiguration>> getConfigsByPropertyDescriptionName() {
        final Map<String, List<TestConfiguration>> map = new HashMap<>();
        for (final PreAndPostConditionsDescription propDescr
                : getConfigsByPropertyDescription().keySet()) {
            map.put(propDescr.getName(),
                    getConfigsByPropertyDescription().get(propDescr));
        }
        return map;
    }

    /* ==============CelectionDescription changes============== */

    // TODO(Holger), rn, we do all our sanity checks (do not remove the voting
    // function, etc)
    // in here. should we move that into CelectionDescription?
    // I do not think so, I like it to remain just a data class, however
    // reasonable people can disagree on that
    private void handleDescrChange(final CElectionDescription descr) {
        descrWithUnsavedChanges.add(descr);
    }

    public final void updateCodeForDescrFunction(final CElectionDescription descr,
                                                 final CElectionDescriptionFunction function,
                                                 final String code) {
        function.setCode(code);
        handleDescrChange(descr);
        function.getExtractedLoops().clear();

        for (final WorkspaceUpdateListener l : updateListener) {
            l.handleDescrChangeUpdatedFunctionCode(descr, function, code);
        }
    }

    public final void addVotingSigFunctionToDescr(final CElectionDescription descr,
                                                  final String nameString) {
        if (descr.hasFunctionName(nameString)) {
            errorHandler.logAndDisplayError(
                    new BeastError(BeastErrorTypes.DUPLICATE_C_FUNC_NAME, nameString));
        } else {
            final VotingSigFunction func =
                    descr.createNewVotingSigFunctionAndAdd(nameString);
            handleDescrChange(descr);
            for (final WorkspaceUpdateListener l : updateListener) {
                l.handleDescrChangeAddedVotingSigFunction(descr, func);
            }
        }
    }

    public final void removeFunctionFromDescr(final CElectionDescription descr,
                                              final CElectionDescriptionFunction func) {
        if (func == descr.getVotingFunction()) {
            errorHandler.logAndDisplayError(
                    new BeastError(BeastErrorTypes.CANT_REMOVE_VOTING_FUNCTION,
                                   func.getName()));
        } else {
            descr.removeFunction(func);
            handleDescrChange(descr);
            for (final WorkspaceUpdateListener l : updateListener) {
                l.handleDescrChangeRemovedFunction(descr, func);
            }
        }
    }

    public final void findLoopBounds(final CElectionDescription descr,
                                     final CElectionDescriptionFunction func) {
        final String code = func.getCode();
        final List<ExtractedCLoop> loops =
                AntlrCLoopParser.findLoops(func.getName(), code, codeGenerationOptions);
        func.setExtractedLoops(loops);

        for (final WorkspaceUpdateListener l : updateListener) {
            l.handleExtractedFunctionLoops(descr, func);
        }
    }

    public final void shutdown() {
        beast.shutdown();
    }

    public final void letUserLoadDescr() {
        try {
            final Tuple<CElectionDescription, File> t =
                    FileDialogHelper
                    .letUserLoadElectionDescription(pathHandler.getElectionDescrDir(), null);
            if (t.first() != null) {
                addElectionDescription(t.first());
                addFileForDescr(t.first(), t.second());
            }

        } catch (IOException e) {
            errorHandler.logAndDisplayError(
                    new BeastError(BeastErrorTypes.IO_ERROR, "", e));
        }
    }

    public final void createTestConfig(final String nameString,
                                       final CElectionDescription descr,
                                       final PreAndPostConditionsDescription propDescr) {
        final TestConfiguration tc = new TestConfiguration(descr, propDescr, nameString);
        final CBMCTestConfiguration configuration = new CBMCTestConfiguration();
        configuration.setDescr(descr);
        configuration.setPropDescr(propDescr);
        configuration.setName(nameString);
        tc.addCBMCTestConfiguration(configuration);
        testConfigList.add(tc);
        for (final WorkspaceUpdateListener l : updateListener) {
            l.handleAddedTestConfig(tc);
        }
    }

    public final void stopRun(final CBMCTestRunWithSymbolicVars run) {
        beast.stopRun(run);
    }

    public final void removeSymbolicVar(final PreAndPostConditionsDescription propDescr,
                                        final SymbolicCBMCVar selectedVar) {
        propDescr.getCbmcVariables().remove(selectedVar);
        propDescrWithUnsavedChanges.add(propDescr);
        for (final WorkspaceUpdateListener l : updateListener) {
            l.handlePropDescrRemovedVar(propDescr, selectedVar);
        }
    }

    public final void addSimpleFunctionToDescr(final CElectionDescription descr,
                                               final SimpleTypeFunction f) {
        descr.addSimpleFunction(f);
        handleDescrChange(descr);
        for (final WorkspaceUpdateListener l : updateListener) {
            l.handleDescrChangeAddedSimpleFunction(descr, f);
        }
    }

    public final void editDescr(final CElectionDescription descr,
                                final String nameString,
                                final VotingInputTypes inType,
                                final VotingOutputTypes outType) {
        descr.setName(nameString);
        descr.setInputType(inType);
        descr.setOutputType(outType);
        handleDescrChange(descr);
        for (final WorkspaceUpdateListener l : updateListener) {
            l.handleDescrChangeInOutName(descr);
        }
    }

    public final void deleteCBMCRun(final CBMCTestRunWithSymbolicVars run) {
        testConfigList.deleteCBMCRun(run);
        for (final WorkspaceUpdateListener l : updateListener) {
            l.handleCBMCRunDeleted(run);
        }
    }

    public final void deleteTestConfig(final TestConfiguration tc) {
        testConfigList.deleteTestConfiguration(tc);
        for (final WorkspaceUpdateListener l : updateListener) {
            l.handleTestConfigDeleted(tc);
        }
    }

    private boolean askUserIfReallyDelete() {
        final Alert reallyRemove =
                new Alert(AlertType.CONFIRMATION,
                          "There are test configurations which refer to the item to be deleted, "
                                  + "which would also be removed. Are you sure?",
                          ButtonType.OK, ButtonType.NO);
        final Optional<ButtonType> res = reallyRemove.showAndWait();
        if (res.isPresent() || res.get().getButtonData().isCancelButton()) {
            return false;
        }
        return true;
    }

    public final void removeDescr(final CElectionDescription descr) {
        if (testConfigList.getTestConfigsByDescr().containsKey(descr)) {
            if (!askUserIfReallyDelete()) {
                return;
            }
        }
        testConfigList.removeAll(descr);
    }

    public final void removePropDescr(final PreAndPostConditionsDescription propDescr) {
        if (testConfigList.getTestConfigsByPropDescr().containsKey(propDescr)) {
            if (!askUserIfReallyDelete()) {
                return;
            }
        }
        testConfigList.removeAll(propDescr);
    }
}
