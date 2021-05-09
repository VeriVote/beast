package edu.pse.beast.api.savingloading.testruns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	private static JSONObject loopBoundToJSON(LoopBound loopBound) {
		JSONObject json = new JSONObject();

		json.put(LOOP_BOUND_TYPE_KEY, loopBound.getLoopBoundType().toString());
		json.put(INDEX_KEY, loopBound.getIndex());
		json.put(FUNCTION_NAME_KEY, loopBound.getFunctionName());

		return json;
	}

	private static LoopBound loopBoundFromJSON(JSONObject json) {
		int idx = json.getInt(INDEX_KEY);
		String funcname = json.getString(FUNCTION_NAME_KEY);
		String typeString = json.getString(LOOP_BOUND_TYPE_KEY);
		LoopBoundType type = LoopBoundType.valueOf(typeString);

		return new LoopBound(type, idx, funcname);
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
		for(String funcName : loopBoundMapJSON.keySet()) {
			JSONArray loopBoundArr = loopBoundMapJSON.getJSONArray(funcName);
			List<LoopBound> loopboundList = new ArrayList<>();
			for(int i = 0; i < loopBoundArr.length(); ++i) {
				LoopBound loopBound = loopBoundFromJSON(loopBoundArr.getJSONObject(i));
				loopboundList.add(loopBound);
			}
			loopBoundMap.put(funcName, loopboundList);
		}
		
		LoopBoundHandler loopBoundHandler = new LoopBoundHandler();
		loopBoundHandler.setFuncNameToLoopbounds(loopBoundMap);
	
		return loopBoundHandler;
	}
}
