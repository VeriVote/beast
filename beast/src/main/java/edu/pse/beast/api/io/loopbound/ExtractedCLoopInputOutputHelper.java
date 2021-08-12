package edu.pse.beast.api.io.loopbound;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.pse.beast.api.codegen.loopbound.LoopBoundType;
import edu.pse.beast.api.cparser.CLoopParseResultType;
import edu.pse.beast.api.cparser.CLoopType;
import edu.pse.beast.api.cparser.ExtractedCLoop;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class ExtractedCLoopInputOutputHelper {
    private static final String UUID_KEY = "uuid";
    private static final String LOOPTYPE_KEY = "looptype";

    private static final String LINE_KEY = "line";
    private static final String POS_IN_LINE_KEY = "pos_in_line";
    private static final String NUMBER_IN_FUNCTION_KEY = "number_in_function";

    private static final String PARSE_RESULT_KEY = "parse_result";
    private static final String PARSED_BOUND_TYPE_KEY = "parsed_bound_type";

    private static final String MANUAL_BOUND_KEY = "manual_bound";

    private static final String FUNCTION_NAME_KEY = "function_name";

    private static final String PARENT_UUID_KEY = "parent_uuid";
    private static final String CHILDREN_UUIDS_KEY = "children_uuids";

    private static JSONObject fromExtractedLoop(final ExtractedCLoop loop) {
        final JSONObject json = new JSONObject();
        json.put(UUID_KEY, loop.getUuid());
        json.put(LOOPTYPE_KEY, loop.getLoopType().name());

        json.put(LINE_KEY, loop.getLine());
        json.put(POS_IN_LINE_KEY, loop.getPosInLine());
        json.put(NUMBER_IN_FUNCTION_KEY, loop.getLoopNumberInFunction());

        json.put(PARSE_RESULT_KEY, loop.getLoopParseResult().name());
        json.put(PARSED_BOUND_TYPE_KEY,
                loop.getParsedLoopBoundType().name());

        if (loop.getParsedLoopBoundType() == LoopBoundType.MANUALLY_ENTERED) {
            json.put(MANUAL_BOUND_KEY, loop.getManualInteger());
        }
        json.put(FUNCTION_NAME_KEY, loop.getFunctionName());
        if (loop.getParentLoop() != null) {
            json.put(PARENT_UUID_KEY, loop.getParentLoop().getUuid());
        }
        final JSONArray childrenUUIDs = new JSONArray();
        for (final ExtractedCLoop child : loop.getChildrenLoops()) {
            childrenUUIDs.put(child.getUuid());
        }
        json.put(CHILDREN_UUIDS_KEY, childrenUUIDs);
        return json;
    }

    private static ExtractedCLoop toExtractedLoop(final JSONObject json) {
        final String uuid = json.getString(UUID_KEY);
        final CLoopType loopType = CLoopType.valueOf(json.getString(LOOPTYPE_KEY));

        final int line = json.getInt(LINE_KEY);
        final int posInLine = json.getInt(POS_IN_LINE_KEY);
        final int numberInFunc = json.getInt(NUMBER_IN_FUNCTION_KEY);

        final CLoopParseResultType parseResultType =
                CLoopParseResultType.valueOf(json.getString(PARSE_RESULT_KEY));
        final LoopBoundType loopBoundType =
                LoopBoundType.valueOf(json.getString(PARSED_BOUND_TYPE_KEY));

        final String functionName = json.getString(FUNCTION_NAME_KEY);
        final ExtractedCLoop.Types types =
                new ExtractedCLoop.Types(loopType, parseResultType, loopBoundType);

        final ExtractedCLoop extractedCLoop =
                ExtractedCLoop.fromStoredValues(uuid, types, line, posInLine,
                                                numberInFunc, functionName);
        if (loopBoundType == LoopBoundType.MANUALLY_ENTERED) {
            extractedCLoop.setManualInteger(json.getInt(MANUAL_BOUND_KEY));
        }
        return extractedCLoop;
    }

    public static JSONArray fromExtractedLoops(final List<ExtractedCLoop> loops) {
        final JSONArray arr = new JSONArray();
        for (final ExtractedCLoop loop : loops) {
            arr.put(fromExtractedLoop(loop));
        }
        return arr;
    }

    private static void setupParentsAndChildren(final ExtractedCLoop loop,
                                                final JSONObject json,
                                                final List<ExtractedCLoop> loops) {
        if (json.has(PARENT_UUID_KEY)) {
            final String parentUUID = json.getString(PARENT_UUID_KEY);
            for (final ExtractedCLoop possibleParent : loops) {
                if (possibleParent.getUuid().equals(parentUUID)) {
                    loop.setParentLoop(possibleParent);
                    break;
                }
            }
        }
        final JSONArray childrenUUIDs = json.getJSONArray(CHILDREN_UUIDS_KEY);
        for (int i = 0; i < childrenUUIDs.length(); ++i) {
            final String currentChildUUID = childrenUUIDs.getString(i);
            for (final ExtractedCLoop possibleChild : loops) {
                if (possibleChild.getUuid().equals(currentChildUUID)) {
                    loop.addChild(possibleChild);
                }
            }
        }
    }

    public static List<ExtractedCLoop> toExtractedLoops(final JSONArray arr) {
        final List<ExtractedCLoop> loops = new ArrayList<ExtractedCLoop>();
        for (int i = 0; i < arr.length(); ++i) {
            loops.add(toExtractedLoop(arr.getJSONObject(i)));
        }
        for (int i = 0; i < arr.length(); ++i) {
            setupParentsAndChildren(loops.get(i), arr.getJSONObject(i), loops);
        }
        return loops;
    }
}
