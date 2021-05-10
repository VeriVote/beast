package edu.pse.beast.api.savingloading.cbmc_code;

import java.io.File;

import org.json.JSONObject;

import edu.pse.beast.api.codegen.CBMCGeneratedCodeInfo;
import edu.pse.beast.api.testrunner.propertycheck.CBMCCodeFileData;

public class CBMCCodeFileDataSaverLoaderHelper {

	private static final String CBMC_GENERATED_CODE_INFO_KEY = "cbmc_generated_code_info";
	private static final String CBMC_TEST_RUN_CBMC_FILE_PATH_KEY = "cbmc_test_run_cbmc_file_path";

	public static JSONObject cbmcCodeFileToJSON(
			CBMCCodeFileData cbmcCodeFileData) {
		JSONObject json = new JSONObject();

		json.put(CBMC_TEST_RUN_CBMC_FILE_PATH_KEY,
				cbmcCodeFileData.getFile().getAbsolutePath());
		json.put(CBMC_GENERATED_CODE_INFO_KEY,
				CBMCGeneratedCodeInfoSaverLoaderHelper.generatedCodeInfoToJSON(
						cbmcCodeFileData.getCodeInfo()));

		return json;
	}

	public static CBMCCodeFileData cbmcCodeFileFromJSON(JSONObject json) {
		CBMCCodeFileData cbmcCodeFileData = new CBMCCodeFileData();

		String codeFilePath = json.getString(CBMC_TEST_RUN_CBMC_FILE_PATH_KEY);
		CBMCGeneratedCodeInfo codeInfo = CBMCGeneratedCodeInfoSaverLoaderHelper
				.generatedCodeInfoFromJSON(
						json.getJSONObject(CBMC_GENERATED_CODE_INFO_KEY));
		cbmcCodeFileData.setFile(new File(codeFilePath));
		cbmcCodeFileData.setCodeInfo(codeInfo);

		return cbmcCodeFileData;
	}
}
