package edu.pse.beast.api.savingloading.cbmc_code;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import edu.pse.beast.api.codegen.cbmc.info.CBMCGeneratedCodeInfo;
import edu.pse.beast.api.savingloading.JSONHelper;

public class CBMCGeneratedCodeInfoSaverLoaderHelper {

	private static final String CODE_KEY = "code";
	private static final String VOTE_NUMBER_TO_VARIABLE_NAME_KEY = "vote_number_to_variable_name";

	// TODO doesnt save the amt, list member var names since this is likely to
	// change in the future
	public static JSONObject generatedCodeInfoToJSON(
			CBMCGeneratedCodeInfo generatedCodeInfo) {
		JSONObject json = new JSONObject();

		json.put(CODE_KEY, generatedCodeInfo.getCode());

		return json;
	}

	public static CBMCGeneratedCodeInfo generatedCodeInfoFromJSON(
			JSONObject json) {
		CBMCGeneratedCodeInfo cbmcGeneratedCodeInfo = new CBMCGeneratedCodeInfo();

		String code = json.getString(CODE_KEY);
		Map<Integer, String> voteNumberToVariableNameMap = JSONHelper
				.jsonObjectToIntStringMap(
						json.getJSONObject(VOTE_NUMBER_TO_VARIABLE_NAME_KEY));

		cbmcGeneratedCodeInfo.setCode(code);

		return cbmcGeneratedCodeInfo;
	}
}
