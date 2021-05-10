package edu.pse.beast.api.savingloading.loopbound;

import java.util.Optional;

import org.json.JSONObject;

import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundType;

public class LoopBoundSaverLoaderHelper {
	private final static String LOOP_BOUND_TYPE_KEY = "loop_bound_type";
	private final static String INDEX_KEY = "index";
	private final static String FUNCTION_NAME_KEY = "function_name";
	private final static String MANUAL_BOUND_INTEGER_KEY = "manual_bound_integer";
	
	public static JSONObject loopBoundToJSON(LoopBound loopBound) {
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

	public static LoopBound loopBoundFromJSON(JSONObject json) {
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
}
