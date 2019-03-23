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

import edu.pse.beast.highlevel.javafx.ChildTreeItem;
import edu.pse.beast.highlevel.javafx.CustomTreeItem;

public class CustomTreeItemAdapter implements JsonSerializer<CustomTreeItem>, JsonDeserializer<CustomTreeItem> {

    @Override
    public CustomTreeItem deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();
        JsonElement element = jsonObject.get("properties");

        if (type.equals(ChildTreeItem.class.getSimpleName())) {
            return context.deserialize(element, ChildTreeItem.class);
        } else {
            System.out.println("you should not serialize parent tree items");
        }
        return null;
    }

    @Override
    public JsonElement serialize(CustomTreeItem src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.add("type", new JsonPrimitive(src.getClass().getSimpleName()));
        result.add("properties", context.serialize(src, src.getClass()));

        return result;
    }

}
