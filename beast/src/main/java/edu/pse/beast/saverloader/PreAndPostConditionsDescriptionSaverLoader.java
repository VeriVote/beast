/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.saverloader;

import java.util.Map;

import edu.pse.beast.datatypes.propertydescription.FormalPropertiesDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariableList;
import edu.pse.beast.saverloader.StaticSaverLoaders.FormalPropertySaverLoader;
import edu.pse.beast.saverloader.StaticSaverLoaders.SaverLoaderHelper;
import edu.pse.beast.saverloader.StaticSaverLoaders.SymbolicVarListSaverLoader;

/**
 * Implements SaverLoader methods for creating saveStrings from
 * PreAndPostConditionsDescription objects and vice versa.
 * @author Holger-Desktop
 */
public class PreAndPostConditionsDescriptionSaverLoader implements SaverLoader {

    @Override
    public String createSaveString(Object obj) {
        SaverLoaderHelper h = new SaverLoaderHelper();
        PreAndPostConditionsDescription props = (PreAndPostConditionsDescription) obj;
        StringBuilder saveStr = new StringBuilder();
        saveStr.append(h.getStringForAttr("name", props.getName()));
        saveStr.append(h.getStringForAttr("pre", FormalPropertySaverLoader.createSaveString(
                props.getPreConditionsDescription())));
        saveStr.append(h.getStringForAttr("post", FormalPropertySaverLoader.createSaveString(
                props.getPostConditionsDescription())));
        saveStr.append(h.getStringForAttr("varlist",
                SymbolicVarListSaverLoader.createSaveString(props.getSymVarList())));
        return saveStr.toString();
    }

    @Override
    public Object createFromSaveString(String s) throws ArrayIndexOutOfBoundsException {
        Map<String, String> m = new SaverLoaderHelper().parseSaveString(s);
        String name = m.get("name");
        String preString = m.get("pre");
        FormalPropertiesDescription pre = FormalPropertySaverLoader.createFromSaveString(preString);
        String postString = m.get("post");
        FormalPropertiesDescription post = FormalPropertySaverLoader.createFromSaveString(postString);
        String varlistString = m.get("varlist");
        SymbolicVariableList varList = SymbolicVarListSaverLoader.createFromSaveString(varlistString);
        return new PreAndPostConditionsDescription(name, pre, post, varList);
    }
}
