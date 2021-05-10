package edu.pse.beast.api.savingloading;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar;
import edu.pse.beast.datatypes.propertydescription.FormalPropertiesDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;

public class PreAndPostPropertySaverLoader {
	private static final int CURRENT_VERSION = 1;

	private static final String VERSION_KEY = "version";
	private static final String CODE_KEY = "code";
	private static final String PRE_DESCR_KEY = "pre_descr";
	private static final String POST_DESCR_KEY = "post_descr";
	private static final String NAME_KEY = "name";
	private static final String SYMB_VAR_KEY = "symbolic_variables";
	private static final String SYMB_VAR_NAME_KEY = "symb_var_name";
	private static final String SYMB_VAR_TYPE_KEY = "symb_var_type";
	private static final String PROP_DESCR_UUID_KEY = "prop_descr_uuid";

	private static JSONArray fromSymbVarList(List<SymbolicCBMCVar> vars) {
		JSONArray arr = new JSONArray();
		for (SymbolicCBMCVar var : vars) {
			JSONObject varObj = new JSONObject();
			varObj.put(SYMB_VAR_NAME_KEY, var.getName());
			varObj.put(SYMB_VAR_TYPE_KEY, var.getVarType().toString());
			arr.put(varObj);
		}
		return arr;
	}

	private static JSONObject fromPropertyDescription(
			FormalPropertiesDescription propDescr) {
		JSONObject json = new JSONObject();

		json.put(CODE_KEY, propDescr.getCode());

		return json;
	}

	public static void storePreAndPostConditionDescription(
			PreAndPostConditionsDescription propDescr, File f)
			throws IOException {
		JSONObject json = new JSONObject();

		json.put(VERSION_KEY, CURRENT_VERSION);
		json.put(PRE_DESCR_KEY, fromPropertyDescription(
				propDescr.getPreConditionsDescription()));
		json.put(POST_DESCR_KEY, fromPropertyDescription(
				propDescr.getPostConditionsDescription()));
		json.put(NAME_KEY, propDescr.getName());
		json.put(SYMB_VAR_KEY, fromSymbVarList(propDescr.getCbmcVariables()));
		json.put(PROP_DESCR_UUID_KEY, propDescr.getUuid());

		SavingLoadingInterface.writeStringToFile(f, json.toString());
	}

	private static List<SymbolicCBMCVar> symbolicVarListFromJson(
			JSONArray arr) {
		List<SymbolicCBMCVar> list = new ArrayList<SymbolicCBMCVar>();
		for (int i = 0; i < arr.length(); ++i) {
			JSONObject symbVarJson = arr.getJSONObject(i);
			String name = symbVarJson.getString(SYMB_VAR_NAME_KEY);
			SymbolicCBMCVar.CBMCVarType type = SymbolicCBMCVar.CBMCVarType
					.valueOf(symbVarJson.getString(SYMB_VAR_TYPE_KEY));
			list.add(new SymbolicCBMCVar(name, type));
		}
		return list;
	}

	private static FormalPropertiesDescription propertyDescriptionFromJson(
			JSONObject json) {
		String code = json.getString(CODE_KEY);
		FormalPropertiesDescription propDescr = new FormalPropertiesDescription(
				code);
		return propDescr;
	}

	public static PreAndPostConditionsDescription loadPreAndPostConditionDescription(
			File f) throws JSONException, IOException {
		JSONObject json = new JSONObject(
				SavingLoadingInterface.readStringFromFile(f));
		JSONArray symbVarArr = json.getJSONArray(SYMB_VAR_KEY);
		List<SymbolicCBMCVar> symbVars = symbolicVarListFromJson(symbVarArr);
		FormalPropertiesDescription preProp = propertyDescriptionFromJson(
				json.getJSONObject(PRE_DESCR_KEY));
		FormalPropertiesDescription postProp = propertyDescriptionFromJson(
				json.getJSONObject(POST_DESCR_KEY));
		String name = json.getString(NAME_KEY);
		String uuid = json.getString(PROP_DESCR_UUID_KEY);
		return new PreAndPostConditionsDescription(uuid, name, symbVars,
				preProp, postProp);
	}

}
