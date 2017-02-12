/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.saverloader.StaticSaverLoaders;

import edu.pse.beast.datatypes.propertydescription.FormalPropertiesDescription;

/**
 * Implements static methods for creating saveStrings from FormalPropertiesDescription objects and vice versa.
 * Methods are static due to convenience.
 * @author Holger-Desktop
 */
public class FormalPropertySaverLoader {
    public static String createSaveString(FormalPropertiesDescription desc) {
        return StringSaverLoader.createSaveString(desc.getCode());
    }
    
    public static FormalPropertiesDescription createFromSaveString(String desc) {
        return new FormalPropertiesDescription(StringSaverLoader.createFromSaveString(desc));
    }
}
