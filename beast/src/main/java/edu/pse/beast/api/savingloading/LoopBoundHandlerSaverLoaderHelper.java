package edu.pse.beast.api.savingloading;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundHandler;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundType;

public class LoopBoundHandlerSaverLoaderHelper {
	private final static String LOOP_BOUND_MAP_KEY = "loop_bound_map";

	private final static String LOOP_BOUND_TYPE_KEY = "loop_bound_type";
	private final static String INDEX_KEY = "index";
	private final static String FUNCTION_NAME_KEY = "function_name";
	private final static String MANUAL_BOUND_INTEGER_KEY = "manual_bound_integer";

	private static JSONObject loopBoundToJSON(LoopBound loopBound) {
		JSONObject json = new JSONObject();

		json.put(LOOP_BOUND_TYPE_KEY, loopBound.getLoopBoundType().toString());
		json.put(INDEX_KEY, loopBound.getIndex());
		json.put(FUNCTION_NAME_KEY, loopBound.getFunctionName());
		if (loopBound
				.getLoopBoundType() == LoopBoundType.MANUALLY_ENTERED_INTEGER) {
			json.put(MANUAL_BOUND_INTEGER_KEY,
					loopBound.getManualLoopBoundIfPresent().get());
		}

		return json;
	}

	private static LoopBound loopBoundFromJSON(JSONObject json) {
		int index = json.getInt(INDEX_KEY);
		String funcname = json.getString(FUNCTION_NAME_KEY);
		String typeString = json.getString(LOOP_BOUND_TYPE_KEY);
		LoopBoundType type = LoopBoundType.valueOf(typeString);

		if (type == LoopBoundType.MANUALLY_ENTERED_INTEGER) {
			int manualBound = json.getInt(MANUAL_BOUND_INTEGER_KEY);
			return new LoopBound(type, index, funcname,
					Optional.of(manualBound));
		} else {
			return new LoopBound(type, index, funcname);
		}
	}

	public static JSONObject loopboundHandlerToJson(
			LoopBoundHandler loopBoundHandler) {
		JSONObject json = new JSONObject();

		JSONObject loopBoundMapJSON = new JSONObject();
		for (String functionName : loopBoundHandler.getFuncNameToLoopbounds()
				.keySet()) {
			JSONArray loopBoundJSONArr = new JSONArray();

			for (LoopBound loopBound : loopBoundHandler
					.getFuncNameToLoopbounds().get(functionName)) {
				JSONObject currentJSON = loopBoundToJSON(loopBound);
				loopBoundJSONArr.put(currentJSON);
			}

			loopBoundMapJSON.put(functionName, loopBoundJSONArr);
		}

		json.put(LOOP_BOUND_MAP_KEY, loopBoundMapJSON);
		return json;
	}

	public static LoopBoundHandler loopBoundHandlerFromJSON(JSONObject json) {
		JSONObject loopBoundMapJSON = json.getJSONObject(LOOP_BOUND_MAP_KEY);

		Map<String, List<LoopBound>> loopBoundMap = new HashMap<>();
		for (String funcName : loopBoundMapJSON.keySet()) {
			JSONArray loopBoundArr = loopBoundMapJSON.getJSONArray(funcName);
			List<LoopBound> loopboundList = new ArrayList<>();
			for (int i = 0; i < loopBoundArr.length(); ++i) {
				LoopBound loopBound = loopBoundFromJSON(
						loopBoundArr.getJSONObject(i));
				loopboundList.add(loopBound);
			}
			loopBoundMap.put(funcName, loopboundList);
		}

		LoopBoundHandler loopBoundHandler = new LoopBoundHandler();
		loopBoundHandler.setFuncNameToLoopbounds(loopBoundMap);

		return loopBoundHandler;
	}
}
