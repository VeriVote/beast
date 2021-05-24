package edu.pse.beast.api.savingloading.loopbound;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundType;

public class LoopBoundSaverLoaderHelper {
	private final static String LOOP_BOUND_TYPE_KEY = "loop_bound_type";
	private final static String INDEX_KEY = "index";
	private final static String FUNCTION_NAME_KEY = "function_name";
	private final static String MANUAL_BOUND_INTEGER_KEY = "manual_bound_integer";
	private final static String PARENT_KEY = "parent";
	private final static String CHILDREN_KEY = "children";

	
	public static JSONObject loopBoundToJSON(LoopBound loopBound) {
		JSONObject json = new JSONObject();

		json.put(LOOP_BOUND_TYPE_KEY, loopBound.getLoopBoundType().toString());
		json.put(INDEX_KEY, loopBound.getIndex());
		json.put(FUNCTION_NAME_KEY, loopBound.getFunctionName());
		json.put(PARENT_KEY, loopBoundToJSON(loopBound.getParent()));
		JSONArray childrenJSON = new JSONArray();
		for(LoopBound child : loopBound.getChildren()) {
			childrenJSON.put(loopBoundToJSON(child));
		}
		json.put(CHILDREN_KEY, childrenJSON);
		
		

		if (loopBound
				.getLoopBoundType() == LoopBoundType.MANUALLY_ENTERED_INTEGER) {
			json.put(MANUAL_BOUND_INTEGER_KEY,
					loopBound.getManualBoundIfNeeded());
		}

		return json;
	}

	public static LoopBound loopBoundFromJSON(JSONObject json) {
		int index = json.getInt(INDEX_KEY);
		String funcname = json.getString(FUNCTION_NAME_KEY);
		String typeString = json.getString(LOOP_BOUND_TYPE_KEY);
		LoopBoundType type = LoopBoundType.valueOf(typeString);
		LoopBound parent = loopBoundFromJSON(json.getJSONObject(PARENT_KEY));
		JSONArray childrenJSON = json.getJSONArray(CHILDREN_KEY);
		List<LoopBound> children = new ArrayList<>();
		
		for(int i = 0; i < childrenJSON.length(); ++i) {
			JSONObject childJSON = childrenJSON.getJSONObject(i);
			children.add(loopBoundFromJSON(childJSON));
		}
		
		if(type == LoopBoundType.MANUALLY_ENTERED_INTEGER) {
			int manualBound = json.getInt(MANUAL_BOUND_INTEGER_KEY);
			return new LoopBound(parent, children, funcname, type, index, manualBound);
		} else {
			return new LoopBound(parent, children, funcname, type, index);
		}	
		
	}
}