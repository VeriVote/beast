/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toBeImplemented;

import edu.pse.beast.datatypes.propertydescription.FormalPropertiesDescription;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariableList;

/**
 *
 * @author Holger-Desktop
 */
public class FormalPropertySaverLoader {
    public static String createSaveString(FormalPropertiesDescription desc) {
        return desc.getCode();        
    }
    
    public static FormalPropertiesDescription createFromSaveString(String desc) {
        return new FormalPropertiesDescription(desc);
    }
}
