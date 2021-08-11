package edu.pse.beast.api.runner.propertycheck.output;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class JSONHelper {
    private static final String LINE_BREAK = "\n";
    private static final String JSON_START = "[";
    private static final String ATTRIBUTE_START = "{ ";
    private static final String ATTRIBUTE_END = " }";
    private static final String JSON_SEP = " : ";

    private static final String OUTPUT_KEY = "output";

    private static String rawTrimmedToJSON(final List<String> rawTrimmed) {
        return ATTRIBUTE_START + OUTPUT_KEY + JSON_SEP
                + String.join(LINE_BREAK, rawTrimmed) + ATTRIBUTE_END;
    }

    public static JSONArray rawOutputToJSON(final List<String> rawOutput) {
        // find the beginning of the json array
        while (!rawOutput.isEmpty() && !rawOutput.get(0).startsWith(JSON_START)) {
            rawOutput.remove(0);
        }
        if (rawOutput.isEmpty()) {
            return null;
        }
        final String jsonString = rawTrimmedToJSON(rawOutput);
        final JSONObject resultJson = new JSONObject(jsonString);
        return resultJson.getJSONArray(OUTPUT_KEY);
    }
}
