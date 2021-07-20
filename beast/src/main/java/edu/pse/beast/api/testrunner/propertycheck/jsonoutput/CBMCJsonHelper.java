package edu.pse.beast.api.testrunner.propertycheck.jsonoutput;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class CBMCJsonHelper {
    private static final String OUTPUT_KEY = "output";

    public static JSONArray rawOutputToJSON(final List<String> rawOutput) {
        // find the beginning of the json array
        while (!rawOutput.isEmpty() && !rawOutput.get(0).startsWith("[")) {
            rawOutput.remove(0);
        }
        if (rawOutput.isEmpty()) {
            return null;
        }
        final String jsonString =
                "{ " + OUTPUT_KEY + " : "
                        + String.join("\n", rawOutput) + "}";
        final JSONObject resultJson = new JSONObject(jsonString);
        return resultJson.getJSONArray(OUTPUT_KEY);
    }
}
