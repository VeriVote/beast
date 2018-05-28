package edu.pse.beast.types.saverLoader;

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

import edu.pse.beast.types.CommonHelpMethods;
import edu.pse.beast.types.InputType;

public class CommonHelpMethodsAdapter implements JsonSerializer<CommonHelpMethods>, JsonDeserializer<CommonHelpMethods> {

	@Override
	public CommonHelpMethods deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();
        JsonElement element = jsonObject.get("properties");

        try {
        	
        	for (Iterator<CommonHelpMethods> iterator = CommonHelpMethods.getImplementations().iterator(); iterator.hasNext();) {
        		CommonHelpMethods implementation = (CommonHelpMethods) iterator.next();
				if(implementation.getClass().getSimpleName().equals(type)) {
					return context.deserialize(element, Class.forName(implementation.getClass().getName()));
				}
			}
        	
        	return null;
        	
        } catch (ClassNotFoundException cnfe) {
            throw new JsonParseException("Unknown element type: " + type, cnfe);
        }

	}

	@Override
	public JsonElement serialize(CommonHelpMethods src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject result = new JsonObject();
		result.add("type", new JsonPrimitive(src.getClass().getSimpleName()));
		result.add("properties", context.serialize(src, src.getClass()));
		
		return result;
	}

}
