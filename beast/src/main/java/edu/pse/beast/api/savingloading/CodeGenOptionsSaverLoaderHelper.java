package edu.pse.beast.api.savingloading;

import org.json.JSONObject;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CodeGenOptionsSaverLoaderHelper {
    private static final String AMT_VOTER_VAR_NAME_KEY = "amount_voter_var_name";
    private static final String AMT_CANDS_VAR_NAME_KEY = "amount_cands_var_name";
    private static final String AMT_SEATS_VAR_NAME_KEY = "amount_seats_var_name";

    public static JSONObject codeGenOptionsToJSON(final CodeGenOptions opts) {
        final JSONObject json = new JSONObject();
        json.put(AMT_VOTER_VAR_NAME_KEY, opts.getCbmcAmountMaxVotersVarName());
        json.put(AMT_CANDS_VAR_NAME_KEY, opts.getCbmcAmountMaxCandsVarName());
        json.put(AMT_SEATS_VAR_NAME_KEY, opts.getCbmcAmountMaxSeatsVarName());
        return json;
    }

    public static CodeGenOptions codeGenOptionsFromJSON(final JSONObject json) {
        final CodeGenOptions codeGenOptions = new CodeGenOptions();
        final String amtVoters = json.getString(AMT_VOTER_VAR_NAME_KEY);
        final String amtCand = json.getString(AMT_CANDS_VAR_NAME_KEY);
        final String amtSeats = json.getString(AMT_SEATS_VAR_NAME_KEY);

        codeGenOptions.setCbmcAmountMaxVotersVarName(amtVoters);
        codeGenOptions.setCbmcAmountMaxCandidatesVarName(amtCand);
        codeGenOptions.setCbmcAmountMaxSeatsVarName(amtSeats);

        return codeGenOptions;
    }
}
