package edu.pse.beast.api.savingloading.loopbound;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;
import edu.pse.beast.api.codegen.loopbounds.LoopBound;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class LoopBoundHandlerSaverLoaderHelper {
    private static final String LOOP_BOUND_LIST_KEY = "loop_bound_list";

    public static JSONObject
            loopboundHandlerToJson(final CodeGenLoopBoundHandler loopBoundHandler) {
        final JSONObject json = new JSONObject();

        final List<LoopBound> loopbounds = loopBoundHandler.getLoopBoundsAsList();
        final JSONArray arr = new JSONArray();
        for (final LoopBound lb : loopbounds) {
            arr.put(LoopBoundSaverLoaderHelper.loopBoundToJSON(lb));
        }
        json.put(LOOP_BOUND_LIST_KEY, arr);
        return json;
    }

    public static CodeGenLoopBoundHandler loopBoundHandlerFromJSON(final JSONObject json) {
        final JSONArray loopboundArr = json.getJSONArray(LOOP_BOUND_LIST_KEY);
        final CodeGenLoopBoundHandler loopBoundHandler = new CodeGenLoopBoundHandler();

        for (int i = 0; i < loopboundArr.length(); ++i) {
            final JSONObject lbJson = loopboundArr.getJSONObject(i);
            final LoopBound lb = LoopBoundSaverLoaderHelper.loopBoundFromJSON(lbJson);
            loopBoundHandler.addLoopBound(lb);
        }
        return loopBoundHandler;
    }
}
