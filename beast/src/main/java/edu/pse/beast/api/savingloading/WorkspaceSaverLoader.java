package edu.pse.beast.api.savingloading;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.json.JSONArray;
import org.json.JSONObject;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.descr.c_electiondescription.CElectionDescription;
import edu.pse.beast.api.descr.property_description.PreAndPostConditionsDescription;
import edu.pse.beast.api.paths.PathHandler;
import edu.pse.beast.api.savingloading.testruns.CBMCTestRunSaverLoaderHelper;
import edu.pse.beast.api.testrunner.propertycheck.symbolic_vars.CBMCTestRunWithSymbolicVars;
import edu.pse.beast.gui.testconfigeditor.testconfig.TestConfiguration;
import edu.pse.beast.gui.testconfigeditor.testconfig.TestConfigurationList;
import edu.pse.beast.gui.testconfigeditor.testconfig.cbmc.CBMCTestConfiguration;
import edu.pse.beast.gui.workspace.BeastWorkspace;

public class WorkspaceSaverLoader {
    private static final String DESCR_FILES_KEY = "descr_files";
    private static final String PROP_DESCR_FILES_KEY = "prop_"+ DESCR_FILES_KEY;

    private static final String CODE_GEN_OPTIONS_KEY = "code_gen_options";

    private static final String TEST_CONFIG_LIST_KEY = "test_config_list";

    private static final String DESCR_UUID_KEY = "descr_uuid";
    private static final String PROP_DESCR_UUID_KEY = "prop_descr_uuid";
    private static final String TEST_CONFIG_NAME_KEY = "name";
    private static final String CBMC_TEST_CONFIG_LIST_KEY = "cbmc_test_config";

    private static final String CANDS = "cands";
    private static final String VOTERS = "voters";
    private static final String SEATS = "seats";

    private static final String MIN = "min_";
    private static final String MIN_CANDS_KEY = MIN + CANDS;
    private static final String MIN_VOTERS_KEY = MIN + VOTERS;
    private static final String MIN_SEATS_KEY = MIN + SEATS;

    private static final String MAX = "max_";
    private static final String MAX_CANDS_KEY = MAX + CANDS;
    private static final String MAX_VOTERS_KEY = MAX + VOTERS;
    private static final String MAX_SEATS_KEY = MAX + SEATS;

    private static final String START_RUNS_ON_CREATION_KEY = "start_runs_on_creation";
    private static final String TEST_RUNS_KEY = "test_runs";

    private static JSONArray
                fileCollectionToJsonArr(final Collection<File> files,
                                        final RelativePathConverter relativePathConverter) {
        final JSONArray arr = new JSONArray();
        for (final File f : files) {
            arr.put(relativePathConverter.getRelativePathTo(f));
        }
        return arr;
    }

    private static JSONObject cbmcTestConfigToJSON(final CBMCTestConfiguration config,
                                                   final RelativePathConverter pathHandler) {
        final JSONObject json = new JSONObject();
        json.put(DESCR_UUID_KEY, config.getDescr().getUuid());
        json.put(PROP_DESCR_UUID_KEY, config.getPropDescr().getUuid());

        json.put(MIN_CANDS_KEY, config.getMinCands());
        json.put(MIN_VOTERS_KEY, config.getMinVoters());
        json.put(MIN_SEATS_KEY, config.getMinSeats());

        json.put(MAX_CANDS_KEY, config.getMaxCands());
        json.put(MAX_VOTERS_KEY, config.getMaxVoters());
        json.put(MAX_SEATS_KEY, config.getMaxSeats());

        json.put(TEST_CONFIG_NAME_KEY, config.getName());
        json.put(START_RUNS_ON_CREATION_KEY, config.getStartRunsOnCreation());

        json.put(TEST_RUNS_KEY,
                 CBMCTestRunSaverLoaderHelper
                .cbmcTestRunListToJSON(config.getRuns(), pathHandler));
        return json;
    }

    private static CElectionDescription descrByUUID(final String uuid,
                                                    final List<CElectionDescription> descrs) {
        for (final CElectionDescription descr : descrs) {
            if (descr.getUuid().equals(uuid)) {
                return descr;
            }
        }
        return null;
    }

    private static PreAndPostConditionsDescription
                propDescrByUUID(final String uuid,
                                final List<PreAndPostConditionsDescription> propDescrs) {
        for (final PreAndPostConditionsDescription propDescr : propDescrs) {
            if (propDescr.getUuid().equals(uuid)) {
                return propDescr;
            }
        }
        return null;
    }

