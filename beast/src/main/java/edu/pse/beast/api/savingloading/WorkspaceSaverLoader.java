package edu.pse.beast.api.savingloading;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.savingloading.testruns.CBMCTestRunSaverLoaderHelper;
import edu.pse.beast.api.testrunner.propertycheck.CBMCTestRun;
import edu.pse.beast.api.testrunner.propertycheck.processes.process_handler.CBMCProcessHandler;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.gui.paths.PathHandler;
import edu.pse.beast.gui.testconfigeditor.testconfig.TestConfiguration;
import edu.pse.beast.gui.testconfigeditor.testconfig.TestConfigurationList;
import edu.pse.beast.gui.testconfigeditor.testconfig.cbmc.CBMCTestConfiguration;
import edu.pse.beast.gui.workspace.BeastWorkspace;

public class WorkspaceSaverLoader {
	private static final String CBMC_PROCESS_STARTER_KEY = "cbmc_process_starter";

	private static final String DESCR_FILES_KEY = "descr_files";
	private static final String PROP_DESCR_FILES_KEY = "prop_descr_files";

	private static final String CODE_GEN_OPTIONS_KEY = "code_gen_options";

	private static final String TEST_CONFIG_LIST_KEY = "test_config_list";


	private static final String DESCR_UUID_KEY = "descr_uuid";
	private static final String PROP_DESCR_UUID_KEY = "prop_descr_uuid";
	private static final String TEST_CONFIG_NAME_KEY = "name";
	private static final String CBMC_TEST_CONFIG_LIST_KEY = "cbmc_test_config";

	private static final String MIN_CANDS_KEY = "min_cands";
	private static final String MIN_VOTERS_KEY = "min_voters";
	private static final String MIN_SEATS_KEY = "min_seats";

	private static final String MAX_CANDS_KEY = "max_cands";
	private static final String MAX_VOTERS_KEY = "max_voters";
	private static final String MAX_SEATS_KEY = "max_seats";

	private static final String CBMC_TEST_CONFIG_NAME_KEY = "name";

	private static final String START_RUNS_ON_CREATION_KEY = "start_runs_on_creation";
	private static final String TEST_RUNS_KEY = "test_runs";

	private static JSONArray fileCollectionToJsonArr(Collection<File> files) {
		JSONArray arr = new JSONArray();
		for (File f : files) {
			arr.put(f.getAbsolutePath());
		}
		return arr;
	}

	private static JSONObject cbmcTestConfigToJSON(
			CBMCTestConfiguration config) {
		JSONObject json = new JSONObject();

		json.put(DESCR_UUID_KEY, config.getDescr().getUuid());
		json.put(PROP_DESCR_UUID_KEY, config.getPropDescr().getUuid());

		json.put(MIN_CANDS_KEY, config.getMinCands());
		json.put(MIN_VOTERS_KEY, config.getMinVoters());
		json.put(MIN_SEATS_KEY, config.getMinSeats());

		json.put(MAX_CANDS_KEY, config.getMaxCands());
		json.put(MAX_VOTERS_KEY, config.getMaxVoters());
		json.put(MAX_SEATS_KEY, config.getMaxSeats());

		json.put(CBMC_TEST_CONFIG_NAME_KEY, config.getName());

		json.put(START_RUNS_ON_CREATION_KEY, config.getStartRunsOnCreation());

		json.put(TEST_RUNS_KEY, CBMCTestRunSaverLoaderHelper
				.cbmcTestRunListToJSON(config.getRuns()));

		return json;
	}

	private static CElectionDescription descrByUUID(String uuid,
			List<CElectionDescription> descrs) {
		for (CElectionDescription descr : descrs) {
			if (descr.getUuid().equals(uuid)) {
				return descr;
			}
		}
		return null;
	}

	private static PreAndPostConditionsDescription propDescrByUUID(String uuid,
			List<PreAndPostConditionsDescription> propDescrs) {
		for (PreAndPostConditionsDescription propDescr : propDescrs) {
			if (propDescr.getUuid().equals(uuid)) {
				return propDescr;
			}
		}
		return null;
	}

