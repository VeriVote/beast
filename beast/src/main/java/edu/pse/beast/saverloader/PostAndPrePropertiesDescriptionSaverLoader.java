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
import edu.pse.beast.saverloader.StaticSaverLoaders.SymbolicVarListSaverLoader;

/**
 * Implements SaverLoader methods for creating saveStrings from PostAndPrePropertiesDescription objects and vice versa.
 * @author Holger-Desktop
 */
public class PostAndPrePropertiesDescriptionSaverLoader implements SaverLoader{

    public String createSaveString(Object props) throws Exception{
        String created = "";
        String name = "<postAndPrePropsName>\n" + ((PostAndPrePropertiesDescription) props).getName() + "\n</postAndPrePropsName>\n";
        String preProps = "<pre>\n" + FormalPropertySaverLoader.createSaveString(
                ((PostAndPrePropertiesDescription) props).getPrePropertiesDescription()) + "\n</pre>\n";
        String postProps = "<post>\n" + FormalPropertySaverLoader.createSaveString(
                ((PostAndPrePropertiesDescription) props).getPostPropertiesDescription()) + "\n</post>\n";
        String varlist = "<varlist>\n" + SymbolicVarListSaverLoader.createSaveString(
                ((PostAndPrePropertiesDescription) props).getSymVarList()) + "\n</varlist>\n";
        created += name + preProps + postProps + varlist;
        return created;
    }
    
    public Object createFromSaveString(String s) throws Exception{
        String split[] = s.split("\n</postAndPrePropsName>\n");
        String name = split[0].replace("<postAndPrePropsName>\n", "");
        split = split[1].split("\n</pre>\n");
        String preString = split[0].replace("<pre>\n", "");
        FormalPropertiesDescription pre = FormalPropertySaverLoader.createFromSaveString(preString);
        split = split[1].split("\n</post>\n");
        String postString = split[0].replace("<post>\n", "");
        FormalPropertiesDescription post = FormalPropertySaverLoader.createFromSaveString(postString);
        split = split[1].split("\n</varlist>\n");
        String varlistString = split[0].replace("<varlist>\n", "");
        SymbolicVariableList varList = SymbolicVarListSaverLoader.createFromSaveString(varlistString);
        return new PostAndPrePropertiesDescription(name, pre, post, varList);
    }
}
