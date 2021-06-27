package edu.pse.beast.api.savingloading.cbmc_code;

import java.util.HashMap;
import java.util.Map;

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

	public static JSONObject generatedCodeInfoToJSON(
			CBMCGeneratedCodeInfo generatedCodeInfo) {
		JSONObject json = new JSONObject();

		json.put(CODE_KEY, generatedCodeInfo.getCode());
		json.put(VOTE_VAR_NAME_TO_VOTE_NUMBER_KEY, new JSONObject(
				generatedCodeInfo.getVoteVariableNameToVoteNumber()));
		json.put(ELECT_VAR_NAME_TO_VOTE_NUMBER_KEY, new JSONObject(
				generatedCodeInfo.getElectVariableNameToElectNumber()));

		json.put(GENERATED_VOTE_VAR_NAME_SET_KEY,
				new JSONObject(generatedCodeInfo.getGeneratedVotingVarNames()));
		json.put(GENERATED_ELECT_VAR_NAME_SET_KEY,
				new JSONObject(generatedCodeInfo.getGeneratedElectVarNames()));
		
		json.put(AMT_MEMBER_VAR_NAME_KEY, generatedCodeInfo.getAmtMemberVarName());
		json.put(LIST_MEMBER_VAR_NAME_KEY, generatedCodeInfo.getListMemberVarName());

		return json;
	}

	public static CBMCGeneratedCodeInfo generatedCodeInfoFromJSON(
			JSONObject json) {
		CBMCGeneratedCodeInfo cbmcGeneratedCodeInfo = new CBMCGeneratedCodeInfo();

		String code = json.getString(CODE_KEY);
		Map<Integer, String> voteNumberToVariableNameMap = JSONHelper
				.jsonObjectToIntStringMap(
						json.getJSONObject(VOTE_VAR_NAME_TO_VOTE_NUMBER_KEY));

		cbmcGeneratedCodeInfo.setCode(code);

		return cbmcGeneratedCodeInfo;
	}
}