	private static CBMCTestConfiguration cbmcPropTestConfigFromJson(
			JSONObject json, List<CElectionDescription> descrs,
			List<PreAndPostConditionsDescription> propDescrs) {
		CBMCTestConfiguration cbmcPropTestConfig = new CBMCTestConfiguration();

		int minVoters = json.getInt(MIN_VOTERS_KEY);
		int minCands = json.getInt(MIN_CANDS_KEY);
		int minSeats = json.getInt(MIN_SEATS_KEY);

		cbmcPropTestConfig.setMinVoters(minVoters);
		cbmcPropTestConfig.setMinCands(minCands);
		cbmcPropTestConfig.setMinSeats(minSeats);;

		int maxVoters = json.getInt(MAX_VOTERS_KEY);
		int maxCands = json.getInt(MAX_CANDS_KEY);
		int maxSeats = json.getInt(MAX_SEATS_KEY);

		cbmcPropTestConfig.setMaxVoters(maxVoters);
		cbmcPropTestConfig.setMaxCands(maxCands);
		cbmcPropTestConfig.setMaxSeats(maxSeats);

		String name = json.getString(CBMC_TEST_CONFIG_NAME_KEY);

		cbmcPropTestConfig.setName(name);

		boolean startRunsOnCreation = json
				.getBoolean(START_RUNS_ON_CREATION_KEY);

		cbmcPropTestConfig.setStartRunsOnCreation(startRunsOnCreation);

		String descrUUID = json.getString(DESCR_UUID_KEY);
		String propDescrUUID = json.getString(PROP_DESCR_UUID_KEY);

		CElectionDescription descr = descrByUUID(descrUUID, descrs);
		if (descr == null) {
			// TODO ERROR
		} else {
			cbmcPropTestConfig.setDescr(descr);
		}

		PreAndPostConditionsDescription propDescr = propDescrByUUID(
				propDescrUUID, propDescrs);
		if (propDescr == null) {
			// TODO ERROR
		} else {
			cbmcPropTestConfig.setPropDescr(propDescr);
		}

		List<CBMCTestRun> cbmcTestRuns = CBMCTestRunSaverLoaderHelper
				.cbmcTestRunListFromJsonArr(json.getJSONArray(TEST_RUNS_KEY),
						descr, propDescr, cbmcPropTestConfig);

		cbmcPropTestConfig.addRuns(cbmcTestRuns);

		return cbmcPropTestConfig;
	}

	private static JSONArray cbmcTestConfigCollectionToJSON(
			Collection<CBMCTestConfiguration> collection) {
		JSONArray arr = new JSONArray();

		for (CBMCTestConfiguration config : collection) {
			arr.put(cbmcTestConfigToJSON(config));
		}

		return arr;
	}

	private static List<CBMCTestConfiguration> cbmcPropTestConfigListFromJsonArr(
			JSONArray arr, List<CElectionDescription> descrs,
			List<PreAndPostConditionsDescription> propDescrs) {
		List<CBMCTestConfiguration> list = new ArrayList<>();

		for (int i = 0; i < arr.length(); ++i) {
			list.add(cbmcPropTestConfigFromJson(arr.getJSONObject(i), descrs,
					propDescrs));
		}

		return list;
	}

	private static JSONObject testConfigurationToJSON(TestConfiguration tc) {
		JSONObject json = new JSONObject();

		json.put(DESCR_UUID_KEY, tc.getDescr().getUuid());
		json.put(PROP_DESCR_UUID_KEY, tc.getPropDescr().getUuid());
		json.put(TEST_CONFIG_NAME_KEY, tc.getName());
		json.put(CBMC_TEST_CONFIG_LIST_KEY, cbmcTestConfigCollectionToJSON(
				tc.getCbmcTestConfigsByName().values()));

		return json;
	}

	private static TestConfiguration testConfigFromJson(JSONObject json,
			List<CElectionDescription> descrs,
			List<PreAndPostConditionsDescription> propDescrs) {

		String descrUUID = json.getString(DESCR_UUID_KEY);
		String propDescrUUID = json.getString(PROP_DESCR_UUID_KEY);
		String name = json.getString(TEST_CONFIG_NAME_KEY);
		List<CBMCTestConfiguration> cbmcTestConfigs = cbmcPropTestConfigListFromJsonArr(
				json.getJSONArray(CBMC_TEST_CONFIG_LIST_KEY), descrs,
				propDescrs);

		CElectionDescription descr = descrByUUID(descrUUID, descrs);
		if (descr == null) {
			// TODO ERROR
		}

		PreAndPostConditionsDescription propDescr = propDescrByUUID(
				propDescrUUID, propDescrs);
		if (propDescr == null) {
			// TODO ERROR
		}

		TestConfiguration config = new TestConfiguration(descr, propDescr,
				name);
		config.addCBMCTestConfigurations(cbmcTestConfigs);
		return config;
	}

