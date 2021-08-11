package edu.pse.beast.api.io.code;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.pse.beast.api.codegen.cbmc.info.GeneratedCodeInfo;
import edu.pse.beast.api.io.JSONHelper;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class GeneratedCodeInfoInputOutputHelper {
    private static final String CODE_KEY = "code";
    private static final String VOTE_VAR_NAME_TO_VOTE_NUMBER_KEY = "vote_number_to_variable_name";
    private static final String ELECT_VAR_NAME_TO_VOTE_NUMBER_KEY = "elect_number_to_variable_name";

    private static final String GENERATED_VOTE_VAR_NAME_SET_KEY = "generated_vote_var_name";
    private static final String GENERATED_ELECT_VAR_NAME_SET_KEY = "generated_elect_var_name";

    private static final String VOTES_AMT_MEMBER_VAR_NAME_KEY = "votes_amt_member_var_name";
    private static final String VOTES_LIST_MEMBER_VAR_NAME_KEY = "votes_list_member_var_name";

    private static final String RESULT_AMT_MEMBER_VAR_NAME_KEY = "result_amt_member_var_name";
    private static final String RESULT_LIST_MEMBER_VAR_NAME_KEY = "result_list_member_var_name";

    private static final String VAR_NAME_TO_INFO_KEY = "var_name_to_info";

    public static JSONObject generatedCodeInfoToJSON(final GeneratedCodeInfo generatedCodeInfo) {
        final JSONObject json = new JSONObject();

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

        json.put(VOTES_AMT_MEMBER_VAR_NAME_KEY,
                generatedCodeInfo.getVotesAmtMemberVarName());
        json.put(VOTES_LIST_MEMBER_VAR_NAME_KEY,
                generatedCodeInfo.getVotesListMemberVarName());

        json.put(RESULT_AMT_MEMBER_VAR_NAME_KEY,
                generatedCodeInfo.getResultAmtMemberVarName());
        json.put(RESULT_LIST_MEMBER_VAR_NAME_KEY,
                generatedCodeInfo.getResultListMemberVarName());
        return json;
    }

    private static Map<String, Integer> toStringIntMap(final JSONObject json) {
        final Map<String, Integer> map = new LinkedHashMap<String, Integer>();
        for (final String key : json.keySet()) {
            map.put(key, json.getInt(key));
        }
        return map;
    }

    private static Set<String> toStringSet(final JSONArray json) {
        final Set<String> set = new LinkedHashSet<String>();
        for (int i = 0; i < json.length(); ++i) {
            set.add(json.getString(i));
        }
        return set;
    }

    public static GeneratedCodeInfo generatedCodeInfoFromJSON(final JSONObject json) {
        final GeneratedCodeInfo generatedCodeInfo = new GeneratedCodeInfo();
        final String code = json.getString(CODE_KEY);
        generatedCodeInfo.setCode(code);

        generatedCodeInfo.setVoteVariableNameToVoteNumber(toStringIntMap(
                json.getJSONObject(VOTE_VAR_NAME_TO_VOTE_NUMBER_KEY)));
        generatedCodeInfo.setElectVariableNameToElectNumber(toStringIntMap(
                json.getJSONObject(ELECT_VAR_NAME_TO_VOTE_NUMBER_KEY)));

        generatedCodeInfo.setGeneratedVotingVarNames(toStringSet(
                json.getJSONArray(GENERATED_VOTE_VAR_NAME_SET_KEY)));
        generatedCodeInfo.setGeneratedElectVarNames(toStringSet(
                json.getJSONArray(GENERATED_ELECT_VAR_NAME_SET_KEY)));

        generatedCodeInfo
                .setVarNamesToInfo(JSONHelper.jsonObjectToStringStringMap(
                        json.getJSONObject(VAR_NAME_TO_INFO_KEY)));

        generatedCodeInfo.setVotesAmtMemberVarName(
                json.getString(VOTES_AMT_MEMBER_VAR_NAME_KEY));
        generatedCodeInfo.setVotesListMemberVarName(
                json.getString(VOTES_LIST_MEMBER_VAR_NAME_KEY));

        generatedCodeInfo.setResultAmtMemberVarName(
                json.getString(RESULT_AMT_MEMBER_VAR_NAME_KEY));
        generatedCodeInfo.setResultListMemberVarName(
                json.getString(RESULT_LIST_MEMBER_VAR_NAME_KEY));
        return generatedCodeInfo;
    }
}
