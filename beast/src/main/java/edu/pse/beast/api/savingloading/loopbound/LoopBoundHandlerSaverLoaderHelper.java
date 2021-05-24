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
	private final static String LOOP_BOUND_LIST_KEY = "loop_bound_list";

	public static JSONObject loopboundHandlerToJson(
			LoopBoundHandler loopBoundHandler) {
		JSONObject json = new JSONObject();
		
		List<LoopBound> loopbounds = loopBoundHandler.getLoopBoundsAsList();
		JSONArray arr = new JSONArray();
		for(LoopBound lb : loopbounds) {
			arr.put(LoopBoundSaverLoaderHelper.loopBoundToJSON(lb));
		}
		
		json.put(LOOP_BOUND_LIST_KEY, arr);
		return json;
	}

	public static LoopBoundHandler loopBoundHandlerFromJSON(JSONObject json) {
		JSONArray loopboundArr = json.getJSONArray(LOOP_BOUND_LIST_KEY);
		LoopBoundHandler loopBoundHandler = new LoopBoundHandler();
		
		for(int i = 0; i < loopboundArr.length(); ++i) {
			JSONObject lbJson = loopboundArr.getJSONObject(i);
			LoopBound lb = LoopBoundSaverLoaderHelper.loopBoundFromJSON(lbJson);
			loopBoundHandler.addLoopBound(lb);
		}
		
		return loopBoundHandler;
	}
}