    private static CBMCTestConfiguration
                cbmcPropTestConfigFromJson(final JSONObject json,
                                           final List<CElectionDescription> descrs,
                                           final List<PreAndPostConditionsDescription> propDescrs,
                                           final RelativePathConverter pathHandler) {
        final CBMCTestConfiguration cbmcPropTestConfig = new CBMCTestConfiguration();

        final int minVoters = json.getInt(MIN_VOTERS_KEY);
        final int minCands = json.getInt(MIN_CANDS_KEY);
        final int minSeats = json.getInt(MIN_SEATS_KEY);

        cbmcPropTestConfig.setMinVoters(minVoters);
        cbmcPropTestConfig.setMinCands(minCands);
        cbmcPropTestConfig.setMinSeats(minSeats);

        final int maxVoters = json.getInt(MAX_VOTERS_KEY);
        final int maxCands = json.getInt(MAX_CANDS_KEY);
        final int maxSeats = json.getInt(MAX_SEATS_KEY);

        cbmcPropTestConfig.setMaxVoters(maxVoters);
        cbmcPropTestConfig.setMaxCands(maxCands);
        cbmcPropTestConfig.setMaxSeats(maxSeats);

        final String name = json.getString(TEST_CONFIG_NAME_KEY);

        cbmcPropTestConfig.setName(name);

        final boolean startRunsOnCreation =
                json.getBoolean(START_RUNS_ON_CREATION_KEY);

        cbmcPropTestConfig.setStartRunsOnCreation(startRunsOnCreation);

        final String descrUUID = json.getString(DESCR_UUID_KEY);
        final String propDescrUUID = json.getString(PROP_DESCR_UUID_KEY);

        final CElectionDescription descr = descrByUUID(descrUUID, descrs);
        if (descr == null) {
            throw new NotImplementedException();
        } else {
            cbmcPropTestConfig.setDescr(descr);
        }

        final PreAndPostConditionsDescription propDescr =
                propDescrByUUID(propDescrUUID, propDescrs);
        if (propDescr == null) {
            throw new NotImplementedException();
        } else {
            cbmcPropTestConfig.setPropDescr(propDescr);
        }

        final List<CBMCTestRunWithSymbolicVars> cbmcTestRuns =
                CBMCTestRunSaverLoaderHelper
                .cbmcTestRunListFromJsonArr(json.getJSONArray(TEST_RUNS_KEY),
                                            descr, propDescr, cbmcPropTestConfig,
                                            pathHandler);
        cbmcPropTestConfig.addRuns(cbmcTestRuns);
        return cbmcPropTestConfig;
    }

    private static JSONArray
                cbmcTestConfigCollectionToJSON(final Collection<CBMCTestConfiguration> collection,
                                               final RelativePathConverter pathHandler) {
        final JSONArray arr = new JSONArray();
        for (final CBMCTestConfiguration config : collection) {
            arr.put(cbmcTestConfigToJSON(config, pathHandler));
        }
        return arr;
    }

    private static List<CBMCTestConfiguration>
                cbmcPropTestConfigListFromJsonArr(final JSONArray arr,
                                                  final List<CElectionDescription> descrs,
                                                  final List<PreAndPostConditionsDescription>
                                                        propDescrs,
                                                  final RelativePathConverter pathHandler) {
        final List<CBMCTestConfiguration> list = new ArrayList<>();
        for (int i = 0; i < arr.length(); ++i) {
            list.add(cbmcPropTestConfigFromJson(arr.getJSONObject(i), descrs,
                                                propDescrs, pathHandler));
        }
        return list;
    }

    private static JSONObject testConfigurationToJSON(final TestConfiguration tc,
                                                      final RelativePathConverter pathHandler) {
        final JSONObject json = new JSONObject();
        json.put(DESCR_UUID_KEY, tc.getDescr().getUuid());
        json.put(PROP_DESCR_UUID_KEY, tc.getPropDescr().getUuid());
        json.put(TEST_CONFIG_NAME_KEY, tc.getName());
        json.put(CBMC_TEST_CONFIG_LIST_KEY,
                 cbmcTestConfigCollectionToJSON(tc.getCbmcTestConfigsByName().values(),
                                                pathHandler));
        return json;
    }

    private static TestConfiguration
                    testConfigFromJson(final JSONObject json,
                                       final List<CElectionDescription> descrs,
                                       final List<PreAndPostConditionsDescription> propDescrs,
                                       final RelativePathConverter pathHandler) {
        final String descrUUID = json.getString(DESCR_UUID_KEY);
        final String propDescrUUID = json.getString(PROP_DESCR_UUID_KEY);
        final String name = json.getString(TEST_CONFIG_NAME_KEY);
        final List<CBMCTestConfiguration> cbmcTestConfigs =
                cbmcPropTestConfigListFromJsonArr(json.getJSONArray(CBMC_TEST_CONFIG_LIST_KEY),
                                                  descrs, propDescrs, pathHandler);

        final CElectionDescription descr = descrByUUID(descrUUID, descrs);
        if (descr == null) {
            throw new NotImplementedException();
        }

        final PreAndPostConditionsDescription propDescr =
                propDescrByUUID(propDescrUUID, propDescrs);
        if (propDescr == null) {
            throw new NotImplementedException();
        }

        final TestConfiguration config =
                new TestConfiguration(descr, propDescr, name);
        config.addCBMCTestConfigurations(cbmcTestConfigs);
        return config;
    }

