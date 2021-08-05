package edu.pse.beast.api.savingloading;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar;
import edu.pse.beast.api.descr.property_description.FormalPropertiesDescription;
import edu.pse.beast.api.descr.property_description.PreAndPostConditionsDescription;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
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

    private static JSONArray fromSymbVarList(final List<SymbolicCBMCVar> vars) {
        final JSONArray arr = new JSONArray();
        for (final SymbolicCBMCVar var : vars) {
            final JSONObject varObj = new JSONObject();
            varObj.put(SYMB_VAR_NAME_KEY, var.getName());
            varObj.put(SYMB_VAR_TYPE_KEY, var.getVarType().toString());
            arr.put(varObj);
        }
        return arr;
    }

    private static JSONObject fromPropertyDescription(final FormalPropertiesDescription propDescr) {
        final JSONObject json = new JSONObject();

        json.put(CODE_KEY, propDescr.getCode());

        return json;
    }

    public static void
            storePreAndPostConditionDescription(final PreAndPostConditionsDescription propDescr,
                                                final File f) throws IOException {
        final JSONObject json = new JSONObject();

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

    private static List<SymbolicCBMCVar> symbolicVarListFromJson(final JSONArray arr) {
        final List<SymbolicCBMCVar> list = new ArrayList<SymbolicCBMCVar>();
        for (int i = 0; i < arr.length(); ++i) {
            final JSONObject symbVarJson = arr.getJSONObject(i);
            final String name = symbVarJson.getString(SYMB_VAR_NAME_KEY);
            final SymbolicCBMCVar.CBMCVarType type =
                    SymbolicCBMCVar.CBMCVarType
                    .valueOf(symbVarJson.getString(SYMB_VAR_TYPE_KEY));
            list.add(new SymbolicCBMCVar(name, type));
        }
        return list;
    }

    private static FormalPropertiesDescription propertyDescriptionFromJson(final JSONObject json) {
        final String code = json.getString(CODE_KEY);
        return new FormalPropertiesDescription(code);
    }

    public static PreAndPostConditionsDescription loadPreAndPostConditionDescription(final File f)
            throws JSONException, IOException {
        final JSONObject json = new JSONObject(SavingLoadingInterface.readStringFromFile(f));
        final JSONArray symbVarArr = json.getJSONArray(SYMB_VAR_KEY);
        final List<SymbolicCBMCVar> symbVars = symbolicVarListFromJson(symbVarArr);
        final FormalPropertiesDescription preProp =
                propertyDescriptionFromJson(json.getJSONObject(PRE_DESCR_KEY));
        final FormalPropertiesDescription postProp =
                propertyDescriptionFromJson(json.getJSONObject(POST_DESCR_KEY));
        final String name = json.getString(NAME_KEY);
        final String uuid = json.getString(PROP_DESCR_UUID_KEY);
        return new PreAndPostConditionsDescription(uuid, name, symbVars, preProp, postProp);
    }

}
