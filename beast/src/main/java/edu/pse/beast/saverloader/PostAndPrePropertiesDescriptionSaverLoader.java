/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.saverloader;

import edu.pse.beast.datatypes.propertydescription.FormalPropertiesDescription;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariableList;
import edu.pse.beast.saverloader.StaticSaverLoaders.FormalPropertySaverLoader;
import edu.pse.beast.saverloader.StaticSaverLoaders.SaverLoaderHelper;
import edu.pse.beast.saverloader.StaticSaverLoaders.SymbolicVarListSaverLoader;

import java.util.Map;

/**
 * Implements SaverLoader methods for creating saveStrings from PostAndPrePropertiesDescription objects and vice versa.
 * @author Holger-Desktop
 */
public class PostAndPrePropertiesDescriptionSaverLoader implements SaverLoader {

    @Override
    public String createSaveString(Object obj) {
        SaverLoaderHelper h = new SaverLoaderHelper();
        PostAndPrePropertiesDescription props = (PostAndPrePropertiesDescription) obj;
        StringBuilder saveStr = new StringBuilder();
        saveStr.append(h.getStringForAttr("name", props.getName()));
        saveStr.append(h.getStringForAttr("pre", FormalPropertySaverLoader.createSaveString(
                props.getPrePropertiesDescription())));
        saveStr.append(h.getStringForAttr("post", FormalPropertySaverLoader.createSaveString(
                props.getPostPropertiesDescription())));
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
        return new PostAndPrePropertiesDescription(name, pre, post, varList);
    }
}
