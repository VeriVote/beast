package edu.pse.beast.api.savingloading.testruns;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.savingloading.CodeGenOptionsSaverLoaderHelper;
import edu.pse.beast.api.savingloading.cbmc_code.CBMCCodeFileDataSaverLoaderHelper;
import edu.pse.beast.api.savingloading.cbmc_code.CBMCGeneratedCodeInfoSaverLoaderHelper;
import edu.pse.beast.api.savingloading.loopbound.LoopBoundSaverLoaderHelper;
import edu.pse.beast.api.testrunner.propertycheck.CBMCCodeFileData;
import edu.pse.beast.api.testrunner.propertycheck.CBMCPropertyCheckWorkUnit;
import edu.pse.beast.api.testrunner.propertycheck.CBMCTestRun;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;

public class CBMCTestRunSaverLoaderHelper {

	private static final String CBMC_CODE_FILE_DATA_KEY = "cbmc_code_file";

	private static final String TEST_RUN_LOGS_KEY = "test_run_logs";

	private static final String AMT_VOTER_KEY = "amt_voter";
	private static final String AMT_CANDS_KEY = "amt_cands";
	private static final String AMT_SEATS_KEY = "amt_seats";

	private static final String LOOP_BOUND_LIST_KEY = "loop_bounds";

	private static final String CODE_GEN_OPTIONS_KEY = "code_gen_options";

	private static JSONArray loopBoundListToJSONArr(List<LoopBound> list) {
		JSONArray json = new JSONArray();
		for (LoopBound lb : list) {
			json.put(LoopBoundSaverLoaderHelper.loopBoundToJSON(lb));
		}
		return json;
	}

	private static List<LoopBound> loopBoundListFromJSONArr(
			JSONArray jsonArray) {
		List<LoopBound> list = new ArrayList<>();

		for (int i = 0; i < list.size(); ++i) {
			list.add(LoopBoundSaverLoaderHelper
					.loopBoundFromJSON(jsonArray.getJSONObject(i)));
		}

		return list;
	}

	private static JSONObject cbmcTestRunToJSON(CBMCTestRun run) {
		JSONObject json = new JSONObject();

		json.put(AMT_VOTER_KEY, run.getV());
		json.put(AMT_CANDS_KEY, run.getC());
		json.put(AMT_SEATS_KEY, run.getS());

		json.put(TEST_RUN_LOGS_KEY, run.getTestOutput());
		json.put(CBMC_CODE_FILE_DATA_KEY, CBMCCodeFileDataSaverLoaderHelper
				.cbmcCodeFileToJSON(run.getCbmcCodeFile()));

		json.put(LOOP_BOUND_LIST_KEY,
				loopBoundListToJSONArr(run.getLoopboundList()));
		json.put(CODE_GEN_OPTIONS_KEY, CodeGenOptionsSaverLoaderHelper
				.codeGenOptionsToJSON(run.getCodeGenOptions()));

		return json;
	}

	private static CBMCTestRun cbmcTestRunFromJSON(JSONObject json,
			CElectionDescription descr,
			PreAndPostConditionsDescription propDescr) {

		int v = json.getInt(AMT_VOTER_KEY);
		int c = json.getInt(AMT_CANDS_KEY);
		int s = json.getInt(AMT_SEATS_KEY);

		String testRunLogs = json.getString(TEST_RUN_LOGS_KEY);

		CBMCCodeFileData codeFileData = CBMCCodeFileDataSaverLoaderHelper
				.cbmcCodeFileFromJSON(
						json.getJSONObject(CBMC_CODE_FILE_DATA_KEY));

		CodeGenOptions codeGenOptions = CodeGenOptionsSaverLoaderHelper
				.codeGenOptionsFromJSON(
						json.getJSONObject(CODE_GEN_OPTIONS_KEY));
		List<LoopBound> loopbounds = loopBoundListFromJSONArr(
				json.getJSONArray(LOOP_BOUND_LIST_KEY));

		CBMCTestRun cbmcTestRun = new CBMCTestRun(v, s, c, codeGenOptions,
				loopbounds, codeFileData, descr, propDescr);
		

		return cbmcTestRun;
	}

	public static JSONArray cbmcTestRunListToJSON(List<CBMCTestRun> runs) {
		JSONArray arr = new JSONArray();

		for (CBMCTestRun r : runs) {
			arr.put(cbmcTestRunToJSON(r));
		}

		return arr;
	}

	public static List<CBMCTestRun> cbmcTestRunListFromJsonArr(JSONArray arr,
			CElectionDescription descr,
			PreAndPostConditionsDescription propDescr) {
		List<CBMCTestRun> runs = new ArrayList<>();
		for (int i = 0; i < arr.length(); ++i) {
			runs.add(cbmcTestRunFromJSON(arr.getJSONObject(i), descr,
					propDescr));
		}
		return runs;
	}

}
