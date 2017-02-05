/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toBeImplemented;

import edu.pse.beast.datatypes.propertydescription.FormalPropertiesDescription;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariableList;
import edu.pse.beast.saverloader.SymbolicVariableListSaverLoader;

/**
 *
 * @author Holger-Desktop
 */
public class PostAndPrePropertiesDescriptionSaverLoader {
    public static String createSaveString(PostAndPrePropertiesDescription props) {
        String created = "";
        String name = "<name>\n" + props.getName() + "\n</name>\n";
        String preProps = "<pre>\n" + FormalPropertySaverLoader.createSaveString(props.getPrePropertiesDescription()) + "\n</pre>\n";
        String postProps = "<post>\n" + FormalPropertySaverLoader.createSaveString(props.getPostPropertiesDescription()) + "\n</post>\n";
        String varlist = "<varlist>\n" + SymbolicVarListSaverLoader.createSaveString(props.getSymVarList()) + "\n</varlist>\n";
        created += name + preProps + postProps + varlist;
        return created;
    }
    
    public static PostAndPrePropertiesDescription createFromSaveString(String s) {
        String split[] = s.split("\n</name>\n");
        String name = split[0].replace("<name>\n", "");
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
