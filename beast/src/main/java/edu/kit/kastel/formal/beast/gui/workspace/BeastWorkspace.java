package edu.kit.kastel.formal.beast.gui.workspace;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import edu.kit.kastel.formal.beast.api.BEAST;
import edu.kit.kastel.formal.beast.api.codegen.cbmc.CodeGenOptions;
import edu.kit.kastel.formal.beast.api.codegen.cbmc.SymbolicVariable;
import edu.kit.kastel.formal.beast.api.cparser.AntlrCLoopParser;
import edu.kit.kastel.formal.beast.api.cparser.ExtractedCLoop;
import edu.kit.kastel.formal.beast.api.io.InputOutputInterface;
import edu.kit.kastel.formal.beast.api.io.PathHandler;
import edu.kit.kastel.formal.beast.api.method.CElectionDescription;
import edu.kit.kastel.formal.beast.api.method.VotingInputType;
import edu.kit.kastel.formal.beast.api.method.VotingOutputType;
import edu.kit.kastel.formal.beast.api.method.function.CElectionDescriptionFunction;
import edu.kit.kastel.formal.beast.api.method.function.SimpleTypeFunction;
import edu.kit.kastel.formal.beast.api.method.function.VotingSigFunction;
import edu.kit.kastel.formal.beast.api.property.FormalPropertyDescription;
import edu.kit.kastel.formal.beast.api.property.PropertyDescription;
import edu.kit.kastel.formal.beast.api.runner.codefile.CodeFileData;
import edu.kit.kastel.formal.beast.api.runner.propertycheck.PropertyCheckWorkUnit;
import edu.kit.kastel.formal.beast.api.runner.propertycheck.run.PropertyCheckRun;
import edu.kit.kastel.formal.beast.api.toolbox.Tuple;
import edu.kit.kastel.formal.beast.gui.FileDialogHelper;
import edu.kit.kastel.formal.beast.gui.configurationeditor.configuration.ConfigurationBatch;
import edu.kit.kastel.formal.beast.gui.configurationeditor.configuration.ConfigurationList;
import edu.kit.kastel.formal.beast.gui.configurationeditor.configuration.cbmc.Configuration;
import edu.kit.kastel.formal.beast.gui.error.BeastError;
import edu.kit.kastel.formal.beast.gui.error.BeastErrorType;
import edu.kit.kastel.formal.beast.gui.error.ErrorHandler;
import edu.kit.kastel.formal.beast.gui.processhandler.CBMCProcessHandlerCreator;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class BeastWorkspace {
    private static final String NONE = "";
    private static final String ASSUME = "assume";
    private static final String ASSERT = "assert";

    private static final String SAVE_PROPERTY_DIALOG_TITLE = "Choose Save File";
    private static final String OPEN_WS_DIALOG_TITLE = "Open the Workspace File";
    private static final String WORKSPACE_STRING = "Workspace";
    private static final String SAVE_WORKSPACE_TITLE = "File for Workspace";

    private static final String DEFAULT_WORKSPACE_NAME = "new_workspace";

    private static final String LOAD_PROPERTY_ERROR_MESSAGE =
            "Trying to Load Property Description";
    private static final String CONFIRMATION_MESSAGE_STRING =
            "There are configurations which refer to the item to be deleted, "
                    + "which would also be removed. Are you sure?";

    private static final String BEASTWS_FILE_ENDING = ".beastws";
    private static final String BELEC_FILE_ENDING = ".belec";
    private static final String BPRP_FILE_ENDING = ".bprp";

    private static final String VOTES_VAR_NAME = "votes";
    private static final String CANDIDATE_AMOUNT_VAR = "C";
    private static final String VOTER_AMOUNT_VAR = "V";
    private static final String SEAT_AMOUNT_VAR = "S";

    private static final String MAX_CANDIDATES = "MAX_CANDIDATES";
    private static final String MAX_VOTERS = "MAX_VOTERS";
    private static final String MAX_SEATS = "MAX_SEATS";

    private static final String THE_FUNCTION = "The function ";
    private static final String LOOPBOUNDS_NOT_DESCRIBED =
            " has loopbounds which are not described.";

    private CBMCProcessHandlerCreator cbmcProcessHandlerCreator;

    private BEAST beast = new BEAST();
    private List<WorkspaceUpdateListener> updateListener = new ArrayList<WorkspaceUpdateListener>();
    private ErrorHandler errorHandler;

    private List<CElectionDescription> loadedDescrs = new ArrayList<CElectionDescription>();
    private Map<CElectionDescription, File> filesPerDescr =
            new LinkedHashMap<CElectionDescription, File>();
    private Set<CElectionDescription> descrWithUnsavedChanges =
            new LinkedHashSet<CElectionDescription>();

    private List<PropertyDescription> loadedPropDescrs = new ArrayList<PropertyDescription>();
    private Map<PropertyDescription, File> filesPerPropDescr =
            new LinkedHashMap<PropertyDescription, File>();
    private Set<PropertyDescription> propDescrWithUnsavedChanges =
            new LinkedHashSet<PropertyDescription>();

    private CodeGenOptions codeGenerationOptions;
    private ConfigurationList configurationList = new ConfigurationList();

    private String name = DEFAULT_WORKSPACE_NAME;
    private File workspaceFile;

    private PathHandler pathHandler;

    private static <K> File getFile(final K descr,
                                    final String name,
                                    final Map<K, File> filesPerDescription,
                                    final File directory,
                                    final String fileEnding,
                                    final String dialogTitle) {
        File f = filesPerDescription.get(descr);
        final String fileName = f != null ? f.getName() : name + fileEnding;
        f = FileDialogHelper.letUserSaveFile(directory, dialogTitle, fileName);
        if (f != null && filesPerDescription.containsKey(descr)
                && f.getParentFile().equals(directory)
                && f.getName().equals(fileName)) {
            f = filesPerDescription.get(descr);
        } else if (f != null) {
            filesPerDescription.put(descr, f);
        }
        return f;
    }

    private static boolean askUserIfReallyDelete() {
        final Alert reallyRemove =
                new Alert(AlertType.CONFIRMATION,
                          CONFIRMATION_MESSAGE_STRING,
                          ButtonType.OK, ButtonType.NO);
        final Optional<ButtonType> res = reallyRemove.showAndWait();
        return !res.isPresent() && !res.get().getButtonData().isCancelButton();
    }

    private void messageUpdateListener() {
        for (final WorkspaceUpdateListener l : updateListener) {
            l.handleWorkspaceUpdateGeneric();
        }
    }

    private boolean saveAll(final Set<CElectionDescription> unchangedMethods,
                            final Set<PropertyDescription> unchangedProperties) {
        final List<CElectionDescription> descrListCopy =
                new ArrayList<CElectionDescription>(unchangedMethods);
        boolean res = true;
        for (final CElectionDescription descr : descrListCopy) {
            res &= descr != null && saveDescr(descr);
        }
        final List<PropertyDescription> propDescrListCopy =
                new ArrayList<PropertyDescription>(unchangedProperties);
        for (final PropertyDescription propDescr : propDescrListCopy) {
            res &= savePropDescr(propDescr);
        }
        return res;
    }

    private void loadWorkspace(final BeastWorkspace ws) {
        loadedDescrs = ws != null ? ws.loadedDescrs : new ArrayList<CElectionDescription>();
        filesPerDescr =
                ws != null ? ws.filesPerDescr : new LinkedHashMap<CElectionDescription, File>();
        descrWithUnsavedChanges =
                ws != null ? ws.descrWithUnsavedChanges : new LinkedHashSet<CElectionDescription>();
        loadedPropDescrs = ws != null ? ws.loadedPropDescrs : new ArrayList<PropertyDescription>();
        filesPerPropDescr =
                ws != null ? ws.filesPerPropDescr : new LinkedHashMap<PropertyDescription, File>();
        propDescrWithUnsavedChanges = ws != null
                ? ws.propDescrWithUnsavedChanges : new LinkedHashSet<PropertyDescription>();
        codeGenerationOptions = ws != null ? ws.codeGenerationOptions : null;
        setConfigList(ws != null ? ws.configurationList : new ConfigurationList());
        name = ws != null ? ws.name : DEFAULT_WORKSPACE_NAME;
        workspaceFile = ws != null ? ws.workspaceFile : null;
        pathHandler = ws != null ? ws.pathHandler : null;
        messageUpdateListener();
    }

    private void handleDescrChange(final CElectionDescription descr) {
        descrWithUnsavedChanges.add(descr);
    }

    public static final BeastWorkspace
            getStandardWorkspace(final CBMCProcessHandlerCreator processHandlerCreator) {
        final BeastWorkspace beastWorkspace = new BeastWorkspace();
        beastWorkspace.cbmcProcessHandlerCreator = processHandlerCreator;

        final CodeGenOptions codeGenOptions = new CodeGenOptions();
        codeGenOptions.setCbmcAmountMaxCandidatesVarName(MAX_CANDIDATES);
        codeGenOptions.setCbmcAmountMaxVotersVarName(MAX_VOTERS);
        codeGenOptions.setCbmcAmountMaxSeatsVarName(MAX_SEATS);
        codeGenOptions.setCurrentVotesVarName(VOTES_VAR_NAME);
        codeGenOptions.setCurrentAmountCandsVarName(CANDIDATE_AMOUNT_VAR);
        codeGenOptions.setCurrentAmountVotersVarName(VOTER_AMOUNT_VAR);
        codeGenOptions.setCurrentAmountSeatsVarName(SEAT_AMOUNT_VAR);
        codeGenOptions.setCbmcAssertName(ASSERT);
        codeGenOptions.setCbmcAssumeName(ASSUME);

        beastWorkspace.setCodeGenOptions(codeGenOptions);
        try {
            beastWorkspace.setPathHandler(new PathHandler());
        } catch (IOException e) {
            e.printStackTrace();
        }
        beastWorkspace.setConfigList(new ConfigurationList());
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

    public final List<PropertyDescription> getLoadedPropDescrs() {
        return loadedPropDescrs;
    }

    public final void addConfiguration(final ConfigurationBatch config) {
        configurationList.add(config);
    }

    public final void setConfigList(final ConfigurationList configList) {
        // remove the old list so it does not receive updates
        updateListener.remove(this.configurationList);
        this.configurationList = configList;
        for (final PropertyCheckRun run : configList.getRuns()) {
            final PropertyCheckWorkUnit wu =
                    new PropertyCheckWorkUnit(cbmcProcessHandlerCreator,
                                              UUID.randomUUID().toString());
            run.setAndInitializeWorkUnit(wu, pathHandler);
        }
        registerUpdateListener(configList);
    }

    public final ConfigurationList getConfigList() {
        return configurationList;
    }

    public final Map<CElectionDescription, File> getFilesPerDescr() {
        return filesPerDescr;
    }

    public final Map<PropertyDescription, File> getFilesPerPropDescr() {
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

    public final PropertyDescription getPropDescrByUUID(final String uuid) {
        for (final PropertyDescription propDescr : loadedPropDescrs) {
            if (propDescr.getUuid().equals(uuid)) {
                return propDescr;
            }
        }
        return null;
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
            final Tuple<PropertyDescription, File> t =
                    FileDialogHelper.letUserLoadPropDescr(pathHandler.getPropDescrDir(), null);
            if (t.first() != null) {
                addPropertyDescription(t.first());
                addFileForPropDescr(t.first(), t.second());
            }
        } catch (IOException e) {
            errorHandler.logAndDisplayError(
                    new BeastError(BeastErrorType.IO_ERROR,
                                   LOAD_PROPERTY_ERROR_MESSAGE,
                                   e));
        }
    }

    public final void addPropertyDescription(final PropertyDescription propDescr) {
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

    public final Map<CElectionDescription, List<ConfigurationBatch>>
                    getConfigsByElectionDescription() {
        return configurationList.getConfigsByDescr();
    }

    public final Map<PropertyDescription, List<ConfigurationBatch>>
                    getConfigsByPropertyDescription() {
        return configurationList.getConfigsByPropDescr();
    }

    public final void createRunsAndAddToConfig(final Configuration config) {
        try {
            final CElectionDescription descr = config.getDescr();
            for (final CElectionDescriptionFunction f : descr.getFunctions()) {
                if (!f.allLoopsDescribed()) {
                    final String more = THE_FUNCTION + f.getName() + LOOPBOUNDS_NOT_DESCRIBED;
                    errorHandler.logAndDisplayError(
                            new BeastError(BeastErrorType.NOT_ALL_LOOPS_DESCRIBED, more));
                    return;
                }
            }
            descr.generateLoopBoundHandler();
            final CodeFileData codeFile =
                    beast.generateCodeFilePropertyCheck(config.getDescr(), config.getPropDescr(),
                                                        codeGenerationOptions, pathHandler);
            final List<PropertyCheckRun> createdRuns =
                    beast.generateCheckRuns(codeFile, config, codeGenerationOptions);
            final String sessionUUID = UUID.randomUUID().toString();
            for (final PropertyCheckRun run : createdRuns) {
                final PropertyCheckWorkUnit workUnit =
                        new PropertyCheckWorkUnit(cbmcProcessHandlerCreator, sessionUUID);
                run.setAndInitializeWorkUnit(workUnit, pathHandler);
            }
            config.addRuns(createdRuns);
            for (final WorkspaceUpdateListener l : updateListener) {
                l.handleWorkspaceUpdateAddedRuns(config, createdRuns);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public final void addRunToQueue(final PropertyCheckRun run) {
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

    public final void addFileForPropDescr(final PropertyDescription loadedPropDescr,
                                          final File propDescrFile) {
        filesPerPropDescr.put(loadedPropDescr, propDescrFile);
    }

    public final void updateFilesForRuns(final Configuration currentConfig)
            throws IOException {
        for (final CElectionDescriptionFunction f : currentConfig.getDescr().getFunctions()) {
            if (!f.allLoopsDescribed()) {
                final String more =
                        THE_FUNCTION + f.getName() + LOOPBOUNDS_NOT_DESCRIBED;
                errorHandler.logAndDisplayError(
                        new BeastError(BeastErrorType.NOT_ALL_LOOPS_DESCRIBED, more));
                return;
            }
        }

        final CodeFileData codeFileData =
                beast.generateCodeFilePropertyCheck(currentConfig.getDescr(),
                                                    currentConfig.getPropDescr(),
                                                    codeGenerationOptions, pathHandler);
        for (final PropertyCheckRun checkRun : currentConfig.getRuns()) {
            checkRun.updateDataForCheck(codeFileData, checkRun.getCodeGenerationOptions());
        }
        for (final WorkspaceUpdateListener l : updateListener) {
            l.handleConfigurationUpdatedFiles(currentConfig);
        }
    }

    public final void addVariableToPropertyDescription(final PropertyDescription currentPropDescr,
                                                       final SymbolicVariable var) {
        try {
            currentPropDescr.addVariable(var);
        } catch (IllegalArgumentException e) {
            errorHandler.logAndDisplayError(
                    new BeastError(BeastErrorType.CBMC_VAR_NAME_ALREADY_EXISTS,
                                   var.getName(), e));
            return;
        }
        propDescrWithUnsavedChanges.add(currentPropDescr);
        for (final WorkspaceUpdateListener l : updateListener) {
            l.handleWorkspaceUpdateAddedVarToPropDescr(currentPropDescr, var);
        }
    }

    public final void updateCodeForPropDescr(final String code,
                                             final FormalPropertyDescription conditionDescription,
                                             final PropertyDescription propDescr) {
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
        final File f = getFile(descr, descr.getName(), filesPerDescr,
                               pathHandler.getElectionDescrDir(), BELEC_FILE_ENDING,
                               SAVE_PROPERTY_DIALOG_TITLE);
        if (f == null) {
            return false;
        }
        try {
            InputOutputInterface.storeCElection(descr, f);
            descrWithUnsavedChanges.remove(descr);
        } catch (IOException e) {
            errorHandler.logAndDisplayError(
                    new BeastError(BeastErrorType.IO_ERROR, descr.getName(), e));
            return false;
        }
        return true;
    }

    public final boolean savePropDescr(final PropertyDescription propDescr) {
        final File f = getFile(propDescr, propDescr.getName(), filesPerPropDescr,
                               pathHandler.getPropDescrDir(), BPRP_FILE_ENDING,
                               SAVE_PROPERTY_DIALOG_TITLE);
        if (f == null) {
            return false;
        }
        try {
            InputOutputInterface.storePropertyDescription(propDescr, f);
            propDescrWithUnsavedChanges.remove(propDescr);
        } catch (IOException e) {
            errorHandler.logAndDisplayError(
                    new BeastError(BeastErrorType.IO_ERROR, propDescr.getName(), e));
            return false;
        }
        return true;
    }

    public final void setWorkspaceFile(final File file) {
        this.workspaceFile = file;
    }

    public final boolean letUserLoadWorkSpace() {
        boolean loaded = false;
        final File f =
                FileDialogHelper
                .letUserOpenFile(WORKSPACE_STRING, BEASTWS_FILE_ENDING,
                                 OPEN_WS_DIALOG_TITLE,
                                 pathHandler.getWorkspaceDir(),
                                 null);
        if (f != null) {
            try {
                final BeastWorkspace ws =
                        InputOutputInterface.loadBeastWorkspace(f, pathHandler);
                loaded = true;
                loadWorkspace(ws);
            } catch (IOException e) {
                e.printStackTrace();
                errorHandler.logAndDisplayError(
                        new BeastError(BeastErrorType.IO_ERROR, WORKSPACE_STRING, e));

            }
        }
        return loaded;
    }

    public final void newWorkspace() {
        loadWorkspace(null);
    }

    public final void saveWorkspace() {
        File f = workspaceFile;
        final String fileName = f != null ? f.getName() : name + BEASTWS_FILE_ENDING;
        f = FileDialogHelper.letUserSaveFile(pathHandler.getWorkspaceDir(),
                                             SAVE_WORKSPACE_TITLE,
                                             fileName);
        if (f == null) {
            return;
        } else if (f.getParentFile().equals(pathHandler.getWorkspaceDir())
                && f.getName().equals(fileName)) {
            f = workspaceFile;
        }
        // Now save voting method and property
        final boolean allSaved = saveAll(descrWithUnsavedChanges, propDescrWithUnsavedChanges);
        if (!allSaved) {
            errorHandler.logAndDisplayError(
                    new BeastError(BeastErrorType.ERROR_WHEN_SAVING_ALL, NONE));
        }
        try {
            InputOutputInterface.storeBeastWorkspace(this, f, pathHandler);
        } catch (IOException e) {
            errorHandler.logAndDisplayError(
                    new BeastError(BeastErrorType.IO_ERROR, NONE, e));
        }
    }

    public final Map<String, List<ConfigurationBatch>> getConfigsByElectionDescriptionName() {
        final Map<String, List<ConfigurationBatch>> map =
                new LinkedHashMap<String, List<ConfigurationBatch>>();
        for (final CElectionDescription descr
                : getConfigsByElectionDescription().keySet()) {
            map.put(descr.getName(),
                    getConfigsByElectionDescription().get(descr));
        }
        return map;
    }

    public final Map<String, List<ConfigurationBatch>> getConfigsByPropertyDescriptionName() {
        final Map<String, List<ConfigurationBatch>> map =
                new LinkedHashMap<String, List<ConfigurationBatch>>();
        for (final PropertyDescription propDescr
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
                    new BeastError(BeastErrorType.DUPLICATE_C_FUNC_NAME, nameString));
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
                    new BeastError(BeastErrorType.CANNOT_REMOVE_VOTING_FUNCTION,
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
                    new BeastError(BeastErrorType.IO_ERROR, NONE, e));
        }
    }

    public final void createConfiguration(final String nameString,
                                          final CElectionDescription descr,
                                          final PropertyDescription propDescr) {
        final ConfigurationBatch tc = new ConfigurationBatch(descr, propDescr, nameString);
        final Configuration configuration = new Configuration();
        configuration.setDescr(descr);
        configuration.setPropDescr(propDescr);
        configuration.setName(nameString);
        tc.addConfiguration(configuration);
        configurationList.add(tc);
        for (final WorkspaceUpdateListener l : updateListener) {
            l.handleAddedConfiguration(tc);
        }
    }

    public final void stopRun(final PropertyCheckRun run) {
        beast.stopRun(run);
    }

    public final void removeSymbolicVar(final PropertyDescription propDescr,
                                        final SymbolicVariable selectedVar) {
        propDescr.getVariables().remove(selectedVar);
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
                                final VotingInputType inType,
                                final VotingOutputType outType) {
        descr.setName(nameString);
        descr.setInputType(inType);
        descr.setOutputType(outType);
        handleDescrChange(descr);
        for (final WorkspaceUpdateListener l : updateListener) {
            l.handleDescrChangeInOutName(descr);
        }
    }

    public final void deleteCBMCRun(final PropertyCheckRun run) {
        configurationList.deleteRun(run);
        for (final WorkspaceUpdateListener l : updateListener) {
            l.handleRunDeleted(run);
        }
    }

    public final void deleteConfiguration(final ConfigurationBatch tc) {
        configurationList.deleteConfiguration(tc);
        for (final WorkspaceUpdateListener l : updateListener) {
            l.handleConfigurationDeleted(tc);
        }
    }

    public final boolean removeDescr(final CElectionDescription descr) {
        if (configurationList.getConfigsByDescr().containsKey(descr)) {
            if (!askUserIfReallyDelete()) {
                return false;
            }
        }
        configurationList.removeAll(descr);
        return true;
    }

    public final boolean removePropDescr(final PropertyDescription propDescr) {
        if (configurationList.getConfigsByPropDescr().containsKey(propDescr)) {
            if (!askUserIfReallyDelete()) {
                return false;
            }
        }
        configurationList.removeAll(propDescr);
        return true;
    }
}
