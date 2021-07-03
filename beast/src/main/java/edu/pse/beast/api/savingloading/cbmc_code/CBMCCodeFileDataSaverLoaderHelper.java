package edu.pse.beast.api.savingloading.cbmc_code;

import java.io.File;

import org.json.JSONObject;

import edu.pse.beast.api.codegen.cbmc.generated_code_info.CBMCGeneratedCodeInfo;
import edu.pse.beast.api.savingloading.RelativePathConverter;
import edu.pse.beast.api.testrunner.CBMCCodeFileData;
import edu.pse.beast.gui.paths.PathHandler;

public class CBMCCodeFileDataSaverLoaderHelper {

	private static final String CBMC_GENERATED_CODE_INFO_KEY = "cbmc_generated_code_info";
	private static final String CBMC_TEST_RUN_CBMC_REL_FILE_PATH_KEY = "cbmc_test_run_cbmc_file_path";

	public static JSONObject cbmcCodeFileToJSON(
			CBMCCodeFileData cbmcCodeFileData, RelativePathConverter pathHandler) {
		JSONObject json = new JSONObject();

		String relPath = pathHandler
				.getRelativePathTo(cbmcCodeFileData.getFile());
		json.put(CBMC_TEST_RUN_CBMC_REL_FILE_PATH_KEY, relPath);
		json.put(CBMC_GENERATED_CODE_INFO_KEY,
				CBMCGeneratedCodeInfoSaverLoaderHelper.generatedCodeInfoToJSON(
						cbmcCodeFileData.getCodeInfo()));

		return json;
	}

	public static CBMCCodeFileData cbmcCodeFileFromJSON(JSONObject json,
			RelativePathConverter pathHandler) {
		CBMCCodeFileData cbmcCodeFileData = new CBMCCodeFileData();

		String relCodeFilePath = json
				.getString(CBMC_TEST_RUN_CBMC_REL_FILE_PATH_KEY);
		File codeFile = pathHandler.getFileFromRelativePath(relCodeFilePath);
		
		CBMCGeneratedCodeInfo codeInfo = CBMCGeneratedCodeInfoSaverLoaderHelper
				.generatedCodeInfoFromJSON(
						json.getJSONObject(CBMC_GENERATED_CODE_INFO_KEY));
		cbmcCodeFileData.setFile(codeFile);
		cbmcCodeFileData.setCodeInfo(codeInfo);

		return cbmcCodeFileData;
	}
}
