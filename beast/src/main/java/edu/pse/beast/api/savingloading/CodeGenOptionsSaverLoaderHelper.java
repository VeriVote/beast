package edu.pse.beast.api.savingloading;

import org.json.JSONObject;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;

public class CodeGenOptionsSaverLoaderHelper {

	private static final String AMT_VOTER_VAR_NAME_KEY = "amount_voter_var_name";
	private static final String AMT_CANDS_VAR_NAME_KEY = "amount_cands_var_name";
	private static final String AMT_SEATS_VAR_NAME_KEY = "amount_seats_var_name";
	
	public static JSONObject codeGenOptionsToJSON(CodeGenOptions opts) {
		JSONObject json = new JSONObject();

		json.put(AMT_VOTER_VAR_NAME_KEY, opts.getCbmcAmountMaxVotersVarName());
		json.put(AMT_CANDS_VAR_NAME_KEY, opts.getCbmcAmountMaxCandidatesVarName());
		json.put(AMT_SEATS_VAR_NAME_KEY, opts.getCbmcAmountMaxSeatsVarName());

		return json;
	}

	public static CodeGenOptions codeGenOptionsFromJSON(JSONObject json) {
		CodeGenOptions codeGenOptions = new CodeGenOptions();
		String amtVoters = json.getString(AMT_VOTER_VAR_NAME_KEY);
		String amtCand = json.getString(AMT_CANDS_VAR_NAME_KEY);
		String amtSeats = json.getString(AMT_SEATS_VAR_NAME_KEY);

		codeGenOptions.setCbmcAmountMaxVotersVarName(amtVoters);
		codeGenOptions.setCbmcAmountMaxCandidatesVarName(amtCand);
		codeGenOptions.setCbmcAmountMaxSeatsVarName(amtSeats);

		return codeGenOptions;
	}
}
