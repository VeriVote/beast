package edu.pse.beast.saverloader.adapter;

import java.lang.reflect.Type;
import java.util.Iterator;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import edu.pse.beast.propertychecker.Result;

/**
 * The Class ResultAdapter.
 */
public final class ResultAdapter
        implements JsonSerializer<Result>, JsonDeserializer<Result> {
    /** The Constant PROPERTIES. */
    private static final String PROPERTIES = "properties";

    /** The Constant TYPE. */
    private static final String TYPE = "type";

    @Override
    public Result deserialize(final JsonElement json, final Type typeOfT,
                              final JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get(TYPE).getAsString();
        JsonElement element = jsonObject.get(PROPERTIES);
        try {
            for (Iterator<Result> iterator =
                    Result.getResultTypes().iterator();
                    iterator.hasNext();) {
                Result result = iterator.next();
                if (result.getClass().getSimpleName().equals(type)) {
                    return context.deserialize(
                            element,
                            Class.forName(result.getClass().getName())
                            );
                }
            }
            return null;
        } catch (ClassNotFoundException cnfe) {
            throw new JsonParseException("Unknown element type: " + type, cnfe);
        }
    }

    @Override
    public JsonElement serialize(final Result src, final Type typeOfSrc,
                                 final JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.add(TYPE, new JsonPrimitive(src.getClass().getSimpleName()));
        result.add(PROPERTIES, context.serialize(src, src.getClass()));
        return result;
    }
}
