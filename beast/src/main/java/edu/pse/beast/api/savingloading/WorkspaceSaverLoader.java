package edu.pse.beast.api.savingloading;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.gui.testruneditor.testconfig.TestConfiguration;
import edu.pse.beast.gui.testruneditor.testconfig.TestConfigurationList;
import edu.pse.beast.gui.testruneditor.testconfig.cbmc.CBMCPropertyTestConfiguration;
import edu.pse.beast.gui.testruneditor.testconfig.cbmc.runs.CBMCTestRun;
import edu.pse.beast.gui.workspace.BeastWorkspace;

public class WorkspaceSaverLoader {
	private static final String DESCR_FILES_KEY = "descr_files";
	private static final String PROP_DESCR_FILES_KEY = "prop_descr_files";

	private static final String CODE_GEN_OPTIONS_KEY = "code_gen_options";

	private static final String AMT_VOTER_VAR_NAME_KEY = "amount_voter_var_name";
	private static final String AMT_CANDS_VAR_NAME_KEY = "amount_cands_var_name";
	private static final String AMT_SEATS_VAR_NAME_KEY = "amount_seats_var_name";

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

	private static final String TEST_RUN_LOGS_KEY = "test_run_logs";

	private static JSONArray fileCollectionToJsonArr(Collection<File> files) {
		JSONArray arr = new JSONArray();
		for (File f : files) {
			arr.put(f.getAbsolutePath());
		}
		return arr;
	}

	private static JSONObject codeGenOptionsToJSON(CodeGenOptions opts) {
		JSONObject json = new JSONObject();

		json.put(AMT_VOTER_VAR_NAME_KEY, opts.getCbmcAmountVotersVarName());
		json.put(AMT_CANDS_VAR_NAME_KEY, opts.getCbmcAmountCandidatesVarName());
		json.put(AMT_SEATS_VAR_NAME_KEY, opts.getCbmcAmountSeatsVarName());

		return json;
	}
	
	private static CodeGenOptions genCodeGenOptions(JSONObject json) {
		CodeGenOptions codeGenOptions = new CodeGenOptions();
		String amtVoters = json.getString(AMT_VOTER_VAR_NAME_KEY);
		String amtCand = json.getString(AMT_CANDS_VAR_NAME_KEY);
		String amtSeats = json.getString(AMT_SEATS_VAR_NAME_KEY);
		
		codeGenOptions.setCbmcAmountVotersVarName(amtVoters);
		codeGenOptions.setCbmcAmountCandidatesVarName(amtCand);
		codeGenOptions.setCbmcAmountSeatsVarName(amtSeats);
		
		return codeGenOptions;
	}

	private static JSONObject cbmcTestRunToJSON(CBMCTestRun run) {
		JSONObject json = new JSONObject();

		json.put(TEST_RUN_LOGS_KEY, run.getTestOutput());

		return json;
	}
	
	private static CBMCTestRun genCBMCTestRun(JSONObject json) {
		CBMCTestRun run = new CBMCTestRun();
		String log = json.getString(TEST_RUN_LOGS_KEY);
		return run;
	}

	private static JSONArray cbmcTestRunListToJSON(List<CBMCTestRun> runs) {
		JSONArray arr = new JSONArray();

		for (CBMCTestRun r : runs) {
			arr.put(cbmcTestRunToJSON(r));
		}

		return arr;
	}

	private static JSONObject cbmcTestConfigToJSON(
			CBMCPropertyTestConfiguration config) {
		JSONObject json = new JSONObject();

		json.put(MIN_CANDS_KEY, config.getMinCands());
		json.put(MIN_VOTERS_KEY, config.getMinVoters());
		json.put(MIN_SEATS_KEY, config.getMinSeats());

		json.put(MAX_CANDS_KEY, config.getMaxCands());
		json.put(MAX_VOTERS_KEY, config.getMaxVoters());
		json.put(MAX_SEATS_KEY, config.getMaxSeats());

		json.put(CBMC_TEST_CONFIG_NAME_KEY, config.getName());

		json.put(START_RUNS_ON_CREATION_KEY, config.getStartRunsOnCreation());

		json.put(TEST_RUNS_KEY, cbmcTestRunListToJSON(config.getRuns()));

		return json;
	}

	private static JSONArray cbmcTestConfigCollectionToJSON(
			Collection<CBMCPropertyTestConfiguration> collection) {
		JSONArray arr = new JSONArray();

		for (CBMCPropertyTestConfiguration config : collection) {
			arr.put(cbmcTestConfigToJSON(config));
		}

		return arr;
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

	private static JSONArray testConfigurationListToJSON(
			TestConfigurationList list) {
		JSONArray arr = new JSONArray();

		for (List<TestConfiguration> tcl : list
				.getConfigsByElectionDescription().values()) {
			for (TestConfiguration tc : tcl) {
				arr.put(testConfigurationToJSON(tc));
			}
		}

		return arr;
	}

	private static JSONObject generateWorkspaceJSON(BeastWorkspace ws) {
		JSONObject json = new JSONObject();
		json.put(DESCR_FILES_KEY,
				fileCollectionToJsonArr(ws.getFilesPerDescr().values()));
		json.put(PROP_DESCR_FILES_KEY,
				fileCollectionToJsonArr(ws.getFilesPerPropDescr().values()));

		json.put(CODE_GEN_OPTIONS_KEY,
				codeGenOptionsToJSON(ws.getCodeGenOptions()));

		return json;
	}

	public static void storeWorkspace(BeastWorkspace beastWorkspace, File f)
			throws IOException {
		JSONObject json = generateWorkspaceJSON(beastWorkspace);
		SavingLoadingInterface.writeStringToFile(f, json.toString());
	}
}
