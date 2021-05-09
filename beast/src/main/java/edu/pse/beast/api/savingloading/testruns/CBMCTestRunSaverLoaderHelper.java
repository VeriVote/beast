package edu.pse.beast.api.savingloading.testruns;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.testrunner.propertycheck.CBMCCodeFile;
import edu.pse.beast.api.testrunner.propertycheck.CBMCPropertyCheckWorkUnit;
import edu.pse.beast.api.testrunner.propertycheck.CBMCTestRun;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;

public class CBMCTestRunSaverLoaderHelper {
	private static final String TEST_RUN_LOGS_KEY = "test_run_logs";
	private static final String TEST_CODE_FILE_KEY = "cbmc_code_file";

	private static final String CBMC_TEST_RUN_CBMC_FILE_PATH_KEY = "cbmc_test_run_cbmc_file_path";

	private static final String AMT_VOTER_KEY = "amt_voter";
	private static final String AMT_CANDS_KEY = "amt_cands";
	private static final String AMT_SEATS_KEY = "amt_seats";

	private static JSONObject cbmcCodeFileToJSONObject(
			CBMCCodeFile cbmcCodeFile) {
		JSONObject json = new JSONObject();

		json.put(CBMC_TEST_RUN_CBMC_FILE_PATH_KEY,
				cbmcCodeFile.getFile().getAbsolutePath());

		return json;
	}

	/*
	 * private CElectionDescription descr; private
	 * PreAndPostConditionsDescription propDescr;
	 * 
	 * private CBMCPropertyCheckWorkUnit workUnit;
	 * 
	 * private int V; private int S; private int C;
	 * 
	 * private CodeGenOptions codeGenOptions; private List<LoopBound>
	 * loopbounds; private CBMCCodeFile cbmcCodeFile;
	 * 
	 * private List<String> testOutput = new ArrayList<>();
	 */

	private static JSONObject cbmcTestRunToJSON(CBMCTestRun run) {
		JSONObject json = new JSONObject();

		json.put(AMT_VOTER_KEY, run.getWorkUnit().getV());
		json.put(AMT_CANDS_KEY, run.getWorkUnit().getC());
		json.put(AMT_SEATS_KEY, run.getWorkUnit().getS());

		json.put(TEST_RUN_LOGS_KEY, run.getTestOutput());
		json.put(TEST_CODE_FILE_KEY,
				cbmcCodeFileToJSONObject(run.getCbmcCodeFile()));

		return json;
	}

	private static CBMCTestRun genCBMCTestRun(JSONObject json,
			CElectionDescription descr,
			PreAndPostConditionsDescription propDescr) {

		return null;
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
			runs.add(genCBMCTestRun(arr.getJSONObject(i), descr, propDescr));
		}
		return runs;
	}

}
