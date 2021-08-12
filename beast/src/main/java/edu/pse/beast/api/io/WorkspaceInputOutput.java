package edu.pse.beast.api.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.json.JSONArray;
import org.json.JSONObject;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.io.run.RunInputOutputHelper;
import edu.pse.beast.api.method.CElectionDescription;
import edu.pse.beast.api.property.PropertyDescription;
import edu.pse.beast.api.runner.propertycheck.run.PropertyCheckRun;
import edu.pse.beast.gui.configurationeditor.configuration.ConfigurationBatch;
import edu.pse.beast.gui.configurationeditor.configuration.ConfigurationList;
import edu.pse.beast.gui.configurationeditor.configuration.cbmc.Configuration;
import edu.pse.beast.gui.workspace.BeastWorkspace;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class WorkspaceInputOutput {
    private static final String DESCR_FILES_KEY = "descr_files";
    private static final String PROP_DESCR_FILES_KEY = "prop_" + DESCR_FILES_KEY;

    private static final String CODE_GEN_OPTIONS_KEY = "code_gen_options";

    private static final String CONFIG_LIST_KEY = "config_list";

    private static final String DESCR_UUID_KEY = "descr_uuid";
    private static final String PROP_DESCR_UUID_KEY = "prop_descr_uuid";
    private static final String CONFIG_NAME_KEY = "name";
    private static final String CBMC_CONFIG_LIST_KEY = "config";

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
    private static final String RUNS_KEY = "runs";

    private static JSONArray
                fileCollectionToJsonArr(final Collection<File> files,
                                        final RelativePathConverter relativePathConverter) {
        final JSONArray arr = new JSONArray();
        for (final File f : files) {
            arr.put(relativePathConverter.getRelativePathTo(f));
        }
        return arr;
    }

    private static JSONObject cbmcConfigToJSON(final Configuration config,
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

        json.put(CONFIG_NAME_KEY, config.getName());
        json.put(START_RUNS_ON_CREATION_KEY, config.getStartRunsOnCreation());

        json.put(RUNS_KEY, RunInputOutputHelper.runListToJSON(config.getRuns(), pathHandler));
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

    private static PropertyDescription propDescrByUUID(final String uuid,
                                                       final List<PropertyDescription> propDescrs) {
        for (final PropertyDescription propDescr : propDescrs) {
            if (propDescr.getUuid().equals(uuid)) {
                return propDescr;
            }
        }
        return null;
    }

    private static Configuration
                cbmcPropertyConfigFromJson(final JSONObject json,
                                           final List<CElectionDescription> descrs,
                                           final List<PropertyDescription> propDescrs,
                                           final RelativePathConverter pathHandler) {
        final Configuration cbmcPropertyConfiguration = new Configuration();

        final int minVoters = json.getInt(MIN_VOTERS_KEY);
        final int minCands = json.getInt(MIN_CANDS_KEY);
        final int minSeats = json.getInt(MIN_SEATS_KEY);

        cbmcPropertyConfiguration.setMinVoters(minVoters);
        cbmcPropertyConfiguration.setMinCands(minCands);
        cbmcPropertyConfiguration.setMinSeats(minSeats);

        final int maxVoters = json.getInt(MAX_VOTERS_KEY);
        final int maxCands = json.getInt(MAX_CANDS_KEY);
        final int maxSeats = json.getInt(MAX_SEATS_KEY);

        cbmcPropertyConfiguration.setMaxVoters(maxVoters);
        cbmcPropertyConfiguration.setMaxCands(maxCands);
        cbmcPropertyConfiguration.setMaxSeats(maxSeats);

        final String name = json.getString(CONFIG_NAME_KEY);

        cbmcPropertyConfiguration.setName(name);

        final boolean startRunsOnCreation = json.getBoolean(START_RUNS_ON_CREATION_KEY);

        cbmcPropertyConfiguration.setStartRunsOnCreation(startRunsOnCreation);

        final String descrUUID = json.getString(DESCR_UUID_KEY);
        final String propDescrUUID = json.getString(PROP_DESCR_UUID_KEY);

        final CElectionDescription descr = descrByUUID(descrUUID, descrs);
        if (descr == null) {
            throw new NotImplementedException();
        } else {
            cbmcPropertyConfiguration.setDescr(descr);
        }

        final PropertyDescription propDescr = propDescrByUUID(propDescrUUID, propDescrs);
        if (propDescr == null) {
            throw new NotImplementedException();
        } else {
            cbmcPropertyConfiguration.setPropDescr(propDescr);
        }

        final List<PropertyCheckRun> cbmcRuns =
                RunInputOutputHelper
                .runListFromJSONArr(json.getJSONArray(RUNS_KEY), descr, propDescr,
                                    cbmcPropertyConfiguration, pathHandler);
        cbmcPropertyConfiguration.addRuns(cbmcRuns);
        return cbmcPropertyConfiguration;
    }

    private static JSONArray
                cbmcConfigCollectionToJSON(final Collection<Configuration> collection,
                                           final RelativePathConverter pathHandler) {
        final JSONArray arr = new JSONArray();
        for (final Configuration config : collection) {
            arr.put(cbmcConfigToJSON(config, pathHandler));
        }
        return arr;
    }

    private static List<Configuration>
                cbmcPropertyConfigListFromJsonArr(final JSONArray arr,
                                                  final List<CElectionDescription> descrs,
                                                  final List<PropertyDescription> propDescrs,
                                                  final RelativePathConverter pathHandler) {
        final List<Configuration> list = new ArrayList<Configuration>();
        for (int i = 0; i < arr.length(); ++i) {
            list.add(cbmcPropertyConfigFromJson(arr.getJSONObject(i), descrs,
                                                propDescrs, pathHandler));
        }
        return list;
    }

    private static JSONObject configurationToJSON(final ConfigurationBatch tc,
                                                  final RelativePathConverter pathHandler) {
        final JSONObject json = new JSONObject();
        json.put(DESCR_UUID_KEY, tc.getDescr().getUuid());
        json.put(PROP_DESCR_UUID_KEY, tc.getPropDescr().getUuid());
        json.put(CONFIG_NAME_KEY, tc.getName());
        json.put(CBMC_CONFIG_LIST_KEY,
                 cbmcConfigCollectionToJSON(tc.getConfigurationsByName().values(), pathHandler));
        return json;
    }

    private static ConfigurationBatch configFromJson(final JSONObject json,
                                                     final List<CElectionDescription> descrs,
                                                     final List<PropertyDescription> pDescrs,
                                                     final RelativePathConverter pathHandler) {
        final String descrUUID = json.getString(DESCR_UUID_KEY);
        final String propDescrUUID = json.getString(PROP_DESCR_UUID_KEY);
        final String name = json.getString(CONFIG_NAME_KEY);
        final List<Configuration> cbmcConfigs =
                cbmcPropertyConfigListFromJsonArr(json.getJSONArray(CBMC_CONFIG_LIST_KEY),
                                                  descrs, pDescrs, pathHandler);

        final CElectionDescription descr = descrByUUID(descrUUID, descrs);
        if (descr == null) {
            throw new NotImplementedException();
        }

        final PropertyDescription propDescr = propDescrByUUID(propDescrUUID, pDescrs);
        if (propDescr == null) {
            throw new NotImplementedException();
        }

        final ConfigurationBatch config = new ConfigurationBatch(descr, propDescr, name);
        config.addCBMCConfigurations(cbmcConfigs);
        return config;
    }

    private static JSONArray configurationListToJSON(final ConfigurationList list,
                                                     final RelativePathConverter pathHandler) {
        final JSONArray arr = new JSONArray();
        for (final List<ConfigurationBatch> tcl : list.getConfigsByDescr().values()) {
            for (final ConfigurationBatch tc : tcl) {
                arr.put(configurationToJSON(tc, pathHandler));
            }
        }
        return arr;
    }

    static ConfigurationList configListFromJsonArr(final JSONArray arr,
                                                   final List<CElectionDescription> descrs,
                                                   final List<PropertyDescription> pDes,
                                                   final RelativePathConverter pathHandler) {
        final ConfigurationList configurationList = new ConfigurationList();
        for (int i = 0; i < arr.length(); ++i) {
            configurationList.add(configFromJson(arr.getJSONObject(i), descrs, pDes, pathHandler));
        }
        return configurationList;
    }

    private static JSONObject
                    generateWorkspaceJSON(final BeastWorkspace ws,
                                          final RelativePathConverter relativePathConverter) {
        final JSONObject json = new JSONObject();
        json.put(DESCR_FILES_KEY, fileCollectionToJsonArr(
                ws.getFilesPerDescr().values(), relativePathConverter));
        json.put(PROP_DESCR_FILES_KEY, fileCollectionToJsonArr(
                ws.getFilesPerPropDescr().values(), relativePathConverter));
        json.put(CODE_GEN_OPTIONS_KEY, CodeGenOptionsInputOutputHelper
                .codeGenOptionsToJSON(ws.getCodeGenOptions()));
        json.put(CONFIG_LIST_KEY, configurationListToJSON(
                ws.getConfigList(), relativePathConverter));
        return json;
    }

    public static BeastWorkspace
                    loadBeastWorkspaceFromFile(final File f,
                                               final RelativePathConverter relativePathConverter)
                                                       throws IOException {
        final BeastWorkspace beastWorkspace = new BeastWorkspace();
        final String fileContents = InputOutputInterface.readStringFromFile(f);
        final JSONObject json = new JSONObject(fileContents);

        final JSONArray descrArr = json.getJSONArray(DESCR_FILES_KEY);
        final List<CElectionDescription> descrs = new ArrayList<CElectionDescription>();

        for (int i = 0; i < descrArr.length(); ++i) {
            final String relativePath = descrArr.getString(i);
            final File descrFile =
                    relativePathConverter.getFileFromRelativePath(relativePath);
            final CElectionDescription loadedDescr = InputOutputInterface.loadCElection(descrFile);
            descrs.add(loadedDescr);
            beastWorkspace.addElectionDescription(loadedDescr);
            beastWorkspace.addFileForDescr(loadedDescr, descrFile);
        }

        final JSONArray propDescrArr = json.getJSONArray(PROP_DESCR_FILES_KEY);
        final List<PropertyDescription> propDescrs = new ArrayList<PropertyDescription>();

        for (int i = 0; i < propDescrArr.length(); ++i) {
            final String relativePath = propDescrArr.getString(i);
            final File propDescrFile =
                    relativePathConverter.getFileFromRelativePath(relativePath);
            final PropertyDescription loadedPropDescr =
                    InputOutputInterface.loadPropertyDescription(propDescrFile);
            propDescrs.add(loadedPropDescr);
            beastWorkspace.addPropertyDescription(loadedPropDescr);
            beastWorkspace.addFileForPropDescr(loadedPropDescr, propDescrFile);
        }
        final CodeGenOptions codeGenOptions =
                CodeGenOptionsInputOutputHelper.codeGenOptionsFromJSON(
                        json.getJSONObject(CODE_GEN_OPTIONS_KEY));
        final ConfigurationList configList =
                configListFromJsonArr(json.getJSONArray(CONFIG_LIST_KEY),
                                      descrs, propDescrs, relativePathConverter);
        beastWorkspace.setConfigList(configList);
        beastWorkspace.setCodeGenOptions(codeGenOptions);
        beastWorkspace.setWorkspaceFile(f);
        beastWorkspace.setPathHandler(new PathHandler());

        return beastWorkspace;
    }

    public static void storeWorkspace(final BeastWorkspace beastWorkspace, final File f,
                                      final RelativePathConverter relativePathConverter)
                                              throws IOException {
        final JSONObject json = generateWorkspaceJSON(beastWorkspace, relativePathConverter);
        InputOutputInterface.writeStringToFile(f, json.toString(2));
    }
}
