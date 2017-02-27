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

    /**
     * Creates a String from a given FormalPropertiesDescription, that can then be saved to a file and later given to
     * createFromSaveString() to retrieve the saved object.
     * @param desc the FormalPropertiesDescription
     * @return the saveString
     */
    public static String createSaveString(FormalPropertiesDescription desc) {
        return StringSaverLoader.createSaveString(desc.getCode());
    }

    /**
     * Creates a FormalPropertiesDescription object from a given, by createSaveString() generated, saveString
     * @param saveString the saveString
     * @return the FormalPropertiesDescription object
     */
    public static FormalPropertiesDescription createFromSaveString(String saveString) {
        return new FormalPropertiesDescription(StringSaverLoader.createFromSaveString(saveString));
    }
}
