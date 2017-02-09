package edu.pse.beast.saverloader;

/**
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
