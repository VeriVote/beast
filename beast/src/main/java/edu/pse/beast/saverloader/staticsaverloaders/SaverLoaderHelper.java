package edu.pse.beast.saverloader.staticsaverloaders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Class SaverLoaderHelper.
 *
 * @author Holger Klein
 */
public class SaverLoaderHelper {
    /** BLANK string. */
    private static final String BLANK = " ";

    /** The current save string. */
    private String currentSaveString;

    /**
     * Gets the string for attr.
     *
     * @param attrName
     *            the attr name
     * @param content
     *            the content
     * @return the string for attr
     */
    public String getStringForAttr(final String attrName,
                                   final String content) {
        return attrName.length() + BLANK + attrName + content.length() + BLANK
                + content;
    }

    /**
     * Gets the string for attr.
     *
     * @param attrName
     *            the attr name
     * @param content
     *            the content
     * @return the string for attr
     */
    public String getStringForAttr(final String attrName, final int content) {
        final String contentString = String.valueOf(content);
        return attrName.length() + BLANK + attrName + contentString.length() + BLANK
                + contentString;
    }

    /**
     * Gets the string for attr.
     *
     * @param attrName
     *            the attr name
     * @param content
     *            the content
     * @return the string for attr
     */
    public String getStringForAttr(final String attrName,
                                   final List<String> content) {
        final StringBuilder code = new StringBuilder();
        content.forEach(s -> code.append(s + "\n"));
        return getStringForAttr(attrName, code.toString());
    }

    /**
     * Parses the save string.
     *
     * @param saveFile
     *            the save file
     * @return the map
     */
    public Map<String, String> parseSaveString(final String saveFile) {
        final Map<String, String> attrNameToContent = new HashMap<String, String>();
        currentSaveString = saveFile;
        try {
            while (currentSaveString.length() > 0) {
                int len = getNumberAndRemoveNumberPartFromString();
                final String attr = getAttrFromStringAndRemoveIt(len);
                len = getNumberAndRemoveNumberPartFromString();
                final String cont = getAttrFromStringAndRemoveIt(len);
                attrNameToContent.put(attr, cont);
            }
        } catch (StringIndexOutOfBoundsException ex) {
            // if the string gets loaded from the file it ends with a \n which
            // causes the load algorithm to throw a
            // StringIndexOutOfBoundsException
            // this exception can safely be ignored
        }
        return attrNameToContent;
    }

    /**
     * Gets the attr from string and remove it.
     *
     * @param len
     *            the len
     * @return the attr from string and remove it
     */
    private String getAttrFromStringAndRemoveIt(final int len) {
        final String attr = currentSaveString.substring(0, len);
        currentSaveString = currentSaveString.substring(len);
        return attr;
    }

    /**
     * Gets the number and remove number part from string.
     *
     * @return the number and remove number part from string
     */
    private int getNumberAndRemoveNumberPartFromString() {
        int i = 0;
        while (currentSaveString.charAt(i) != ' ') {
            i++;
        }
        final String numberS = currentSaveString.substring(0, i);
        currentSaveString = currentSaveString.substring(i + 1);
        return Integer.valueOf(numberS);
    }
}
