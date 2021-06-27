package edu.pse.beast.api.savingloading.cbmc_code;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.pse.beast.api.codegen.cbmc.info.CBMCGeneratedCodeInfo;
import edu.pse.beast.api.savingloading.JSONHelper;

public class CBMCGeneratedCodeInfoSaverLoaderHelper {

	private static final String CODE_KEY = "code";
	private static final String VOTE_VAR_NAME_TO_VOTE_NUMBER_KEY = "vote_number_to_variable_name";
	private static final String ELECT_VAR_NAME_TO_VOTE_NUMBER_KEY = "elect_number_to_variable_name";

	private static final String GENERATED_VOTE_VAR_NAME_SET_KEY = "generated_vote_var_name";
	private static final String GENERATED_ELECT_VAR_NAME_SET_KEY = "generated_elect_var_name";

	private static final String AMT_MEMBER_VAR_NAME_KEY = "amt_member_var_name";
	private static final String LIST_MEMBER_VAR_NAME_KEY = "list_member_var_name";

	private static final String VAR_NAME_TO_INFO_KEY = "var_name_to_info";

	public static JSONObject generatedCodeInfoToJSON(
			CBMCGeneratedCodeInfo generatedCodeInfo) {
		JSONObject json = new JSONObject();

		json.put(CODE_KEY, generatedCodeInfo.getCode());

		json.put(VOTE_VAR_NAME_TO_VOTE_NUMBER_KEY, new JSONObject(
				generatedCodeInfo.getVoteVariableNameToVoteNumber()));
		json.put(ELECT_VAR_NAME_TO_VOTE_NUMBER_KEY, new JSONObject(
				generatedCodeInfo.getElectVariableNameToElectNumber()));

		json.put(GENERATED_VOTE_VAR_NAME_SET_KEY, new JSONArray(
				generatedCodeInfo.getGeneratedVotingVarNames().toArray()));
		json.put(GENERATED_ELECT_VAR_NAME_SET_KEY, new JSONArray(
				generatedCodeInfo.getGeneratedElectVarNames().toArray()));

		json.put(VAR_NAME_TO_INFO_KEY,
				new JSONObject(generatedCodeInfo.getVarNamesToInfo()));

		json.put(AMT_MEMBER_VAR_NAME_KEY,
				generatedCodeInfo.getAmtMemberVarName());
		json.put(LIST_MEMBER_VAR_NAME_KEY,
				generatedCodeInfo.getListMemberVarName());

		return json;
	}

	private static Map<String, Integer> toStringIntMap(JSONObject json) {
		Map<String, Integer> map = new HashMap<>();
		for (String key : json.keySet()) {
			map.put(key, json.getInt(key));
		}
		return map;
	}

	private static Set<String> toStringSet(JSONArray json) {
		Set<String> set = new HashSet<>();
		for (int i = 0; i < json.length(); ++i) {
			set.add(json.getString(i));
		}
		return set;
	}

	public static CBMCGeneratedCodeInfo generatedCodeInfoFromJSON(
			JSONObject json) {
		CBMCGeneratedCodeInfo cbmcGeneratedCodeInfo = new CBMCGeneratedCodeInfo();

		String code = json.getString(CODE_KEY);
		cbmcGeneratedCodeInfo.setCode(code);

		cbmcGeneratedCodeInfo.setVoteVariableNameToVoteNumber(toStringIntMap(
				json.getJSONObject(VOTE_VAR_NAME_TO_VOTE_NUMBER_KEY)));
		cbmcGeneratedCodeInfo.setElectVariableNameToElectNumber(toStringIntMap(
				json.getJSONObject(ELECT_VAR_NAME_TO_VOTE_NUMBER_KEY)));

		cbmcGeneratedCodeInfo.setGeneratedVotingVarNames(toStringSet(
				json.getJSONArray(GENERATED_VOTE_VAR_NAME_SET_KEY)));
		cbmcGeneratedCodeInfo.setGeneratedElectVarNames(toStringSet(
				json.getJSONArray(GENERATED_ELECT_VAR_NAME_SET_KEY)));

		cbmcGeneratedCodeInfo
				.setVarNamesToInfo(JSONHelper.jsonObjectToStringStringMap(
						json.getJSONObject(VAR_NAME_TO_INFO_KEY)));

		cbmcGeneratedCodeInfo
				.setAmtMemberVarName(json.getString(AMT_MEMBER_VAR_NAME_KEY));
		cbmcGeneratedCodeInfo
				.setListMemberVarName(json.getString(LIST_MEMBER_VAR_NAME_KEY));

		return cbmcGeneratedCodeInfo;
	}
}
