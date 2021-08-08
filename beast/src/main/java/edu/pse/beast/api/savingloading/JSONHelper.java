package edu.pse.beast.api.savingloading;

import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONObject;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class JSONHelper {
    public static JSONObject stringStringMapToJSON(final Map<String, String> map) {
        return new JSONObject(map);
    }

    public static JSONObject intStringMapToJSON(final Map<Integer, String> map) {
        return new JSONObject(map);
    }

    public static Map<String, String> jsonObjectToStringStringMap(final JSONObject json) {
        final Map<String, String> map = new LinkedHashMap<String, String>();
        for (final String key : json.keySet()) {
            map.put(key, json.getString(key));
        }
        return map;
    }

    public static Map<Integer, String> jsonObjectToIntStringMap(final JSONObject json) {
        final Map<Integer, String> map = new LinkedHashMap<Integer, String>();
        for (final String key : json.keySet()) {
            map.put(Integer.valueOf(key), json.getString(key));
        }
        return map;
    }
}
