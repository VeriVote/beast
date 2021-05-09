package edu.pse.beast.api.savingloading;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class JSONHelper {
	public static JSONObject StringStringMapToJSON(Map<String, String> map) {
		JSONObject json = new JSONObject(map);
		return json;
	}

	public static JSONObject IntStringMapToJSON(Map<Integer, String> map) {
		JSONObject json = new JSONObject(map);
		return json;
	}

	public static Map<String, String> jsonObjectToStringStringMap(
			JSONObject json) {
		Map<String, String> map = new HashMap<>();

		for (String key : json.keySet()) {
			map.put(key, json.getString(key));
		}

		return map;
	}

	public static Map<Integer, String> jsonObjectToIntStringMap(
			JSONObject json) {
		Map<Integer, String> map = new HashMap<>();

		for (String key : json.keySet()) {
			map.put(Integer.valueOf(key), json.getString(key));
		}

		return map;
	}
}
