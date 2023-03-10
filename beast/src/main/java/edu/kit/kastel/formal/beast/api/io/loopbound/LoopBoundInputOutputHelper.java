package edu.kit.kastel.formal.beast.api.io.loopbound;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.kit.kastel.formal.beast.api.codegen.loopbound.LoopBound;
import edu.kit.kastel.formal.beast.api.codegen.loopbound.LoopBoundType;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class LoopBoundInputOutputHelper {
    private static final String LOOP_BOUND_TYPE_KEY = "loop_bound_type";
    private static final String INDEX_KEY = "index";
    private static final String FUNCTION_NAME_KEY = "function_name";
    private static final String MANUAL_BOUND_INTEGER_KEY = "manual_bound_integer";
    private static final String CHILDREN_KEY = "children";

    public static JSONObject loopBoundToJSON(final LoopBound loopBound) {
        final JSONObject json = new JSONObject();

        json.put(LOOP_BOUND_TYPE_KEY, loopBound.getLoopBoundType().toString());
        json.put(INDEX_KEY, loopBound.getIndex());
        json.put(FUNCTION_NAME_KEY, loopBound.getFunctionName());
        final JSONArray childrenJSON = new JSONArray();
        for (final LoopBound child : loopBound.getChildren()) {
            childrenJSON.put(loopBoundToJSON(child));
        }
        json.put(CHILDREN_KEY, childrenJSON);

        if (loopBound.getLoopBoundType() == LoopBoundType.MANUALLY_ENTERED) {
            json.put(MANUAL_BOUND_INTEGER_KEY,
                    loopBound.getManualBoundIfNeeded());
        }
        return json;
    }

    public static LoopBound loopBoundFromJSON(final JSONObject json) {
        final int index = json.getInt(INDEX_KEY);
        final String funcname = json.getString(FUNCTION_NAME_KEY);
        final String typeString = json.getString(LOOP_BOUND_TYPE_KEY);
        final LoopBoundType type = LoopBoundType.valueOf(typeString);
        final JSONArray childrenJSON = json.getJSONArray(CHILDREN_KEY);
        final List<LoopBound> children = new ArrayList<LoopBound>();

        for (int i = 0; i < childrenJSON.length(); ++i) {
            final JSONObject childJSON = childrenJSON.getJSONObject(i);
            children.add(loopBoundFromJSON(childJSON));
        }

        if (type == LoopBoundType.MANUALLY_ENTERED) {
            final int manualBound = json.getInt(MANUAL_BOUND_INTEGER_KEY);
            return new LoopBound(children, funcname, type, index, manualBound);
        } else {
            return new LoopBound(children, funcname, type, index);
        }

    }
}
