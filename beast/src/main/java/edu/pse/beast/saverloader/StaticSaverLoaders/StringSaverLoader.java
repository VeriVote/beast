package edu.pse.beast.saverloader.StaticSaverLoaders;

/**
 * Implements static methods for creating saveStrings from Strings and vice versa.
 * Replaces certain characters to ensure custom userinputs not
 * Methods are static due to convenience.
 * @author NikolaiLMS
 */
public class StringSaverLoader {

    public static String createFromSaveString(String saveString) {
        return saveString.replace(">>", ">");
    }

    public static String createSaveString(String string) {
        return string.replace(">", ">>");
    }
}
