package edu.pse.beast.saverloader.staticsaverloaders;

import edu.pse.beast.datatypes.propertydescription.FormalPropertiesDescription;

/**
 * Implements static methods for creating saveStrings from
 * FormalPropertiesDescription objects and vice versa. Methods are static for
 * convenience.
 *
 * @author Holger Klein
 */
public final class FormalPropertySaverLoader {
    private FormalPropertySaverLoader() { }

    /**
     * Creates a String from a given FormalPropertiesDescription, that can then be
     * saved to a file and later given to createFromSaveString() to retrieve the
     * saved object.
     *
     * @param desc the FormalPropertiesDescription
     * @return the saveString
     */
    public static String createSaveString(FormalPropertiesDescription desc) {
        return StringSaverLoader.createSaveString(desc.getCode());
    }

    /**
     * Creates a FormalPropertiesDescription object from a given, by
     * createSaveString() generated, saveString
     *
     * @param saveString the saveString
     * @return the FormalPropertiesDescription object
     */
    public static FormalPropertiesDescription createFromSaveString(String saveString) {
        return new FormalPropertiesDescription(StringSaverLoader.createFromSaveString(saveString));
    }
}