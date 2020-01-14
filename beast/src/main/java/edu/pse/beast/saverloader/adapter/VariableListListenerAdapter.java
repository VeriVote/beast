package edu.pse.beast.saverloader.adapter;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import edu.pse.beast.booleanexpeditor.booleanexpcodearea.errorfinder.FormalExpErrorFinderTreeListener;
import edu.pse.beast.datatypes.propertydescription.VariableListListener;

public class VariableListListenerAdapter
        implements JsonSerializer<VariableListListener>,
                   JsonDeserializer<VariableListListener> {

    @Override
    public VariableListListener deserialize(JsonElement json, Type typeOfT,
                                            JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();
        JsonElement element = jsonObject.get("properties");
        try {
            if (FormalExpErrorFinderTreeListener.class.getSimpleName().equals(type)) {
                return context.deserialize(
                        element,
                        Class.forName(FormalExpErrorFinderTreeListener.class.getName()));
            } else {
                System.err.println("cant deserialize variable list listener");
            }
            return null;
        } catch (ClassNotFoundException cnfe) {
            throw new JsonParseException("Unknown element type: " + type, cnfe);
        }
    }

    @Override
    public JsonElement serialize(VariableListListener src, Type typeOfSrc,
                                 JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.add("type", new JsonPrimitive(src.getClass().getSimpleName()));
        result.add("properties", context.serialize(src, src.getClass()));
        return result;
    }
}
