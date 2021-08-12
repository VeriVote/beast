package edu.pse.beast.api.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.pse.beast.api.codegen.cbmc.SymbolicVariable;
import edu.pse.beast.api.property.FormalPropertyDescription;
import edu.pse.beast.api.property.PropertyDescription;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class PropertyInputOutput {
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

    private static JSONArray fromSymbVarList(final List<SymbolicVariable> vars) {
        final JSONArray arr = new JSONArray();
        for (final SymbolicVariable var : vars) {
            final JSONObject varObj = new JSONObject();
            varObj.put(SYMB_VAR_NAME_KEY, var.getName());
            varObj.put(SYMB_VAR_TYPE_KEY, var.getVarType().toString());
            arr.put(varObj);
        }
        return arr;
    }

    private static JSONObject fromPropertyDescription(final FormalPropertyDescription propDescr) {
        final JSONObject json = new JSONObject();

        json.put(CODE_KEY, propDescr.getCode());

        return json;
    }

    public static void
            storePropertyDescription(final PropertyDescription propDescr,
                                     final File f) throws IOException {
        final JSONObject json = new JSONObject();

        json.put(VERSION_KEY, CURRENT_VERSION);
        json.put(PRE_DESCR_KEY, fromPropertyDescription(propDescr.getPreConditionsDescription()));
        json.put(POST_DESCR_KEY, fromPropertyDescription(propDescr.getPostConditionsDescription()));
        json.put(NAME_KEY, propDescr.getName());
        json.put(SYMB_VAR_KEY, fromSymbVarList(propDescr.getVariables()));
        json.put(PROP_DESCR_UUID_KEY, propDescr.getUuid());

        InputOutputInterface.writeStringToFile(f, json.toString(2));
    }

    private static List<SymbolicVariable> symbolicVarListFromJson(final JSONArray arr) {
        final List<SymbolicVariable> list = new ArrayList<SymbolicVariable>();
        for (int i = 0; i < arr.length(); ++i) {
            final JSONObject symbVarJson = arr.getJSONObject(i);
            final String name = symbVarJson.getString(SYMB_VAR_NAME_KEY);
            final SymbolicVariable.VariableType type =
                    SymbolicVariable.VariableType
                    .valueOf(symbVarJson.getString(SYMB_VAR_TYPE_KEY));
            list.add(new SymbolicVariable(name, type));
        }
        return list;
    }

    private static FormalPropertyDescription propertyDescriptionFromJson(final JSONObject json) {
        final String code = json.getString(CODE_KEY);
        return new FormalPropertyDescription(code);
    }

    public static PropertyDescription loadPropertyDescription(final File f)
            throws JSONException, IOException {
        final JSONObject json = new JSONObject(InputOutputInterface.readStringFromFile(f));
        final JSONArray symbVarArr = json.getJSONArray(SYMB_VAR_KEY);
        final List<SymbolicVariable> symbVars = symbolicVarListFromJson(symbVarArr);
        final FormalPropertyDescription preProp =
                propertyDescriptionFromJson(json.getJSONObject(PRE_DESCR_KEY));
        final FormalPropertyDescription postProp =
                propertyDescriptionFromJson(json.getJSONObject(POST_DESCR_KEY));
        final String name = json.getString(NAME_KEY);
        final String uuid = json.getString(PROP_DESCR_UUID_KEY);
        return new PropertyDescription(uuid, name, symbVars, preProp, postProp);
    }

}