    private static JSONArray
                testConfigurationListToJSON(final TestConfigurationList list,
                                            final RelativePathConverter pathHandler) {
        final JSONArray arr = new JSONArray();
        for (final List<TestConfiguration> tcl : list.getTestConfigsByDescr().values()) {
            for (final TestConfiguration tc : tcl) {
                arr.put(testConfigurationToJSON(tc, pathHandler));
            }
        }
        return arr;
    }

    static TestConfigurationList
                testConfigListFromJsonArr(final JSONArray arr,
                                          final List<CElectionDescription> descrs,
                                          final List<PreAndPostConditionsDescription> propDescrs,
                                          final RelativePathConverter pathHandler) {
        final TestConfigurationList testConfigurationList = new TestConfigurationList();
        for (int i = 0; i < arr.length(); ++i) {
            testConfigurationList.add(testConfigFromJson(arr.getJSONObject(i),
                                                         descrs, propDescrs, pathHandler));
        }
        return testConfigurationList;
    }

    private static JSONObject
                    generateWorkspaceJSON(final BeastWorkspace ws,
                                          final RelativePathConverter relativePathConverter) {
        final JSONObject json = new JSONObject();
        json.put(DESCR_FILES_KEY, fileCollectionToJsonArr(
                ws.getFilesPerDescr().values(), relativePathConverter));
        json.put(PROP_DESCR_FILES_KEY, fileCollectionToJsonArr(
                ws.getFilesPerPropDescr().values(), relativePathConverter));
        json.put(CODE_GEN_OPTIONS_KEY, CodeGenOptionsSaverLoaderHelper
                .codeGenOptionsToJSON(ws.getCodeGenOptions()));
        json.put(TEST_CONFIG_LIST_KEY, testConfigurationListToJSON(
                ws.getTestConfigList(), relativePathConverter));
        return json;
    }

    public static BeastWorkspace
                    loadBeastWorkspaceFromFile(final File f,
                                               final RelativePathConverter relativePathConverter)
                                                       throws IOException {
        final BeastWorkspace beastWorkspace = new BeastWorkspace();
        final String fileContents = SavingLoadingInterface.readStringFromFile(f);
        final JSONObject json = new JSONObject(fileContents);

        final JSONArray descrArr = json.getJSONArray(DESCR_FILES_KEY);
        final List<CElectionDescription> descrs = new ArrayList<>();

        for (int i = 0; i < descrArr.length(); ++i) {
            final String relativePath = descrArr.getString(i);
            final File descrFile =
                    relativePathConverter.getFileFromRelativePath(relativePath);
            final CElectionDescription loadedDescr =
                    SavingLoadingInterface.loadCElection(descrFile);
            descrs.add(loadedDescr);
            beastWorkspace.addElectionDescription(loadedDescr);
            beastWorkspace.addFileForDescr(loadedDescr, descrFile);
        }

        final JSONArray propDescrArr = json.getJSONArray(PROP_DESCR_FILES_KEY);
        final List<PreAndPostConditionsDescription> propDescrs = new ArrayList<>();

        for (int i = 0; i < propDescrArr.length(); ++i) {
            final String relativePath = propDescrArr.getString(i);
            final File propDescrFile =
                    relativePathConverter.getFileFromRelativePath(relativePath);
            final PreAndPostConditionsDescription loadedPropDescr =
                    SavingLoadingInterface.loadPreAndPostConditionDescription(propDescrFile);
            propDescrs.add(loadedPropDescr);
            beastWorkspace.addPropertyDescription(loadedPropDescr);
            beastWorkspace.addFileForPropDescr(loadedPropDescr, propDescrFile);
        }
        final CodeGenOptions codeGenOptions =
                CodeGenOptionsSaverLoaderHelper.codeGenOptionsFromJSON(
                        json.getJSONObject(CODE_GEN_OPTIONS_KEY));
        final TestConfigurationList testConfigList =
                testConfigListFromJsonArr(json.getJSONArray(TEST_CONFIG_LIST_KEY),
                                          descrs, propDescrs, relativePathConverter);
        beastWorkspace.setTestConfigList(testConfigList);
        beastWorkspace.setCodeGenOptions(codeGenOptions);
        beastWorkspace.setWorkspaceFile(f);
        beastWorkspace.setPathHandler(new PathHandler());

        return beastWorkspace;
    }

    public static void storeWorkspace(final BeastWorkspace beastWorkspace,
                                      final File f,
                                      final RelativePathConverter relativePathConverter)
                                              throws IOException {
        final JSONObject json =
                generateWorkspaceJSON(beastWorkspace, relativePathConverter);
        SavingLoadingInterface.writeStringToFile(f, json.toString());
    }
}