	private static JSONArray testConfigurationListToJSON(
			TestConfigurationList list) {
		JSONArray arr = new JSONArray();

		for (List<TestConfiguration> tcl : list.getTestConfigsByDescr()
				.values()) {
			for (TestConfiguration tc : tcl) {
				arr.put(testConfigurationToJSON(tc));
			}
		}

		return arr;
	}

	static TestConfigurationList testConfigListFromJsonArr(JSONArray arr,
			List<CElectionDescription> descrs,
			List<PreAndPostConditionsDescription> propDescrs) {
		TestConfigurationList testConfigurationList = new TestConfigurationList();

		for (int i = 0; i < arr.length(); ++i) {
			testConfigurationList.add(testConfigFromJson(arr.getJSONObject(i),
					descrs, propDescrs));
		}

		return testConfigurationList;
	}

	private static JSONObject generateWorkspaceJSON(BeastWorkspace ws) {
		JSONObject json = new JSONObject();
		json.put(DESCR_FILES_KEY,
				fileCollectionToJsonArr(ws.getFilesPerDescr().values()));
		json.put(PROP_DESCR_FILES_KEY,
				fileCollectionToJsonArr(ws.getFilesPerPropDescr().values()));
		json.put(CODE_GEN_OPTIONS_KEY, CodeGenOptionsSaverLoaderHelper
				.codeGenOptionsToJSON(ws.getCodeGenOptions()));
		json.put(TEST_CONFIG_LIST_KEY,
				testConfigurationListToJSON(ws.getTestConfigList()));				
	
		return json;
	}

	public static BeastWorkspace loadBeastWorkspaceFromFile(File f)
			throws IOException {
		BeastWorkspace beastWorkspace = new BeastWorkspace();

		String fileContents = SavingLoadingInterface.readStringFromFile(f);
		JSONObject json = new JSONObject(fileContents);

		JSONArray descrArr = json.getJSONArray(DESCR_FILES_KEY);
		List<CElectionDescription> descrs = new ArrayList<>();

		for (int i = 0; i < descrArr.length(); ++i) {
			String absolutePath = descrArr.getString(i);
			File descrFile = new File(absolutePath);

			CElectionDescription loadedDescr = SavingLoadingInterface
					.loadCElection(descrFile);
			descrs.add(loadedDescr);

			beastWorkspace.addElectionDescription(loadedDescr);
			beastWorkspace.addFileForDescr(loadedDescr, descrFile);
		}

		JSONArray propDescrArr = json.getJSONArray(PROP_DESCR_FILES_KEY);
		List<PreAndPostConditionsDescription> propDescrs = new ArrayList<>();

		for (int i = 0; i < propDescrArr.length(); ++i) {
			String absolutePath = propDescrArr.getString(i);
			File propDescrFile = new File(absolutePath);
			PreAndPostConditionsDescription loadedPropDescr = SavingLoadingInterface
					.loadPreAndPostConditionDescription(propDescrFile);

			propDescrs.add(loadedPropDescr);
			beastWorkspace.addPropertyDescription(loadedPropDescr);
			beastWorkspace.addFileForPropDescr(loadedPropDescr, propDescrFile);
		}

		CodeGenOptions codeGenOptions = CodeGenOptionsSaverLoaderHelper
				.codeGenOptionsFromJSON(
						json.getJSONObject(CODE_GEN_OPTIONS_KEY));
		
		TestConfigurationList testConfigList = testConfigListFromJsonArr(
				json.getJSONArray(TEST_CONFIG_LIST_KEY), descrs, propDescrs);

		beastWorkspace.setTestConfigList(testConfigList);
		beastWorkspace.setCodeGenOptions(codeGenOptions);
	
		beastWorkspace.setWorkspaceFile(f);
		beastWorkspace.setPathHandler(new PathHandler());
		
		return beastWorkspace;
	}

	public static void storeWorkspace(BeastWorkspace beastWorkspace, File f)
			throws IOException {
		JSONObject json = generateWorkspaceJSON(beastWorkspace);
		SavingLoadingInterface.writeStringToFile(f, json.toString());
	}
}
