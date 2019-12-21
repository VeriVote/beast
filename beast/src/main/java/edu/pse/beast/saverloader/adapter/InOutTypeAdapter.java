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

import edu.pse.beast.types.InOutType;
import edu.pse.beast.types.InputType;
import edu.pse.beast.types.OutputType;

public class InOutTypeAdapter implements JsonSerializer<InOutType>, JsonDeserializer<InOutType> {

    @Override
    public InOutType deserialize(JsonElement json, Type typeOf,
                                 JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();
        JsonElement element = jsonObject.get("properties");
        try {
            for (Iterator<InputType> iterator = InputType.getInputTypes().iterator();
                    iterator.hasNext();) {
                InputType inType = (InputType) iterator.next();
                if (inType.getClass().getSimpleName().equals(type)) {
                    return context.deserialize(element,
                                               Class.forName(inType.getClass().getName()));
                }
            }
            for (Iterator<OutputType> iterator = OutputType.getOutputTypes().iterator();
                    iterator.hasNext();) {
                OutputType outType = (OutputType) iterator.next();
                if (outType.getClass().getSimpleName().equals(type)) {
                    return context.deserialize(element,
                                               Class.forName(outType.getClass().getName()));
                }
            }
            return null;
        } catch (ClassNotFoundException cnfe) {
            throw new JsonParseException("Unknown element type: " + type, cnfe);
        }
    }

    @Override
    public JsonElement serialize(InOutType src, Type typeOfSrc,
                                 JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.add("type", new JsonPrimitive(src.getClass().getSimpleName()));
        result.add("properties", context.serialize(src, src.getClass()));
        return result;
    }
}