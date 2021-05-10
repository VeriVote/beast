package edu.pse.beast.api.savingloading.loopbound;

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

	public static JSONObject loopboundHandlerToJson(
			LoopBoundHandler loopBoundHandler) {
		JSONObject json = new JSONObject();

		JSONObject loopBoundMapJSON = new JSONObject();
		for (String functionName : loopBoundHandler.getFuncNameToLoopbounds()
				.keySet()) {
			JSONArray loopBoundJSONArr = new JSONArray();

			for (LoopBound loopBound : loopBoundHandler
					.getFuncNameToLoopbounds().get(functionName)) {
				JSONObject currentJSON = LoopBoundSaverLoaderHelper
						.loopBoundToJSON(loopBound);
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
				LoopBound loopBound = LoopBoundSaverLoaderHelper
						.loopBoundFromJSON(loopBoundArr.getJSONObject(i));
				loopboundList.add(loopBound);
			}
			loopBoundMap.put(funcName, loopboundList);
		}

		LoopBoundHandler loopBoundHandler = new LoopBoundHandler();
		loopBoundHandler.setFuncNameToLoopbounds(loopBoundMap);

		return loopBoundHandler;
	}
}
