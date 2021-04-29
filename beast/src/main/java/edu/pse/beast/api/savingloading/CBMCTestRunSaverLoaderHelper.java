package edu.pse.beast.api.savingloading;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.pse.beast.gui.runs.CBMCTestRun;

public class CBMCTestRunSaverLoaderHelper {
	private static final String TEST_RUN_LOGS_KEY = "test_run_logs";
	private static final String CBMC_TEST_RUN_CBMC_FILE_PATH_KEY = "cbmc_test_run_cbmc_file_path";
	private static final String AMT_VOTER_KEY = "amt_voter";
	private static final String AMT_CANDS_KEY = "amt_cands";
	private static final String AMT_SEATS_KEY = "amt_seats";

	private static JSONObject cbmcTestRunToJSON(CBMCTestRun run) {
		JSONObject json = new JSONObject();

		json.put(AMT_VOTER_KEY, run.getWorkUnit().getV());
		json.put(AMT_CANDS_KEY, run.getWorkUnit().getC());
		json.put(AMT_SEATS_KEY, run.getWorkUnit().getS());

		json.put(TEST_RUN_LOGS_KEY, run.getTestOutput());
		json.put(CBMC_TEST_RUN_CBMC_FILE_PATH_KEY,
				run.getCbmcFile().getAbsolutePath());

		return json;
	}

	private static CBMCTestRun genCBMCTestRun(JSONObject json) {
		CBMCTestRun run = new CBMCTestRun();

		String log = json.getString(TEST_RUN_LOGS_KEY);
		String cbmcTestFilePath = json
				.getString(CBMC_TEST_RUN_CBMC_FILE_PATH_KEY);
		File cbmcTestFile = new File(cbmcTestFilePath);

		run.setCBMCFile(cbmcTestFile);

		return run;
	}

	public static JSONArray cbmcTestRunListToJSON(List<CBMCTestRun> runs) {
		JSONArray arr = new JSONArray();

		for (CBMCTestRun r : runs) {
			arr.put(cbmcTestRunToJSON(r));
		}

		return arr;
	}

	public static List<CBMCTestRun> cbmcTestRunListFromJsonArr(JSONArray arr) {
		List<CBMCTestRun> runs = new ArrayList<>();
		for (int i = 0; i < arr.length(); ++i) {
			runs.add(genCBMCTestRun(arr.getJSONObject(i)));
		}
		return runs;
	}

}
